package com.nrevu.foodorder;

import java.util.ArrayList;

import android.app.Activity;
import android.content.ComponentName;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class contacts extends Activity{
		
	EditText name,mail,phoneNo,mainPh;
	Button bAdd,bView;
	String Name,Number,email;
	String Number1;
	int i=1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Number1=getIntent().getStringExtra("ContactNo");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contacts);
		
		name =(EditText)findViewById(R.id.edtName);
		phoneNo=(EditText)findViewById(R.id.edtNumber);
		mail=(EditText)findViewById(R.id.edtEmail);
		bAdd=(Button)findViewById(R.id.btnAdd2);
		bView=(Button)findViewById(R.id.btnView2);
		mainPh=(EditText)findViewById(R.id.editPhNo);
		 phoneNo.setText(Number1);
		  
		// phoneNo1.setText(phoneNo);

		bAdd.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				Name = name.getText().toString();
				 Number = phoneNo.getText().toString();
				  email = mail.getText().toString();
				 fetch_contacts();
				 if(i==1)
				 {
				ArrayList < ContentProviderOperation > ops = new ArrayList < ContentProviderOperation > ();

				 ops.add(ContentProviderOperation.newInsert(
				 ContactsContract.RawContacts.CONTENT_URI)
				     .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
				     .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
				     .build());
				 if (Name != null) {
				     ops.add(ContentProviderOperation.newInsert(
				     ContactsContract.Data.CONTENT_URI)
				         .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
				         .withValue(ContactsContract.Data.MIMETYPE,
				     ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
				         .withValue(
				     ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME,
				     Name).build());
				 }
				 
				 if (Number != null) {
				     ops.add(ContentProviderOperation.
				     newInsert(ContactsContract.Data.CONTENT_URI)
				         .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
				         .withValue(ContactsContract.Data.MIMETYPE,
				     ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
				         .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, Number)
				         .withValue(ContactsContract.CommonDataKinds.Phone.TYPE,
				     ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)
				         .build());
				 }
				 
				 if (email != null) {
				     ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
				         .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
				         .withValue(ContactsContract.Data.MIMETYPE,
				     ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)
				         .withValue(ContactsContract.CommonDataKinds.Email.DATA, email)
				         .withValue(ContactsContract.CommonDataKinds.Email.TYPE, ContactsContract.CommonDataKinds.Email.TYPE_WORK)
				         .build());
				 }

				 
				 try {
				     getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
				     Toast.makeText(contacts.this,  "Contact Saved", Toast.LENGTH_SHORT).show();
				     

				 } catch (Exception e) {
				     e.printStackTrace();
				     Toast.makeText(contacts.this, "Exception: " + e.getMessage(), Toast.LENGTH_SHORT).show();
				 	}
				 }
				 finish();
			}	
		});
		
		bView.setOnClickListener(new OnClickListener(){
		public void onClick(View v){
			Intent i = new Intent();
    		i.setComponent(new ComponentName("com.android.contacts", "com.android.contacts.DialtactsContactsEntryActivity"));
    	    i.setAction("android.intent.action.MAIN");
    	    i.addCategory("android.intent.category.LAUNCHER");
    	    i.addCategory("android.intent.category.DEFAULT");
    	    startActivity(i);
		}
	});
	
		}
	
	public void fetch_contacts()
	{
		String ExName;
		
		Uri CONTENT_URI = ContactsContract.Contacts.CONTENT_URI;
		String _ID = ContactsContract.Contacts._ID;
	    ExName = ContactsContract.Contacts.DISPLAY_NAME;
	    ContentResolver contentResolver = getContentResolver();
		Cursor cursor = contentResolver.query(CONTENT_URI, null,null, null, null);

		while(cursor.moveToNext())
		{
			String name12 = cursor.getString(cursor.getColumnIndex( ExName ));
			if(Name.equalsIgnoreCase(name12))
		{
			i=0;
			Toast.makeText(contacts.this," Contact  "+Name +"  Already Exists",Toast.LENGTH_LONG).show();	
		}
		}

			
	}
	
}