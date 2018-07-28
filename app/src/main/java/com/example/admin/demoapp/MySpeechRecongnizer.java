package com.example.admin.demoapp;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MySpeechRecongnizer implements RecognitionListener {
    protected AudioManager mAudioManager;
    private SpeechRecognizer speechRecognizer;
    private Intent mIntent;
    private final static String TAG = MySpeechRecongnizer.class.getSimpleName();
    protected boolean mIsListening;

    private Context context;
    private boolean mIsStreamSolo;


    private boolean mMute = true;


    private MySpeechRecongnizer.onResultsReady mListener;

    public MySpeechRecongnizer(Context context, MySpeechRecongnizer.onResultsReady mListener) {
        this.context = context;
        this.mListener = mListener;
        mAudioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        startListening();
    }


    @Override
    public void onBeginningOfSpeech() {
        Log.d("Speech", "onBeginningOfSpeech");
    }

    @Override
    public void onBufferReceived(byte[] buffer) {
        Log.d("Speech", "onBufferReceived");
    }

    @Override
    public void onEndOfSpeech() {
        Log.d("Speech", "onEndOfSpeech");
        //Toast.makeText(context, "EndOfSpeech", Toast.LENGTH_LONG).show();
        Log.d(TAG, "onEndOfSpeech: ================================================");


    }

    @Override
    public void onError(int error) {
        Log.d(TAG, "onError: " + error);
        if (error == SpeechRecognizer.ERROR_RECOGNIZER_BUSY) {
            if (mListener != null) {
                List<String> errorList = new ArrayList<String>(1);
                errorList.add("ERROR RECOGNIZER BUSY");
                if (mListener != null)
                    mListener.onResults(errorList);
            }
            return;
        }

        if (error == SpeechRecognizer.ERROR_NO_MATCH) {
            if (mListener != null)
                mListener.onResults(null);
        }

        if (error == SpeechRecognizer.ERROR_NETWORK) {
            ArrayList<String> errorList = new ArrayList<String>(1);
            errorList.add("STOPPED LISTENING");
            if (mListener != null)
                mListener.onResults(errorList);
        }
        Log.d(TAG, "error = " + error);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                listenAgain();
            }
        }, 100);

    }

    public void destroy() {
        mIsListening = false;
        if (!mIsStreamSolo) {
            mAudioManager.setStreamMute(AudioManager.STREAM_NOTIFICATION, false);
            mAudioManager.setStreamMute(AudioManager.STREAM_ALARM, false);
            mAudioManager.setStreamMute(AudioManager.STREAM_MUSIC, false);
            mAudioManager.setStreamMute(AudioManager.STREAM_RING, false);
            mAudioManager.setStreamMute(AudioManager.STREAM_SYSTEM, false);
            mIsStreamSolo = true;
        }
        Log.d(TAG, "onDestroy");
        if (speechRecognizer != null) {
            speechRecognizer.stopListening();
            speechRecognizer.cancel();
            speechRecognizer.destroy();
            speechRecognizer = null;
        }

    }

    @Override
    public void onEvent(int eventType, Bundle params) {
        Log.d("Speech", "onEvent");
    }

    @Override
    public void onPartialResults(Bundle partialResults) {
        Log.d("Speech", "onPartialResults");
    }

    @Override
    public void onReadyForSpeech(Bundle params) {
        Log.d("Speech", "onReadyForSpeech");
    }

    @Override
    public void onResults(Bundle results) {
        Log.d("Speech", "onResults");
        if (results != null && mListener != null)
            mListener.onResults(results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION));





        listenAgain();
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                listenAgain();
//            }
//        },1000);



    }

    private void listenAgain() {
        if (mIsListening) {
            mIsListening = false;
            speechRecognizer.cancel();
            startListening();
        }
    }

    private void startListening() {
        if (!mIsListening) {
            mIsListening = true;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                // turn off beep sound
                if (!mIsStreamSolo && mMute) {
                    mAudioManager.setStreamMute(AudioManager.STREAM_NOTIFICATION, true);
                    mAudioManager.setStreamMute(AudioManager.STREAM_ALARM, true);
                    mAudioManager.setStreamMute(AudioManager.STREAM_MUSIC, true);
                    mAudioManager.setStreamMute(AudioManager.STREAM_RING, true);
                    mAudioManager.setStreamMute(AudioManager.STREAM_SYSTEM, true);
                    mIsStreamSolo = true;
                }
            }
            speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context);
            speechRecognizer.setRecognitionListener(this);
            mIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            mIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            mIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
            speechRecognizer.startListening(mIntent);
            Log.d(TAG, "startListening:============================================================");

        }
    }

    @Override
    public void onRmsChanged(float rmsdB) {
        Log.d("Speech", "onRmsChanged" + rmsdB);
    }


    public interface onResultsReady {
        public void onResults(List<String> results);
    }
    public boolean ismIsListening() {
        return mIsListening;
    }

}