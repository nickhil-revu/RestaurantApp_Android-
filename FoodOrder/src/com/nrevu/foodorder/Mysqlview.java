package com.nrevu.foodorder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import android.app.Activity;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

public class Mysqlview extends Activity {

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mysqlview);
         
        
        
        TextView tv = (TextView) findViewById(R.id.tvsqlinfo);
        Storage info = new Storage(this);
        info.write();
        String data = info.getData();
        info.close();
        tv.setText(data);
      
        //Toast.makeText(Mysqlview.this, data,Toast.LENGTH_LONG).show();
         
	}
}

