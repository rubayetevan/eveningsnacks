package eveningsnacks.bdjobs.com.eveningsnacks.Notification

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


class MyFirebaseMessagingService : FirebaseMessagingService() {

    private val TAG = MyFirebaseMessagingService::class.java.simpleName

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        Log.e(TAG, "sendRegistrationToServer: "+remoteMessage?.data?.toString())
    }

}