package com.darrylbayliss.omgandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.ShareActionProvider;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by ogris on 6/6/2015.
 */
public class DetailActivity extends ActionBarActivity{
    private final String IMAGE_URL_BASE = "http://covers.openlibrary.org/b/id/";
    String mImageUrl;
    ShareActionProvider mShareActionProvider;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ImageView imageView = (ImageView) findViewById(R.id.img_cover);

        String coverID = this.getIntent().getExtras().getString("coverID");

        if(coverID.length() > 0 ) {
            mImageUrl = IMAGE_URL_BASE + coverID + "-L.jpg";
            Picasso.with(this).load(mImageUrl).placeholder(R.drawable.img_books_loading).into(imageView);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem shareItem = menu.findItem(R.id.menu_item_share);

        if(shareItem != null) {
            mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(shareItem);
        }
        setShareIntent();
        return true;
    }

    private void setShareIntent() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "From the app");
        shareIntent.putExtra(Intent.EXTRA_TEXT, mImageUrl);
        mShareActionProvider.setShareIntent(shareIntent);
    }
}
