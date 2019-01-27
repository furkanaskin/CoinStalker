package com.faskn.coinstalker.model

class CommentsDTO {

    constructor() //empty for firebase

    constructor(messageText: String) {
        text = messageText
    }

    var text: String? = null
    var timestamp: Long = System.currentTimeMillis()
}