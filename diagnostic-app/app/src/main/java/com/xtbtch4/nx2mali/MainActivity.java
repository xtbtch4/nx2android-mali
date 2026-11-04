package com.xtbtch4.nx2mali;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class MainActivity extends Activity {
    static { System.loadLibrary("vkprobe"); System.loadLibrary("cpuprobe"); System.loadLibrary("vkzc"); }
    private TextView output;
    private native String runVk();
    private native String runCpu();
    private native String runZeroCopy();

    @Override public void onCreate(Bundle state) {
        super.onCreate(state);
        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(18, 24, 18, 12);
        root.setBackgroundColor(0xff10141c);
        TextView title = new TextView(this);
        title.setText("nx2android · Mali diagnostics");
        title.setTextSize(21); title.setTextColor(0xffffffff);
        root.addView(title);
        TextView hint = new TextView(this);
        hint.setText("Run tests separately. The zero-copy test may take longer and use substantial memory.");
        hint.setTextColor(0xffc5ccd8); root.addView(hint);
        addButton(root, "1. Vulkan / BCn / ASTC", () -> show("VULKAN PROBE\n\n" + runVk()));
        addButton(root, "2. ARM64 CPU", () -> show("CPU PROBE\n\n" + runCpu()));
        addButton(root, "3. GPU zero-copy (advanced)", () -> show("ZERO-COPY PROBE\n\n" + runZeroCopy()));
        ScrollView scroll = new ScrollView(this);
        output = new TextView(this);
        output.setTextColor(0xffe6edf3); output.setTextSize(12);
        output.setTextIsSelectable(true); output.setPadding(10, 14, 10, 14);
        output.setText("Choose a diagnostic test above.");
        scroll.addView(output);
        root.addView(scroll, new LinearLayout.LayoutParams(-1, 0, 1));
        setContentView(root);
    }
    private void addButton(LinearLayout root, String label, View.OnClickListener click) {
        Button b = new Button(this); b.setText(label); b.setOnClickListener(click); root.addView(b);
    }
    private void show(String text) { output.setText(text); }
}
