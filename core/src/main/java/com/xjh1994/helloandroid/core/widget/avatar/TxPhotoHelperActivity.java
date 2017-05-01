package com.xjh1994.helloandroid.core.widget.avatar;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.xjh1994.helloandroid.core.R;

import java.io.File;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

import static com.xjh1994.helloandroid.core.widget.avatar.TxPhotoHelper.RC_CAMERA_PERM;
import static com.xjh1994.helloandroid.core.widget.avatar.TxPhotoHelper.RC_GALLERY_PERM;

/**
 * Created by xjh1994 on 16/12/2.
 */

public class TxPhotoHelperActivity extends AppCompatActivity
        implements EasyPermissions.PermissionCallbacks {

    private static final int RC_SETTINGS_SCREEN = 125;

    private int mType;
    private int mQuality;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mType = getIntent().getIntExtra(TxPhotoHelper.EXTRA_TYPE, 0);
        mQuality = getIntent().getIntExtra(TxPhotoHelper.EXTRA_QUALITY, 100);
        if (mType == TxPhotoHelper.TAKE_PHOTO) {
            cameraTask();
        } else if (mType == TxPhotoHelper.CHOOSE_PICTURE) {
            galleryTask();
        } else if (mType == TxPhotoHelper.CROP_PICTURE) {

        }
    }

    @AfterPermissionGranted(RC_CAMERA_PERM)
    public void cameraTask() {
        if (EasyPermissions.hasPermissions(this, Manifest.permission.CAMERA)) {
            TxPhotoHelper.takePhoto(this);
        } else {
            EasyPermissions.requestPermissions(this, getString(R.string.tx_rationale_camera),
                    RC_CAMERA_PERM, Manifest.permission.CAMERA);
        }
    }

    @AfterPermissionGranted(RC_GALLERY_PERM)
    public void galleryTask() {
        if (EasyPermissions.hasPermissions(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            TxPhotoHelper.pickFromGallery(this);
        } else {
            EasyPermissions.requestPermissions(this, getString(R.string.tx_rationale_gallery),
                    RC_GALLERY_PERM, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            finish();
            return;
        }

        if (requestCode == TxPhotoHelper.CHOOSE_PICTURE) {
            //相册
            if (data != null && data.getData() != null) {
                Uri uri = data.getData();// 获取图片的uri
                TxPhotoHelper.cropPicture(this, uri);
            } else {
                finish();
            }
        } else if (requestCode == TxPhotoHelper.CROP_PICTURE) {
            if (data != null) {
                Bitmap bitmap = data.getParcelableExtra("data");
                if (bitmap != null) {
                    TxPhotoHelper.uploadAvatar(this, bitmap, mQuality);   //结束Activity返回
                } else {
                    finish();
                }
            } else {
                finish();
            }
        } else if (requestCode == TxPhotoHelper.TAKE_PHOTO) {
            File tempFile = TxPhotoHelper.getTempFile();
            if (tempFile.exists()) {
                Uri uri = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    uri = FileProvider.getUriForFile(this, getPackageName() + ".fileprovider", tempFile);
                } else {
                    uri = Uri.fromFile(tempFile);
                }
                TxPhotoHelper.cropPicture(this, uri);
                TxPhotoHelper.setTempFile(null);
            } else {
                finish();
            }
        } else if (requestCode == TxPhotoHelper.UPLOAD_PICTURE) {   //只上传图片，不设置头像
            //相册
            if (data != null && data.getData() != null) {
                Uri uri = data.getData();// 获取图片的uri
                TxPhotoHelper.cropPicture(this, uri, TxPhotoHelper.CROP_AND_UPLOAD_PICTURE);
            } else {
                finish();
            }
        } else if (requestCode == TxPhotoHelper.CROP_AND_UPLOAD_PICTURE) {
            if (data != null) {
                Bitmap bitmap = data.getParcelableExtra("data");
                if (bitmap != null) {
                    TxPhotoHelper.uploadFile(this, bitmap, mQuality);   //结束Activity返回
                } else {
                    finish();
                }
            } else {
                finish();
            }
        }

        if (requestCode == RC_SETTINGS_SCREEN) {
            Toast.makeText(this, R.string.tx_returned_from_app_settings_to_activity, Toast.LENGTH_SHORT)
                    .show();
            finish();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this, getString(R.string.tx_rationale_ask_again))
                    .setTitle(getString(R.string.tx_title_settings_dialog))
                    .setPositiveButton(getString(R.string.tx_setting))
                    .setNegativeButton(getString(R.string.tx_cancel), null /* click listener */)
                    .setRequestCode(RC_SETTINGS_SCREEN)
                    .build()
                    .show();
        } else {
            finish();
        }
    }
}
