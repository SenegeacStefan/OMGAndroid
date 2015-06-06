package com.darrylbayliss.omgandroid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by ogris on 6/6/2015.
 */
public class JSONAdapter extends BaseAdapter{
    private static final String IMAGE_URL_BASE = "http://covers.openlibrary.org/b/id/";

    Context mContext;
    LayoutInflater mInflater;
    JSONArray mJsonArray;

    public JSONAdapter(Context context, LayoutInflater inflater) {
        mContext = context;
        mInflater = inflater;
        mJsonArray = new JSONArray();
    }

    @Override
    public int getCount() {
        return mJsonArray.length();
    }

    @Override
    public Object getItem(int i) {
        return mJsonArray.optJSONObject(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if(convertView == null) {
            convertView = mInflater.inflate(R.layout.row_book,null);
            holder = new ViewHolder();
            holder.thumbnailImageView = (ImageView) convertView.findViewById(R.id.img_thumbnail);
            holder.titleTextView = (TextView) convertView.findViewById(R.id.text_title);
            holder.authorTextView = (TextView) convertView.findViewById(R.id.text_author);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        JSONObject jsonObject = (JSONObject)getItem(pos);

        if(jsonObject.has("cover_i")) {
            String imgID = jsonObject.optString("cover_i");
            String imgURL = IMAGE_URL_BASE + imgID + "-S.jpg";
            Picasso.with(mContext).load(imgURL).placeholder(R.drawable.ic_books).into(holder.thumbnailImageView);
        }else{
            holder.thumbnailImageView.setImageResource(R.drawable.ic_books);
        }
        String bookTitle = "";
        String authorName = "";
        if(jsonObject.has("title")) {
            bookTitle = jsonObject.optString("title");
        }

        if(jsonObject.has("author_name")) {
            authorName = jsonObject.optJSONArray("author_name").optString(0);
        }
        holder.titleTextView.setText(bookTitle);
        holder.authorTextView.setText(authorName);

        return convertView;
    }

    public void updateData(JSONArray jsonArray) {
        mJsonArray = jsonArray;
        notifyDataSetChanged();
    }

    private static class ViewHolder {
        public ImageView thumbnailImageView;
        public TextView titleTextView;
        public TextView authorTextView;

    }
}
