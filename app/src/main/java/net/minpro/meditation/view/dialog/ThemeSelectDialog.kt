package net.minpro.meditation.view.dialog

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import net.minpro.meditation.MyApplication
import net.minpro.meditation.R

class ThemeSelectDialog: DialogFragment() {

    val appContext = MyApplication.appContext

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val recyclerView = RecyclerView(MyApplication.appContext)
        with(recyclerView) {
            layoutManager = GridLayoutManager(appContext, 2)
            adapter = ThemeSelectAdapter()
        }

        val dialog = AlertDialog.Builder(activity!!).apply {
            setTitle(R.string.select_theme)
            setView(recyclerView)
        }.create()

        return dialog
    }
}