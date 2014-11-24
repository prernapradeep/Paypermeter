package com.techathon2014.paypermeter;

import bolts.Continuation;
import bolts.Task;

import com.ibm.mobile.services.core.IBMBluemix;
import com.ibm.mobile.services.data.IBMData;
import com.ibm.mobile.services.data.IBMDataObject;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class TextComplaintForm extends ActionBarActivity {
	EditText driverLic;
	String lic;
	EditText registrationNo;
	String reg;
	EditText serialNo;
	String serial;
	EditText badgeNo;
	String badge;
	int count;
	Button report;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_text_complaint_form);
		
        
        IBMBluemix.initialize(this,"3e44e59b-8a9b-42a3-9f47-c6fc26e8cfb6", "486cfd86233c14320761078dff9b3077b3edf9e4", "PayPerMeter.mybluemix.net");
        IBMData.initializeService();
        TripDetails.registerSpecialization(TripDetails.class);
        
        count=0;
    
	}
	
	public void createAutoItem(View v) {
		if(count>0)
		{
		final TripDetails rec = new TripDetails();
		if (!lic.equals("")) {
			rec.setDriverlic(lic);
			
			rec.save().continueWith(new Continuation<IBMDataObject, Void>() {

				@Override
				public Void then(Task<IBMDataObject> task) throws Exception {
					// Log if the save was cancelled.
					if (task.isCancelled()){
						Log.e(rec.CLASS_NAME, "Exception : Task " + task.toString() + " was cancelled.");
					}
					 // Log error message, if the save task fails.
					else if (task.isFaulted()) {
						Log.e(rec.CLASS_NAME, "Exception : " + task.getError().getMessage());
					}

					 // If the result succeeds, load the list.
					else {
						
					}
					return null;
				}

			});
			
			// Set text field back to empty after item is added.
			driverLic.setText("");
		}
		if (!reg.equals("")) {
			rec.setRegNo(reg);
			
			rec.save().continueWith(new Continuation<IBMDataObject, Void>() {

				@Override
				public Void then(Task<IBMDataObject> task) throws Exception {
					// Log if the save was cancelled.
					if (task.isCancelled()){
						Log.e(rec.CLASS_NAME, "Exception : Task " + task.toString() + " was cancelled.");
					}
					 // Log error message, if the save task fails.
					else if (task.isFaulted()) {
						Log.e(rec.CLASS_NAME, "Exception : " + task.getError().getMessage());
					}

					 // If the result succeeds, load the list.
					else {
						
					}
					return null;
				}

			});
			
			// Set text field back to empty after item is added.
			registrationNo.setText("");
		}
		if (!serial.equals("")) {
			rec.setPolsn(serial);
			
			rec.save().continueWith(new Continuation<IBMDataObject, Void>() {

				@Override
				public Void then(Task<IBMDataObject> task) throws Exception {
					// Log if the save was cancelled.
					if (task.isCancelled()){
						Log.e(rec.CLASS_NAME, "Exception : Task " + task.toString() + " was cancelled.");
					}
					 // Log error message, if the save task fails.
					else if (task.isFaulted()) {
						Log.e(rec.CLASS_NAME, "Exception : " + task.getError().getMessage());
					}

					 // If the result succeeds, load the list.
					else {
						
					}
					return null;
				}

			});
			
			// Set text field back to empty after item is added.
			serialNo.setText("");
		}
		if (!badge.equals("")) {
			rec.setBadge(badge);
			
			rec.save().continueWith(new Continuation<IBMDataObject, Void>() {

				@Override
				public Void then(Task<IBMDataObject> task) throws Exception {
					// Log if the save was cancelled.
					if (task.isCancelled()){
						Log.e(rec.CLASS_NAME, "Exception : Task " + task.toString() + " was cancelled.");
					}
					 // Log error message, if the save task fails.
					else if (task.isFaulted()) {
						Log.e(rec.CLASS_NAME, "Exception : " + task.getError().getMessage());
					}

					 // If the result succeeds, load the list.
					else {
						
					}
					return null;
				}

			});
			
			// Set text field back to empty after item is added.
			badgeNo.setText("");	
		}
		Toast.makeText(this, "Details have been added to our cloud server", Toast.LENGTH_LONG).show();
		}
		else {
			Toast.makeText(this, "Please enter atleast one field", Toast.LENGTH_LONG).show();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.text_complaint_form, menu);
		 	
			driverLic = (EditText)findViewById(R.id.editText1);
	        driverLic.setOnFocusChangeListener(new OnFocusChangeListener() {          

	            public void onFocusChange(View v, boolean hasFocus) {
	                if(!hasFocus) {
	                	lic = driverLic.getText().toString();
	                	
	                	if(!lic.equals("")){
	                		count++;
	                	}
	                }
	            }
	        });
	        
	        registrationNo = (EditText)findViewById(R.id.EditText01);
	        registrationNo.setOnFocusChangeListener(new OnFocusChangeListener() {          

	            public void onFocusChange(View v, boolean hasFocus) {
	                if(!hasFocus) {
	                	reg = registrationNo.getText().toString();
	                	
	                	if(!reg.equals("")){
	                		count++;
	                	}
	                }
	            }
	        });
	        
	        serialNo = (EditText)findViewById(R.id.EditText02);
	        serialNo.setOnFocusChangeListener(new OnFocusChangeListener() {          

	            public void onFocusChange(View v, boolean hasFocus) {
	                if(!hasFocus) {
	                	serial = serialNo.getText().toString();
	                	
	                	if(!serial.equals("")){
	                		count++;
	                	}
	                }
	            }
	        });
	        
	        badgeNo = (EditText)findViewById(R.id.EditText03);
	        badgeNo.setOnFocusChangeListener(new OnFocusChangeListener() {          

	            public void onFocusChange(View v, boolean hasFocus) {
	                if(!hasFocus) {
	                	badge = badgeNo.getText().toString();
	                	
	                	if(!badge.equals("")){
	                		count++;
	                	}
	                }
	            }
	        });
	     
	        
	        report = (Button)findViewById(R.id.button1);
	        report.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View view) {
                	badge = badgeNo.getText().toString();
                	
                	if(!badge.equals("")){
                		count++;
                	}
                	
                	lic = driverLic.getText().toString();
                	
                	if(!lic.equals("")){
                		count++;
                	}
                	
                	reg = registrationNo.getText().toString();
                	
                	if(!reg.equals("")){
                		count++;
                	}
                	
                	serial = serialNo.getText().toString();
                	
                	if(!serial.equals("")){
                		count++;
                	}
                	
                	createAutoItem(view);
                		
				}
	        });
	        return true;
	}
	       
	

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
