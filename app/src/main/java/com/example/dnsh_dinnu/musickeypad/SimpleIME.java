package com.example.dnsh_dinnu.musickeypad;

import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputConnection;

import static android.os.Build.VERSION_CODES.M;

/**
 * Created by dnsh-dinnu on 15/10/17.
 */

public class SimpleIME extends InputMethodService implements KeyboardView.OnKeyboardActionListener {
    private KeyboardView kv;
    private Keyboard keyboard;
    int p=0;

    private boolean caps = false;

    @Override
    public void onPress(int primaryCode) {
        p++;
        Log.d("MusicKeypad", "p value:" +p);
    }

    @Override
    public void onRelease(int primaryCode) {
    }

    @Override
    public void onText(CharSequence text) {
    }

    @Override
    public void swipeDown() {
    }

    @Override
    public void swipeLeft() {
    }

    @Override
    public void swipeRight() {
    }

    @Override
    public void swipeUp() {
    }

    @Override
    public View onCreateInputView() {
        kv = (KeyboardView)getLayoutInflater().inflate(R.layout.keyboard, null);
        keyboard = new Keyboard(this, R.xml.qwerty);
        kv.setKeyboard(keyboard);
        kv.setOnKeyboardActionListener(this);
        return kv;
    }

    @Override
    public void onKey(int primaryCode, int[] keyCodes) {
        InputConnection ic = getCurrentInputConnection();
        playClick(p);
        switch(primaryCode){
            case Keyboard.KEYCODE_DELETE :
                ic.deleteSurroundingText(1, 0);
                break;
            case Keyboard.KEYCODE_SHIFT:
                caps = !caps;
                keyboard.setShifted(caps);
                kv.invalidateAllKeys();
                break;
            case Keyboard.KEYCODE_DONE:
                ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER));
                break;
            default:
                char code = (char)primaryCode;
                if(Character.isLetter(code) && caps){
                    code = Character.toUpperCase(code);
                }
                ic.commitText(String.valueOf(code),1);
        }
    }

    private void playClick(int keyCode){
        switch(keyCode%4){
            case 1:
                final MediaPlayer sound1 = MediaPlayer.create(this, R.raw.knife_sound1);
                sound1.start();
                sound1.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        sound1.release();
                    }
                });
                break;
            case 2:
                final MediaPlayer sound2 = MediaPlayer.create(this, R.raw.knife_sound2);
                sound2.start();
                sound2.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        sound2.release();
                    }
                });
                break;
            case 3:
                final MediaPlayer sound3 = MediaPlayer.create(this, R.raw.knife_sound3);
                sound3.start();
                sound3.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        sound3.release();
                    }
                });
                break;
            case 0:
                final MediaPlayer sound4 = MediaPlayer.create(this, R.raw.knife_sound4);
                sound4.start();
                sound4.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        sound4.release();
                    }
                });
                break;
            default:
                final MediaPlayer sound0 = MediaPlayer.create(this, R.raw.re);
                sound0.start();
                sound0.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        sound0.release();
                    }
                });
        }
    }
}
