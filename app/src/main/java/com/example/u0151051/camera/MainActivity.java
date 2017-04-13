package com.example.u0151051.camera;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    ImageView iv;
    Button btn1, btn2;
    //intent回傳值的結果碼
    private  final  int Image_result_code=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findview();
    }

    void findview() {
        iv = (ImageView) findViewById(R.id.imageView);
        btn1 = (Button) findViewById(R.id.button);
        btn2 = (Button) findViewById(R.id.button2);
        btn1.setOnClickListener(c);
        btn2.setOnClickListener(c);
    }

    View.OnClickListener c = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.button:
                    //利用intent,開啟手機自己的照相機
                    //宣告一個intent,意圖android.MediaStore.ACTION_IMAGE_CAPTURE
                    Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    //打開照相機
                    startActivityForResult(intent,Image_result_code);
                    break;
                case R.id.button2:

                    break;

            }
        }
    };

    //使用intent的回傳值
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==Image_result_code&&resultCode==RESULT_OK){
            Bundle bundle=data.getExtras();
            Bitmap bitmap=(Bitmap) bundle.get("data");
            iv.setImageBitmap(bitmap);
        }
    }
}
