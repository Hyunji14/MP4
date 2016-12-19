package com.example.lp.lastpictures;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
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
import java.util.ArrayList;

/**
 * Created by Hwang on 2016-12-17.
 */

public class FindAddressActivity extends Activity {

    String clientId = "6tbLoSmdmKXBTMRK3uO3";
    String clientSecret = "o02DkUdPPM";

    ListView status1 ;
    TextView status ;
    TextView status2 ;
    boolean inItem = false;


    ArrayList<CafeData> mDatas  = new ArrayList<CafeData>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_findaddress);

        status = (TextView)findViewById(R.id.status);
        status1 = (ListView)findViewById(R.id.status1); //파싱된 결과출력
        status2 = (TextView)findViewById(R.id.status2);


        new Thread() {//쓰레드
            public void run() {
                String naverHtml = getNaverHtmlTitle();
                Bundle bun = new Bundle();

                bun.putString("NAVER_HTML", naverHtml);
                Message msg = handler.obtainMessage();
                msg.setData(bun);
                handler.sendMessage(msg);
            }
        }.start();

    }



    private String getNaverHtmlTitle(){//주소를 가지고local 검색api를 이용하여 주변 카페의 목록을 가져온다
        Intent intent = getIntent();
        String intent_address = intent.getStringExtra("주소");
        //String query = "대전시 유성구 용산동 근처 카페";
        String query = intent_address+" 근처 카페";

        //System.out.println("RQMkml"+query1);

        try{//utf-8 인코딩
            query = URLEncoder.encode(query, "utf-8");
        }catch (UnsupportedEncodingException e1){

        }

        String title ="";
        String address ="";

        String naverHtml_title = "";
        String naverHtml_address = "";
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
                response.append(inputLine);//xml로 받은 결과

            }
            br.close();
            //System.out.println("Rbdkkkkkdkdkdk"+response.toString());


            //xml 파싱 시작
            XmlPullParserFactory parserCreator = XmlPullParserFactory.newInstance();
            XmlPullParser parser = parserCreator.newPullParser();

            parser.setInput(new StringReader(response.toString()));

            int parserEvent = parser.getEventType();


            while (parserEvent != XmlPullParser.END_DOCUMENT){

                switch(parserEvent){
                    case XmlPullParser.END_DOCUMENT://문서의 끝
                        break;
                    case XmlPullParser.START_DOCUMENT://문서 시작
                        break;
                    case XmlPullParser.END_TAG://엔드 태그를 만날때
                        if (parser.getName().equals("item")) {

                                naverHtml_title = title;
                                naverHtml_address = address;

                                mDatas.add(new CafeData(naverHtml_title, naverHtml_address));

                            naverHtml = "파싱끝";
                        }

                    case XmlPullParser.START_TAG://시작 태그를 만날 때
                        String tag = parser.getName();

                        if(tag.equals("item") ){
                            inItem = true;//카페의 시작
                        }
                        if(tag.equals("title")){//카페이름

                            if (inItem) {
                                title = parser.nextText();

                                String[] sp_title = title.toString().split("<b>카페</b>");//b태그를 만날때 b태그 삭제
                                if (sp_title.length > 1) {
                                    title = sp_title[1];
                                    for (int i = 0; i < sp_title.length; i++)
                                        System.out.println(sp_title[i] + " " + i);
                                } else {
                                    title = sp_title[0];
                                }
                            }
                        }
                        if(tag.equals("address")){//주소
                            if (inItem) {
                                address = parser.nextText();
                            }
                        }

                }
                parserEvent = parser.next();//다음 목록
            }
            //status2.setText("파싱 끝!");
        } catch(Exception e){
            //status1.setText("삐빅, 에러입니다");
            naverHtml = status.getText()+"삐빅 에러데스네";
        }

        return naverHtml;
    }


    Handler handler = new Handler() {//핸들러
        public void handleMessage(Message msg) {
            Bundle bun = msg.getData();
            String naverHtml_title = bun.getString("NAVER_HTML");

            CafeDataAdapter adapter = new CafeDataAdapter(getLayoutInflater(),mDatas);
            status1.setAdapter(adapter);
            status1.setOnItemClickListener(new AdapterView.OnItemClickListener(){//리스트뷰 클릭 이벤트

                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    String cafe_name = mDatas.get(i).getTitle();
                    //Toast.makeText(FindAddressActivity.this, cafe_name , Toast.LENGTH_SHORT).show();

                    Intent intent1 = new Intent(FindAddressActivity.this, SearchMenuActivity.class);
                    intent1.putExtra("Cafe", cafe_name);//클릭하면 다음 액티비티로 넘긴다
                    startActivity(intent1);
                }
            });

        }
    };



    public class CafeData{//카페데이터 저장용
        String title;
        String address;

        public CafeData(String title, String address) {
            this.address = address;
            this.title = title;
        }

        public void setTitle(String title){
            this.title = title;
        }
        public void setAddress(String address){
            this.address = address;
        }
        public String getTitle(){
            return title;
        }
        public String getAddress(){
            return address;
        }

    }
}
