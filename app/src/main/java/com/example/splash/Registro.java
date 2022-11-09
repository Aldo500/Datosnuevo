package com.example.splash;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;
import com.example.splash.Json.MyInfo;
import com.google.gson.Gson;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import kotlin.collections.ArrayDeque;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


public class Registro extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    public static final String archivo = "archivo.json";
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
        Registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyInfo info = new MyInfo();
                info.setUsuario(String.valueOf(Usuario.getText()));
                info.setContraseña(String.valueOf(Contraseña.getText()));
                info.setCorreo(String.valueOf(Correo.getText()));
                info.setTelefono(String.valueOf(Telefono.getText()));
                List2Json(info,list);
                Intent i = new Intent(Registro.this, login.class);
                startActivity(i);
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
