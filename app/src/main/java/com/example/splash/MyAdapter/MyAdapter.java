package com.example.splash.MyAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.splash.Json.MyData;
import com.example.splash.R;

import java.io.Serializable;
import java.util.List;

public class MyAdapter extends BaseAdapter implements Serializable {
    private List<MyData> list;
    private Context context;
    private LayoutInflater layoutInflater;
    private int []imagenes ={R.drawable.anadimg,R.drawable.editimg};

    public MyAdapter(List<MyData> list, Context context){
        this.list = list;
        this.context = context;
        if(context != null){
            layoutInflater = LayoutInflater.from(context);
        }
    }
    public boolean isEmptyOrNull(){

        return list == null || list.size() == 0;
    }

    @Override
    public int getCount()
    {
        if(isEmptyOrNull())
        {
            return 0;
        }
        return list.size();
    }

    @Override
    public Object getItem(int i){
        if(isEmptyOrNull())
        {
            return null;
        }
        return list.get(i);
    }

    @Override
    public long getItemId(int i)
    {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup){
        TextView textView = null;
        TextView textView2 = null;
        ImageView imageView = null;
        ImageView imageView2 = null;
        ImageView imageView3 = null;
        view = layoutInflater.inflate(R.layout.activity_lista, null);
        textView =view.findViewById(R.id.textViewId2);
        textView2 = view.findViewById(R.id.textViewId1);
        textView.setText(list.get(i).getPassRed());
        textView2.setText(list.get(i).getRed());
        imageView = view.findViewById(R.id.imageViewId);
        imageView2 = view.findViewById(R.id.imageViewanad);
        imageView3 = view.findViewById(R.id.imageViewedit);
        imageView.setImageResource(list.get(i).getPerfil());
        imageView2.setImageResource(imagenes[0]);
        imageView3.setImageResource(imagenes[1]);
        return view;
    }
}
