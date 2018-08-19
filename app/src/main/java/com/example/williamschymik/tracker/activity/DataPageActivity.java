package com.example.williamschymik.tracker.activity;

import android.content.Intent;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.williamschymik.tracker.DataPage;
import com.example.williamschymik.tracker.DataPageHandler;
import com.example.williamschymik.tracker.DataLogHandler;
import com.example.williamschymik.tracker.components.NumericComponent;
import com.example.williamschymik.tracker.components.OptionsComponent;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;

public class DataPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String name = getIntent().getStringExtra("DATA_PAGE_NAME");

        final DataPageHandler datapageHandler = DataPageHandler.getInstance(this);
        DataPage dataPage = datapageHandler.getDataPage(name);

        List<DataPage.DataPageComponent> components = dataPage.components;

        // Base layout
        LinearLayout baseLayout = new LinearLayout(this);
        baseLayout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams baseLayoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        baseLayout.setLayoutParams(baseLayoutParams);

        OptionsComponent optionsComponent = new OptionsComponent();
        NumericComponent numericComponent = new NumericComponent();
        for (DataPage.DataPageComponent component : components) {
            Log.i(TAG, component.type);
            switch(component.type) {
                case("options"):
                    baseLayout.addView(optionsComponent.getView(this, component));
                    break;
                case("numeric"):
                    baseLayout.addView(numericComponent.getView(this, component));
                    break;
                default:
                    assert false : component.type;
            }
        }

        // Footer layout
        LinearLayout footerLayout = new LinearLayout(this);
        footerLayout.setOrientation(LinearLayout.VERTICAL);
        footerLayout.setGravity(Gravity.CENTER | Gravity.BOTTOM);
        LinearLayout.LayoutParams footerLayoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 0);
        footerLayoutParams.weight = 1;
        footerLayout.setLayoutParams(footerLayoutParams);
        baseLayout.addView(footerLayout);

        // Add back button
        Button backButton = new Button(this);
        backButton.setText("Back");
        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                back(v);
            }
        });
        ViewGroup.LayoutParams backButtonParams = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        backButton.setLayoutParams(backButtonParams);
        footerLayout.addView(backButton);

        setContentView(baseLayout);
    }

    public void back(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();
    }

    private static final String TAG = "DataPageActivity";
}