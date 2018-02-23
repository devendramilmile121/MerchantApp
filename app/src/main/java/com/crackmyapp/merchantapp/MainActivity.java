package com.crackmyapp.merchantapp;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText offername;
    EditText offerdetails;
    EditText actualprice;
    EditText discount;
    EditText discountprice;
    ImageView productimg;
    String path;
    TextView no_img;

    private static final int SELECTED_PIC = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        offername = findViewById(R.id.et_offername);
        offerdetails = findViewById(R.id.et_offer_details);
        actualprice = findViewById(R.id.et_actual_price);
        discount = findViewById(R.id.et_discount);
        discountprice = findViewById(R.id.et_discounted_price);
        productimg = findViewById(R.id.imageView);
        no_img = findViewById(R.id.tev_no_img);

    }

    public void SendOffer(View view) {
        Intent intent = new Intent();
        intent.setAction("com.crackmyapp.merchantapp");
        intent.putExtra("Image_path",path);
        intent.putExtra("Offer_Name",offername.getText().toString());
        intent.putExtra("Offer_Details",offerdetails.getText().toString());
        intent.putExtra("Actual_Price",actualprice.getText().toString());
        intent.putExtra("Discount",discount.getText().toString());
        intent.putExtra("Discount_Price",discountprice.getText().toString());
        intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        sendBroadcast(intent);

    }

    public void PickImage(View v) {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, SELECTED_PIC);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case SELECTED_PIC:
                if (resultCode == RESULT_OK) {
                    Uri uri = data.getData();
                    String[] projection = {MediaStore.Images.Media.DATA};

                    Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(projection[0]);
                   String filepath = cursor.getString(columnIndex);
                    cursor.close();

                    Bitmap bitmap = BitmapFactory.decodeFile(filepath);
                    Drawable drawable = new BitmapDrawable(bitmap);
                    productimg.setBackground(drawable);
                    path = filepath;
                    no_img.setText("");
                }
                break;
            default:
                break;
        }

    }
}
