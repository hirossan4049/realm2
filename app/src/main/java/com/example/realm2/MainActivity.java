package com.example.realm2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;
import java.util.jar.Attributes;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    EditText editText;
    EditText numberText;
    ArrayAdapter adapter;
    int count;

    public Realm realm;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        realm = Realm.getDefaultInstance();//realm_start

        listView = (ListView)findViewById(R.id.listView);
        editText = (EditText)findViewById(R.id.editText);
        numberText = (EditText)findViewById(R.id.numberText);

        Button send = findViewById(R.id.button);
        adapter= new ArrayAdapter(this,android.R.layout.simple_list_item_1);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ArrayAdapter adapter = (ArrayAdapter)listView.getAdapter();

                String item = (String)adapter.getItem(position);
                adapter.remove(item);

            }
        });
    }


    public void button(View v) {
        EditText edittext = findViewById(R.id.editText);
        EditText edittext1 = findViewById(R.id.numberText);
        //使う変数はfinal
        final String text = editText.getText().toString();
        final String number_text = numberText.getText().toString();
        Toast.makeText(this,"追加しました",Toast.LENGTH_LONG).show();

        //Realm データ登録
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                RelmCard number = bgRealm.createObject(RelmCard.class);
                number.Name =text;
                number.Number=Integer.valueOf(number_text);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                // Transaction was a success.
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                // Transaction failed and was automatically canceled.
            }
        });

        //データの読み出し
        RealmResults<RelmCard> result = realm.where(RelmCard.class)
                //.equalTo("Name",text)
                .findAllAsync();

        adapter.add(result.asas);
        editText.getText().clear();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();

        realm.close();
    }

}
