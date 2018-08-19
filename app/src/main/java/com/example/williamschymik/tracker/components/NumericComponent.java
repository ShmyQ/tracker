package com.example.williamschymik.tracker.components;

import android.content.Context;
import android.support.v4.widget.TextViewCompat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.williamschymik.tracker.DataLog;
import com.example.williamschymik.tracker.DataLogHandler;
import com.example.williamschymik.tracker.DataPage;

import org.json.JSONArray;
import org.json.JSONException;

public class NumericComponent extends BaseSubmitComponent {

    public LinearLayout getDataSelectionView(final Context context, DataPage.DataPageComponent component) {
        // Add options selections
        LinearLayout baseLayout = new LinearLayout(context);
        baseLayout.setOrientation(LinearLayout.VERTICAL);
        final int optionsId = View.generateViewId();
        baseLayout.setId(optionsId);
        LinearLayout.LayoutParams optionsLayoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        baseLayout.setLayoutParams(optionsLayoutParams);

        // Seekbar text
        final TextView seekBarText = new TextView(context);
        seekBarText.setText("0");
        TextViewCompat.setTextAppearance(seekBarText,android.R.style.TextAppearance_Large);
        ViewGroup.LayoutParams titleParams = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        seekBarText.setLayoutParams(titleParams);

        // Seekbar
        SeekBar seekBar = new SeekBar(context);
        int max = Integer.valueOf(component.value);
        seekBar.setMax(max);

        ViewGroup.LayoutParams seekBarParams = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        seekBar.setLayoutParams(seekBarParams);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                int val = (progress * (seekBar.getWidth() - 2 * seekBar.getThumbOffset())) / seekBar.getMax();
                seekBarText.setText("" + progress);
                seekBarText.setX(seekBar.getX() + val + seekBar.getThumbOffset() / 2);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        baseLayout.addView(seekBar);

        baseLayout.addView(seekBarText);

        return baseLayout;
    }

    protected void onSubmit(Context context, LinearLayout layout, DataPage.DataPageComponent component) {
        SeekBar seekBar = (SeekBar) layout.getChildAt(0);
        Log.i(TAG, "" + seekBar.getProgress());
        final DataLogHandler myDb = DataLogHandler.getInstance(context);
        myDb.insertDataLog(new DataLog(component.getDataType(), "" + seekBar.getProgress()));
    }

    private static final String TAG = "DataPageComponent";
}
