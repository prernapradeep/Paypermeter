package com.techathon2014.paypermeter;

import java.io.FileNotFoundException;
import java.io.IOException;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

public class SubmitPhotoComplaint extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_submit_photo_complaint);
		Bitmap bitmap = null;
		Intent intent = getIntent();
		Uri selectedImage = intent.getParcelableExtra("ImageUri");
		try{
			bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
		}
		 catch (FileNotFoundException e) {
             e.printStackTrace();
         } catch (IOException e) {
        	 e.printStackTrace();
         }
		ImageView imgView = (ImageView)findViewById(R.id.driverphoto);
        imgView.setImageBitmap(bitmap);
	}
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.submit_photo_complaint, menu);
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
