package com.example.journote_login

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.app.NotificationCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.journote_login.databinding.FragmentFolderBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.functions.FirebaseFunctions
import com.google.firebase.ktx.Firebase
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.folder_row.view.*
import kotlinx.android.synthetic.main.fragment_folder.*
import java.lang.Exception
import kotlinx.serialization.*
import kotlinx.serialization.json.*
import kotlinx.serialization.builtins.*

class FolderFragment : Fragment() {
    private val db = Firebase.firestore

    companion object {
        val TAG = "FolderFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentFolderBinding>(
            inflater, R.layout.fragment_folder, container, false
        )

        verifyUserIsLoggedIn()

        binding.folderToolbar.inflateMenu(R.menu.nav_menu)
        binding.folderToolbar.setOnClickListener {
            findNavController().navigate(R.id.action_folderFragment_to_loginFragment)
        }
        binding.folderToolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_profile_button -> {
                    findNavController().navigate(R.id.action_folderFragment_to_userFragment)
                }
            }
            false
        }


        binding.createFolderFab.setOnClickListener {
            var dialog = AddFolderDialogFragment()
            dialog.show(childFragmentManager, "AddFolderDialog")
        }


        return binding.root
    }

    private fun verifyUserIsLoggedIn() {
        val uid = FirebaseAuth.getInstance().uid
        if (uid == null) {
            findNavController()?.navigate(R.id.action_folderFragment_to_titleFragment)
        } else {
            Log.d(TAG, "Successfully login with uid: $uid")
            fetchUserFolder()
        }
    }

    private fun fetchUserFolder() {
        val user = Firebase.auth.currentUser
        val adapter = GroupAdapter<GroupieViewHolder>()
        user?.let {
            val userEmail = user.email
            val userPath = hashMapOf(
                "docPath" to "users/" + userEmail
            )
            FirebaseFunctions.getInstance()
                .getHttpsCallable("getSubCollections")
                .call(userPath)
                .addOnSuccessListener { folderCollections ->
                    if (folderCollections != null) {
                        Log.d(TAG, folderCollections.data.toString())
                        val folder = folderCollections.data as HashMap<String, ArrayList<String>>
                        for (i in folder.get("collections")!!) {
                            println(i)
                            adapter.add(FolderItem(i))
                        }
                        folder_recyclerView.adapter = adapter

                    } else {
                        Log.d(TAG, "No Folder")
                    }
                }

                .addOnFailureListener {
                    Log.wtf("FF", it)
                }

        }

    }
}
class FolderItem(val folder: String) : Item<GroupieViewHolder>() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.folder_button.text = folder

    }

    override fun getLayout(): Int {
        return R.layout.folder_row
    }
}
