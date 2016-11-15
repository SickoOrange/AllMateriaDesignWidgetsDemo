package com.example.androidm;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

//针对九组危险权限 进行动态申请权限
        /*checkSelfPermission( )	判断权限是否具有某项权限
          requestPermissions( )	    申请权限
          onRequestPermissionsResult( )	申请权限回调方法
          shouldShowRequestPermissionRationale( )	是否要提示用户申请该权限的缘由*/

/*用户点击再一次拒绝以后 该如何处理 其实谷歌已经给我们提供了一个Google提供了一个非常好的思路，详见EasyPermissions
        EasyPermissions并没有存储上一次shouldShowRequestPermissionRationale()的返回值，
        而是在申请权限被拒后调用shouldShowRequestPermissionRationale()方法，如果此时返回false则说明用户勾选了“不再询问”。

        这里说一下我的办法 首先默认booeal b为true
        首先在请求权限的时候 判断 b， b是true则常规请求权限 b是false 说明用户点击了不在询问按钮 需要引导用户去设置页面手动打开权限
        然后我们在 请求权限失败的回调函数onRequestPermissionsResult中
        将b=shouldShowRequestPermissionRationale
        他只会在两种情况下返回false 第一种用户接受权限 此时对b不做任何处理 认为true
        第二种用户点击了不在询问按钮 如上所述

        当用户打开设置页面的时候 也有两种情况 一个是给了权限 另外一个是什么都不做 返回原APP中
        此时我们在onActivityResult 再一次检查权限 如果用户给了权限 就将b=true 如果没有 b还是维持false不变 这个时候就可以再一次去setting页面
        在这个过程中 我们可以适当增加dialog 来告知用户 我们为什么需要权限*/


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final int REQUEST_CODE = 1;
    private static final int REQUEST_CODE_WRITE_SETTINGS = 2;
    private static final int PERMISSION_REQUEST_SEND_SMS = 3;
    private static final int SETTING_ENABLE_PERMISSION = 4;
    private Toast toast;
    private Button btn_1;
    private Button btn_2;
    private Button btn_3;
    private boolean b = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        btn_1 = (Button) findViewById(R.id.btn_1);
        btn_2 = (Button) findViewById(R.id.btn_2);
        btn_3 = (Button) findViewById(R.id.btn_3);
        btn_1.setOnClickListener(this);
        btn_2.setOnClickListener(this);
        btn_3.setOnClickListener(this);
    }


    private void requestWriteSettings() {
        Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivityForResult(intent, REQUEST_CODE_WRITE_SETTINGS);
    }

    private void requestSystemAlertWindowPermission() {
        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
        System.out.println(getPackageName());
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivityForResult(intent, REQUEST_CODE);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQUEST_CODE:
                if (Settings.canDrawOverlays(this)) {
                    show("申请特殊权限 弹窗权限成功");
                }
                break;
            case REQUEST_CODE_WRITE_SETTINGS:
                show("申请特殊权限 写系统设置成功");
                break;
            case SETTING_ENABLE_PERMISSION:
                //打开设置按钮 设置权限的时候 回调这个函数


                //再一次检查权限 如果有权限 则改为ture 没有 不变
                int permissionCheck_Again = ContextCompat.checkSelfPermission(MainActivity.this, Manifest
                        .permission.SEND_SMS);
                if (permissionCheck_Again == PackageManager.PERMISSION_GRANTED) {
                    show("具有发短信的权限，可以发短信");
                    b=true;
                }
                break;
        }
    }

    //优化Toast显示
    private void show(String s) {
        if (toast == null) {
            toast = Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT);
            toast.show();
        } else {
            toast.cancel();
            toast = null;
            show(s);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_1:
                //申请特殊权限之 System Alert Window 设置悬浮框
                requestSystemAlertWindowPermission();
                break;
            case R.id.btn_2:
                //申请WRITE_SETTINGS权限
                requestWriteSettings();
                break;
            case R.id.btn_3:

                if (!b) {
                    show("用户点击了不在询问 引导用户去设置页面");
                    Uri uri = Uri.fromParts("package", this.getPackageName(), null);
                    Intent intent = new Intent(Settings
                            .ACTION_APPLICATION_DETAILS_SETTINGS, uri);
                    startActivityForResult(intent, SETTING_ENABLE_PERMISSION);
                    return;
                }

                int permissionCheck = ContextCompat.checkSelfPermission(MainActivity.this, Manifest
                        .permission.SEND_SMS);
                if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                    show("具有发短信的权限，可以发短信");
                } else {
                    //没有权限 需要动态申请短信权限
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest
                            .permission
                            .SEND_SMS}, PERMISSION_REQUEST_SEND_SMS);
                }
                break;
        }
    }

    //动态申请权限时 返回结果 调用的方法
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_REQUEST_SEND_SMS:
                if (grantResults.length > 0 && grantResults[0] == PackageManager
                        .PERMISSION_GRANTED) {
                    //已经授予权限
                    show("已经 授予权限 做一些事情");

                }
                if (grantResults.length > 0 && grantResults[0] == PackageManager
                        .PERMISSION_DENIED) {
                    //已经授予权限
                    show("权限被拒绝了");
                    //如果用户点击了不在询问 拒绝授予权限 此时
                    b = ActivityCompat.shouldShowRequestPermissionRationale(MainActivity
                            .this, Manifest.permission.SEND_SMS);
                    show("结果" + b);

                    //此结果只有在两种情况下返回false  第一种就是申请权限 用户同意
                    //第二种 申请权限 用户不同意 但是用户点击了 不在询问按钮
                    //返回true的情况 是权限被拒绝 但是用户没点击返回按钮
                    //如果用户点击了不在询问 此时 在申请权限 只能引导用户去setting页面进行设置

                  /*  if (lastB) {
                        if (!b) {
                            show("用户点击了不在询问 引导用户去设置页面");
                            Uri uri = Uri.fromParts("package", this.getPackageName(), null);
                            Intent intent = new Intent(Settings
                                    .ACTION_APPLICATION_DETAILS_SETTINGS, uri);
                            startActivityForResult(intent, SETTING_ENABLE_PERMISSION);
                        }

                    }*/


                }

        }
    }
}
