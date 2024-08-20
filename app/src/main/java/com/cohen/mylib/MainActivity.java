package com.cohen.mylib;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.util.Log;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.cohen.myspantext.ImageLoadCallback;
import com.cohen.myspantext.SpanText;

public class MainActivity extends AppCompatActivity {

    private TextView id_main_txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        id_main_txt = findViewById(R.id.id_main_txt);
        Log.d("pttt", "Main Activity");

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
        spanString4.add("2\n")
                .add(new SpanText.Superscript(Spannable.SPAN_EXCLUSIVE_EXCLUSIVE));


        // Initialize SpanText and SpanString globally
        SpanText spanText = new SpanText();
        SpanText.SpanString spanStringI2 = new SpanText.SpanString();
        SpanText.SpanString spanStringI1 = new SpanText.SpanString();

        // Add other spans or text to spanString if needed
        spanStringI2.addNewLine("This is a text after the image");

        // Load the image asynchronously
        spanStringI1.addImage("https://upload.wikimedia.org/wikipedia/commons/thumb/e/e1/FullMoon2010.jpg/1024px-FullMoon2010.jpg", new ImageLoadCallback() {
            @Override
            public void onImageLoaded() {

                // The image has been loaded, now update spanText
                spanText.add(spanStringI1);
                spanText.add(spanStringI2);
                // Update the TextView with the SpannableString
                id_main_txt.setText(spanText.makeSpannableString());
            }
        });

        // You can continue to use spanText and spanString elsewhere in your code
        SpanText.SpanString spanStringI = new SpanText.SpanString();
        spanStringI.addNewLine("This is a text before the image");
        spanStringI.add(new SpanText.Color(Color.GREEN));


        SpanText.SpanString spanStringIM1 = new SpanText.SpanString();

        // Load the image asynchronously
        spanStringIM1.addImage("https://upload.wikimedia.org/wikipedia/commons/1/14/Panorama_Sunset_In_Bat_Yam.jpg", new ImageLoadCallback() {
            @Override
            public void onImageLoaded() {

                // The image has been loaded, now update spanText
                spanText.add(spanStringIM1);
                // Update the TextView with the SpannableString
                id_main_txt.setText(spanText.makeSpannableString());
            }
        });

        spanText.add(spanString0).add(spanString).add(spanString2).add(spanString3).add(spanString4).add(spanStringI);
        id_main_txt.setText(spanText.makeSpannableString());
    }
}