package eveningsnacks.bdjobs.com.eveningsnacks.Notification

import android.util.Log
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.FirebaseInstanceIdService

class MyFirebaseInstanceIDService : FirebaseInstanceIdService() {

    private val TAG = MyFirebaseInstanceIDService::class.java.simpleName


    override fun onTokenRefresh() {
        super.onTokenRefresh()
        val refreshedToken = FirebaseInstanceId.getInstance().token
        Log.e(TAG, "sendRegistrationToServer: "+refreshedToken.toString())
    }

}