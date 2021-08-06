package com.example.vatis

import com.example.vatis.items.BookmarkItem
import com.example.vatis.items.MemoSubItem

interface CellClickListener {
    fun onCellClickListener(data: BookmarkItem) {}
    fun onCellClickListener(data: MemoSubItem) {}

}