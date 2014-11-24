package com.techathon2014.paypermeter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.ibm.mobile.services.core.IBMBluemix;
import com.ibm.mobile.services.data.IBMData;



public class MainActivity extends ActionBarActivity{

    Button mBtnFind;
    GoogleMap mMap;
    SupportMapFragment mMapFragment;
    ArrayList<LatLng> markerPoints1;
    ArrayList<LatLng> markerPoints;
    ArrayList<LatLng> markerPoints2;
    EditText startL;
    EditText endL;
    String startText;
    String endText;
    String url;
    int flag;
    double shortest;
    double approx;
    double totdist;
    double mincost=25;
    double mindist=1.9;
    double diffdist;
    int cost;
    String provider;
    Location oldLocation;
    float[] dist=new float[1];
    long res; 
    LocationManager locationManager;
    LocationListener locationListener;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);  
        
        flag=0;
        
        IBMBluemix.initialize(this,"3e44e59b-8a9b-42a3-9f47-c6fc26e8cfb6", "486cfd86233c14320761078dff9b3077b3edf9e4", "PayPerMeter.mybluemix.net");
        IBMData.initializeService();
        TripDetails.registerSpecialization(TripDetails.class);
        
  
    }
    
    public double displayApprox()
    {
    	if(startL.getText().toString()=="" || endL.getText().toString()==""){
    		Toast.makeText(this,"Enter both Start Point and Destination Point",Toast.LENGTH_LONG).show();
    	}
    	shortest = shortest/1000;
    	if(shortest>mindist){
    	totdist = shortest;
    	diffdist = totdist - mindist; 
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
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        
        markerPoints1 = new ArrayList<LatLng>();
        markerPoints2 = new ArrayList<LatLng>();
        
        mMapFragment = ((SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map));
        mMap = mMapFragment.getMap();
        mMap.setMyLocationEnabled(true);

        mMapFragment.getView().setVisibility(View.VISIBLE);
        
        startL = (EditText)findViewById(R.id.Start_Location);
        startL.setOnFocusChangeListener(new OnFocusChangeListener() {          

            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                	startText = startL.getText().toString();
                	url = "https://maps.googleapis.com/maps/api/geocode/json?";
                	
                	if(startText==null || startText.equals("")){
                        Toast.makeText(getBaseContext(), "No Start point is entered", Toast.LENGTH_SHORT).show();
                        return;
                	}
                    
                    
                    try {
                        // encoding special characters like space in the user input place
                        startText = URLEncoder.encode(startText, "utf-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    flag=1;
                    String address = "address=" + startText;
                    
                    String sensor = "sensor=false";
     
                    // url , from where the geocoding data is fetched
                    url = url + address + "&" + sensor;
                    
                    // Instantiating DownloadTask to get places from Google Geocoding service
                    // in a non-ui thread
                    DownloadTask downloadTask = new DownloadTask();
     
                    // Start downloading the geocoding places
                    downloadTask.execute(url);
                    
                    
                }
     
            }

        });
   
        
        endL = (EditText)findViewById(R.id.EditText01);
        endL.setOnFocusChangeListener(new OnFocusChangeListener() {          

            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                	endText = endL.getText().toString();
                	url = "https://maps.googleapis.com/maps/api/geocode/json?";
                	
                	if(endText==null || endText.equals("")){
                        Toast.makeText(getBaseContext(), "No Destination point is entered", Toast.LENGTH_SHORT).show();
                        return;
                	}

                    try {
                        // encoding special characters like space in the user input place
                        endText = URLEncoder.encode(endText, "utf-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    flag=2;
                    String address = "address=" + endText;
                    
                    String sensor = "sensor=false";
     
                    // url , from where the geocoding data is fetched
                    url = url + address + "&" + sensor;
                    
                    // Instantiating DownloadTask to get places from Google Geocoding service
                    // in a non-ui thread
                    DownloadTask downloadTask = new DownloadTask();
     
                    // Start downloading the geocoding places
                    downloadTask.execute(url);
                }
                
                
            }
        });
        
        
                    
        
        final Button calcapprox = (Button)findViewById(R.id.CalculateApprox);
		calcapprox.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				cost=(int)displayApprox();
				calcapprox.setText("The Appoximate Fare is Rs."+ cost);
				}  
		});
        
    	final Button button = (Button)findViewById(R.id.StartJourney);
    	button.setTag(1);
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				int status =(Integer)view.getTag();
				if(status == 1) { 
				    button.setText("Stop Journey");
				   	button.setTag(0); //Stop
				   	status = (Integer)view.getTag();
				   	startTracking();
				} 
				if(status == 0) {
						final Button stopbutton = (Button)findViewById(R.id.StartJourney);
						stopbutton.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View v) {
								locationManager.removeUpdates(locationListener);
								Intent intent = new Intent(MainActivity.this,DistanceTravelled.class);	
								intent.putExtra("DistanceTravelled",res);
								intent.putExtra("ApproxFare",cost);
								startActivity(intent);
							}
						});
				}	
						
				}
		});	
        return true;
        
    }

    
    public void startTracking(){
    	locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE); 
    	locationListener = new LocationListener() {
    	int i=0;
    	
    	    @Override
    	    public void onStatusChanged(String provider, int status, Bundle extras) {
    	    }
    	 
    	    @Override
    	    public void onProviderEnabled(String provider) {
    	        Toast.makeText(MainActivity.this,"Provider enabled: " + provider, Toast.LENGTH_SHORT).show();
    	    }
    	 
    	    @Override
    	    public void onProviderDisabled(String provider) {
    	        Toast.makeText(MainActivity.this,
    	                "Provider disabled: " + provider, Toast.LENGTH_SHORT)
    	                .show();
    	    }
    	 
    	    @Override
    	    public void onLocationChanged(Location location) {
    	    	if(i==0) {
    	    		res=0;
    	    		oldLocation=location;
    	    	}
    	        // Do work with new location
    	        doWorkWithNewLocation(location);
    	        i++;
    	    }
    	};
    	 
    	long minTime = 5 * 1000; // Minimum time interval for update in seconds, i.e. 5 seconds.
    	long minDistance = 10; // Minimum distance change for update in meters, i.e. 10 meters.
    	 
    	// Assign LocationListener to LocationManager in order to receive location updates.
    	// Acquiring provider that is used for location updates will also be covered later.
    	// Instead of LocationListener, PendingIntent can be assigned, also instead of 
    	// provider name, criteria can be used, but we won't use those approaches now.
    	provider=getProviderName();
    	locationManager.requestLocationUpdates((provider), minTime, minDistance, locationListener);
    	
    }
    
    void doWorkWithNewLocation(Location location) {
    	
        double oldlat1 = oldLocation.getLatitude();
        double oldlon1 = oldLocation.getLongitude(); 
        double lat1=location.getLatitude();
        double lon1=location.getLongitude();
        
        Location.distanceBetween(oldlat1,oldlon1,lat1,lon1,dist);
        res=res+(long)dist[0];
     
        setOldLocation(location);
    }
     
    public Location getOldLocation()
    {
    	return oldLocation;
    }
    
 
    public void setOldLocation(Location loc)
    {
    	oldLocation=loc;
    }
    /**
    * Time difference threshold set for one minute.
    */
    static final int TIME_DIFFERENCE_THRESHOLD = 1 * 60 * 1000;
     
    boolean isBetterLocation(Location oldLocation, Location newLocation) {
        // If there is no old location, of course the new location is better.
        if(oldLocation == null) {
            return true;
        }
     
        // Check if new location is newer in time.
        boolean isNewer = newLocation.getTime() > oldLocation.getTime();
     
        // Check if new location more accurate. Accuracy is radius in meters, so less is better.
        boolean isMoreAccurate = newLocation.getAccuracy() < oldLocation.getAccuracy();       
        if(isMoreAccurate && isNewer) {         
            // More accurate and newer is always better.         
            return true;     
        } else if(isMoreAccurate && !isNewer) {         
            // More accurate but not newer can lead to bad fix because of user movement.         
            // Let us set a threshold for the maximum tolerance of time difference.         
            long timeDifference = newLocation.getTime() - oldLocation.getTime(); 
     
            // If time difference is not greater then allowed threshold we accept it.         
            if(timeDifference > -TIME_DIFFERENCE_THRESHOLD) {
                return true;
            }
        }
        return false;
    }
    
    String getProviderName() {
        LocationManager locationManager = (LocationManager) this
                .getSystemService(Context.LOCATION_SERVICE);
     
        Criteria criteria = new Criteria();
        criteria.setPowerRequirement(Criteria.POWER_LOW); // Chose your desired power consumption level.
        criteria.setAccuracy(Criteria.ACCURACY_FINE); // Choose your accuracy requirement.
        criteria.setSpeedRequired(true); // Chose if speed for first location fix is required.
        criteria.setAltitudeRequired(false); // Choose if you use altitude.
        criteria.setBearingRequired(false); // Choose if you use bearing.
        criteria.setCostAllowed(false); // Choose if this provider can waste money :-)
     
        // Provide your criteria and flag enabledOnly that tells
        // LocationManager only to return active providers.
        return locationManager.getBestProvider(criteria, true);
    }
    
    private String downloadUrl(String strUrl) throws IOException{
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try{
            URL url = new URL(strUrl);
            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();
 
            // Connecting to url
            urlConnection.connect();
 
            // Reading data from url
            iStream = urlConnection.getInputStream();
 
            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));
 
            StringBuffer sb = new StringBuffer();
 
            String line = "";
            while( ( line = br.readLine()) != null){
                sb.append(line);
            }
 
            data = sb.toString();
            br.close();
 
        }catch(Exception e){
            Log.d("Exception while downloading url", e.toString());
        }finally{
            iStream.close();
            urlConnection.disconnect();
        }
 
        return data;
    }
    /** A class, to download Places from Geocoding webservice */
    private class DownloadTask extends AsyncTask<String, Integer, String>{
 
        String data = null;
 
        // Invoked by execute() method of this object
        @Override
        protected String doInBackground(String... url) {
            try{
                data = downloadUrl(url[0]);
            }catch(Exception e){
                Log.d("Background Task",e.toString());
            }
            return data;
        }
 
        // Executed after the complete execution of doInBackground() method
        @Override
        protected void onPostExecute(String result){
 
            // Instantiating ParserTask which parses the json data from Geocoding webservice
            // in a non-ui thread
            ParserTask parserTask = new ParserTask();
 
            // Start parsing the places in JSON format
            // Invokes the "doInBackground()" method of the class ParseTask
            parserTask.execute(result);
        }
    }
 
    /** A class to parse the Geocoding Places in non-ui thread */
    class ParserTask extends AsyncTask<String, Integer, List<HashMap<String,String>>>{
 
        JSONObject jObject;
 
        // Invoked by execute() method of this object
        @Override
        protected List<HashMap<String,String>> doInBackground(String... jsonData) {
 
            List<HashMap<String, String>> places = null;
            GeocodeJSONParser parser = new GeocodeJSONParser();
 
            try{
                jObject = new JSONObject(jsonData[0]);
 
                /** Getting the parsed data as a an ArrayList */
                places = parser.parse(jObject);
 
            }catch(Exception e){
                Log.d("Exception",e.toString());
            }
            return places;
        }
 
        // Executed after the complete execution of doInBackground() method
        @Override
        protected void onPostExecute(List<HashMap<String,String>> list){
 
            // Clears all the existing markers
            mMap.clear();
 
            for(int i=0;i<list.size();i++){
 
                // Creating a marker
                MarkerOptions markerOptions = new MarkerOptions();
 
                // Getting a place from the places list
                HashMap<String, String> hmPlace = list.get(i);
 
                // Getting latitude of the place
                double lat = Double.parseDouble(hmPlace.get("lat"));
 
                // Getting longitude of the place
                double lng = Double.parseDouble(hmPlace.get("lng"));
 
                // Getting name
                String name = hmPlace.get("formatted_address");
 
                LatLng latLng = new LatLng(lat, lng);
 
                // Setting the position for the marker
                markerOptions.position(latLng);
 
                // Setting the title for the marker
                markerOptions.title(name);
 
                // Placing a marker on the touched position
                mMap.addMarker(markerOptions);
                
                if(flag==1)
                	markerPoints1.add(latLng);
                else if(flag==2)
                	markerPoints2.add(latLng);
 
                // Locate the first location
                if(i==0) {
                    mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                }
            }
            
            for(int i=0;i<markerPoints1.size();i++){
            	for (int j=0;j<markerPoints2.size();j++){
            		LatLng pos1 = markerPoints1.get(i);
            		LatLng pos2 = markerPoints2.get(j);
            		
            	    double lat1 = pos1.latitude;
            	    double lon1 = pos1.longitude;
            	    
            	    double lat2 = pos2.latitude;
            	    double lon2 = pos2.longitude;
            	    
            	    double dLat = Math.toRadians(lat2-lat1);
            	    double dLon = Math.toRadians(lon2-lon1);
            	    double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
            	    Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
            	    Math.sin(dLon/2) * Math.sin(dLon/2);
            	    double c = 2 * Math.asin(Math.sqrt(a));
            	    
            	    double distance = 6366000 * c;
            	    
            	    if(i==0 && j==0)
            	    {
            	    	shortest = distance;
            	    }
            	    else
            	    {
            	    	if(distance<shortest)
            	    		shortest=distance;
            	    }   
            	               	    
            	}	
            }
            
            
            Toast message = Toast.makeText(getBaseContext(),String.valueOf(shortest),Toast.LENGTH_SHORT);
            message.show();
            
        }
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
