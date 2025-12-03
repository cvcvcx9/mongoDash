/**
 * MongoDash User Behavior Tracker
 * 
 * 사용법:
 * <script src="/js/tracker.js"></script>
 * <script>
 *   const tracker = new MongoDashTracker({
 *     apiEndpoint: 'http://localhost:8080/api/track'
 *   });
 *   tracker.init();
 * </script>
 */

class MongoDashTracker {
    constructor(config) {
        this.apiEndpoint = config.apiEndpoint || 'http://api.cvcvcx9.org:8081/api/track';
        this.storageKey = 'md_session_id';
        this.sessionId = this.restoreSession();
        this.eventBuffer = [];
        this.bufferLimit = 10; // 10개 쌓이면 자동 전송
        this.flushInterval = 5000; // 5초마다 자동 전송
        this.lastMouseTime = 0;
        this.viewportWidth = window.innerWidth;
        this.viewportHeight = window.innerHeight;
        this.lastViewport = { w: this.viewportWidth, h: this.viewportHeight };
        this.deviceType = this.getDeviceType();
        this.pageStartTime = Date.now();
    }

    init() {
        console.log('MongoDash Tracker Initialized');
        this.startSession();
        this.setupEventListeners();
        
        // 주기적으로 데이터 전송
        setInterval(() => this.flush(), this.flushInterval);
    }

    restoreSession() {
        try {
            const stored = localStorage.getItem(this.storageKey);
            if (stored) return stored;
        } catch (e) {
            console.warn('Cannot access localStorage', e);
        }
        const id = this.generateUUID();
        try {
            localStorage.setItem(this.storageKey, id);
        } catch (e) {
            console.warn('Cannot persist sessionId', e);
        }
        return id;
    }

    // 1. 세션 시작 (방문자 정보 전송)
    async startSession() {
        const sessionData = {
            sessionId: this.sessionId,
            referrer: document.referrer,
            referrerDomain: this.getDomain(document.referrer),
            landingUrl: window.location.href,
            deviceType: this.deviceType,
            userAgent: navigator.userAgent,
            viewportWidth: this.viewportWidth,
            viewportHeight: this.viewportHeight
        };

        try {
            await fetch(`${this.apiEndpoint}/session`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(sessionData)
            });
            console.log('Session started:', this.sessionId);
        } catch (e) {
            console.error('Failed to start session', e);
        }
    }

    // 2. 이벤트 리스너 등록
    setupEventListeners() {
        // 클릭 이벤트
        document.addEventListener('click', (e) => {
            this.trackEvent('CLICK', {
                x: e.pageX,
                y: e.pageY,
                targetSelector: this.getSelector(e.target)
            });
        });

        // 마우스 이동 (데스크톱에서만, Throttling: 0.5초마다 수집)
        if (this.deviceType === 'desktop') {
            document.addEventListener('mousemove', (e) => {
                const now = Date.now();
                if (now - this.lastMouseTime > 500) {
                    this.trackEvent('MOVE', {
                        x: e.pageX,
                        y: e.pageY
                    });
                    this.lastMouseTime = now;
                }
            });
        }

        // 페이지 이탈 시 남은 데이터 전송
        window.addEventListener('beforeunload', () => {
            this.trackDwell();
            this.flush();
        });

        // 탭 숨김 시 체류 시간 기록
        document.addEventListener('visibilitychange', () => {
            if (document.visibilityState === 'hidden') {
                this.trackDwell();
                this.flush();
            } else if (document.visibilityState === 'visible') {
                this.pageStartTime = Date.now();
            }
        });

        // 뷰포트 변경 시 세션 업데이트
        window.addEventListener('resize', () => {
            this.viewportWidth = window.innerWidth;
            this.viewportHeight = window.innerHeight;
            const { w, h } = this.lastViewport;
            if (w === this.viewportWidth && h === this.viewportHeight) return;
            this.lastViewport = { w: this.viewportWidth, h: this.viewportHeight };
            this.updateSessionViewport();
        });
    }

    // 3. 이벤트 버퍼에 추가
    trackEvent(type, data = {}) {
        const event = {
            sessionId: this.sessionId,
            pageUrl: window.location.href,
            eventType: type,
            timestamp: new Date().toISOString(),
            ...data
        };

        // 좌표 정규화 저장
        if (typeof event.x === 'number' && typeof event.y === 'number') {
            event.viewportWidth = this.viewportWidth;
            event.viewportHeight = this.viewportHeight;
            event.xRatio = this.viewportWidth ? event.x / this.viewportWidth : null;
            event.yRatio = this.viewportHeight ? event.y / this.viewportHeight : null;
            delete event.x;
            delete event.y;
        }

        this.eventBuffer.push(event);

        if (this.eventBuffer.length >= this.bufferLimit) {
            this.flush();
        }
    }

    // 4. 데이터 전송 (Flush)
    async flush() {
        if (this.eventBuffer.length === 0) return;

        const eventsToSend = [...this.eventBuffer];
        this.eventBuffer = []; // 버퍼 비우기

        try {
            // sendBeacon은 페이지 이탈 시에도 안정적으로 전송됨
            const blob = new Blob([JSON.stringify(eventsToSend)], { type: 'application/json' });
            navigator.sendBeacon(`${this.apiEndpoint}/events`, blob);
            
            // fetch fallback (sendBeacon이 안될 경우를 대비해 fetch도 고려 가능하지만 여기선 생략)
            console.log(`Flushed ${eventsToSend.length} events`);
        } catch (e) {
            console.error('Failed to flush events', e);
            // 실패 시 다시 버퍼에 넣는 로직이 필요할 수 있음
        }
    }

    // 세션 정보 업데이트(뷰포트 변경)
    async updateSessionViewport() {
        try {
            await fetch(`${this.apiEndpoint}/session`, {
                method: 'PATCH',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({
                    sessionId: this.sessionId,
                    viewportWidth: this.viewportWidth,
                    viewportHeight: this.viewportHeight
                })
            });
        } catch (e) {
            console.warn('Failed to update session viewport', e);
        }
    }

    // 현재 페이지 체류 시간 기록
    trackDwell() {
        const now = Date.now();
        const durationMs = now - this.pageStartTime;
        if (durationMs > 0) {
            this.trackEvent('DWELL', { durationMs });
        }
        this.pageStartTime = now;
    }

    // --- 유틸리티 함수들 ---

    generateUUID() {
        return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
            var r = Math.random() * 16 | 0, v = c == 'x' ? r : (r & 0x3 | 0x8);
            return v.toString(16);
        });
    }

    getDeviceType() {
        const width = window.innerWidth;
        if (width < 768) return 'mobile';
        if (width < 1024) return 'tablet';
        return 'desktop';
    }

    getDomain(url) {
        if (!url) return null;
        try {
            return new URL(url).hostname;
        } catch (e) {
            return null;
        }
    }

    getSelector(element) {
        if (!element) return null;
        if (element.id) return '#' + element.id;
        if (element.className) return '.' + element.className.split(' ').join('.');
        return element.tagName.toLowerCase();
    }
}

if (typeof window !== 'undefined') {
    window.MongoDashTracker = MongoDashTracker;
}
