package com.example.android.glass.cardsample;

/*
 * Copyright 2019 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.android.glass.cardsample.R;
import com.example.android.glass.cardsample.fragments.BaseFragment;

/**
 * Fragment with the main card layout.
 */
public class MainLayoutActivity extends Activity {

    private static final String TEXT_KEY = "text_key";
    private static final String FOOTER_KEY = "footer_key";
    private static final String TIMESTAMP_KEY = "timestamp_key";
    private static final int BODY_TEXT_SIZE = 40;

    /**
     * Returns new instance of {@link com.example.android.glass.cardsample.fragments.MainLayoutFragment}.
     *
     * @param text is a String with the card main text.
     * @param footer is a String with the card footer text.
     * @param timestamp is a String with the card timestamp text.
     */
    public static MainLayoutActivity newInstance(String text, String footer, String timestamp,
                                                                                                @Nullable Integer menu) {
        MainLayoutActivity myFragment = new MainLayoutActivity();

        final Bundle args = new Bundle();
        args.putString(TEXT_KEY, text);
        args.putString(FOOTER_KEY, footer);
        args.putString(TIMESTAMP_KEY, timestamp);
//        if (menu != null) {
//            args.putInt(MENU_KEY, menu);
//        }
//        myFragment.setArguments(args);

        return myFragment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
//        if (savedInstanceState.getArguments() != null) {
//            final TextView textView = new TextView(getContext());
//            textView.setText(getArguments().getString(TEXT_KEY, getString(R.string.empty_string)));
//            textView.setTextSize(BODY_TEXT_SIZE);
//            textView.setTypeface(Typeface.create(getString(R.string.thin_font), Typeface.NORMAL));
//
//            final FrameLayout bodyLayout = view.findViewById(R.id.body_layout);
//            bodyLayout.addView(textView);
////      Button b = new Button(getContext());
////      bodyLayout.addView(b);
//            final TextView footer = view.findViewById(R.id.footer);
//            footer.setText(getArguments().getString(FOOTER_KEY, getString(R.string.empty_string)));
//
//            final TextView timestamp = view.findViewById(R.id.timestamp);
//            timestamp.setText(getArguments().getString(TIMESTAMP_KEY, getString(R.string.empty_string)));
//        }
    }
}

