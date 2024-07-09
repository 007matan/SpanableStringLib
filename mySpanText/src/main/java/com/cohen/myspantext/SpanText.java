package com.cohen.myspantext;


import android.graphics.BlurMaskFilter;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.style.AlignmentSpan;
import android.text.style.BulletSpan;
import android.text.style.CharacterStyle;
import android.text.style.ForegroundColorSpan;
import android.text.style.MaskFilterSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.SubscriptSpan;
import android.text.style.SuperscriptSpan;
import android.text.style.UnderlineSpan;

import java.util.ArrayList;

/**
 * Class SpanText
 * The class SpanText takes a list of SpanString and turned them into SpannableString
 * the goal is to put together several pieces of SpanStrings to one Text, and eventually make
 * it One big SpannableString
 */
public class SpanText {


    private ArrayList<SpanString> text = new ArrayList<>();

    public SpanText() {
    }

    public void clear() {
        text.clear();
    }

    public SpanText add(SpanString spanText) {
        text.add(spanText);
        return this;
    }

    public SpannableStringBuilder makeSpannableString() {
        SpannableStringBuilder spannableString = new SpannableStringBuilder();
        for (int i = 0; i < text.size(); i++) { //run all over the strings
            spannableString.append(text.get(i).getString());
            for (int j = 0; j < text.get(i).getSpans().size(); j++) {
                int startIndex = spannableString.length() - text.get(i).getString().length();
                int endIndex = spannableString.length();
                //spannableString.setSpan(text.get(i).getSpans().get(j).getSpan(), startIndex, endIndex, text.get(i).getSpans().get(j).getFlag());
                spannableString.setSpan(text.get(i).getSpans().get(j).getSpan(), startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
            }
        }
        return spannableString;
    }

    /**
     * Class SpanString
     * The class assemble by two attributes string and list of SpanType
     * the spans attr is the type of spans that the string need to preform later
     * when the SpanText will make it to SpannableString
     */
    public static class SpanString {

        String string = "";
        ArrayList<SpanType> spans = new ArrayList<>();

        public SpanString() {
        }

        public SpanString add(String s) {
            string += s;
            return this;
        }

        public SpanString addNewLine(String s) {
            string += s + "\n";
            return this;
        }

        public SpanString add(SpanType spanType) {
            spans.add(spanType);
            return this;
        }

        public String getString() {
            return string;
        }

        public ArrayList<SpanType> getSpans() {
            return spans;
        }
    }

    /**
     * Class SpanType
     * An abstract class of types of spans that we could set on SpannableString later
     * the class has a abstract method getSpan that return the Span for future use in SpannableString
     * and a flag attribute (Integer) that represent the SpannableStringFlags
     */
    public static abstract class SpanType {

        abstract Object getSpan();
    }

    /**
     * Class Size
     * A represent of a type of a span that set the size of a string
     * size - float size of the string
     * 0.1 < size < 3.0
     */
    public static class Size extends SpanType {
        private final float MIN = 0.2F;
        private final float MAX = 3.0F;
        private float size;

        public Size(float size) {
            size = Math.min(size, MAX);
            size = Math.max(size, MIN);
            this.size = size;
        }
        @Override
        Object getSpan() {
            return new RelativeSizeSpan(size);
        }
    }

    /**
     * Class Bullet
     * A represent of a type of a span that set a Bullet
     * color - int represent the color of the bullet
     * size - int represent the size of the bullet
     */
    public static class Bullet extends SpanType {
        private final int MIN = 1;
        private final int MAX = 4;

        int color;
        int size;

        public Bullet() {
            size = Math.min(size, MAX);
            size = Math.max(size, MIN);
            this.size = size;
            this.color = color;
        }
        @Override
        Object getSpan() {
            return new BulletSpan(size, color);
        }
    }

    /**
     * Class Color
     * A represent of a type of a span that set a color of a
     * color - int represent the color of the string
     */
    public static class Color extends SpanType {
        int color;

        public Color(int color) {
            this.color = color;
        }
        @Override
        Object getSpan() {
            return new ForegroundColorSpan(color);
        }
    }

    /**
     * Class Shadow
     * A represent of a type of a span that set a Shadow to a string
     * radius - float represent the blur radius of rhe shadow
     * dx - the horizontal offset
     * dy - the vertical offset
     * shadowColor = the color of a shadow
     * 0.5 < radius < 20
     */
    public static class Shadow extends SpanType {
        private final float MIN = 0.5F;
        private final float MAX = 20.0F;
        float radius;
        float dx;
        float dy;
        int shadowColor;


        public Shadow(float radius, float horizontalOffset, float verticalOffset, int shadowColor) {
            radius = Math.min(radius, MAX);
            radius = Math.max(radius, MIN);
            this.radius = radius;
            this.dx = horizontalOffset;
            this.dy = verticalOffset;
            this.shadowColor = shadowColor;

        }
        @Override
        Object getSpan() {
            return new CharacterStyle() {
                @Override
                public void updateDrawState(TextPaint tp) {
                    tp.setShadowLayer(radius, dx, dy, shadowColor);
                }
            };
        }
    }

    /**
     * Class Strikethrough
     * A represent of a type of a span that set a strikethrough effect on a string
     */
    public static class Strikethrough extends SpanType {
        @Override
        Object getSpan() {
            return new StrikethroughSpan();
        }
    }

    /**
     * Class AlignOpposite
     * A represent of a type of a span that used to align the string from right to left
     */
    public static class AlignOpposite extends SpanType {

        @Override
        Object getSpan() {
            return new AlignmentSpan.Standard(Layout.Alignment.ALIGN_OPPOSITE);
        }
    }

    /**
     * Class AlignOpposite
     * A represent of a type of a span that used to align the string to center
     */
    public static class AlignCenter extends SpanType {

        @Override
        Object getSpan() {
            return new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER);
        }
    }

