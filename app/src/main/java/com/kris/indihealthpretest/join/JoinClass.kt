package com.kris.indihealthpretest.join

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.kris.indihealthpretest.R
import org.jitsi.meet.sdk.*
import timber.log.Timber
import java.net.MalformedURLException
import java.net.URL

class JoinClass : AppCompatActivity(){

    private val broadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            onBroadcastReceived(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join)

        // Initialize default options for Jitsi Meet conferences.
        val serverURL: URL
        serverURL = try {
            // When using JaaS, replace "https://meet.jit.si" with the proper serverURL
            URL("https://meet.jit.si")
        } catch (e: MalformedURLException) {
            e.printStackTrace()
            throw RuntimeException("Invalid server URL!")
        }
        val defaultOptions = JitsiMeetConferenceOptions.Builder()
            .setServerURL(serverURL)
            // When using JaaS, set the obtained JWT here
            //.setToken("MyJWT")
            // Different features flags can be set
            //.setFeatureFlag("toolbox.enabled", false)
            //.setFeatureFlag("filmstrip.enabled", false)
            .setWelcomePageEnabled(false)
            .build()
        JitsiMeet.setDefaultConferenceOptions(defaultOptions)

        registerForBroadcastMessages()
    }

    override fun onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver)
        super.onDestroy()
    }


    fun onButtonClick(v: View?) {
        val editText = findViewById<EditText>(R.id.conferenceName)
        val text = editText.text.toString()
        if (text.length > 0) {
            // Build options object for joining the conference. The SDK will merge the default
            // one we set earlier and this one when joining.
            val options = JitsiMeetConferenceOptions.Builder()
                .setRoom(text)
                // Settings for audio and video
                //.setAudioMuted(true)
                //.setVideoMuted(true)
                .build()
            // Launch the new activity with the given options. The launch() method takes care
            // of creating the required Intent and passing the options.
            JitsiMeetActivity.launch(this, options)
        }
    }


    private fun registerForBroadcastMessages() {
        val intentFilter = IntentFilter()

        /* This registers for every possible event sent from JitsiMeetSDK
           If only some of the events are needed, the for loop can be replaced
           with individual statements:
           ex:  intentFilter.addAction(BroadcastEvent.Type.AUDIO_MUTED_CHANGED.action);
                intentFilter.addAction(BroadcastEvent.Type.CONFERENCE_TERMINATED.action);
                ... other events
         */
        for (type in BroadcastEvent.Type.values()) {
            intentFilter.addAction(type.action)
        }

        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, intentFilter)
    }

    // Example for handling different JitsiMeetSDK events
    private fun onBroadcastReceived(intent: Intent?) {
        if (intent != null) {
            val event = BroadcastEvent(intent)
            when (event.getType()) {
                BroadcastEvent.Type.CONFERENCE_JOINED -> Timber.i("Conference Joined with url%s", event.getData().get("url"))
                BroadcastEvent.Type.PARTICIPANT_JOINED -> Timber.i("Participant joined%s", event.getData().get("name"))
            }
        }
    }

    // Example for sending actions to JitsiMeetSDK
    private fun hangUp() {
        val hangupBroadcastIntent: Intent = BroadcastIntentHelper.buildHangUpIntent()
        LocalBroadcastManager.getInstance(org.webrtc.ContextUtils.getApplicationContext()).sendBroadcast(hangupBroadcastIntent)
    }

//    private lateinit var binding: ActivityJoinBinding
//
//    private val broadcastReceiver = object : BroadcastReceiver() {
//        override fun onReceive(context: Context?, intent: Intent?) {
//            onBroadcastReceived(intent)
//        }
//    }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityJoinBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        initView()
//    }
//
//    private fun initView() {
//        binding.btnJoin.setOnClickListener {
//            val serverURL: URL
//            serverURL = try {
//                // When using JaaS, replace "https://meet.jit.si" with the proper serverURL
//                URL("https://meet.jit.si/CobaJitsi")
//            } catch (e: MalformedURLException) {
//                e.printStackTrace()
//                throw RuntimeException("Invalid server URL!")
//            }
//            val defaultOptions = JitsiMeetConferenceOptions.Builder()
//                .setServerURL(serverURL)
//                // When using JaaS, set the obtained JWT here
//                //.setToken("MyJWT")
//                // Different features flags can be set
//                //.setFeatureFlag("toolbox.enabled", false)
//                //.setFeatureFlag("filmstrip.enabled", false)
//                .setWelcomePageEnabled(false)
//                .build()
//            JitsiMeet.setDefaultConferenceOptions(defaultOptions)
//
//            registerForBroadcastMessages()
////            val view = JitsiMeetView(this)
////            val options = JitsiMeetConferenceOptions.Builder()
////                .setServerURL(URL("https://meet.jit.si/CobaJitsi"))
////                .setRoom("test123")
////                .setAudioMuted(false)
////                .setVideoMuted(false)
////                .setAudioOnly(false)
////                .setWelcomePageEnabled(false)
////                .setConfigOverride("requireDisplayName", true)
////                .build()
////            view.join(options)
////            setContentView(view)
//        }
//    }
//
//    override fun onDestroy() {
//        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver)
//        super.onDestroy()
//    }
//
//    fun onButtonClick(v: View?) {
//        val editText = findViewById<EditText>(R.id.conferenceName)
//        val text = editText.text.toString()
//        if (text.length > 0) {
//            // Build options object for joining the conference. The SDK will merge the default
//            // one we set earlier and this one when joining.
//            val options = JitsiMeetConferenceOptions.Builder()
//                .setRoom(text)
//                // Settings for audio and video
//                //.setAudioMuted(true)
//                //.setVideoMuted(true)
//                .build()
//            // Launch the new activity with the given options. The launch() method takes care
//            // of creating the required Intent and passing the options.
//            JitsiMeetActivity.launch(this, options)
//        }
//    }
//
//    private fun registerForBroadcastMessages() {
//        val intentFilter = IntentFilter()
//
//        /* This registers for every possible event sent from JitsiMeetSDK
//           If only some of the events are needed, the for loop can be replaced
//           with individual statements:
//           ex:  intentFilter.addAction(BroadcastEvent.Type.AUDIO_MUTED_CHANGED.action);
//                intentFilter.addAction(BroadcastEvent.Type.CONFERENCE_TERMINATED.action);
//                ... other events
//         */
//        for (type in BroadcastEvent.Type.values()) {
//            intentFilter.addAction(type.action)
//        }
//
//        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, intentFilter)
//    }
//
//    // Example for handling different JitsiMeetSDK events
//    private fun onBroadcastReceived(intent: Intent?) {
//        if (intent != null) {
//            val event = BroadcastEvent(intent)
//            when (event.getType()) {
//                BroadcastEvent.Type.CONFERENCE_JOINED -> Timber.i("Conference Joined with url%s", event.getData().get("url"))
//                BroadcastEvent.Type.PARTICIPANT_JOINED -> Timber.i("Participant joined%s", event.getData().get("name"))
//            }
//        }
//    }
//
//    // Example for sending actions to JitsiMeetSDK
//    private fun hangUp() {
//        val hangupBroadcastIntent: Intent = BroadcastIntentHelper.buildHangUpIntent()
//        LocalBroadcastManager.getInstance(org.webrtc.ContextUtils.getApplicationContext()).sendBroadcast(hangupBroadcastIntent)
//    }
//
//    override fun requestPermissions(p0: Array<out String>?, p1: Int, p2: PermissionListener?) {
//
//    }
}