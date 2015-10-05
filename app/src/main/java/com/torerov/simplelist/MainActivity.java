package com.torerov.simplelist;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    ArrayAdapter<String> mAdapter;
    EditText inputView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inputView = (EditText)findViewById(R.id.edit_input);
        Button button = (Button)findViewById(R.id.button);
        // add 버튼 클릭 시 mAdapter에 text추가.
        // 그리고 text가 추가된 부분으로 스크롤이 부드럽게 내려감.
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = inputView.getText().toString();
                mAdapter.add(text);
                listView.smoothScrollToPosition(mAdapter.getCount() - 1);
            }
        });

        // 리스트를 클릭 시 해당 리스트의 text를 Toast메시지로 띄워줌.
        listView = (ListView)findViewById(R.id.listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String text = (String)listView.getItemAtPosition(position);
                //Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT).show();

            }
        });
        //mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1); //어댑터 생성
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice);
        listView.setAdapter(mAdapter); //리스트 뷰에 어댑터 설정
        //list 선택 모드
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        //listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        initData();

        button = (Button)findViewById(R.id.btn_choice);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listView.getChoiceMode() == ListView.CHOICE_MODE_SINGLE){
                    int position = listView.getCheckedItemPosition();
                    String text = (String)listView.getItemAtPosition(position);
                    Toast.makeText(MainActivity.this, text , Toast.LENGTH_SHORT).show();
                } else if(listView.getChoiceMode() == ListView.CHOICE_MODE_MULTIPLE){
                    SparseBooleanArray selection = listView.getCheckedItemPositions();
                    //String을 계속 추가하기 때문에 StringBuilder 사용, String 변경은 StringBuffer 사용
                    StringBuilder sb = new StringBuilder();
                    for(int index = 0; index < selection.size(); index++){
                        int position = selection.keyAt(index);
                        if(selection.get(position)){
                            String text = (String)listView.getItemAtPosition(position);
                            sb.append(text).append(", ");
                        }
                    }
                    Toast.makeText(MainActivity.this, "choice : " + sb.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initData() {
        String[] items = getResources().getStringArray(R.array.list_item);
        for(String s : items){
            mAdapter.add(s);
        }
    }
}
