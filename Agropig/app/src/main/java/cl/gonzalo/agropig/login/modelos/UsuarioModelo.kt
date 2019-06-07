package cl.gonzalo.agropig.login.modelos

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import cl.gonzalo.agropig.R

class UsuarioModelo {

    //region TODO: ID
    fun set_id(activity: Activity, id: Int) {
        val sharedPref =
            activity.getSharedPreferences(activity.getString(R.string.usuario_get_id), Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putInt(activity.getString(R.string.usuario_get_id), id)
        editor.apply()
    }

    fun get_id(activity: Activity): Int? {
        val prefs = activity.getSharedPreferences(activity.getString(R.string.usuario_get_id), Context.MODE_PRIVATE)
        return prefs.getInt(activity.getString(R.string.usuario_get_id), 0)
    }

    fun del_id(activity: Activity) {
        val sharedPref =
            activity.getSharedPreferences(activity.getString(R.string.usuario_get_id), Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putInt(activity.getString(R.string.usuario_get_id), 0)
        editor.apply()
    }
    //endregion

    //region TODO: NOMBRE
    fun set_nombre(activity: Activity, token: String) {
        val sharedPref =
            activity.getSharedPreferences(activity.getString(R.string.usuario_get_nombre), Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString(activity.getString(R.string.usuario_get_nombre), token)
        editor.apply()
    }

    fun get_nombre(activity: Activity): String? {
        val prefs = activity.getSharedPreferences(activity.getString(R.string.usuario_get_nombre), Context.MODE_PRIVATE)
        return prefs.getString(activity.getString(R.string.usuario_get_nombre), null)
    }

    fun del_nombre(activity: Activity) {
        val sharedPref =
            activity.getSharedPreferences(activity.getString(R.string.usuario_get_nombre), Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString(activity.getString(R.string.usuario_get_nombre), null)
        editor.apply()
    }
    //endregion

    //region TODO: APELLIDO
    fun set_apellido(activity: Activity, token: String) {
        val sharedPref =
            activity.getSharedPreferences(activity.getString(R.string.usuario_get_apellido), Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString(activity.getString(R.string.usuario_get_apellido), token)
        editor.apply()
    }

    fun get_apellido(activity: Activity): String? {
        val prefs =
            activity.getSharedPreferences(activity.getString(R.string.usuario_get_apellido), Context.MODE_PRIVATE)
        return prefs.getString(activity.getString(R.string.usuario_get_apellido), null)
    }

    fun del_apellido(activity: Activity) {
        val sharedPref =
            activity.getSharedPreferences(activity.getString(R.string.usuario_get_apellido), Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString(activity.getString(R.string.usuario_get_apellido), null)
        editor.apply()
    }
    //endregion

    //region TODO: CORREO
    fun set_correo(activity: Activity, token: String) {
        val sharedPref =
            activity.getSharedPreferences(activity.getString(R.string.usuario_get_correo), Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString(activity.getString(R.string.usuario_get_correo), token)
        editor.apply()
    }

    fun get_correo(activity: Activity): String? {
        val prefs = activity.getSharedPreferences(activity.getString(R.string.usuario_get_correo), Context.MODE_PRIVATE)
        return prefs.getString(activity.getString(R.string.usuario_get_correo), null)
    }

    fun del_correo(activity: Activity) {
        val sharedPref =
            activity.getSharedPreferences(activity.getString(R.string.usuario_get_correo), Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString(activity.getString(R.string.usuario_get_correo), null)
        editor.apply()
    }

    companion object {

        private var single_instance: UsuarioModelo? = null

        val instance: UsuarioModelo
            get() {
                if (single_instance == null)
                    single_instance = UsuarioModelo()
                return single_instance!!
            }
    }
    //endregion

}
