package com.example.u0151051.camera;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

//要在AndroidManifest.xml加以下權限
//<uses-permission android:name="android.permission.CAMERA"></uses-permission>
//<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"></uses-permission>
//<uses-feature android:name="android.hardware.camera" />
//<uses-feature android:name="android.hardware.camera.autofocus" />
//以下程式碼不適用於Android6.0以上(Android6.0以後把權限分為一般權限和危險權限,相機屬於危險權限,程式碼要多加判斷句,但本程式碼還沒加)
public class MainActivity extends AppCompatActivity {
    ImageView iv;
    Button btn1, btn2;
    //intent回傳值的結果碼
    private final int Image_result_code = 1;//1為打開相機
    private final int pick_result = 2;//2為打開圖片庫

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
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    //打開照相機
                    startActivityForResult(intent, Image_result_code);
                    break;
                case R.id.button2:
                    //利用intent,開啟手機自己的圖片庫
                    Intent intent1 = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent1, pick_result);
                    break;

            }
        }
    };

    //使用intent的回傳值
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            //選擇啟動相機
            case Image_result_code:
                if (resultCode == RESULT_OK) {
                    Bundle bundle = data.getExtras();
                    //使用Bitmap來存放圖片
                    Bitmap bitmap = (Bitmap) bundle.get("data");
                    iv.setImageBitmap(bitmap);
                }
                break;
            //選擇圖片庫
            case pick_result:
                if (resultCode == RESULT_OK) {
                    Uri uri = data.getData();
                    iv.setImageURI(uri);
                }
                break;
        }
    }
}
