## 车险客户系统
### 应用简介  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;本车险客户系统采用MVC架构设计，客户端基于Android平台开发，使用AndroidStudio作为开发工具；服务器后台使用php语言编写，采用PHPStorm作为开发工具；数据库使用的是MySql。在本系统中，Android客户端实现了普通用户订购车险、积分换购商品、接收车险公司发布的活动消息以及提供车险公司提供的各类服务等功能。后台管理系统实现了查看和发布活动消息，查看用户提交的订单，更改订单状态以及查看和录入各种信息进数据库等功能。 

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;整个App的开发充分发挥了面向对象的思想，对很多反复使用的功能进行了封装，对有些功能如列表适配器进行了抽象设计使其满足大部分使用场合，降低了模块间的耦合度，提高了代码的效率。对App的网络层基于Volley进行了二次封装，并使用Java反射技术编写了JavaBean装配器，实现对请求结果转化成JavaBean的过程的自动化。
### APP主要功能模块  
#### 1.登录模块  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;在该模块中，app首先会判断用户本地的token是否存在，若不存在则跳转到注册页面；若存在，则会判断本地的token是否过期，**部分**代码如下：   
```Java
new CheckTokenIsExpired(Config.getCachedPhoneNumberMD5(this), new CheckTokenIsExpired.SuccessCallback() {
                @Override
                public void onSuccess(String token) {
                        Config.cachePhoneNumberMd5(AtyWelcome.this, Config.getCachedPhoneNumberMD5(AtyWelcome.this));
                    Config.cachedToken(AtyWelcome.this, token);
                    Intent intent = new Intent(AtyWelcome.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }, new CheckTokenIsExpired.FailCallback() {
                @Override
                public void onFail(String errorCode) {
                    Intent intent = new Intent(AtyWelcome.this, AtyMain.class);
                    startActivity(intent);
                    finish();
                }
            });

```
#### 2.网络通信模块
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;在该模块中，首先会根传入的值method判断网络请求数据方式是post还是get，然后会确定要相应的请求url和参数，进行网络请求，使用get方式请求的代码如下：
```Java
StringBuffer buffer = new StringBuffer();
for (int i = 0; i < kvs.length; i = i + 2) {
   buffer.append(kvs[i]).append("=").append(kvs[i + 1]).append("&");
}
String serverUrl = url + "?" + buffer.toString();
    stringRequest=new StringRequest(Request.Method.GET, serverUrl, new Response.Listener<String>() {
    @Override
    public void onResponse(String s) {
          parseData(s,successCallback,failCallback,className);
    }
}, new Response.ErrorListener() {
      @Override
      public void onErrorResponse(VolleyError volleyError) {
           if (failCallback != null) {
             ailCallback.onFail(volleyError.getMessage());
           }
      }
  });
```
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;在获得网络请求的返回值后，首先取得json数据中key为“status”的value，若为1，则表示请求数据成功，否则失败。 当请求成功时，首先根据传入的值resultDataType判断返回的数据是json数组还是字符串，若是json字符串则直接将字符串通过接口回调回去，若是json字符串，则根据要生成的bean的类名自动装配成Bean的列表并通过接口回调回去。自动装配Bean的**部分**代码如下：
```Java
ClassLoader loader = Thread.currentThread().getContextClassLoader();
Class clazz = loader.loadClass(className);
Constructor constructor = clazz.getConstructor((Class[]) null);
Object object = constructor.newInstance();
Method[] methods = clazz.getDeclaredMethods();
for (int j = 0; j < methods.length; j++) {
    String methodName = methods[j].getName();
    if (methodName.length() > 3) {
            String methodNameHead = methodName.substring(0, 3);
        String methodNameFiled = methodName.substring(3, methodName.length());
        if (methodNameHead.equals("set")) {
            methods[j].invoke(object, obj.get(methodNameFiled.toLowerCase()));
        }
     }
}
  list.add(object);
}
if (successCallback != null) {
   successCallback.onSuccess(list);
}
```
#### 3.列表适配器模块
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;此列表适配器模块为通用列表适配器模块，在该模块中，通过使用泛型大大扩大了列表的适配对象的范围，并且为使用者提供了填充列表项内容的接口，抽象了适配器的具体适配过程，使适配器的可用范围大大扩大。**部分**关键代码如下:
```Java
public ListAdapter(List<T> list, Context context, AddListItemContent addListItemContent) {
        super();
        this.list=list;
inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.addListItemContent=addListItemContent;
}

public interface AddListItemContent{
    View onAddListItemContent(View convertView,LayoutInflater inflater,int position);
}
```
#### 4.定位模块模块
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;在该模块中，首先获取安卓自带的定位管理器（LocationManager），然后实例化定位监听接口，代码如下：
```Java
LocationManager mLocationManager= (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
LocationListener mLocationListener=new LocationListener() {
    @Override
    public void onLocationChanged(Location location) {
        mLocation=location;
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.i("network_provider:", provider + "开启");
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.i(Config.ERROR, provider + "无法开启");
    }
};
```
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;最后通过使用网络定位获取包含手机经度和纬度的Location类对象返回。代码如下：
```Java
mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, mLocationListener);
mLocation=mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
```
#### 5.网络图片加载模块
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;在该模块中，首先会判断所有获取的网络图片是否已缓存过，若已缓存，则直接从图片缓存中取出改图片，代码如下：
```Java
if(imageLoader.isCached(imageUrl,maxWidth,maxHeight)){
String cacheKey=newStringBuilder(imageUrl.length()+12).append("#W").append(maxWidth).append("#H").append(maxHeight).append("#S").append     (ImageView.ScaleType.CENTER_INSIDE.ordinal()).append(imageUrl) .toString();
    imageView.setImageBitmap(bitmapCache.getBitmap(cacheKey));
}
```
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;若未缓存，则会使用图片加载器imageLoader加载该图片,代码如下：
```Java
else{
    getImageLoader().get(imageUrl,newcom.android.volley.toolbox.ImageLoader.ImageListener() {
        @Override
        public void onResponse(com.android.volley.toolbox.ImageLoader.ImageContainer imageContainer, boolean b) {
            imageView.setImageBitmap(imageContainer.getBitmap());
        }

        @Override
        public void onErrorResponse(VolleyError volleyError) {
            Log.i(Config.DEBUG,"volleyError:"+volleyError);
        }
    },maxWidth,maxHeight);
}
```
#### 6.MD5加密模块
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;在该模块中，首先使用md5算法对象生成一个加密后的128位的二进制码，然后将128位的二进制码转化成32位的16进制码。
```Java
public class MD5Tool {
	public static String getMD5(String message) {
		String md5str = "";
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] input = message.getBytes();
			byte[] buff = md.digest(input);
			md5str = bytesToHex(buff);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return md5str;
	}

	public static String bytesToHex(byte[] bytes) {
		StringBuffer md5str = new StringBuffer();
		int digital;
		for (int i = 0; i < bytes.length; i++) {
			digital = bytes[i];
			if (digital < 0) {
				digital += 256;
			}
			if (digital < 16) {
				md5str.append("0");
			}
			md5str.append(Integer.toHexString(digital));
		}
		return md5str.toString().toUpperCase();
	}
}
```
### 应用截图
![](https://github.com/vincent0929/AutoInsurance/blob/master/image/1.png) ![](https://github.com/vincent0929/AutoInsurance/blob/master/image/2.png) ![](https://github.com/vincent0929/AutoInsurance/blob/master/image/3.png) ![](https://github.com/vincent0929/AutoInsurance/blob/master/image/4.png) ![](https://github.com/vincent0929/AutoInsurance/blob/master/image/5.png) ![](https://github.com/vincent0929/AutoInsurance/blob/master/image/6.png) ![](https://github.com/vincent0929/AutoInsurance/blob/master/image/7.png) ![](https://github.com/vincent0929/AutoInsurance/blob/master/image/8.png) ![](https://github.com/vincent0929/AutoInsurance/blob/master/image/9.png) ![](https://github.com/vincent0929/AutoInsurance/blob/master/image/10.png) ![](https://github.com/vincent0929/AutoInsurance/blob/master/image/11.png) ![](https://github.com/vincent0929/AutoInsurance/blob/master/image/12.png) ![](https://github.com/vincent0929/AutoInsurance/blob/master/image/13.png) ![](https://github.com/vincent0929/AutoInsurance/blob/master/image/14.png) ![](https://github.com/vincent0929/AutoInsurance/blob/master/image/15.png)
