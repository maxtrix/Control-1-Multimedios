package com.example.control1multimedios;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class Actividad1 extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_actividad1);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.actividad1, menu);
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
	
	public void myClickHandler(View view) {
	    
	    ConnectivityManager connMgr = (ConnectivityManager) 
	        getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
	    if (networkInfo != null && networkInfo.isConnected()) {
	        // fetch data
	    } else {
	        // display error
	    }
	    
	}
	
	
	Thread t = new Thread() {
		public void run() {
	Looper.prepare(); //For Preparing Message Pool for the child Thread
	HttpClient client = new DefaultHttpClient();
	HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000); //Timeout Limit
	HttpResponse response;
	JSONObject json = new JSONObject();
	try {
		HttpPost post = new HttpPost("http://www.mocky.io/v2/5440667984d353f103f697c0");              
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);  
		nameValuePairs.add(new BasicNameValuePair("id", bundle.getString("id")));  
		post.setEntity(new UrlEncodedFormEntity(nameValuePairs));             
		response = client.execute(post);
		/*Checking response */
		if(response!=null){
			String temp = EntityUtils.toString(response.getEntity());
			Log.i("tag", temp);      }

		} catch(Exception e) {
			e.printStackTrace();
		}
	Looper.loop(); //Loop in the message queue
	}
	};
	t.start(); 	
	

	public class MainActivity extends Activity {
	    JSONParser jsonparser = new JSONParser();
	    TextView tv;
	    String ab;
	    JSONObject jobj = null;
	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_main);
	        tv = (TextView) findViewById(R.id.tvResult);
	        new retrievedata().execute();

	    }

	    class retrievedata extends AsyncTask<String,String,String>{

	        @Override
	        protected String doInBackground(String... arg0) {
	            // TODO Auto-generated method stub
	            jobj = jsonparser.makeHttpRequest("http://www.mocky.io/v2/5440667984d353f103f697c0");

	            // check your log for json response
	            Log.d("Login attempt", jobj.toString());

	            ab = jobj.optString("text");
	            return ab;
	        }
	        protected void onPostExecute(String ab){

	            tv.setText(ab);
	        }

	    }

	}
	





public class JSONParser {

	    static InputStream is = null;
	    static JSONObject jobj = null;
	    static String json = "";
	    public JSONParser(){

	    }
	    public JSONObject makeHttpRequest(String url){
	            DefaultHttpClient httpclient = new DefaultHttpClient();
	            HttpPost httppost = new HttpPost(url);
	            try {
	                HttpResponse httpresponse = httpclient.execute(httppost);
	                HttpEntity httpentity = httpresponse.getEntity();
	                is = httpentity.getContent();

	            } catch (ClientProtocolException e) {
	                // TODO Auto-generated catch block
	                e.printStackTrace();
	            } catch (IOException e) {
	                // TODO Auto-generated catch block
	                e.printStackTrace();
	            }

	            try {
	                BufferedReader reader = new BufferedReader(new InputStreamReader(is,"utf-8"),8);
	                StringBuilder sb = new StringBuilder();
	                String line = null;
	                try {
	                    while((line = reader.readLine())!=null){
	                        sb.append(line+"\n");   

	                    }
	                    is.close();
	                    json = sb.toString();
	                    try {
	                        jobj = new JSONObject(json);
	                    } catch (JSONException e) {
	                        // TODO Auto-generated catch block
	                        e.printStackTrace();
	                    }
	                } catch (IOException e) {

	                    e.printStackTrace();
	                }

	            } catch (UnsupportedEncodingException e) {
	                // TODO Auto-generated catch block
	                e.printStackTrace();
	            }

	        return jobj;

	    }

	}
