package com.example.u0151051.camera;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

//要在AndroidManifest.xml加以下權限
//<uses-permission android:name="android.permission.CAMERA"></uses-permission>
//<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"></uses-permission>
//<uses-feature android:name="android.hardware.camera" />
//<uses-feature android:name="android.hardware.camera.autofocus" />
//Android6.0以後把權限分為一般權限和危險權限,開相機屬於危險權限,程式碼要多加權限判斷句
public class MainActivity extends AppCompatActivity {
    ImageView iv;
    Button btn1, btn2;
    //程式設計者可自行設定intent回傳值的結果碼
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
                //按下打開相機
                case R.id.button:
                    //使用由「Support v4」類別庫所提供的「ActivityCompat」類別
                    int permissions = ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA);
                    if (permissions != PackageManager.PERMISSION_GRANTED) {
                        //未取得權限，向使用者要求允許權限(ActivityCompat類別提供向使用者請求權限的類別方法"requestPermissions"
                        //requestPermissions(Context物件，想要求的權限,請求的辨識編號)
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, Image_result_code);
                    } else {
                        //已有權限，執行打開相機
                        takepicture();
                    }
                    break;
                case R.id.button2:
                    //利用intent,開啟手機自己的圖片庫
                    Intent intent1 = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent1, pick_result);
                    break;

            }
        }
    };

    //當使用者允許或拒絕權限(當按下按鈕時會自動執行onRequestPermissionsResult)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //參數requestCode可以辨識不同的權限
        switch (requestCode) {
            case Image_result_code:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //取得權限，進行打開相機
                    takepicture();
                } else {
                    Toast.makeText(MainActivity.this, "使用者沒打開相機權限", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    public void takepicture() {
        //利用intent,開啟手機自己的照相機
        //宣告一個intent,意圖android.MediaStore.ACTION_IMAGE_CAPTURE
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //打開照相機
        startActivityForResult(intent, Image_result_code);
    }

    //使用intent的回傳值來傳入圖片
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            //選擇啟動相機後回傳值
            case Image_result_code:
                if (resultCode == RESULT_OK) {
                    Bundle bundle = data.getExtras();
                    //使用Bitmap來存放圖片
                    Bitmap bitmap = (Bitmap) bundle.get("data");
                    iv.setImageBitmap(bitmap);
                }
                break;
            //選擇圖片庫回傳值
            case pick_result:
                if (resultCode == RESULT_OK) {
                    Uri uri = data.getData();
                    iv.setImageURI(uri);
                }
                break;
        }
    }
}
