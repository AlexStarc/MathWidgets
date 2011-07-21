/*****************************************************************************
 *  Copyright 2011 Alex Starchenko (sandrstar at hotmail dot com)            *
 *****************************************************************************/

/*
 * This work is licensed under the Creative Commons Attribution 3.0 Unported License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by/3.0/
 * or send a letter to Creative Commons, 444 Castro Street, Suite 900,
 * Mountain View, California, 94041, USA.
 */

package com.sandrstar.android.mathwidgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;


/**
 * @author Alex Starchenko (sandrstar at hotmail dot com)
 *
 */
public class FractionLineNumber extends android.view.View {

    /** Constant for default fraction line weight (in dp)*/
    private static final int DEFAULT_LINE_WEIGHT = 1;

    /** Constant for space between line and text (in dp) */
    private static final int DEFAULT_LINE_SPACING = 1;

    /** constant for spacing for particular screen */
    private final int spacing;

    /** Text to be displayed on up part of fraction */
    private String mUpText = " ";
    /** Text to be displayed on down part of fraction */
    private String mDownText = " ";
    /** Text and line color */
    private Integer mColor = Color.WHITE;
    /** Text typeface: normal / bold / italic */
    private Typeface mTypeface = null;
    /** Text size */
    private int mSize = 0;
    /** Weight of fraction line (in pixels) */
    private int mLineWeight = DEFAULT_LINE_WEIGHT;
    /** Paint object to be used for text drawing */
    private Paint mTextPaint = null;
    /** Paint object to be used for line drawing */
    private Paint mLinePaint = null;
    /* These dimensions are common and doesn't depend on cutting / view placing
     * and to be used in onMeasure() in order to eliminate multiple calculations
     */
    private int mWidth = 0;
    private int mHeight = 0;
    private int mXOffset = 0;

    /**
     * Simple constructor to use when creating a view from code.
     *
     * @see android.view.View#View(Context)
     *
     * @param context The Context the view is running in, through which it can access the current theme, resources, etc.
     */
    public FractionLineNumber(final Context context) {
        super(context);

        spacing = (int)(getResources().getDisplayMetrics().density * DEFAULT_LINE_SPACING + 0.5f);

        init();
    }

    /**
     * Constructor that is called when inflating a view from XML.
     * This is called when a view is being constructed from an XML file, supplying attributes that were specified in the XML file.
     * This version uses a default style of 0, so the only attribute values applied are those in the Context's
     * Theme and the given AttributeSet.
     * The method onFinishInflate() will be called after all children have been added.
     *
     * @see android.view.View#View(Context, AttributeSet)
     *
     * @param context The Context the view is running in, through which it can access the current theme, resources, etc.
     * @param attrs The attributes of the XML tag that is inflating the view.
     */
    public FractionLineNumber(final Context context, final AttributeSet attrs) {
        super(context, attrs);

        spacing = (int)(getResources().getDisplayMetrics().density * DEFAULT_LINE_SPACING + 0.5f);

        init();
        initAttrs(context, attrs);
    }

    /**
     *
     * Perform inflation from XML and apply a class-specific base style.
     * This constructor of View allows subclasses to use their own base style when they are inflating.
     * For example, a Button class's constructor would call this version of
     * the super class constructor and supply R.attr.buttonStyle for defStyle;
     * this allows the theme's button style to modify all of the base view attributes (in particular its background)
     * as well as the Button class's attributes.
     *
     * @see android.view.View#View(Context, AttributeSet, int)
     *
     * @param context - The Context the view is running in, through which it can access the current theme, resources, etc.
     * @param attrs - The attributes of the XML tag that is inflating the view.
     * @param defStyle - The default style to apply to this view.
     *                   If 0, no style will be applied (beyond what is included in the theme).
     *                   This may either be an attribute resource, whose value will be retrieved from the current theme,
     *                   or an explicit style resource.
     *                   <b>Currently view doesn't handle this parameter</b>
     */
    FractionLineNumber(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        spacing = (int)(getResources().getDisplayMetrics().density * DEFAULT_LINE_SPACING + 0.5f);

        init();
        initAttrs(context, attrs);
    }

    /**
     * Function to init fields of the view
     */
    private void init() {
        this.mTextPaint = new Paint();
        this.mLinePaint = new Paint();
        setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        setColor(Color.WHITE);
    }

