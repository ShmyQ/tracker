package com.example.williamschymik.tracker.components;

import android.content.Context;
import android.support.v4.widget.TextViewCompat;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.williamschymik.tracker.DataLogHandler;
import com.example.williamschymik.tracker.DataPage;

import org.json.JSONArray;
import org.json.JSONException;

public abstract class BaseSubmitComponent implements DataPageComponent {

    public View getView(final Context context, final DataPage.DataPageComponent component) {
        // Base layout
        LinearLayout baseLayout = new LinearLayout(context);
        baseLayout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams baseLayoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        baseLayout.setLayoutParams(baseLayoutParams);

        // Add title
        TextView titleText = new TextView(context);
        titleText.setText(component.title);
        titleText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        TextViewCompat.setTextAppearance(titleText,android.R.style.TextAppearance_Large);
        ViewGroup.LayoutParams titleParams = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        titleText.setLayoutParams(titleParams);

        baseLayout.addView(titleText);

        final LinearLayout dataLayout = getDataSelectionView(context, component);
        baseLayout.addView(dataLayout);

        // Add submit button
        Button submitButton = new Button(context);
        submitButton.setText("Submit");
        submitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onSubmit(context, dataLayout, component);
            }
        });

        ViewGroup.LayoutParams submitButtonParams = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        submitButton.setLayoutParams(submitButtonParams);
        baseLayout.addView(submitButton);

        return baseLayout;
    }

    abstract LinearLayout getDataSelectionView(final Context context, DataPage.DataPageComponent component);

    protected abstract void onSubmit(Context context, LinearLayout layout, DataPage.DataPageComponent component);

    private static final String TAG = "DataPageComponent";
}
