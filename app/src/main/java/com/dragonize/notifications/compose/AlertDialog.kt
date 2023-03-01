package com.displayapp.dialog

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.view.*
import com.displayapp.databinding.DialogAlertBinding
import com.displayapp.databinding.DialogLogoutBinding


class AlertDialog(val context: Context, val dialog: Dialog?=null) {
    var onItemClick: ((click: String) -> Unit)? = null
    private var inflater: LayoutInflater = (context as Activity).layoutInflater
    private var binding: DialogAlertBinding = DialogAlertBinding.inflate(inflater)

    init {
        dialog?.setContentView(binding.root)
        dialog?.setCancelable(true)
        dialog?.setCanceledOnTouchOutside(true)
        binding.root.setOnClickListener { hide() }
        binding.btnOk.setOnClickListener { (context as Activity).finish() }
    }

    fun show(): Dialog? {
        dialog?.show()

        return dialog
    }

    fun hide() {
        dialog?.dismiss()
    }
}