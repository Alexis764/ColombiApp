package com.project.colombiapp.core.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.project.colombiapp.core.Categories

@Entity
data class FinanceEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("id")
    val id: Int = 0,

    @ColumnInfo("isIncome") val isIncome: Boolean,

    @ColumnInfo("amount") val amount: Int,

    @ColumnInfo("category") val category: Categories
)
