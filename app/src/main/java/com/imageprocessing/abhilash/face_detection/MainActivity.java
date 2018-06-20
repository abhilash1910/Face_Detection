package com.imageprocessing.abhilash.face_detection;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;

public class MainActivity extends AppCompatActivity {
ImageView imageView;
//TextView textView;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        imageView=(ImageView)findViewById(R.id.imageview);
        //textView=(TextView)findViewById(R.id.textview);
        btn=(Button)findViewById(R.id.button);
        final Bitmap mybitmap= BitmapFactory.decodeResource(getApplicationContext().getResources(),R.drawable.ca);
        imageView.setImageBitmap(mybitmap);
        final Paint paint=new Paint();
        paint.setStrokeWidth(10);
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);

        final Bitmap tempbitmap=Bitmap.createBitmap(mybitmap.getWidth(),mybitmap.getHeight(),Bitmap.Config.RGB_565);
        final Canvas canvas=new Canvas(tempbitmap);
        canvas.drawBitmap(mybitmap,0,0,null);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FaceDetector faceDetector=new FaceDetector.Builder(getApplicationContext())
                        .setLandmarkType(FaceDetector.ALL_LANDMARKS)
                        .setTrackingEnabled(false)
                        .setMode(FaceDetector.FAST_MODE)
                        .build();
                if(!faceDetector.isOperational())
                {
                    Toast.makeText(MainActivity.this,"Wait",Toast.LENGTH_SHORT).show();
               return;
                }
                else
                {
                    Frame frame=new Frame.Builder().setBitmap(mybitmap).build();
                    SparseArray<Face> sparseArray=faceDetector.detect(frame);
                    for(int i=0;i<sparseArray.size();i++)
                    {
                        Face face =sparseArray.valueAt(i);
                        float x1=face.getPosition().x;
                        float y1=face.getPosition().y;
                        float x2=x1 + face.getWidth();
                        float y2= y1 + face.getHeight();
                        RectF rectF=new RectF(x1,y1,x2,y2);
                        canvas.drawRoundRect(rectF,2,2,paint);

                    }
                    imageView.setImageDrawable( new BitmapDrawable(getResources(),tempbitmap));
                }
            }
        });

    }
}
