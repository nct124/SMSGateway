package ntu.smsgateway.service;

import android.telephony.SmsManager;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by n on 2/3/2017.
 */

public class FirebaseMessageService extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseMsgService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        //Displaying data in log
        //It is optional
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        Log.d(TAG, "Notification Message Type: " + remoteMessage.getMessageType());
        Log.d(TAG, "Phone: " + remoteMessage.getData().get("phoneNumber"));
        Log.d(TAG, "Msg: " + remoteMessage.getData().get("Msg"));
        String phone = remoteMessage.getData().get("phoneNumber");
        String msg = remoteMessage.getData().get("Msg");
        sendSMS(phone,msg);
    }

    public void sendSMS(String phoneNo, String msg) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNo, null, msg, null, null);
        } catch (Exception ex) {
            Log.d(TAG,"error: "+ex.getMessage());
            ex.printStackTrace();
        }
    }
}
