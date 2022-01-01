package com.lenovo.loginregis

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.text.Spanned
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class LoginActivity : AppCompatActivity() {
    var TxUsername: EditText? = null
    var TxPassword: EditText? = null
    var BtnLogin: Button? = null
    var dbHelper: DBHelper? = null
    protected override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        TxUsername = findViewById(R.id.txUsername) as EditText?
        TxPassword = findViewById(R.id.txPassword) as EditText?
        BtnLogin = findViewById(R.id.btnLogin) as Button?
        dbHelper = DBHelper(this)

        val tvCreateAccount = findViewById(R.id.tvCreateAccount) as TextView
        tvCreateAccount.text = fromHtml(
            "I don't have account yet. " +
                    "</font><font color='#3b5998'>create one</font>"
        )
        tvCreateAccount.setOnClickListener {
            startActivity(
                Intent(
                    this@LoginActivity,
                    RegisterActivity::class.java
                )
            )
        }
        BtnLogin!!.setOnClickListener {
            val username = TxUsername!!.text.toString().trim { it <= ' ' }
            val password = TxPassword!!.text.toString().trim { it <= ' ' }
            val res = dbHelper!!.checkUser(username, password)
            if (res == true) {
                Toast.makeText(this@LoginActivity, "Login Successful", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this@LoginActivity, MainActivity::class.java))
            } else {
                Toast.makeText(this@LoginActivity, "Login Failed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        fun fromHtml(html: String?): Spanned {
            val result: Spanned
            result = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY)
            } else {
                Html.fromHtml(html)
            }
            return result
        }
    }
}