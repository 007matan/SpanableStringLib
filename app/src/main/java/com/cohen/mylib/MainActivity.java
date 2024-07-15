package com.cohen.mylib;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.cohen.myspantext.SpanText;

public class MainActivity extends AppCompatActivity {

    private TextView id_main_txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        id_main_txt = findViewById(R.id.id_main_txt);

        SpanText.SpanString spanString0 = new SpanText.SpanString();
        spanString0.addNewLine("System Message:")
                .add(new SpanText.AlignCenter())
                .add(new SpanText.Color(Color.RED));

        SpanText.SpanString spanString = new SpanText.SpanString();
        spanString.addNewLine("Hello From")
                .add(new SpanText.Size(3))
                .add(new SpanText.LetterSpacing(0.3F))
                .add(new SpanText.Color(Color.MAGENTA))
                .add(new SpanText.Shadow(2.5F, 0.2F, 0.4F, Color.GREEN));

        SpanText.SpanString spanString2 = new SpanText.SpanString();
        spanString2.add("Matan")
                .add(new SpanText.Size(2))
                .add(new SpanText.Underline())
                .add(new SpanText.Blur(3.5F));

        SpanText.SpanString spanString3 = new SpanText.SpanString();
        spanString3.addImage(getResources(), R.drawable.ic_safe, 80);

        SpanText.SpanString spanString4 = new SpanText.SpanString();
        spanString4.add("2")
                .add(new SpanText.Superscript(Spannable.SPAN_EXCLUSIVE_EXCLUSIVE));


        SpanText spanText = new SpanText();
        spanText.add(spanString0).add(spanString).add(spanString2).add(spanString3).add(spanString4);

        id_main_txt.setText(spanText.makeSpannableString());
    }
}