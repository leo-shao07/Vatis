package com.example.journote_login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.example.journote_login.databinding.FragmentAddFolderDialogBinding


class AddFolderDialogFragment : DialogFragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentAddFolderDialogBinding>(
            inflater, R.layout.fragment_add_folder_dialog, container, false
        )

        binding.addFolderToolbar.inflateMenu(R.menu.dialog_menu)
        binding.addFolderToolbar.setOnMenuItemClickListener{
            when(it.itemId){
                R.id.dialog_menu_clear_button -> {
                    dismiss()
                }
            }
            false
        }

        return binding.root
    }
    override fun onResume() {
        super.onResume()
        val params: ViewGroup.LayoutParams = dialog!!.window!!.attributes
        params.width = WindowManager.LayoutParams.MATCH_PARENT
        params.height = WindowManager.LayoutParams.WRAP_CONTENT
        dialog!!.window!!.attributes = params as WindowManager.LayoutParams
    }
}