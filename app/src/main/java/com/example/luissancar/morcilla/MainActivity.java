package com.example.luissancar.morcilla;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.webkit.URLUtil;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import android.webkit.WebChromeClient;
import android.widget.ProgressBar;

import java.util.Vector;


public class MainActivity extends AppCompatActivity {
    WebView w;

    AutoCompleteTextView url;
    TextView footer;
    final Activity activity = this;
    ProgressBar progressbar;
    Vector<String> arrayUrls = new Vector<String>();





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
        url = (AutoCompleteTextView) findViewById(R.id.editText2);
        footer = (TextView) findViewById(R.id.textView2);
        //////////

        WebSettings webSettings = w.getSettings();
        webSettings.setJavaScriptEnabled(true);






        progressbar = (ProgressBar) findViewById(R.id.progressBar);

        w.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int progress) {
                progressbar.setProgress(0);
                progressbar.setVisibility(View.VISIBLE);
                MainActivity.this.setProgress(progress * 1000);

                progressbar.incrementProgressBy(progress);

                if (progress == 100) {
                    progressbar.setVisibility(View.GONE);
                }
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



        url.setText("http://iesayala.ddns.net");
        cargarArray();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, arrayUrls);
        url.setAdapter(adapter);
        goWeb();

    }

    /// inserta url
    public void insertaUrl() {


        String urlBaseDatos = w.getUrl().toString();
        boolean anadido=false;

        SQLiteDatabase db = null;
        AdminSQL admin = new AdminSQL(this, "urls", null, 1);
        db = admin.getWritableDatabase();
        db.beginTransaction();
        try {
            String sql = "SELECT url FROM url WHERE url='" + urlBaseDatos.toString()+"'";
            Cursor cursor = db.rawQuery(sql, null);
            if (!cursor.moveToFirst()) {
                anadido=true;
                ContentValues registro = new ContentValues();
                registro.put("url", urlBaseDatos.toString());
                db.insert("url", null, registro);
                db.setTransactionSuccessful();

                // Toast.makeText(this, "Insertado", Toast.LENGTH_LONG).show();
            } //else
            //  Toast.makeText(this, "Error registro duplicado", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(this, "Erro1:" + e.getMessage().toString(), Toast.LENGTH_LONG).show();
        } finally {
            db.endTransaction();
            db.close();
            if (anadido)
                cargarArray();
        }

    }




    ///////////

/////// carga basedatos a array

    public void cargarArray(){


        SQLiteDatabase db2 = null;
        AdminSQL admin = new AdminSQL(this, "urls", null, 1);
        db2 = admin.getReadableDatabase();
        db2.beginTransaction();
        arrayUrls.clear();
        try {
            String sql = "SELECT url FROM url WHERE 1";
            Cursor cursor = db2.rawQuery(sql, null);
            while (cursor.moveToNext())
           {
                arrayUrls.add(cursor.getString(0));
                arrayUrls.add(cursor.getString(0).substring(7,cursor.getString(0).length()-1));
               // Toast.makeText(this, cursor.getString(0).substring(7,cursor.getString(0).length()-1), Toast.LENGTH_LONG).show();
            }
            db2.setTransactionSuccessful();//else
            //  Toast.makeText(this, "Error registro duplicado", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(this, "Erro:2" + e.getMessage().toString(), Toast.LENGTH_LONG).show();
        } finally {
            db2.endTransaction();
            db2.close();


        }

    }


    /////////





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

    @Override
    public void onBackPressed()
    {
        if (w.canGoBack())
        {
            w.goBack();
        }
        else
        {
            super.onBackPressed();
        }
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

        insertaUrl();

        w.requestFocus();
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
