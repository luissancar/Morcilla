package com.example.luissancar.morcilla;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    WebView w;

    EditText url;
    TextView footer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        w=(WebView)findViewById(R.id.web);
        w.setWebViewClient(new WebViewClient());  // al hacer click no salta a otro navegador
        url=(EditText)findViewById(R.id.editText2);
        footer=(TextView)findViewById(R.id.textView2);


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

         footer.setText("Esperando a: "+url.getText().toString());
         //Bundle bun=getIntent().getExtras();
         w.loadUrl("http://"+url.getText().toString());
         footer.setText(w.getUrl().toString());
         //Lineas para ocultar el teclado virtual (Hide keyboard)
         InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
         imm.hideSoftInputFromWindow(url.getWindowToken(), 0);
     }

     public void gooBack(View v) {
        w.goBack();
    }

}
