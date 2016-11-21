package com.example.lp.lastpictures;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TabHost;

/**
 * Created by Rete on 2016-11-21.
 */

public class SelectFindActivity {
    public String[] arrlist={"1번", "2번", "3번"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectfind);
        //SearchView searchView1 = (SearchView) findViewById(R.id.searchView);

        //<<<<<<<<<<<<탭 구현>>>>>>>>>>>>>>>>>>>
        TabHost tabHost = (TabHost) findViewById(R.id.tabHost);
        tabHost.setup();

        TabHost.TabSpec sp1 = tabHost.newTabSpec("tab1");
        sp1.setIndicator("GPS검색");
        //sp1.setIndicator("GPS검색", getResources().getDrawable(R.drawable.gps)); 이미지 넣는코드
        sp1.setContent(R.id.tab1);
        tabHost.addTab(sp1);

        TabHost.TabSpec sp2 = tabHost.newTabSpec("tab2");
        sp2.setIndicator("직접 검색");
        //sp2.setIndicator(ContextCompat.getDrawable(context, R.drawable.search);); 이미지 넣는코드
        //ContextCompat.getDrawable(, R.drawable.search);

        sp2.setContent(R.id.tab2);
        tabHost.addTab(sp2);


        //탭호스트 색지정
        /**
         for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) {
         // tabHost.getTabWidget().getChildAt(0).setBackgroundColor(Color.parseColor(#코드)); 탭호스트 색지정
         RelativeLayout relLayout = (RelativeLayout)tabHost.getTabWidget().getChildAt(i);
         TextView tv = (TextView)relLayout.getChildAt(i);
         tv.setTextColor(Color.parseColor("#FFFFFF"));
         }**/




        //searchView1.setIconifiedByDefault(false);




        //<<<<<<<<<<<<<<<<리스트 뷰 구현>>>>>>>>>>>>>>>>>>>>>>>
        ArrayAdapter<String> Adapter1;
        Adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrlist);
        ListView list1 = (ListView)findViewById(R.id.listView2);
        list1.setAdapter(Adapter1);
        list1.setOnItemClickListener((AdapterView.OnItemClickListener) this);
    }
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l){
        String cList =arrlist[i];
        Intent intent1 = new Intent(SelectFindActivity.this, SearchMenu.class);
        intent1.putExtra("arr_text", cList);
        startActivity(intent1);

    }
    /**private SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {

    @Override
    public boolean onQueryTextSubmit(String query) {
    InputMethodManager imm= (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
    imm.hideSoftInputFromWindow(searchView1.getWindowToken(), 0);
    searchView1.setQuery("", false);
    searchView1.setIconified(true);
    Toast.makeText(getActivity(), "search 결과", Toast.LENGTH_LONG).show();
    return false;
    }
    @Override
    public boolean onQueryTextChange(String newText) {
    // TODO Auto-generated method stub
    return false;
    }
    };**/  //search View 사용하는 코드 같은데 일단 주석처리


}
