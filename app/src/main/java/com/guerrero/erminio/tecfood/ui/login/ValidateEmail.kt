package com.guerrero.erminio.tecfood.ui.login

import java.util.regex.Matcher
import java.util.regex.Pattern

class ValidateEmail {
    companion object {
        private var pat: Pattern? = null
        private var mat: Matcher? = null
        fun isEmail(email: String): Boolean {
            pat = Pattern.compile("^[A-Za-z0-9+_.-]+@tecsup\\.edu\\.pe$")
            mat = pat!!.matcher(email)
            return mat!!.find()

        }
    }
}