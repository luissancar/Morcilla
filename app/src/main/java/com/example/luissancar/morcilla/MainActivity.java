package com.example.luissancar.morcilla;

import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    WebView w;

    EditText url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        w=(WebView)findViewById(R.id.web);
        w.setWebViewClient(new WebViewClient());  // al hacer click no salta a otro navegador
        url=(EditText)findViewById(R.id.editText2);


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

    }



     public void goWeb() {

         //Bundle bun=getIntent().getExtras();
         w.loadUrl("http://"+url.getText().toString());
     }

     public void gooBack(View v) {
        w.goBack();
    }

}
