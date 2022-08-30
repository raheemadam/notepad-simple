package xyz.teamgravity.notepad.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import xyz.teamgravity.notepad.data.local.constant.NoteDatabaseConst

@Entity(tableName = NoteDatabaseConst.TABLE_NOTE)
data class NoteEntity(

    @PrimaryKey(autoGenerate = true)
    val _id: Long,

    val title: String,
    val body: String,

    val createdTime: Long,
    val editedTime: Long,
)