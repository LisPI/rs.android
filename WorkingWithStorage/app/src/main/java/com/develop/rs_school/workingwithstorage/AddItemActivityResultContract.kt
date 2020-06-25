package com.develop.rs_school.workingwithstorage

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import com.develop.rs_school.workingwithstorage.database.Friend

const val intentKeyFriend = "FRIEND"

class AddItemActivityResultContract : ActivityResultContract<Void?, Friend?>() {

    override fun createIntent(context: Context, input: Void?): Intent {
        return Intent(context, AddItemActivity::class.java)
    }
    override fun parseResult(resultCode: Int, intent: Intent?): Friend? {
        val data = intent?.getParcelableExtra<Friend>(intentKeyFriend)
        return if (resultCode == Activity.RESULT_OK && data != null) data
        else null
    }
}