package com.example.bouba.cowfarming;

/**
 * Created by bouba on 01-Feb-18.
 */
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class CustomGridViewAdapter extends ArrayAdapter<Item> {
    Context context; int layoutResourceId;
    ArrayList<Item> data = new ArrayList<Item>();
    public CustomGridViewAdapter(Context context, int layoutResourceId, ArrayList<Item> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        RecordHolder holder = null;
        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new RecordHolder();
            holder.matricule = (TextView) row.findViewById(R.id.textView17);
            holder.dob = (TextView) row.findViewById(R.id.textView16);
            holder.image = (ImageView) row.findViewById(R.id.imageView6);
            holder.velage = (TextView)row.findViewById(R.id.calving);
            holder.milking = (TextView)row.findViewById(R.id.milking);
            holder.one = (LinearLayout)row.findViewById(R.id.one);
            row.setTag(holder);
        } else {
            holder = (RecordHolder) row.getTag();
        }
        Item item = data.get(position);
        holder.matricule.setText(holder.matricule.getText().toString()+" "+item.matricule.toString());
        holder.dob.setText(item.dob.toString());
        holder.velage.setText(item.velage);
        holder.milking.setText(item.stop);
        if (item.gender.equals("Male"))
            holder.one.setVisibility(View.GONE);
        if (item.pic!=0)
            holder.image.setImageResource(item.pic);
        else{
            final int THUMBSIZE = 64;
            Bitmap ThumbImage = ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(item.url.toString()),
                    THUMBSIZE, THUMBSIZE);
            holder.image.setImageBitmap(ThumbImage);
            //holder.image.setImageURI(Uri.parse(item.url.toString()));
        }
        return row;
    }
    static class RecordHolder {
        TextView matricule;
        TextView dob;
        ImageView image;
        TextView velage;
        TextView milking;
        String gender;
        LinearLayout one;
    }
}