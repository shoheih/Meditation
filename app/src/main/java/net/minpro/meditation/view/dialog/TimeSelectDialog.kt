package net.minpro.meditation.view.dialog

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import net.minpro.meditation.R
import net.minpro.meditation.viewmodel.MainViewModel
import org.koin.android.viewmodel.ext.android.sharedViewModel

class TimeSelectDialog: DialogFragment() {

    var selectedItemId = 0

    //private lateinit var viewModel: MainViewModel
    private val viewModel: MainViewModel by sharedViewModel()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        //viewModel = ViewModelProviders.of(activity!!).get(MainViewModel::class.java)

        val dialog = AlertDialog.Builder(activity!!).apply {
            setTitle(R.string.select_time)
            setSingleChoiceItems(R.array.time_list, selectedItemId){ dialog, which ->
                selectedItemId = which
                viewModel.setTime(selectedItemId)
                dialog.dismiss()
            }
        }.create()

        return dialog
    }
}