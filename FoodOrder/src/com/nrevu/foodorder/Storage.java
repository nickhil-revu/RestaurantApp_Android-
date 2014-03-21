package com.nrevu.foodorder;


import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.widget.Toast;

// main class to database functionalities.
public class Storage {
	
	public static final String KEY_ID = "_id"; //a row and assign a ID.
	public static final String KEY_NAME = "product_name"; // product name.
	public static final String KEY_PRICE = "product_price"; // product price.
	public static final String KEY_DATE = "order_date"; // date of order.
	private static final String TAG="Storage";
	
	private static final String DATABASE_NAME="OrderDatabase"; //database name
	public static final String DATABASE_TABLE="OrderTable"; //table name
	private static final int DATABASE_VERSION = 1; 
	
	int flag=0;
	
	
	private DbHelpher ourHelper;
	private final Context ourContext;
	private SQLiteDatabase ourDatabase;
	
	private static class DbHelpher extends SQLiteOpenHelper{

		public DbHelpher(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			// TODO Auto-generated constructor stub
		}

		// this is called only once when database is created.
		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			db.execSQL("CREATE TABLE " + DATABASE_TABLE + " (" +
					KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
					KEY_NAME + " TEXT NOT NULL, "+
					KEY_PRICE + " TEXT NOT NULL, "+
					KEY_DATE + " TEXT NOT NULL);"
										
					);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			db.execSQL("DROP TABLE IF EXISTS " +DATABASE_TABLE);
			onCreate(db);
		}
		
		
		
	}
	
	public Storage(Context c){
		ourContext = c;
	}
	
	//allows to write to the database
	public Storage write() throws SQLException {
		ourHelper = new DbHelpher(ourContext);
		ourDatabase = ourHelper.getWritableDatabase();
		return this;
	}
	 //closes the database.
	public void close(){
		ourHelper.close();
	}

	public long createEntry(String name, String price, String date) {
		// TODO Auto-generated method stub
		ContentValues cv = new ContentValues(); //like bundle in onCreate.
		cv.put(KEY_PRICE, price );
		cv.put(KEY_NAME, name);
		cv.put(KEY_DATE, date);
		return ourDatabase.insert(DATABASE_TABLE, null, cv);
	}

	public String getData() {
		// TODO Auto-generated method stub
		String[] columns = new String[]{KEY_ID,KEY_NAME,KEY_PRICE,KEY_DATE};
		Cursor c = ourDatabase.query(DATABASE_TABLE, columns, null, null, null, null, null);
		String result = " ";
		
		int iRow = c.getColumnIndex(KEY_ID);
		int iName = c.getColumnIndex(KEY_NAME);
		int iPrice = c.getColumnIndex(KEY_PRICE);
		int iDate = c.getColumnIndex(KEY_DATE);
		
		for(c.moveToFirst(); !c.isAfterLast();c.moveToNext()){
			flag=1;
			result = result + c.getString(iRow)+ "  \t" +c.getString(iName)+ "  \t    \t\t\t $" +c.getString(iPrice)+ "    \t    \t\t\t"+c.getString(iDate)+ "\n";
		}
		return result;
	}
	
	
	protected void exporting(){
	    if(isExternalStorageWritable()==true)
	       {
	          	Toast.makeText(ourContext,"SD OK",Toast.LENGTH_LONG).show();
	          	
	          write();
	           String data = getData();
	           if(flag==0)
	           {
	        	   Toast.makeText(ourContext,"No History", Toast.LENGTH_LONG);
	           }
	           else{
	           close();
	          	try {
	               File myFile = new File("/sdcard/myfile.txt");
	               myFile.createNewFile();
	               FileOutputStream fOut = new FileOutputStream(myFile);
	               OutputStreamWriter myOutWriter = 
	                                       new OutputStreamWriter(fOut);
	               myOutWriter.append(data);
	               myOutWriter.close();
	               fOut.close();
	               Toast.makeText(ourContext,
	                       "Done exporting",
	                       Toast.LENGTH_SHORT).show();
	          	}catch (Exception e) {
		               Toast.makeText(ourContext, e.getMessage(),
		                       Toast.LENGTH_SHORT).show();
		           		}
	           }
	             	
	       }
	       else
	       	Toast.makeText(ourContext,"SD NOT OK",Toast.LENGTH_LONG).show();
		}
	
	//To check if SD card is working
	    
	    public boolean isExternalStorageWritable() {
	        String state = Environment.getExternalStorageState();
	        if (Environment.MEDIA_MOUNTED.equals(state)) {
	            return true;
	        }
	        
	        return false;
	    }
	

}