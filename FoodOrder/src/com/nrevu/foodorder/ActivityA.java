package com.nrevu.foodorder;



import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

public class ActivityA extends FragmentActivity implements FoodFrag.frag_Selected{
	
	int size;
	int rates[][]={{4,5,7},{3,4,6},{7,9,11},{5,6,7}};
	int item_sel;
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    
	     item_sel =getIntent().getIntExtra("ItemSelected",10);
		  switch(item_sel)
		  {
		  case 0: setContentView(R.layout.a);
		  		 break;
		  case 1:setContentView(R.layout.b);
		  	  	break;
		  case 2: setContentView(R.layout.c);
	  		    break;
	      case 3:setContentView(R.layout.d);
	  	  	    break;
		  }
		  
		  
		  
		  
		  
	}
	   
	
	   

	@Override
	public void onBackPressed() {
		Toast.makeText(this, "back button pressed",Toast.LENGTH_LONG).show();
		// TODO Auto-generated method stub
		super.onBackPressed();
		
	}




	@Override
	public void onButtonAdd_Clicked(int size1) {
		size=size1;
		Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
        myIntent.putExtra("price1",rates[item_sel][size]);
        myIntent.putExtra("size_selected",size);
        setResult(RESULT_OK,myIntent);
		Toast.makeText(ActivityA.this, "Item Successfully Added",Toast.LENGTH_LONG).show();
        finish();	
	}

	
	@Override
	public void onButtonCancel_Clicked() {
		Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
        myIntent.putExtra("price1",0);
        setResult(RESULT_OK,myIntent);
        finish();
		
	}

	
}

	

