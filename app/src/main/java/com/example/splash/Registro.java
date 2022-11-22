package com.example.splash;
import static android.provider.Contacts.SettingsColumns.KEY;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.splash.Json.MyDesUtil;
import com.example.splash.Json.MyInfo;
import com.google.firebase.firestore.auth.User;
import com.google.gson.Gson;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import kotlin.collections.ArrayDeque;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import DesUtil.DesUtil;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


public class Registro extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    public static final String archivo = "archivo.json";
    public static final String KEY = "+4xij6jQRSBdCymMxweza/uMYo+o0EUg";
    private String testClaro = "(Correo)";
    private String testDesCifrado;
    private User user = new User();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registro);
        List<MyInfo> list = new ArrayList<MyInfo>();
        Button Registro = findViewById(R.id.bregistro);
        EditText Usuario = findViewById(R.id.user_text);
        EditText Contraseña = findViewById(R.id.pass_text);
        EditText Correo = findViewById(R.id.correo_text);
        EditText Telefono = findViewById(R.id.phone_text);
        EditText Fecha = findViewById(R.id.date_text);
        RadioButton Masculino = findViewById(R.id.radioButton1);
        RadioButton Femenino = findViewById(R.id.radioButton2);
        String testCifrado = null;
        MyDesUtil myDesUtil = null;
        private DesUtil desUtil = new DesUtil();
        Registro.setOnClickListener(new View.OnClickListener() {
            protected void onCreate(Bundle savedInstanceState)
            {
                String testCifrado = null;
                MyDesUtil myDesUtil = null;

                myDesUtil = new MyDesUtil( );
                if( isNotNullAndNotEmpty( KEY ) )
                {
                    myDesUtil.addStringKeyBase64( KEY );
                }
                if( !isNotNullAndNotEmpty( testClaro ) )
                {
                    return;
                }
                Log.i( TAG , testClaro);

        /*testCifrado = myDesUtil.cifrar( testClaro );
        if( !isNotNullAndNotEmpty( testCifrado ) )
        {
            return;
        }
        Log.i( TAG , testCifrado );
         */
                testCifrado = "KdSqSsH+W2gk1j361x9IlQ==";

                testDesCifrado = myDesUtil.desCifrar( testCifrado );
                if( !isNotNullAndNotEmpty( testDesCifrado ) )
                {
                    return;
                }
                Log.i( TAG, testDesCifrado );

                Log.i( TAG , myDesUtil.toStringSecreteKey( ) );
            }


            public boolean isNotNullAndNotEmpty( String aux )
            {
                return aux != null && aux.length() > 0;
            }
         /*  public boolean sendInfo(String correoCifrado, String mensajeHTMLCifrado , Context context){
                JsonObjectRequest jsonObjectRequest = null;
                JSONObject jsonObject = null;
                String url = "https://us-central1-nemidesarrollo.cloudfunctions.net/function-test";
                RequestQueue requestQueue = null;
                if( correoCifrado == null || correoCifrado.length() == 0 || mensajeHTMLCifrado == null || mensajeHTMLCifrado.length() == 0 )
                {
                    return false;
                }
                jsonObject = new JSONObject( );
                try
                {
                    jsonObject.put("correo" , correoCifrado );
                    jsonObject.put("mensaje" , mensajeHTMLCifrado );
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
                jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
        */
                    @Override
                    public void onClick(View view) {

                        MyInfo info = new MyInfo();
                        info.setUsuario(String.valueOf(Usuario.getText()));
                        info.setContraseña(String.valueOf(Contraseña.getText()));
                        info.setCorreo(String.valueOf(Correo.getText()));
                        info.setTelefono(String.valueOf(Telefono.getText()));
                        List2Json(info, list);
                        Intent i = new Intent(Registro.this, login.class);
                        startActivity(i);


                        try {
                            desUtil.addStringKeyBase64(KEY);
                            String emailCifrado = desUtil.cifrar(user.getCorreo());
                            String htmlCifrado = desUtil.cifrar("<html><h1>Registro para una app????</h1></html>");
                            boolean result = sendInfo(emailCifrado, htmlCifrado, getBaseContext());
                            Log.d("Estado", "Correo Cifrado para enviar: " + emailCifrado);
                            Log.d("Estado", "HTML Cifrado para enviar: " + htmlCifrado);
                            Log.d("Estado", "Respuesta Volley: " + result);
                        } catch (Exception e) {
                            Log.d("Estado", "Error");
                            e.printStackTrace();
                        }
                    }

                    protected void onStart() {
                        if (testClaro.equals(testDesCifrado)) {
                            Toast.makeText(getBaseContext(), "Son iguales", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getBaseContext(), "Son diferentes", Toast.LENGTH_LONG).show();
                        }
                    }
                }
        });
    }
    public void Objet2Json(MyInfo info){
        Gson gson = null;
        String json = null;
        String mensaje = null;
        gson = new Gson();
        json = gson.toJson(info);
        if(json != null){
            Log.d(TAG, json);
            mensaje ="object a Json OK";
        }
        else{
            mensaje = "Error object a Json";
        }
        Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_LONG).show();
    }
    public void List2Json(MyInfo info, List<MyInfo> list){
        Gson gson = null;
        String json = null;
        gson = new Gson();
        list.add(info);
        json = gson.toJson(list, ArrayList.class);
        if(json == null){
            Log.d(TAG, "Error json");
        }
        else{
            Log.d(TAG, json);
            writeFile(json);
        }
        Toast.makeText(getApplicationContext(), "Ok", Toast.LENGTH_SHORT).show();
    }
    private boolean writeFile(String text){
        File file = null;
        FileOutputStream fileOutputStream = null;
        try{
            file = getFile();
            fileOutputStream = new FileOutputStream( file );
            fileOutputStream.write(text.getBytes(StandardCharsets.UTF_8));
            fileOutputStream.close();
            Log.d(TAG, "OK");
            return true;
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return false;
    }
    private File getFile(){
        return new File(getDataDir(),archivo);
    }
}
