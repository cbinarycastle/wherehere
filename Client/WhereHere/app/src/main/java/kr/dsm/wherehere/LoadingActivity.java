package kr.dsm.wherehere;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.widget.Toast;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.ArrayList;

/**
 * Created by hojak on 2017-03-31.
 */

public class LoadingActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        final Intent intent = new Intent(this, MainActivity.class);

        PermissionListener permissionListener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                final Handler handler = new Handler(){
                    @Override
                    public void handleMessage(Message msg) {
                        startActivity(intent);
                    }
                };

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                        handler.sendEmptyMessage(0);
                    }
                }, 1000);
            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                Toast.makeText(LoadingActivity.this, "권한거부\n"+ deniedPermissions.toString(),
                        Toast.LENGTH_SHORT).show();
            }
        };

        new TedPermission(this)
                .setPermissionListener(permissionListener)
                .setRationaleMessage("서버와 통신을 하기 위해 인터넷 접근 권한이 필요합니다")
                .setDeniedMessage("여가 어디래요 를 사용할 수 없음" +
                        "[설정] > [권한] 에서 권한을 허용할 수 있습니다")
                .setPermissions(
                        Manifest.permission.INTERNET
                ).check();

        setContentView(R.layout.activity_loading);
        }
}
