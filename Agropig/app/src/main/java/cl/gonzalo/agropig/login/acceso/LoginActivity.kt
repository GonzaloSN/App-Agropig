package cl.gonzalo.agropig.login.acceso

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import cl.gonzalo.agropig.MainLoginActivity
import cl.gonzalo.agropig.R
import cl.gonzalo.agropig.login.controladores.LoginControlador
import cl.gonzalo.agropig.login.modelos.UsuarioModelo
import cl.gonzalo.agropig.login.user.Usuario
import cl.gonzalo.agropig.login.utils.ProgressDialogSingleton
import kotlinx.android.synthetic.main.activity_login.*
import cl.gonzalo.agropig.Principal

class LoginActivity : AppCompatActivity() {
    val TAG = "MainLoginActivity"
    var loginControlador: LoginControlador ? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginControlador = LoginControlador()
        listenOnClick()
    }

    private fun listenOnClick() {
        /*btnGoRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }*/

        btnLogin.setOnClickListener {
            if (!edtEmail.text.isEmpty() && !edtPassword.text.isEmpty()){
                val usuario = Usuario()
                usuario.email = edtEmail.text.toString()
                usuario.password = edtPassword.text.toString()
                login(usuario)

            }else
                Toast.makeText(this, "Ingresa tu correo y contraseña", Toast.LENGTH_SHORT).show()
        }

    }


    private fun login(usuario: Usuario){
        val progressDialog = ProgressDialogSingleton.showProgressDialog(this, "Iniciando sesión..")
        loginControlador!!.login(this, usuario, object : LoginControlador.LoginCallback{
            override fun enExito(usuario: Usuario) {
                 save(usuario)
                ProgressDialogSingleton.hideProgressDialog(progressDialog)
            }

            override fun enError(error: String) {
                 Log.e(TAG, error)
                ProgressDialogSingleton.hideProgressDialog(progressDialog)
            }

        })
    }

    private fun save(usuario: Usuario){

        //Guardamos los datos del usuario y procedemos ir a la actividad principal

        UsuarioModelo.instance.set_id(this, usuario.id)
        UsuarioModelo.instance.set_correo(this, usuario.email!!)
        UsuarioModelo.instance.set_nombre(this, usuario.first_name!!)
        UsuarioModelo.instance.set_apellido(this, usuario.last_name!!)

        val intent = Intent(this, Principal::class.java)
        startActivity(intent)
        finish()
    }
}
