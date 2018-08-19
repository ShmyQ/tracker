package com.example.williamschymik.tracker.components;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import com.example.williamschymik.tracker.DataLog;
import com.example.williamschymik.tracker.DataLogHandler;
import com.example.williamschymik.tracker.DataPage;

import org.json.JSONArray;
import org.json.JSONException;

public class OptionsComponent extends BaseSubmitComponent {

    public LinearLayout getDataSelectionView(final Context context, DataPage.DataPageComponent component) {
        // Add options selections
        LinearLayout optionsLayout = new LinearLayout(context);
        optionsLayout.setOrientation(LinearLayout.VERTICAL);
        final int optionsId = View.generateViewId();
        optionsLayout.setId(optionsId);
        LinearLayout.LayoutParams optionsLayoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        optionsLayout.setLayoutParams(optionsLayoutParams);

        try {
            JSONArray options = new JSONArray(component.value);
            for (int i=0; i < options.length(); i++) {
                String option = options.getString(i);

                RadioButton rb = new RadioButton(context);
                rb.setText(option);
                rb.setTag(option.toLowerCase().replace(' ', '_'));
                optionsLayout.addView(rb);
            }
        }
        catch (JSONException e) {
            assert false;
        }

        return optionsLayout;
    }

    protected void onSubmit(Context context, LinearLayout layout, DataPage.DataPageComponent component) {
        JSONArray tags = new JSONArray();
        for (int i = 0; i < layout.getChildCount(); i++) {
            RadioButton symptom = (RadioButton) layout.getChildAt(i);
            String tag = symptom.getTag().toString();
            if (symptom.isChecked()) {
                tags.put(tag);
            }
        }
        final DataLogHandler myDb = DataLogHandler.getInstance(context);
        myDb.insertDataLog(new DataLog(component.getDataType(), tags.toString()));
    }

    private static final String TAG = "DataPageComponent";
}
