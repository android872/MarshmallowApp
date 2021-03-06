package com.example.networks.marshmallowapp.LocalDataStorage;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.networks.marshmallowapp.LocalDataStorage.model.DataItem;
import com.example.networks.marshmallowapp.R;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        //String itemId = getIntent().getExtras().getString(DataItemAdapter.ITEM_ID_KEY);
       // DataItem item = SampleDataProvider.dataItemMap.get(itemId);

       // Toast.makeText(this, "Received item " + item.getItemName(),
        //        Toast.LENGTH_SHORT).show();

        DataItem item = getIntent().getExtras().getParcelable(DataItemAdapter.ITEM_KEY);
        if (item != null) {
            Toast.makeText(this, "Received item " + item.getItemName(),
                    Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Didn't receive any data", Toast.LENGTH_SHORT).show();
        }


    }
}
