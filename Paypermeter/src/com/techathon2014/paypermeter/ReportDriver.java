package com.techathon2014.paypermeter;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;

import android.support.v7.app.ActionBarActivity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class ReportDriver extends ActionBarActivity {

	 public final static int REQUEST_CAMERA = 1;
	 public static final int GET_FROM_GALLERY = 3;
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_report_driver);
		//getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}
	
	public void TextComplaint(View view) {
	    // Do something in response to button
		Intent intent = new Intent(ReportDriver.this, TextComplaintForm.class);
		startActivity(intent);
	}
	
	public void uploadOrTake(View view) {
	    AlertDialog.Builder builder = new AlertDialog.Builder(this);
	    final String[] items = getResources().getStringArray(R.array.choose_one);
	    builder.setTitle(R.string.pick_option)
	           .setItems(R.array.choose_one, new DialogInterface.OnClickListener() {
	               public void onClick(DialogInterface dialog, int which) {
	               // The 'which' argument contains the index position
	               // of the selected item
	            	   System.out.println(which); 
	            	   if (items[which].equals("Camera"))
	            	   {
	            		  //System.out.println(which);
	            		   Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		                     startActivityForResult(cameraIntent, REQUEST_CAMERA); 
		                      
	            	   }
	            	  if (items[which].equals("Gallery")) {
	            		  Intent intent = new Intent();
	            		  intent.setType("image/*");
	            		  intent.setAction(Intent.ACTION_GET_CONTENT);
	            		  startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI),GET_FROM_GALLERY);
	            	   }	
	            	  
	            		
	           }
	    });
	    builder.create();
	    builder.show();
	}
	
    
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    super.onActivityResult(requestCode, resultCode, data);
	    //Detects request codes
	    if(requestCode==GET_FROM_GALLERY) {
	        Uri selectedImage = data.getData();
	        Intent intent = new Intent(this, SubmitPhotoComplaint.class);
	        intent.putExtra("ImageUri",selectedImage);
	        startActivity(intent);
	    }
	    else if(requestCode == REQUEST_CAMERA){
	    	Bitmap photo = (Bitmap) data.getExtras().get("data"); 
	    	String root = Environment.getExternalStorageDirectory().toString();
	    	File myDir = new File(root +"/DCIM" +"/PayPerMeter_images");    
	    	myDir.mkdirs();
	    	Random generator = new Random();
	    	int n = 10000;
	    	n = generator.nextInt(n);
	    	String fname = "Image-"+ n +".jpg";
	    	File file = new File (myDir, fname);
	    	if (file.exists ()) file.delete (); 
	    	try {
	    	       FileOutputStream out = new FileOutputStream(file);
	    	       photo.compress(Bitmap.CompressFormat.JPEG, 90, out);
	    	       //sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED,Uri.parse(root +"/DCIM" +"/PayPerMeter_images")));
	    	       Toast toast = Toast.makeText(this, "Picture saved in "+root +"/DCIM" +"/PayPerMeter_images",Toast.LENGTH_LONG);
	    	       toast.show();
	    	       
	    	       out.flush();
	    	       out.close();

	    	} catch (Exception e) {
	    	       e.printStackTrace();
	    	}
	    }
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.report_driver, menu);
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
