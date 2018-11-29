package com.vishal.mygallery;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;



import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ItemViewHolder> {

    private Context context;
    private List<SectionModel> sectionModelArrayList;
    private boolean isEditEnable;
    OnImageItemClickListener mCallback;

    public GalleryAdapter(Context context, OnImageItemClickListener mCallback, List<SectionModel> sectionModelArrayList, boolean isEditEnable) {
        this.context = context;
        this.mCallback = mCallback;
        this.isEditEnable = isEditEnable;
        this.sectionModelArrayList = sectionModelArrayList;
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemViewHolder itemViewHolder, int i) {
        final int position = itemViewHolder.getAdapterPosition();
        final SectionModel sectionModel = sectionModelArrayList.get(position);


        if (sectionModel != null) {

            itemViewHolder.sectionLabel.setText(sectionModel.getSectionLabel());
            if (sectionModel.getItemArrayList() != null && sectionModel.getItemArrayList().size() > 0) {
                String count = "(" + sectionModel.getItemArrayList().size() + ")";
                itemViewHolder.sectionItemCountTv.setText(count);
            }


            if (sectionModel.isEnable()) {
                itemViewHolder.selectionChb.setVisibility(View.VISIBLE);
                if (sectionModel.isSelected()) {
                    itemViewHolder.selectionChb.setChecked(true);
                } else {
                    itemViewHolder.selectionChb.setChecked(false);
                }

            } else {
                itemViewHolder.selectionChb.setVisibility(View.GONE);
                itemViewHolder.selectionChb.setChecked(false);

            }


            if (isEditEnable) {
                itemViewHolder.layout.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        if (!sectionModel.isEnable()) {
                            setSelectHeader(true);
                            mCallback.onItemLongClick(sectionModelArrayList);
                        }

                        return false;
                    }
                });


                if (sectionModel.isEnable()) {
                    itemViewHolder.selectionChb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                            if (isChecked) {


                                if (!sectionModel.isSelected() && sectionModel.isAllDeSelected()) {
                                    sectionModel.setSelected(true);
                                    setAllChildSelection(sectionModel, true, position, itemViewHolder.itemRecyclerView);
                                } else if (!sectionModel.isSelected() && !sectionModel.isAllDeSelected()) {
                                    sectionModel.setSelected(true);
                                    setAllChildSelection(sectionModel, true, position, itemViewHolder.itemRecyclerView);
                                }


                            } else {


                                if (sectionModel.isSelected() && sectionModel.isAllSelected()) {
                                    sectionModel.setSelected(false);
                                    setAllChildSelection(sectionModel, false, position, itemViewHolder.itemRecyclerView);

                                } else if (sectionModel.isSelected() && !sectionModel.isAllSelected()) {
                                    sectionModel.setSelected(false);
                                    setAllChildSelection(sectionModel, true, position, itemViewHolder.itemRecyclerView);
                                }


                            }
                        }
                    });
                }
            }

            itemViewHolder.itemRecyclerView.setHasFixedSize(true);
            itemViewHolder.itemRecyclerView.setNestedScrollingEnabled(false);

            ItemRecyclerViewAdapter adapter;


            if (itemViewHolder.itemRecyclerView.getAdapter() == null) {
                adapter = new ItemRecyclerViewAdapter(context, isEditEnable);

                if (itemViewHolder.itemRecyclerView.getLayoutManager() == null) {
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 3);

                    itemViewHolder.itemRecyclerView.setLayoutManager(gridLayoutManager);

                    if (itemViewHolder.itemRecyclerView.getItemDecorationCount() == 0) {
                        itemViewHolder.itemRecyclerView.addItemDecoration(new GridSpacesItemDecoration((int) context.getResources().getDimension(R.dimen._3sdp), 3));
                    }

                }
                itemViewHolder.itemRecyclerView.setAdapter(adapter);
            } else {
                adapter = ((ItemRecyclerViewAdapter) itemViewHolder.itemRecyclerView.getAdapter());
            }

            adapter.setUpdatedItems(sectionModel.getItemArrayList());

            adapter.setCallback(new ItemRecyclerViewAdapter.OnChildItemClickListener() {
                @Override
                public void onChildItemClicked(View view, int pos, boolean isSelected) {
                    if (sectionModel.isEnable()) {
                        try {

                            boolean mSelected = !sectionModel.getItemArrayList().get(pos).isSelected();
                            sectionModel.getItemArrayList().get(pos).setSelected(mSelected);


                            Objects.requireNonNull(itemViewHolder.getRecyclerView().getAdapter()).notifyItemChanged(pos);


                            if (!sectionModel.isSelected() && sectionModel.isAllSelected()) {
                                sectionModel.setSelected(true);
                                itemViewHolder.selectionChb.setChecked(true);
                            } else if (sectionModel.isSelected() && !sectionModel.isAllSelected()) {
                                sectionModel.setSelected(false);
                                itemViewHolder.selectionChb.setChecked(false);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        mCallback.onItemClicked(sectionModelArrayList);

                    } else {
                        ImageModel imageModel = sectionModel.getItemArrayList().get(pos);
                        mCallback.onImageClicked(imageModel, pos, position, sectionModel.getSectionLabel());
                    }
                }

                @Override
                public void onChildItemLongClicked(View view, int pos, boolean isSelected) {

                    if (!sectionModel.isEnable()) {

                        sectionModel.getItemArrayList().get(pos).setSelected(true);
                        Objects.requireNonNull(itemViewHolder.getRecyclerView().getAdapter()).notifyItemChanged(pos);


                        if (!sectionModel.isSelected() && sectionModel.isAllSelected()) {
                            sectionModel.setSelected(true);
                            itemViewHolder.selectionChb.setChecked(true);
                        } else if (sectionModel.isSelected() && !sectionModel.isAllSelected()) {
                            sectionModel.setSelected(false);
                            itemViewHolder.selectionChb.setChecked(false);
                        }
//                            sectionModel.setSelected(true);
                        setSelectHeader(true);

                        mCallback.onItemLongClick(sectionModelArrayList);
//                        mFragment.onItemClicked(sectionModelArrayList);

                    }

                }
            });

        }
    }

    public List<SectionModel> getUpdatedList() {

        return this.sectionModelArrayList;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_header_layout, parent, false);
        return new ItemViewHolder(view);

    }


    public void removeSelectedItems(int pos) {
        try {
            if (sectionModelArrayList != null && sectionModelArrayList.size() > 0) {
//                for (int i = 0; i < sectionModelArrayList.size(); i++) {


                if (sectionModelArrayList.get(pos).isSelected()) {
                    sectionModelArrayList.remove(pos);
                    notifyItemRemoved(pos);

                }

//                }
            }
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }

    public void setAllChildSelection(SectionModel sectionModel, boolean isSelected, int itemPos, RecyclerView recyclerView) {
        if (sectionModel.isEnable()) {
            if (sectionModelArrayList != null && sectionModelArrayList.size() > 0) {

                if (sectionModelArrayList.get(itemPos).getItemArrayList() != null && sectionModelArrayList.get(itemPos).getItemArrayList().size() > 0) {
                    for (int i = 0; i < sectionModelArrayList.get(itemPos).getItemArrayList().size(); i++) {
                        try {
                            sectionModelArrayList.get(itemPos).getItemArrayList().get(i).setSelected(isSelected);

                            Objects.requireNonNull(recyclerView.getAdapter()).notifyItemChanged(i);

                        } catch (IndexOutOfBoundsException e) {
                            e.printStackTrace();
                        }
                    }
                }
                mCallback.onItemClicked(sectionModelArrayList);

            }

        }
    }

    public void setSelectHeader(boolean isSelected) {
//        if (!sectionModel.isEnable()) {
        if (sectionModelArrayList != null && sectionModelArrayList.size() > 0) {
            try {

                for (int i = 0; i < sectionModelArrayList.size(); i++) {
                    sectionModelArrayList.get(i).setEnable(isSelected);

                    notifyItemChanged(i);
                }

            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        }

    }

    public interface OnItemClickListener {
        void onItemClicked(List<SectionModel> updatedModelList);

        void onImageClicked(ImageModel bumpModel, int Childpos, int parentPos, String week);
    }

    public ArrayList<Integer> getSelectedItemID() {
        ArrayList<Integer> selectedId = new ArrayList<>();
        if (sectionModelArrayList != null && sectionModelArrayList.size() > 0) {
            for (SectionModel m : sectionModelArrayList) {
                if (m.isSelected()) {
                    selectedId.addAll(m.getallItemsIds());
                } else {
                    for (ImageModel md : m.getItemArrayList()) {
                        if (md.isSelected()) {
                            selectedId.add(md.getId());
                        }
                    }
                }

            }
        }
        return selectedId;
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(List<SectionModel> updatedModelList);
    }

    @Override
    public int getItemCount() {
        return sectionModelArrayList.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView sectionLabel, sectionItemCountTv;
        private CheckBox selectionChb;
        private RecyclerView itemRecyclerView;
        private RelativeLayout layout;

        public ItemViewHolder(View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.item_header_parent_layout);
            sectionLabel = itemView.findViewById(R.id.item_header_layout_title_tv);
            sectionItemCountTv = itemView.findViewById(R.id.item_header_layout_count_tv);
            selectionChb = itemView.findViewById(R.id.item_header_checkbox);
            itemRecyclerView = itemView.findViewById(R.id.item_section_recyclerview);
        }

        public RecyclerView getRecyclerView() {
            return itemRecyclerView;
        }


    }


}




