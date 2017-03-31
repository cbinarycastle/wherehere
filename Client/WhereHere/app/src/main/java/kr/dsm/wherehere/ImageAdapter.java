package kr.dsm.wherehere;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by BeINone on 2017-04-01.
 */

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageVH> {

    private Context mContext;
    private List<String> mImages;

    public ImageAdapter(Context context, List<String> images) {
        mContext = context;
        mImages = images;
    }

    @Override
    public ImageVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.vh_image, parent, false);
        return new ImageVH(rootView);
    }

    @Override
    public void onBindViewHolder(ImageVH holder, int position) {
        holder.bind(Base64.decode(mImages.get(position), Base64.DEFAULT));
    }

    @Override
    public int getItemCount() {
        return mImages.size();
    }

    public class ImageVH extends RecyclerView.ViewHolder {

        private ImageView mImageView;

        public ImageVH(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.iv_vh_image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }

        public void bind(byte[] image) {
            Glide.with(mContext).load(image).asBitmap().into(mImageView);
        }
    }

}
