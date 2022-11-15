package com.example.splash;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.splash.Json.MyData;
import com.example.splash.Json.MyInfo;
import com.example.splash.MyAdapter.MyAdapter;

import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class entrada extends AppCompatActivity {
    private TextView usrtext;
    private ListView listView;
    private List<MyData> list;
    private int []cuentas = {R.drawable.classroom,R.drawable.gmail,R.drawable.facebook,R.drawable.zoom};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entrada);
        String aux = null;
        MyInfo info = null;
        MyData myData = null;
        Object object = null;
        usrtext = findViewById(R.id.datosusr);
        listView = (ListView) findViewById(R.id.listViewId);
        list = new ArrayList<MyData>();
        Intent intent = getIntent();
        for(int i = 0; i <4; i++){
            myData = new MyData();
            myData.setPassRed(String.format("Contraseña: %d" , (int)(Math.random()*1000000)));
            if(i == 0){
                myData.setRed(String.format("Classroom"));
                myData.setPerfil(cuentas[0]);
            }
            if(i == 1){
                myData.setRed(String.format("Gmail"));
                myData.setPerfil(cuentas[1]);
            }
            if(i == 2){
                myData.setRed(String.format("Facebook"));
                myData.setPerfil(cuentas[2]);
            }
            if(i == 3){
                myData.setRed(String.format("Zoom"));
                myData.setPerfil(cuentas[3]);
            }

            list.add(myData);
        }

        MyAdapter myAdapter = new MyAdapter(list, getBaseContext());
        listView.setAdapter(myAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                toast(i);
            }
        });

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
    private void toast(int i)
    {
        Toast.makeText(getBaseContext(), list.get(i).getPassRed(),Toast.LENGTH_SHORT).show();
    }
}
