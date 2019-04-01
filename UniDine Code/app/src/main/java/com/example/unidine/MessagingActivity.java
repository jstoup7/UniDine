package com.example.unidine;

import android.app.Application;
import android.content.Context;

import co.chatsdk.core.error.ChatSDKException;
import co.chatsdk.core.session.ChatSDK;
import co.chatsdk.core.session.InterfaceManager;
import co.chatsdk.firebase.FirebaseNetworkAdapter;
import co.chatsdk.firebase.file_storage.FirebaseFileStorageModule;
import co.chatsdk.firebase.push.FirebasePushModule;
import co.chatsdk.ui.manager.BaseInterfaceAdapter;

public class MessagingActivity extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //chatSDK
        Context context = getApplicationContext();
        try {
            // Create a new configuration
            co.chatsdk.core.session.Configuration.Builder builder = new co.chatsdk.core.session.Configuration.Builder(context);
            // Perform any other configuration steps (optional)
            builder.firebaseRootPath("prod");
            // Initialize the Chat SDK
            ChatSDK.initialize(builder.build(), new FirebaseNetworkAdapter(), new BaseInterfaceAdapter(context));
            // File storage is needed for profile image upload and image messages
            FirebaseFileStorageModule.activate();
            // Push notification module
            FirebasePushModule.activate();
            // Activate any other modules you need.
            // ...
        } catch (ChatSDKException e) {
            // Handle any exceptions
            e.printStackTrace();
        }
        //InterfaceManager.shared().a.startLoginActivity(context, true);
    }
}
