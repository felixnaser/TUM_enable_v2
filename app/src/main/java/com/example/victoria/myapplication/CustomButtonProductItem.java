package com.example.victoria.myapplication;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Lennart Mittag on 06.12.2015.
 */
public class CustomButtonProductItem extends LinearLayout
{
    LinearLayout containerCustomButtonProductItem;

    private TextView txtOben;
    private TextView txtUnten;
    private ImageButton imgBtn;

    private String productName;
    private double price;
    private int id;


    public CustomButtonProductItem(Context context)
    {
        super(context);
        initializeLayout(context);
    }

    public CustomButtonProductItem(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        initializeLayout(context);

        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.CustomButton, 0, 0);

        String productName = a.getString(R.styleable.CustomButton_product_name);
        double price = (double) a.getFloat(R.styleable.CustomButton_product_price, 0.0f);
        int imgIdentifier = a.getResourceId(R.styleable.CustomButton_img_identifier, 0);

        txtOben.setText(productName);
        txtUnten.setText("Price: " + String.format("%1$,.2f", price));
        imgBtn.setImageResource(imgIdentifier);
        imgBtn.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        imgBtn.setAdjustViewBounds(true);
        imgBtn.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                performClick();
            }
        });
    }

    public CustomButtonProductItem(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        initializeLayout(context);
    }

    private void initializeLayout(Context context)
    {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (inflater != null)
        {
            inflater.inflate(R.layout.custom_button_product_item, this);
        }

        containerCustomButtonProductItem = (LinearLayout) findViewById(R.id.container_custom_button_product_item);
        txtOben = (TextView) findViewById(R.id.textView);
        txtUnten = (TextView) findViewById(R.id.textView2);
        imgBtn = (ImageButton) findViewById(R.id.imageButton);
    }

    public double getPrice()
    {
        return price;
    }

    public void setPrice(double price)
    {
        this.price = price;
        txtUnten.setText("Price: " + String.format("%1$,.2f", price) + "€");
    }

    public int getIdentifier()
    {
        return id;
    }

    public void setIdentifier(int id)
    {
        this.id = id;
        imgBtn.setImageResource(id);
        imgBtn.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                performClick();
            }
        });
    }

    public String getProductName()
    {
        return productName;
    }

    public void setProductName(String productName)
    {
        this.productName = productName;
        txtOben.setText(productName);
    }

    public void setBackground(int id)
    {
        containerCustomButtonProductItem.setBackgroundResource(id);
        txtOben.setBackgroundResource(id);
        txtUnten.setBackgroundResource(id);
    }
}
