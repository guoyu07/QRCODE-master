package in.eatontap_mk.gpslocation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

//related to dependency



public class MainActivity extends AppCompatActivity  {


  //QR code data member defination
    private TextView restroName, restroTable,title;
    ImageView logo;
    Button confirmation;
    String restaurantname,restauranttable;

    // qr String when format is not in json
    private int qrindex2, qrindex;;
    String qrdata;
    String indexqr;


    //qr code scanner object
    private IntentIntegrator qrScan;
    public char buftable[];
    public char bufname[];
    private char bufid[];
    Context mContext;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = getApplicationContext();
        //QR objects
        restroName=(TextView) findViewById(R.id.qr_restroname);
        restroTable=(TextView) findViewById(R.id.qr_restrotable);
        title =(TextView) findViewById(R.id.textView);
        confirmation=(Button) findViewById(R.id.qr_button);
       // Resources res = getResources(); /** from an Activity */
        logo=(ImageView)findViewById(R.id.imageView1);
        qrScan = new IntentIntegrator(this);


        qrScan.initiateScan();
    }

    //Getting the scan results
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            //if qrcode has nothing in it
            if (result.getContents() == null) {

                LinearLayout rootView=(LinearLayout)findViewById(R.id.rootview);
                Snackbar.make(rootView, "QR Code didn't match. Try again", Snackbar.LENGTH_LONG)

                        .setAction("Retry", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // retry to send email here
                                qrScan = new IntentIntegrator(MainActivity.this);
                                qrScan.initiateScan();
                            }
                        }).show();
            } else {
                qrdata=result.getContents();
                bufname=new char[qrdata.length()];
                //getting index of '_' & '/'
                qrindex=qrdata.indexOf('_');
                qrindex2=qrdata.indexOf('/');

                title.setText("You are at");

                buftable=new char[qrdata.length()-qrindex];
                qrdata.getChars(0,qrindex,bufname,0);

                restroName.setText(String.valueOf(bufname));

                //change this image according to the image
                logo.setImageDrawable(getDrawable(R.drawable.logo1));


                qrdata.getChars(qrindex+1,qrindex2,buftable,0);



                bufid = new char[qrdata.length() - qrindex2];
                qrdata.getChars(qrindex2+1,qrdata.length(),bufid,0);
                restroTable.setText(String.valueOf(buftable)+ " " +String.valueOf(bufid));



            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
   //onclick for button
    public void startscan(final View view) {
        if(!(bufname== null)) {
            Snackbar.make(view, "Your table at " + String.valueOf(bufname) + " is confirmed", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();

            // start a new activity and pass hotel name, id, table number here
        }else{
            Snackbar.make(view, "QR Code didn't match. Try again", Snackbar.LENGTH_LONG)

                    .setAction("Retry", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // retry to scan image again here
                            qrScan = new IntentIntegrator(MainActivity.this);
                            qrScan.initiateScan();
                        }
                    }).show();
        }

    }
}