    /**
     * Helper function to provide view init using attrs
     *
     * @param context - context to be used
     * @param attrs   - attributes to be used for initialization
     */
    private void initAttrs(Context context, AttributeSet attrs) {
        Typeface fontFamily = Typeface.DEFAULT;

        // here we might need to parse attributes from our custom view
        final TypedArray aAttr = context.obtainStyledAttributes(attrs,
                                                                R.styleable.FractionLineNumber);

        // now obtain every attribute separately
        // Up Text
        final CharSequence upTextStr = aAttr.getString(R.styleable.FractionLineNumber_upText);

        if(null != upTextStr) {
            setUpText(upTextStr.toString());
        }

        // Down Text
        final CharSequence downTextStr = aAttr.getString(R.styleable.FractionLineNumber_downText);

        if(null != downTextStr) {
            setDownText(downTextStr.toString());
        }

        // Color
        setColor(aAttr.getColor(R.styleable.FractionLineNumber_textColor, Color.WHITE));

        switch(aAttr.getInt(R.styleable.FractionLineNumber_typeface, 0)) {
        // sans
        case 1:
            fontFamily = Typeface.SANS_SERIF;
            break;

        // serif
        case 2:
            fontFamily = Typeface.SERIF;
            break;

        // monospace
        case 3:
            fontFamily = Typeface.MONOSPACE;
            break;

        // normal
        case 0:
        // and all rest values
        default:
            fontFamily = Typeface.DEFAULT;
            break;
        }

        // Typeface
        switch(aAttr.getInt(R.styleable.FractionLineNumber_textStyle, 0)) {
        // normal
        case 0:
            this.mTypeface = Typeface.create(fontFamily, Typeface.NORMAL);
            break;

        // bold
        case 1:
            this.mTypeface = Typeface.create(fontFamily, Typeface.BOLD);
            break;

        // italic
        case 2:
            this.mTypeface = Typeface.create(fontFamily, Typeface.ITALIC);
            break;

        default:
            break;
        }

        final int textSize = aAttr.getDimensionPixelOffset(R.styleable.FractionLineNumber_textSize, -1);

        if(textSize > 0) {
            setTextSize(textSize);
        }

        // Assume default line weight as 3 pixels
        final int lineWeight = aAttr.getDimensionPixelSize(R.styleable.FractionLineNumber_lineWeight, DEFAULT_LINE_WEIGHT);

        if(lineWeight >= 0) {
            setLineWeight(lineWeight);
        }
    }

    /**
     * Setter for Up fraction text
     *
     * @param text the text to set to up fraction position
     */
    public void setUpText(final String text) {
        this.mUpText = text;
        validateDimensions();
        requestLayout();
        invalidate();
    }

    /**
     * Getter for Up fraction text
     *
     * @return the up text
     */
    public String getUpText() {
        return this.mUpText;
    }

    /**
     * Setter for Down fraction text
     *
     * @param text the text to set to down fraction position
     */
    public void setDownText(final String text) {
        this.mDownText = text;
        validateDimensions();
        requestLayout();
        invalidate();
    }

    /**
     * Getter for Down fraction text
     *
     * @return the down text
     */
    public String getDownText() {
        return this.mDownText;
    }

    /**
     * Setter for text/line color
     *
     * @param mColor the mColor to set
     */
    public void setColor(final int color) {
        this.mColor = color;
        this.mTextPaint.setColor(color);
        this.mLinePaint.setStyle(Paint.Style.FILL);
        this.mLinePaint.setColor(color);
        invalidate();
    }

    /**
     * Getter for text/line color
     *
     * @return the mColor
     */
    public int getColor() {
        return this.mColor;
    }

    /**
     * Setter for text typeface
     *
     * @param mTypeface the mTypeface to set
     */
    public void setTypeface(final Typeface mTypeface) {
        this.mTypeface = mTypeface;
        this.mTextPaint.setTypeface(mTypeface);
        validateDimensions();
        requestLayout();
        invalidate();
    }

    /**
     * Getter for typeface
     *
     * @return the mTypeface
     */
    public Typeface getTypeface() {
        return this.mTypeface;
    }

    /**
     * Setter for text size.
     *
     * @param mSize the mSize to set, in pixels
     */
    public void setTextSize(final int mSize) {
        this.mSize = mSize;
        this.mTextPaint.setTextSize(mSize);
        validateDimensions();
        requestLayout();
        invalidate();
    }

