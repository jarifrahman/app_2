package com.example.app_2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class RecordListAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private ArrayList<Model> recordList;

    public RecordListAdapter(Context context, int layout, ArrayList<Model> recordList) {
        this.context = context;
        this.layout = layout;
        this.recordList = recordList;
    }

    @Override
    public int getCount() {
        return recordList.size();

    }

    @Override
    public Object getItem(int position) {
        return recordList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder implements View.OnClickListener{
        ImageView rowImage;
        TextView rowName,rowAge,rowPhone;


        @Override
        public void onClick(View v) {
            Toast.makeText(context, "works", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View row = view;
        ViewHolder holder = new ViewHolder();
        if(row==null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row= inflater.inflate(layout,null);
            holder.rowName = row.findViewById(R.id.row_name);
            holder.rowAge = row.findViewById(R.id.row_age);
            holder.rowPhone = row.findViewById(R.id.row_phone);
            holder.rowImage = row.findViewById(R.id.row_image);
            row.setTag(holder);
        }
        else{
            holder = (ViewHolder) row.getTag();

        }

        Model model = recordList.get(i);

        holder.rowName.setText(model.getName());
        holder.rowAge.setText(model.getAge());
        holder.rowPhone.setText(model.getPhone());
        byte[] recordImage = model.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(recordImage,0,recordImage.length);
        holder.rowImage.setImageBitmap(bitmap);


        return row;
    }
}
