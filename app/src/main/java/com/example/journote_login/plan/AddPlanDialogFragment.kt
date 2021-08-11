package com.example.journote_login.plan
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.example.journote_login.R
import com.example.journote_login.user.UserActivity
import kotlinx.android.synthetic.main.fragment_add_plan_dialog.view.*


class AddPlanDialogFragment(val folderName: String) : DialogFragment() {

    private val db = Firebase.firestore

    companion object {
        val TAG = "AddPlanDialogFragment"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add_plan_dialog, container, false)

        view.add_plan_toolbar.inflateMenu(R.menu.dialog_menu)
        view.add_plan_toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.dialog_menu_clear_button -> {
                    dismiss()
                }
            }
            false
        }

        view.add_plan_button.setOnClickListener {
            val planNameText = view.add_plan_name_text_input.text.toString()
            val planDays = Integer.parseInt(view.add_plan_edit_days.text.toString())
            val timeString = System.currentTimeMillis().toString()
            createPlanWithImportCode(timeString, folderName ,planNameText, planDays)
        }

        return view
    }

    override fun onResume() {
        super.onResume()
        val params: ViewGroup.LayoutParams = dialog!!.window!!.attributes
        params.width = WindowManager.LayoutParams.MATCH_PARENT
        params.height = WindowManager.LayoutParams.MATCH_PARENT
        dialog!!.window!!.attributes = params as WindowManager.LayoutParams
        dialog!!.window!!.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
    }


    private fun addPlan(folderName: String, planName: String, planDays: Number, importCode: String) {

        if (planName.isEmpty()) {
            Toast.makeText(activity, "Please enter the Folder Name", Toast.LENGTH_SHORT).show()
            return
        }
        val user = Firebase.auth.currentUser
        val userEmail = user?.email

        val createPlanData = hashMapOf(
            "dayCount" to planDays,
            "importCode" to importCode,
            "setOff" to true

        )

        if (userEmail != null) {
            db.collection("users").document(userEmail).collection(folderName).document(planName)
                .set(createPlanData)
                .addOnSuccessListener {
                    Log.d(TAG, "Plan successfully written!")
                    dismiss()
                }
                .addOnFailureListener { e ->
                    Log.w(TAG, "Error adding plan", e)
                }
        }

    }
    private fun createPlanWithImportCode(timeString: String, folderName: String, planName: String, planDays: Number){
        val user = Firebase.auth.currentUser
        val userEmail = user?.email
        val docRef = userEmail?.let { it1 -> db.collection("users").document(it1) }
        if (docRef != null) {
            docRef.get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        Log.d(TAG, "DocumentSnapshot data: ${document.data}")
                        val username = document.data?.get("name")
                        val usernameString = username.toString().filterNot { it.isWhitespace() }
                        val importCode = usernameString+timeString
                        Log.d(TAG, "importCode:"+importCode)

                        addPlan(folderName, planName, planDays, importCode)

                    } else {
                        Log.d(TAG, "No such document")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d(TAG, "get failed with ", exception)
                }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }


}