    /**
     * Getter for text size
     *
     * @return the mSize, in pixels
     */
    public int getTextSize() {
        return this.mSize;
    }

    /**
     * Setter for line weight
     *
     * @param lineWeight - lineWeight in pixels
     */
    public void setLineWeight(int lineWeight) {
        this.mLineWeight = lineWeight;
        validateDimensions();
        requestLayout();
        invalidate();
    }

    /**
     * Getter for line weight
     *
     * @return the line weight to be drawn as fraction divider, in pixels
     */
    public int getLineWeight() {
        return this.mLineWeight;
    }

    /**
     * Measure the view and its content to determine the measured width and the measured height.
     * This method is invoked by measure(int, int)
     * and should be overriden by subclasses to provide accurate and efficient measurement of their contents.
     *
     * @see android.view.View#measure(int, int)
     *
     * @param widthMeasureSpec - horizontal space requirements as imposed by the parent. The requirements are encoded with View.MeasureSpec.
     * @param heightMeasureSpec - vertical space requirements as imposed by the parent. The requirements are encoded with View.MeasureSpec.
     */
    @Override
    protected void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec) {
        setMeasuredDimension(measureWidth(widthMeasureSpec),
                             measureHeight(heightMeasureSpec));
    }

    /**
     * Determines the width of this view
     *
     * @param measureSpec A measureSpec packed into an int
     * @return The width of the view, honoring constraints from measureSpec
     */
    private int measureWidth(final int measureSpec) {
        int textWidth = 0;
        final int specMode = MeasureSpec.getMode(measureSpec);
        final int specSize = MeasureSpec.getSize(measureSpec);

        if(specMode == MeasureSpec.EXACTLY) {
            // We were told how big to be
            textWidth = specSize;
        } else {
            textWidth = this.mWidth;
            textWidth += getPaddingLeft() + getPaddingRight();

            if (specMode == MeasureSpec.AT_MOST) {
                // Respect AT_MOST value if that was what is called for by measureSpec
                textWidth = Math.min(textWidth, specSize);
            }
        }

        return textWidth;
    }

    /**
     * Determines the height of this view
     *
     * @param measureSpec A measureSpec packed into an int
     * @return The height of the view, honoring constraints from measureSpec
     */
    private int measureHeight(final int measureSpec) {
        int textHeight = 0;
        final int specMode = MeasureSpec.getMode(measureSpec);
        final int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            // We were told how big to be
            textHeight = specSize;
        } else {
            textHeight = this.mHeight;
            textHeight += getPaddingTop() + getPaddingBottom();

            if (specMode == MeasureSpec.AT_MOST) {
                // Respect AT_MOST value if that was what is called for by measureSpec
                textHeight = Math.min(textHeight, specSize);
            }
        }
        return textHeight;
    }

    /**
     * Implement this to do your drawing.
     *
     * @see android.view.View#onDraw(Canvas)
     *
     * @param canvas - the canvas on which the background will be drawn
     */
    @Override
    protected void onDraw(final Canvas canvas) {
        final Rect clipRect = canvas.getClipBounds();

        // Draw up text
        canvas.translate(clipRect.left + getPaddingLeft() + this.mXOffset,
                         clipRect.top + getPaddingTop() + (-this.mTextPaint.ascent() + this.mTextPaint.descent()));
        canvas.drawText(this.mUpText, 0, 0, this.mTextPaint);

        // Draw middle line
        canvas.translate(-this.mXOffset,
                         this.spacing + this.mLineWeight);
        canvas.drawRect(0, 0, this.mWidth, this.mLineWeight, this.mLinePaint);

        // Draw down text
        canvas.translate(this.mXOffset,
                         (-this.mTextPaint.ascent() + this.mTextPaint.descent()));
        canvas.drawText(this.mDownText, 0, 0, this.mTextPaint);

        super.onDraw(canvas);
    }

    /**
     * Validates mWidth and mHeight variables or calculates them for first time
     */
    private void validateDimensions() {
        this.mWidth = (int)(Math.max(this.mTextPaint.measureText(this.mUpText),
                                     this.mTextPaint.measureText(this.mDownText)) +
                            this.spacing * 2);
        this.mHeight = (int)((-this.mTextPaint.ascent() + this.mTextPaint.descent()) * 2 +
                             this.spacing * 2 +
                             this.mLineWeight);

        this.mXOffset = this.spacing;
    }
}
