package com.vishal.mygallery;

import android.content.Context;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;



import java.util.ArrayList;
import java.util.List;



public class ItemRecyclerViewAdapter extends RecyclerView.Adapter<ItemRecyclerViewAdapter.ItemViewHolder> {


    private OnChildItemClickListener mCallback;


    private Context context;
    private List<ImageModel> arrayList;
    private boolean isEditEnable;

    public ItemRecyclerViewAdapter(Context context, boolean isEditEnable) {
        this.context = context;
        this.isEditEnable = isEditEnable;
        this.arrayList = new ArrayList<>();
    }

    public void setCallback(OnChildItemClickListener mCallback) {
        this.mCallback = mCallback;
    }

    public void setUpdatedItems(List<ImageModel> updatedlist) {
        this.arrayList = updatedlist;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        view.getLayoutParams().height = getScreenWidth() / 3;

        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemViewHolder holder, int position) {
        ImageModel model = arrayList.get(holder.getAdapterPosition());

        if (!model.getFile_name().equals("")) {


            GlideApp.with(context)
                    .load(model.getFile_name())
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .into(holder.itemImg);

        }

        if (model.isSelected()) {
            holder.selectedLayout.setVisibility(View.VISIBLE);
        } else {
            holder.selectedLayout.setVisibility(View.GONE);
        }


        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mCallback.onChildItemClicked(v, holder.getAdapterPosition(), true);

            }
        });
        if (isEditEnable) {
            holder.parentLayout.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mCallback.onChildItemLongClicked(v, holder.getAdapterPosition(), true);
                    return true;
                }
            });
        }


    }

    public void onChildItemRemoved() {


        try {
            if (arrayList != null && arrayList.size() > 0) {
                for (int i = 0; i < arrayList.size(); i++) {
                    if (arrayList.get(i).isSelected()) {
                        arrayList.remove(i);
                        notifyItemRemoved(i);
                        i = -1;
                    }
                }
                onChildItemNotify();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onChildItemNotify() {


        notifyDataSetChanged();

    }

    private int getScreenWidth() {
        Point size = new Point();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = null;
        if (wm != null) {
            display = wm.getDefaultDisplay();

            display.getSize(size);
        }


        return size.x;
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private ImageView itemImg;
        private FrameLayout selectedLayout, parentLayout;

        public ItemViewHolder(View itemView) {
            super(itemView);
            selectedLayout = itemView.findViewById(R.id.item_layout_selection_layout);
            parentLayout = itemView.findViewById(R.id.item_layout_selection_parent_layout);
            itemImg = itemView.findViewById(R.id.item_layout_img);

        }
    }

    public interface OnChildItemClickListener {
        void onChildItemClicked(View view, int position, boolean isSelected);

        void onChildItemLongClicked(View view, int position, boolean isSelected);
    }

}