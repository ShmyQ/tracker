package com.example.williamschymik.tracker.components;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import com.example.williamschymik.tracker.DataPage;

public interface DataPageComponent {

    View getView(final Context context, DataPage.DataPageComponent component);
}
