package com.samsung.android.sfdc

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import com.samsung.android.sfdc.Api_pkg.Auth
import com.samsung.android.sfdc.Case_pkg.Cases
import com.samsung.android.sfdc.Comment_pkg.Comments
import com.samsung.android.sfdc.Report_pkg.Reports


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val authbutton = findViewById<Button>(R.id.Start)
        authbutton.setOnClickListener {
            gotoAuth(this)
        }
        val caseButton = findViewById<Button>(R.id.caseApi)
        caseButton.setOnClickListener {
            gotoCase(this)
        }
        val reportButton = findViewById<Button>(R.id.reportApi)
        reportButton.setOnClickListener {
            gotoReport(this)
        }
        val commentButton = findViewById<Button>(R.id.commentsApi)
        commentButton.setOnClickListener {
            gotoComment(this)
        }
    }
}

fun gotoAuth(mainActivity: MainActivity) {
    val context = mainActivity.applicationContext
    val intent = Intent(context, Auth::class.java)
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_MULTIPLE_TASK)
    startActivity(context, intent, null)
}


fun gotoCase(mainActivity: MainActivity) {
    val context = mainActivity.applicationContext
    val intent = Intent(context, Cases::class.java)
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_MULTIPLE_TASK)
    startActivity(context, intent, null)
}

fun gotoReport(mainActivity: MainActivity) {
    val context = mainActivity.applicationContext
    val intent = Intent(context, Reports::class.java)
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_MULTIPLE_TASK)
    startActivity(context, intent, null)
}


fun gotoComment(mainActivity: MainActivity) {
    val context = mainActivity.applicationContext
    val intent = Intent(context, Comments::class.java)
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_MULTIPLE_TASK)
    startActivity(context, intent, null)
}


