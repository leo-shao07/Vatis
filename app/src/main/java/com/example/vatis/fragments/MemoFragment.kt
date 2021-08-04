package com.example.vatis.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vatis.CellClickListener
import com.example.vatis.adapters.MemoItemAdapter
import com.example.vatis.items.MemoSubItem
import com.example.vatis.R
import com.example.vatis.items.MemoItem
import kotlinx.android.synthetic.main.fragment_memo.view.*


class MemoFragment : Fragment(), CellClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_memo, container, false)
        view.memo_list.layoutManager = LinearLayoutManager(activity)

        val memoList = ArrayList<MemoItem>()
        val memoSubListA = ArrayList<MemoSubItem>()
        val memoSubListB = ArrayList<MemoSubItem>()

        // use dummy data for now
        memoSubListA.add(MemoSubItem("spot_1", "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"))
        memoSubListA.add(MemoSubItem("spot_2", "BBBBBBBBBBBBBBBBBBBBBBBBBBBB"))
        memoSubListA.add(MemoSubItem("spot_3", "CCCCCCCCCCCCCCCCCCCCCCCCC"))
        memoList.add(MemoItem("Day 1", memoSubListA))

        memoSubListB.add(MemoSubItem("spot_4", "KKKKKKKKKKKKKKKKKKKKKKKKKK"))
        memoSubListB.add(MemoSubItem("spot_5", "74147414741474147414741474147414"))
        memoSubListB.add(MemoSubItem("spot_6", "老子直接在你留言區跳繩"))
        memoSubListB.add(MemoSubItem("spot_7", "嘿嘿嘿嘿"))
        memoList.add(MemoItem("Day 2", memoSubListB))

        // TODO: add OnSnapshotListener to capture Plan order change

        view.memo_list.adapter = MemoItemAdapter(memoList, this)
        return view
    }

    fun fetchMemoList() {
        // TODO: write function to get data from FireStore
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCellClickListener(data: MemoSubItem) {
        Toast.makeText(this.context, "memo_sub_item clicked", Toast.LENGTH_SHORT).show()
        // var oldContent = view.memo_content_text.text
        val editDialogFragment = MemoEditDialogFragment(data)
        editDialogFragment.show(childFragmentManager, "MemoEditDialog")
    }


}