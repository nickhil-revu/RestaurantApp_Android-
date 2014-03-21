package com.nrevu.foodorder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Calendar;

import com.nrevu.foodorder.R.id;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Contacts.Intents;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnInitListener {

	String [] menu = {"Fried Rice","Noodles","Sushi","Chicken Soup"};
	Button checkout,reset,call;
	Button reset_btn,chkout_btn,view_db,save_db,contacts ;
	ImageButton call_btn;
	ProgressBar progressBar;
    EditText tot,edph;
    ListView lv;
    TextView tv,tv7;
    String savedkey,name;
    int pos,sel_size;
    private TextToSpeech myTTS;
  int total=0;int t1;
  Storage info = new Storage(this);
  
    @Override
    protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
    	 if( savedInstanceState != null)
    	  { 
    	    t1=savedInstanceState.getInt("total1");
    	    total=t1;
    	    name=savedInstanceState.getString("itemname1");
    	    Toast.makeText(MainActivity.this, savedInstanceState.getString("savedkey1"), Toast.LENGTH_LONG).show();
          }
    	       
        setContentView(R.layout.activity_main);
        contacts=(Button)findViewById(R.id.btncontacts);
        tot=(EditText)findViewById(R.id.Total);
        chkout_btn=(Button)findViewById(R.id.btnchkout);
        reset_btn=(Button)findViewById(R.id.btnreset);
        call_btn=(ImageButton)findViewById(R.id.btncall);
        tv=(TextView)findViewById(R.id.TextView01);
        lv=(ListView)findViewById(R.id.listView1);
		view_db=(Button)findViewById(R.id.ViewDB);
		save_db=(Button)findViewById(R.id.SaveDB);
		tv7 = (TextView) findViewById(R.id.tvsqlinfo);
        edph = (EditText) findViewById(R.id.editPhNo);
       // progressBar =(ProgressBar)findViewById(R.id.progressBar2);
        
        tot.setText(""+t1);
        if(t1!=0){
        chkout_btn.setVisibility(View.VISIBLE);
		reset_btn.setVisibility(View.VISIBLE);
		tv.setVisibility(View.VISIBLE);
		tot.setVisibility(View.VISIBLE);
        }
        
	    
		/*Code Related to list Menu Items*/
        
		 ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, 
			        android.R.layout.simple_list_item_1, menu);
        lv = (ListView) findViewById(R.id.listView1);
		lv.setAdapter(adapter);
		lv.requestFocus();
		lv.setOnItemClickListener(new OnItemClickListener(){
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				pos=position;
				switch(sel_size)
				{
				case 0: name+="\n\t\t"+menu[pos]+"(small)";
				break;
				case 1: name+="\n\t\t "+menu[pos]+"(medium)";
				break;
				case 2: name+="\n\t\t "+menu[pos]+"(large)";
				break;
				
				}
				Intent in = new Intent();
				in=new Intent(MainActivity.this,ActivityA.class).putExtra("ItemSelected",position);
				startActivityForResult(in,1);
				
			}
		});
	
		
		
		save_db.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				Toast.makeText(MainActivity.this,"Saving",Toast.LENGTH_LONG).show();
			try{
			String price = tot.getText().toString();
			String mydate = java.text.DateFormat.getDateInstance().format(Calendar.getInstance().getTime());
			String name1=name.replaceAll("null\n\t","");
			Storage entry = new Storage(MainActivity.this);
			entry.write();
			entry.createEntry(name1, price, mydate );
			entry.close();
			Toast.makeText(MainActivity.this,"Order Saved to Database",Toast.LENGTH_LONG).show();
			}catch (Exception e){
				e.printStackTrace();
				Toast.makeText(MainActivity.this,"error in saving",Toast.LENGTH_LONG).show();
				}
			}
		});
	
		view_db.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				Intent i = new Intent(MainActivity.this,Mysqlview.class);
				startActivity(i);
			}
		});
	
		chkout_btn.setOnClickListener(new OnClickListener(){
	    	public void onClick(View v){
	    		Toast.makeText(MainActivity.this,"Your Order has been sent",Toast.LENGTH_LONG).show();
	    		//((Update)getApplication()).reset();
	    		myTTS.speak("Thank you. Please come again", TextToSpeech.QUEUE_FLUSH, null);
	    		startActivity(new Intent(MainActivity.this,MainActivity.class));
	    		finish();
	    	}
	    });
		
		reset_btn.setOnClickListener(new OnClickListener(){
	    	public void onClick(View v){
	    		finish();
	    		startActivity(new Intent(MainActivity.this,MainActivity.class));
	    	}
	    });
	    
	    call_btn.setOnClickListener(new OnClickListener(){
	    	public void onClick(View v){
	    		Uri number=Uri.parse("tel:7323743");
	    		Intent call=new Intent(Intent.ACTION_DIAL,number);
	    		startActivity(call);
	    	}
	    });
	    
	    contacts.setOnClickListener(new OnClickListener(){
	    	public void onClick(View v){
	    		Intent i=new Intent(MainActivity.this,contacts.class);
	    		i.putExtra("ContactNo", edph.getText().toString());
	    		edph.setText("");
	    		startActivity(i);   		
	    	}
	    });
	
		//Start
		Intent checkTTSIntent = new Intent();
	    checkTTSIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
	    startActivityForResult(checkTTSIntent, 0);
		//stop

    } //Close onCreate()
 
        
   
    
    
    
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent myIntent) {
		if(requestCode==1)
		{
			
			int value = myIntent.getIntExtra("price1",0);
			sel_size=myIntent.getIntExtra("size_selected",0);
			if(value!=0)
			{
				chkout_btn.setVisibility(View.VISIBLE);
				reset_btn.setVisibility(View.VISIBLE);
				tv.setVisibility(View.VISIBLE);
				tot.setVisibility(View.VISIBLE);
			}
			total+=value;
			tot.setText(""+total);
         }
		//start
		if(requestCode==0)
		{
			if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
                //the user has the necessary data - create the TTS
            myTTS = new TextToSpeech(this, this);
            }
            else {
                    //no data - install it now
                Intent installTTSIntent = new Intent();
                installTTSIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                startActivity(installTTSIntent);
            }
			
		}
		//stop
			
	}
	
	public void onInit(int initStatus)
	{
    }


	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putString("savedkey1","welcome Back");
		outState.putInt("total1", Integer.parseInt(tot.getText().toString()));
		outState.putString("itemname1", name);
		super.onSaveInstanceState(outState);
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu);
		MenuInflater showup = getMenuInflater();
		showup.inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch(item.getItemId())
		{
		case R.id.about:
			Intent i =new Intent(this,PrefActivity.class);
			startActivity(i);
			break;
		case R.id.export:
			info.exporting();
			break;
		case R.id.exit:
			finish();
		}
		return false;
	}
	
	

}//Closing class




























