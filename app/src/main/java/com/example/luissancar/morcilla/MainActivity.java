package com.example.luissancar.morcilla;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.URLUtil;
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
        if (savedInstanceState!=null) {
            url.setText(savedInstanceState.getString("url"));
            goWeb();
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (outState!=null) {
            String url2=new  String();
            outState.putString("url", (String) url2 );
            url.setText(url2.toString());
            super.onSaveInstanceState(outState);}
    }

    public void goWeb() {
        if (!url.getText().toString().startsWith("http://"))
            url.setText("http://" + url.getText().toString());
         if (URLUtil.isNetworkUrl(url.getText().toString()) ) {
             footer.setText("Esperando a: " + url.getText().toString());
             w.loadUrl(url.getText().toString());
             footer.setText(url.getText().toString());
         }
         //Lineas para ocultar el teclado virtual (Hide keyboard)
         InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
         imm.hideSoftInputFromWindow(url.getWindowToken(), 0);
     }

     public void gooBack(View v) {

         if (w.canGoBack())
             w.goBack();
    }

}
