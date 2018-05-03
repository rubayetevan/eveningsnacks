package eveningsnacks.bdjobs.com.eveningsnacks.Session

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit


class SessionManager(private val context: Context) {

    var pref: SharedPreferences? = null

    init {
        pref = context.getSharedPreferences(SessionManagerProperties.PREF_NAME.toString(), 0)
    }

    fun createSession(name: String, email: String, userid: String, photoUrl: String) {

        pref?.edit {
            putString(SessionManagerProperties.KEY_NAME.toString(), name)
            putString(SessionManagerProperties.KEY_EMAIL.toString(), email)
            putString(SessionManagerProperties.KEY_USER_ID.toString(), userid)
            putString(SessionManagerProperties.KEY_PHOTO_URL.toString(), photoUrl)
            putBoolean(SessionManagerProperties.KEY_IS_LOGIN.toString(), true)
        }


    }

    fun getUserDetails(): HashMap<String, String> {

        val userDetails = HashMap<String, String>()

        userDetails[SessionManagerProperties.KEY_NAME.toString()] = pref?.getString(SessionManagerProperties.KEY_NAME.toString(), null)!!
        userDetails[SessionManagerProperties.KEY_EMAIL.toString()] = pref?.getString(SessionManagerProperties.KEY_EMAIL.toString(), null)!!
        userDetails[SessionManagerProperties.KEY_USER_ID.toString()] = pref?.getString(SessionManagerProperties.KEY_USER_ID.toString(), null)!!
        userDetails[SessionManagerProperties.KEY_PHOTO_URL.toString()] = pref?.getString(SessionManagerProperties.KEY_PHOTO_URL.toString(), null)!!

        return userDetails

    }

    fun logoutUser() {
        pref?.edit()?.clear()?.apply()
    }

    fun isLoggedIn():Boolean{

        return pref?.getBoolean(SessionManagerProperties.KEY_IS_LOGIN.toString(),false)!!
    }
}