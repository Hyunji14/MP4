package com.example.lp.lastpictures;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Hwang on 2016-12-17.
 */

public class FindAddressActivity extends Activity{

    String clientId = "6tbLoSmdmKXBTMRK3uO3";
    String clientSecret = "o02DkUdPPM";

    TextView status1 ;
    TextView status ;
    TextView status2 ;
    boolean inItem = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_findaddress);

        status = (TextView)findViewById(R.id.status);
        status1 = (TextView)findViewById(R.id.status1); //파싱된 결과를 보자
        status2 = (TextView)findViewById(R.id.status2);


        //Toast.makeText(this, address, Toast.LENGTH_SHORT).show();


        new Thread() {
            public void run() {
                String naverHtml = getNaverHtml();

                Bundle bun = new Bundle();
                bun.putString("NAVER_HTML", naverHtml);
                Message msg = handler.obtainMessage();
                msg.setData(bun);
                handler.sendMessage(msg);
            }
        }.start();



    }

    private String getNaverHtml(){
        Intent intent = getIntent();
        String intent_address = intent.getStringExtra("주소");
        //String query = "대전시 유성구 용산동 근처 카페";
        String query = intent_address+" 근처 카페";

        //System.out.println("RQMkml"+query1);

        try{
            query = URLEncoder.encode(query, "utf-8");
        }catch (UnsupportedEncodingException e1){

        }

        String title ="";
        String address ="";

        String naverHtml = "";

        try{
            URL url = new URL("https://openapi.naver.com/v1/search/local.xml?query="+ query);//xml로 호출

            HttpURLConnection con = (HttpURLConnection)url.openConnection();

            con.setRequestMethod("GET");
            con.setRequestProperty("X-Naver-Client-Id", clientId);
            con.setRequestProperty("X-Naver-Client-Secret", clientSecret);

//            //Toast.makeText(this, "여기까지", Toast.LENGTH_SHORT).show();
            int responseCode = con.getResponseCode();
//
//
            BufferedReader br;
            if(responseCode==200) { // 정상 호출
               br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            }
            else {  // 에러 발생
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
//
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();
            System.out.println("Rbdkkkkkdkdkdk"+response.toString());


            ///InputStream is = url.openStream();
            XmlPullParserFactory parserCreator = XmlPullParserFactory.newInstance();
            XmlPullParser parser = parserCreator.newPullParser();

            parser.setInput(new StringReader(response.toString()));

            int parserEvent = parser.getEventType();


            while (parserEvent != XmlPullParser.END_DOCUMENT){
                switch(parserEvent){
                    case XmlPullParser.END_DOCUMENT://문서의 끝
                        break;
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        String tag = parser.getName();
                        //System.out.println("parserGETName"+parser.getName());
                        //parser가 시작 태그를 만나면 실행
                        if(tag.equals("item") ){
                            inItem = true;
                            //System.out.println("parserGETName1 "+inItem);
                        }
                        if(tag.equals("title")){
                            //System.out.println("parserGETName2 "+inItem);
                            if (inItem) {
                                title = parser.nextText();
                                //System.out.println("parserNext "+parser.nextText());
                               // System.out.println("parserGET"+parser.getText());
                            }
                        }
                        if(tag.equals("address")){
                            if (inItem) {
                                address = parser.nextText();
                            }
                        }

                    case XmlPullParser.END_TAG:
                        if(parser.getName().equals("item")){
                            naverHtml = status1.getText()+"상호 : "+ title +"\n주소 : "+ address +"\n\n";
                            //inItem = false;
                        }
                        break;

                }
                parserEvent = parser.next();
            }
            //status2.setText("파싱 끝!");
        } catch(Exception e){
            //status1.setText("삐빅, 에러입니다");
            naverHtml = status1.getText()+"삐빅 에러데스네";
        }

        return naverHtml;
    }

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            Bundle bun = msg.getData();
            String naverHtml = bun.getString("NAVER_HTML");
            status1.setText(naverHtml);
        }
    };
}
