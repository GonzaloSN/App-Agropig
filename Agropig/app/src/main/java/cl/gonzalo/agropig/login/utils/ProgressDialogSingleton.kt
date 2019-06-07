package cl.gonzalo.agropig.login.utils

import android.app.ProgressDialog
import android.content.Context

object ProgressDialogSingleton {

    fun showProgressDialog(context: Context, menssage: String): ProgressDialog {
        val pDialog = ProgressDialog(context)
        pDialog.setMessage(menssage)
        pDialog.setCancelable(false)
        pDialog.show()
        return pDialog
    }

    fun hideProgressDialog(pDialog: ProgressDialog) {
        if (pDialog.isShowing)
            pDialog.dismiss()
    }


}
