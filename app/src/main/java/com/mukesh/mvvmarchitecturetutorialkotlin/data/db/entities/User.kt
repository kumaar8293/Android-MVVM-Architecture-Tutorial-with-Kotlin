package com.mukesh.mvvmarchitecturetutorialkotlin.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

const val CURRENT_USER_ID = 0

@Entity(tableName = "user_table")
data class User(
    var created_at: String? = null,
    var email: String? = null,
    var email_verified_at: String? = null,
    var id: Int? = null,
    var name: String? = null,
    var updated_at: String? = null
) {
    @PrimaryKey(autoGenerate = false)
    var uid: Int = CURRENT_USER_ID
}