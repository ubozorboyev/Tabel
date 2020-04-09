package com.example.tabel.model

data class LoginResponse(
    val status_auth: String,
    val userid: Int,
    val username: String
)

/// WOrkers Model

data class PageThreeModel(
    val status_auth: String,
    val userid: Int,
    val username: String,
    val workers: List<Worker>
)

data class Worker(
    val i: Int,
    val workerid: Int,
    val workerimg: String,
    val workername: String,
    val workerposition: String,
    val workertel: String
)


/// Day add Model

data class ToDayAddModel(
    val status_auth: String,
    val userid: Int,
    val username: String,
    val todayadded: List<YesterdayaddedData>
)

data class YesterDayAddModel(
    val status_auth: String,
    val userid: Int,
    val username: String,
    val yesterdayadded: List<YesterdayaddedData>
)

data class YesterdayaddedData(
    val extend: String,
    val hour: Int,
    val objectname: String,
    val workername: String
)

//// object model

data class ObjectModel(
    val objects: List<Object>,
    val status_auth: String,
    val userid: Int,
    val username: String
)

data class Object(
    val i: Int,
    val objectid: Int,
    val objectname: String
)

////     Hours data

data class HoursModel(
    val hours: List<Hour>,
    val status_auth: String,
    val userid: Int,
    val username: String
)

data class Hour(
    val hour: Int
)

data class PostResponse(
    val status_auth: String,
    val status_save: List<StatusSave>,
    val userid: Int,
    val username: String
)

data class StatusSave(
    val status_add_db: String,
    val workerid: String
)


data class LastAddedModel(
    val lastadded: List<Lastadded>,
    val status_auth: String,
    val userid: Int,
    val username: String
)

data class Lastadded(
    val hour: Int,
    val objectid: Int,
    val worker_id: Int
)