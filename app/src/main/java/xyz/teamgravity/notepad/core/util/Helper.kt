package xyz.teamgravity.notepad.core.util

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import xyz.teamgravity.notepad.R
import xyz.teamgravity.notepad.core.constant.ConnectionConst

object Helper {

    /**
     * Shares the app text with other apps.
     */
    fun shareApp(context: Context) {
        Intent().apply {
            action = Intent.ACTION_SEND
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, shareAppText(context))
            context.startActivity(Intent.createChooser(this, context.getString(R.string.choose)))
        }
    }

    /**
     * Returns the share app text.
     */
    private fun shareAppText(context: Context): String {
        return context.getString(R.string.share_app, "${ConnectionConst.PLAY_STORE_DETAIL_PAGE}${context.packageName}")
    }

    /**
     * Navigates the user to rate the app.
     */
    fun rateApp(context: Context) {
        val appPackageName = context.packageName
        try {
            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$appPackageName")))
        } catch (e: ActivityNotFoundException) {
            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("${ConnectionConst.PLAY_STORE_DETAIL_PAGE}$appPackageName")))
        }
    }

    /**
     * Navigates the user to show source code on Github.
     */
    fun viewSourceCode(context: Context) {
        context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(ConnectionConst.GITHUB_SOURCE_CODE)))
    }

    /**
     * Navigates the user to connect us via Telegram.
     */
    fun connectViaTelegram(context: Context) {
        context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(ConnectionConst.SUPPORT_TELEGRAM)))
    }

    /**
     * Navigates the user to connect us via Email.
     */
    fun connectViaEmail(context: Context) {
        Intent().apply {
            data = Uri.fromParts("mailto", ConnectionConst.SUPPORT_MAIL, null)
            putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.improvement))
            context.startActivity(Intent.createChooser(this, context.getString(R.string.choose)))
        }
    }

    /**
     * Shares note as a text.
     */
    fun shareNote(context: Context, note: String) {
        Intent().apply {
            action = Intent.ACTION_SEND
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, note)
            context.startActivity(Intent.createChooser(this, context.getString(R.string.choose)))
        }
    }
}