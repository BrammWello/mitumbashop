package com.devbramm.mitumba.Adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.devbramm.mitumba.R;

import java.util.ArrayList;

public class TopDealsAdapter extends RecyclerView.Adapter<TopDealsAdapter.ViewHolder> {

    //vars
    private ArrayList<String> mItemNames = new ArrayList<>();
    private ArrayList<Integer> mImageUrls = new ArrayList<>();
    private Context mContext;

    public TopDealsAdapter(Context context, ArrayList<String> dealName, ArrayList<Integer> imageUrls){
        mItemNames = dealName;
        mImageUrls = imageUrls;
        mContext = context;
    }

    @NonNull
    @Override
    public TopDealsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.top_deals_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopDealsAdapter.ViewHolder viewHolder, final int position) {
        Glide.with(mContext)
                .asBitmap()
                .load(mImageUrls.get(position))
                .into(viewHolder.dealImage);

        viewHolder.dealName.setText(mItemNames.get(position));

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, mItemNames.get(position), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mImageUrls.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView dealImage;
        TextView dealName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            dealImage = itemView.findViewById(R.id.deal_image);
            dealName = itemView.findViewById(R.id.deal_name);
        }
    }
}
