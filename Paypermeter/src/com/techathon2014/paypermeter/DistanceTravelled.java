package com.techathon2014.paypermeter;

import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
//import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class DistanceTravelled extends ActionBarActivity {
	long res;
	int cost;
	int actual;
    double mincost=25;
    double mindist=1.9;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_distance_travelled);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        
		Intent intent = getIntent();
		res=intent.getLongExtra("DistanceTravelled",0);
		cost=intent.getIntExtra("ApproxFare",0);

		
	    TextView t=(TextView)findViewById(R.id.textView2); 
	    
	    t.append(" is Rs."+ String.valueOf(cost));
		//Toast.makeText(DistanceTravelled.this,"Distance is "+ res,Toast.LENGTH_SHORT).show();
	    
	    t=(TextView)findViewById(R.id.TextView01); 
	    actual=(int)displayApprox(res);
	    t.append(" is Rs."+ actual);
	}
	
    public double displayApprox(long shortest)
    {
    	if(shortest>mindist)
    	{
    	double totdist = shortest/1000;
    	double diffdist = totdist - mindist; 
    	return mincost + (diffdist*13);  
    	}
    	else if(shortest<=mindist && shortest!=0)
    	{
    		return mincost;
    	}
    	else if(shortest==0){
    		return 0;
    	}
    	else
    		return 0;
    }
    
	public void clickReport(View view) {
		//final Button reportbutton = (Button)findViewById(R.id.report);
		//reportbutton.setOnClickListener(new OnClickListener() {
			//@Override
			//public void onClick(View v) {
				Intent intent = new Intent(DistanceTravelled.this,ReportDriver.class);							
				startActivity(intent);
		//	}
		//});
	}
	
	public void closeApp(View view){
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.distance_travelled, menu);
		
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			//return true;
		}
	    switch (id) {
	    // Respond to the action bar's Up/Home button
	    case android.R.id.home:
	        NavUtils.navigateUpFromSameTask(this);
	        return true; 
	    }
		return super.onOptionsItemSelected(item);
	}
}
