package nju.androidchat.client.component;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.StyleableRes;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import nju.androidchat.client.R;

public class ItemTextReceive2 extends LinearLayout {
    @StyleableRes
    int index0 = 0;

    private TextView textView;
    private Context context;
    private UUID messageId;
    private OnRecallMessageRequested onRecallMessageRequested;

    private Map<String,R.drawable> imagesMap = new HashMap<>();


    public ItemTextReceive2(Context context, String text, UUID messageId) {
        super(context);
        this.context = context;
        inflate(context, R.layout.item_text_receive2, this);
        ImageView imageView=findViewById(R.id.imgView);
        //if(!text.equals("lml.jpg"))
        //   imageView.setImageDrawable(getResources().getDrawable(R.drawable.error));
        //int icon = context.getResources().getIdentifier(text, "mipmap","nju.androidchat.images");
        //System.out.println(icon);
        //imageView.setImageResource(icon);
        //System.out.println(context.getPackageResourcePath());
        //String path = Environment.getExternalStorageDirectory().getAbsolutePath();
        //System.out.println(path);

        //InputStream is = null;
        //try {
            //is = new URL("nju/androidchat/images/"+text).openStream();
            //is =  new FileInputStream("D:"+ File.separator+"lml.jpg");
        //    is = getClass().getClassLoader().getResourceAsStream("D:\\lml.jpg");
        //} catch (Exception e) {
        //    e.printStackTrace();
        //}
       // Bitmap bitmap = BitmapFactory.decodeStream( is );
//      Bitmap bm = BitmapFactory.decodeFile("./"+text);
        //imageView.setImageBitmap(bitmap);
        //this.textView = findViewById(R.id.chat_item_content_text);
        this.messageId = messageId;
        //setText(text);

//        System.out.println(text);
//        URL url = null;
//        try {
//            url = new URL(text);
//            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
//            conn.setRequestMethod("GET");  //设置请求方法为GET
//            conn.setReadTimeout(5*1000);  //设置请求过时时间为5秒
//            InputStream inputStream = conn.getInputStream();  //通过输入流获得图片数据
//            byte[] data = StreamTool.readInputStream(inputStream);   //获得图片的二进制数据
//            Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length); //生成位图
//            imageView.setImageBitmap(bitmap);  //显示图片
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                String url = text;
                final Bitmap bitmap = getHttpBitmap(url);
//view的post方法是运行在主线程中的，因为所有view都自带一个handler，所有handler都有post方法，子线程无法刷新UI
                imageView.post(new Runnable() {
                    @Override
                    public void run() {
                        imageView.setImageBitmap(bitmap);
                    }
                });
            }
        }).start();



    }

    public static Bitmap getHttpBitmap(String address) {
        URL myFileURL;
        Bitmap bitmap=null;
        try{
            myFileURL = new URL(address);
//获得连接
            HttpURLConnection conn=(HttpURLConnection)myFileURL.openConnection();
//设置超时时间为6000毫秒，conn.setConnectionTiem(0);表示没有时间限制
            conn.setConnectTimeout(6000);
//连接设置获得数据流
            conn.setDoInput(true);
//不使用缓存
            conn.setUseCaches(false);
//这句可有可无，没有影响
            conn.connect();
//得到数据流
            InputStream is = conn.getInputStream();
//解析得到图片
            bitmap = BitmapFactory.decodeStream(is);
//关闭数据流
            is.close();
        }catch(Exception e){
           e.printStackTrace();
        }
        return bitmap;
    }

    public void init(Context context) {

    }

    public String getText() {
        return textView.getText().toString();
    }

    public void setText(String text) {
        textView.setText(text);
    }
}
