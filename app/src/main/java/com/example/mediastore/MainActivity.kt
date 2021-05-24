package com.example.mediastore

import android.Manifest
import android.content.ContentUris
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.GridLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Here, thisActivity is the current activity

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1);
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 1);
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), 1);
        }
        setContentView(R.layout.activity_main);
        val photoList = getImageList()

        recycler_view.adapter = PhotoAdapter(photoList)
        recycler_view.layoutManager = GridLayoutManager(this,2)
        recycler_view.setHasFixedSize(true)



    }
    private fun getImageList(): List<PhotoItem> {
        val list = ArrayList<PhotoItem>()
        val projection = arrayOf(
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
                MediaStore.Images.Media.DATE_TAKEN,
                MediaStore.Images.Media.DISPLAY_NAME
        )

        val images: Uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        Log.i("ListingImages", " query count=" + images.toString())
        applicationContext.contentResolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection,
                null,
                null,
                null
        )?.use { cursor ->
            val idColumn = cursor.getColumnIndexOrThrow(
                    MediaStore.Images.Media._ID
            )
            val nameColumn = cursor.getColumnIndexOrThrow(
                    MediaStore.Images.Media.DISPLAY_NAME
            )
            while (cursor.moveToNext()) {
                val id = cursor.getLong(idColumn)
                val name = cursor.getString(nameColumn)
                val contentUri = ContentUris.withAppendedId(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id
                )
                val item = PhotoItem(contentUri.toString(), name)
                list += item
                Log.i("ListingImages", " Media ID: $id" +" Media NAME: $name"+ "URI: $contentUri")
            }
        }
        return list
    }

}