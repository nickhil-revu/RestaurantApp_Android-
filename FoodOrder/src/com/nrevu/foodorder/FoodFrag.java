package com.nrevu.foodorder;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;



	    public class FoodFrag extends Fragment {

	    	private frag_Selected onButtonClickedListener ;
	    	//private Size_Selected onRadioButtonClickedListener;  
	    	int size=0;  
	    	  public interface frag_Selected {
	    		  public void onButtonAdd_Clicked(int size);
	    		  public void onButtonCancel_Clicked();
	    		  
	    	  }
	    	  
	    	  public interface Size_Selected{
	    		  public void onSizeSelected();
	    	  }
	    	
	    	  @Override
			public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
				
			  View view=inflater.inflate(R.layout.f_frag, container,false);
	    	  Button fAdd=(Button)view.findViewById(R.id.fragAdd);
	    	  Button fcancel=(Button)view.findViewById(R.id.fragCancel); 
	    	  RadioButton small =(RadioButton)view.findViewById(R.id.radioSmall);
	    	  RadioButton medium =(RadioButton)view.findViewById(R.id.radioMed);
	    	  RadioButton large =(RadioButton)view.findViewById(R.id.radioLarge);
	    	  
	    	  small.setOnClickListener(new View.OnClickListener(){
	    		  public void onClick(View v){
	    			  size=0;
	    		  }
	    	  });
	    	  medium.setOnClickListener(new View.OnClickListener(){
	    		  public void onClick(View v){
	    			  size=1;
	    		  }
	    	  });
	    	  large.setOnClickListener(new View.OnClickListener(){
	    		  public void onClick(View v){
	    			  size=2;
	    		  }
	    	  });
	    	 
	    	  
	    	  
	    	  fAdd.setOnClickListener(new View.OnClickListener()
	    	  {
	    		  public void onClick(View v)
	    		  {	   				   
	    			  onButtonClickedListener.onButtonAdd_Clicked(size);
	    		  }
	    	  });
	    	  
			 
	    	 
	    	  fcancel.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) 
				{
					onButtonClickedListener.onButtonCancel_Clicked();	
				}
			  });
	    	  			
			return view;
		}
			
	    	  
	   
	    	  @Override
	    	  public void onAttach(Activity activity) {
	    	    super.onAttach(activity);
	    	      
	    	    try 
	    	    {
	    	      onButtonClickedListener = (frag_Selected)activity;
	    	    } catch (ClassCastException e) {
	    	    	throw new ClassCastException(activity.toString() + 
	    	                " must implement OnNewItemAddedListener");
	    	    }
	    	  }
	    
	    }

