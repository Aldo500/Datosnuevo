package com.example.splash;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.widget.TextView;
import com.example.splash.Json.MyInfo;
import android.os.Bundle;

public class entrada extends AppCompatActivity {
    private TextView usrtext;
    private TextView cortext;
    private TextView datetext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entrada);
        String aux = null;
        MyInfo info = null;
        Object object = null;
        usrtext = findViewById(R.id.datosusr);
        Intent intent = getIntent();
        if( intent != null){
            aux = intent.getStringExtra("Usuario");
            if(aux != null && aux.length()>0){
                usrtext.setText(aux);
            }
            if( intent.getExtras() != null){
                object = intent.getExtras().get("MyInfo");
                if(object != null){
                    if(object instanceof MyInfo){
                        info = (MyInfo) object;
                        usrtext.setText("Usuario " + info.getUsuario());
                    }
                }
            }
        }
    }
}
