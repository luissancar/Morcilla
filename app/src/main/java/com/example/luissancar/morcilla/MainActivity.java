package com.example.luissancar.morcilla;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.URLUtil;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

public class MainActivity extends AppCompatActivity {
    WebView w;

    EditText url;
    TextView footer;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        w = (WebView) findViewById(R.id.web);
        w.setWebViewClient(new WebViewClient());  // al hacer click no salta a otro navegador
        url = (EditText) findViewById(R.id.editText2);
        footer = (TextView) findViewById(R.id.textView2);

        w.setOnTouchListener(new View.OnTouchListener() {

            public final static int FINGER_RELEASED = 0;
            public final static int FINGER_TOUCHED = 1;
            public final static int FINGER_DRAGGING = 2;
            public final static int FINGER_UNDEFINED = 3;

            private int fingerState = FINGER_RELEASED;


            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {


                switch (motionEvent.getAction()) {

                    case MotionEvent.ACTION_POINTER_DOWN:
                        Toast.makeText(MainActivity.this, "up2", Toast.LENGTH_LONG).show();

                        break;

                    case MotionEvent.ACTION_UP:
                        if(fingerState != FINGER_DRAGGING) {
                            fingerState = FINGER_RELEASED;

                            // Your onClick codes
                            Toast.makeText(MainActivity.this, "up", Toast.LENGTH_LONG).show();




                        }
                        else if (fingerState == FINGER_DRAGGING) fingerState = FINGER_RELEASED;
                        else fingerState = FINGER_UNDEFINED;
                        break;

                    case MotionEvent.ACTION_MOVE:
                        if (fingerState == FINGER_TOUCHED || fingerState == FINGER_DRAGGING) fingerState = FINGER_DRAGGING;
                        else fingerState = FINGER_UNDEFINED;
                        break;

                    default:
                        fingerState = FINGER_UNDEFINED;

                }

                return false;
            }
        });





        final EditText edittext = (EditText) findViewById(R.id.editText2);

        edittext.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    goWeb();
                    return true;
                }
                return false;
            }
        });

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    /**
     * forma de guardar el estado
     */
    @Override
    protected void onSaveInstanceState(Bundle estado) {
        super.onSaveInstanceState(estado);
        estado.putString("url", url.getText().toString());

    }




    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        url.setText(savedInstanceState.getString("url").toString());
        goWeb();


    }



    public void goWeb() {
        if (!url.getText().toString().startsWith("http://"))
            url.setText("http://" + url.getText().toString());
        if (URLUtil.isNetworkUrl(url.getText().toString())) {
            footer.setText("Esperando a: " + url.getText().toString());
            w.loadUrl(url.getText().toString());
            footer.setText(url.getText().toString());
        }
        //Lineas para ocultar el teclado virtual (Hide keyboard)
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(url.getWindowToken(), 0);
    }

    public void gooBack(View v) {

        if (w.canGoBack())
            w.goBack();
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
