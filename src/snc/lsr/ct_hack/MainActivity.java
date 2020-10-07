package snc.lsr.ct_hack;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Timer;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

    public static String acc,pss,loginMsg;
	TextView console;
	Hack hak=new Hack(this,console);
	MToast t=new MToast(this);
	private void setNotification() {
	    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
	    PendingIntent contextIntent = PendingIntent.getActivity(this, 0, intent, 0);
	    NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
//	    Notification notification = new Notification(R.drawable.icon, getString(R.string.app_name), System.currentTimeMillis());
	    Notification notification = new Notification.Builder(getApplicationContext())
	        .setContentTitle("CT-Hack")
	        .setContentText("Currently Background Running...")
	        .setSmallIcon(R.drawable.ic_launcher)
//	        .setLargeIcon(aBitmap)
	        .addAction(R.drawable.ic_launcher, "Re-connect ChinaNet", contextIntent)
	        .build();
	    notification.flags = Notification.FLAG_ONGOING_EVENT; // 设置常驻 Flag
//	    notification.setLatestEventInfo(getApplicationContext(), getString(R.string.app_name), getString(R.string.information), contextIntent);
	    notificationManager.notify(R.string.app_name, notification);
	}

	private void cancelNotification() {
	    NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
	    notificationManager.cancel(R.string.app_name);
	}

	Runnable hack=new Runnable(){
		public void run(){
		    while(hak.hack());
		    try {
				this.wait();
				tr.run();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	};
	
	Runnable tr=new Runnable(){
		public void run(){
			final java.util.Timer timer;
			timer = new Timer(true);
			timer.schedule(
				new java.util.TimerTask() {
					public void run() {
						try {
				            URL url = new java.net.URL("http://www.baidu.com");
				            HttpURLConnection connection = (java.net.HttpURLConnection)url.openConnection();
				            connection.connect();
				            int status_code = connection.getResponseCode();
				            if(status_code==302)
				            {
				            	t.t("网络连接已断开");
				            	timer.cancel();
				        		hack.run();
				        		this.wait();
				            }else if(status_code==200){
				            	console.setText("您现在已连接到网络\n");
				            }
				        } catch (Exception e) {
				        	t.t("请求出错: " + e.getMessage() + ",请检查网络连接");
				        	e.printStackTrace();
				        }
					}
				}, 0, 5000);
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		console = (TextView) findViewById(R.id.console);

		String a = "ChinaNet Portal Hacking v0.4.0 by Dolphin @BUCT_SNC_SYS.\n";
	    	a += "Copyright 2014 Dolphin Wood.\n";
	    	a += "Licensed under http://opensource.org/licenses/MIT\n\n";
	    	a += "Designed and built with all the love in the world.\n\n";
	    	a += "Everything will be done automatically :)\n\n";
	    	a += "Refactored with JAVA by Nyx @BUCT_SNC_DEV.\n";
	    	a += "进程守护已启动！\n";
	    	
	    console.setText(a);
	    
	    Button button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
	            cancelNotification();
            	finish();
            }
        });

    	setNotification();

    	tr.run();
	}
}
