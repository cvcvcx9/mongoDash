package com.cvcvcx.MongoDash.repository

import com.cvcvcx.MongoDash.entity.Event
import org.springframework.data.mongodb.repository.MongoRepository

interface EventRepository : MongoRepository<Event,String>