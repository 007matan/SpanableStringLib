package com.cohen.myspantext;


import android.content.res.Resources;
import android.graphics.BlurMaskFilter;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.style.AlignmentSpan;
import android.text.style.BulletSpan;
import android.text.style.CharacterStyle;
import android.text.style.DynamicDrawableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.MaskFilterSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.SubscriptSpan;
import android.text.style.SuperscriptSpan;
import android.text.style.UnderlineSpan;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.ArrayList;

/**
 * Class SpanText
 * The class SpanText takes a list of SpanString and turned them into SpannableString
 * the goal is to put together several pieces of SpanStrings to one Text, and eventually make
 * it One big SpannableString
 */
public class SpanText {
    private ArrayList<SpanString> text = new ArrayList<>();

    public SpanText() {}
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
                spannableString.setSpan(text.get(i).getSpans().get(j).getSpan(), startIndex, endIndex, text.get(i).getSpans().get(j).getFlag());
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
        private String string = "";
        private ArrayList<SpanType> spans = new ArrayList<>();

        public SpanString() {}
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
        public SpanString addImage(Resources resources, int drawable, int size){
            addImage(resources, drawable, size, Image.POS_CENTER);
            return this;
        }
        public void addImage(Resources resources, int drawableRes, int size, int position){
            String modified = "%icon%";
            Drawable drawable = ResourcesCompat.getDrawable(resources, drawableRes, resources.newTheme());
            if(drawable != null){
                drawable.setBounds(0, 0, size, size);
                string += modified;
                spans.add(new Image(drawable, position));
            }
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
        int flag = Spannable.SPAN_EXCLUSIVE_EXCLUSIVE;

        int getFlag(){
            return flag;
        }
        void setFlag(int flag){
            this.flag = flag;
        }
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
        private final float MAX = 5.0F;
        private float size;

        public Size(float size, int flag){
            size = Math.min(size, MAX);
            size = Math.max(size, MIN);
            this.size = size;
            setFlag(flag);
        }
        public Size(float size) {
            this(size, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
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
        private final int MAX = 400;
        int color;
        int size;

        public Bullet() {
            this(android.graphics.Color.BLACK, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        public Bullet(int color, int size, int flag){
            size = Math.min(size, MAX);
            size = Math.max(size, MIN);
            this.color = color;
            this.size = size;
            setFlag(flag);
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
            this(color, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        public Color(int color, int flag){
            this.color = color;
            setFlag(flag);
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
            this(radius, horizontalOffset, verticalOffset, shadowColor, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        public Shadow(float radius, float horizontalOffset, float verticalOffset, int shadowColor, int flag) {
            radius = Math.min(radius, MAX);
            radius = Math.max(radius, MIN);
            this.radius = radius;
            this.dx = horizontalOffset;
            this.dy = verticalOffset;
            this.shadowColor = shadowColor;
            setFlag(flag);
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
        public Strikethrough(){}
        public Strikethrough(int flag){
            setFlag(flag);
        }
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
        public AlignOpposite(){}
        public AlignOpposite(int flag){
            setFlag(flag);
        }
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
        public AlignCenter(){}
        public AlignCenter(int flag){
            setFlag(flag);
        }
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
        private final float MAX = 25.0F;
        private float radius;

        public Blur(float radius) {
            this(radius, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        public Blur(float radius, int flag) {
            radius = Math.min(radius, MAX);
            radius = Math.max(radius, MIN);
            this.radius = radius;
            setFlag(flag);
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
        public Superscript(){}
        public Superscript(int flag){
            setFlag(flag);
        }
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
        public Subscript(){}
        public Subscript(int flag){
            setFlag(flag);
        }
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
        public Underline(){}
        public Underline(int flag){
            setFlag(flag);
        }
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
        private final float MAX = 2.0F;
        private float space;

        public LetterSpacing(float space){
            this(space, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        public LetterSpacing(float space, int flag) {
            space = Math.min(space, MAX);
            space = Math.max(space, MIN);
            this.space = space;
            setFlag(flag);
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

        public Transparent(int value){
            this(value, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        public Transparent(int value, int flag) {
            value = Math.min(value, MAX);
            value = Math.max(value, MIN);
            this.value = value;
            setFlag(flag);
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
    public static class Image extends SpanType {
        private Drawable drawable;

        private static final int POS_BASELINE = 1;

        private static final int POS_CENTER = 2;
        private static final int POS_BOTTOM = 0;


        private int position = 0;


        public Image(Drawable drawable, int position){
            setPosition(position);
            this.drawable = drawable;
        }
        public Image setPosition(int position){
            if(position > -1 && position < 3)
                this.position = position;
            else
                this.position = POS_CENTER;
            return this;
        }

        @Override
        Object getSpan() {
            if(position == POS_BASELINE){
                return new ImageSpan(drawable, DynamicDrawableSpan.ALIGN_BASELINE);
            }
            else if(position == POS_CENTER){
                return new ImageSpan(drawable, DynamicDrawableSpan.ALIGN_CENTER);
            }
            else {
                return new ImageSpan(drawable);
            }
        };
    }
}