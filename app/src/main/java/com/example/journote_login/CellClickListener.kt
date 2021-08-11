package com.example.journote_login

import com.example.journote_login.items.FolderItem
import com.example.journote_login.items.PlanItem

interface CellClickListener {
    fun onCellClickListener(data: FolderItem) {}
    fun onCellClickListener(data: PlanItem){}
}
