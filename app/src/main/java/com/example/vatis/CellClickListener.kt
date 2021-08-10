package com.example.vatis

import com.example.vatis.items.BookmarkItem
import com.example.vatis.items.MemoSubItem
import com.example.vatis.items.RecommendationItem

interface CellClickListener {
    fun onCellClickListener(data: BookmarkItem) {}
    fun onCellClickListener(data: MemoSubItem) {}
    fun onCellClickListener(data: RecommendationItem) {}

}