    /**
     * Class Blur
     * A represent of a type of a span that used to apply blur to the string
     * radius - float represent the blur radius
     * 0.5 < radius < 20
     */
    public static class Blur extends SpanType {
        private final float MIN = 0.5F;
        private final float MAX = 20.0F;

        private float radius;

        public Blur(float radius) {
            radius = Math.min(radius, MAX);
            radius = Math.max(radius, MIN);
            this.radius = radius;
        }

        @Override
        Object getSpan() {
            return new MaskFilterSpan(new BlurMaskFilter(radius, BlurMaskFilter.Blur.NORMAL));
        }
    }

    /**
     * Class Superscript
     * A represent of a type of a span that set the string slightly above the normal line
     */
    public static class Superscript extends SpanType {

        @Override
        Object getSpan() {
            return new SuperscriptSpan();
        }
    }

    /**
     * Class Subscript
     * A represent of a type of a span that set the string slightly below the normal line
     */
    public static class Subscript extends SpanType {

        @Override
        Object getSpan() {
            return new SubscriptSpan();
        }
    }

    /**
     * Class Underline
     * A represent of a type of a span that set line below the string
     */
    public static class Underline extends SpanType {
        @Override
        Object getSpan() {
            return new UnderlineSpan();
        }
    }

    /**
     * Class Underline
     * A represent of a type of a span that set the space between characters of the string
     * space - float the size of thw spacing
     * 0.0 < space < 10.0
     */
    public static class LetterSpacing extends SpanType {
        private final float MIN = 0.0F;
        private final float MAX = 10.0F;
        private float space;

        public LetterSpacing(float space) {
            space = Math.min(space, MAX);
            space = Math.max(space, MIN);
            this.space = space;
        }

        @Override
        Object getSpan() {
            return new CharacterStyle() {
                @Override
                public void updateDrawState(TextPaint tp) {
                    tp.setLetterSpacing(space);
                }
            };
        }
    }

    /**
     * Class Transparent
     * A represent of a type of a span that set the transparency of the string
     * value - int represent the transparency level 0 - 255
     */
    public static class Transparent extends SpanType {
        private final int MIN = 0;
        private final int MAX = 255;
        private final int value;

        public Transparent(int value) {
            value = Math.min(value, MAX);
            value = Math.max(value, MIN);
            this.value = value;
        }

        @Override
        Object getSpan() {
            return new CharacterStyle() {
                @Override
                public void updateDrawState(TextPaint tp) {
                    tp.setAlpha(value);
                }
            };
        }
    }
}