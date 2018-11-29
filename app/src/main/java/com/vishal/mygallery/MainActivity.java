package com.vishal.mygallery;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements OnImageItemClickListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.blank_layout)
    LinearLayout blankLayout;
    @BindView(R.id.selected_item_count_tv)
    TextView selectedItemCountTv;
    @BindView(R.id.selected_item_delete_tv)
    TextView deleteTv;
    @BindView(R.id.selected_item_clear_tv)
    TextView clearTv;
    @BindView(R.id.selected_item_action_layout)
    CardView actionLayout;
    @BindView(R.id.parent_layout)
    CoordinatorLayout parentLayout;
    private List<SectionModel> mlist;
    private boolean isLongPressEnable = false;
    private int selectedParentPos = -1, selectedChildPos = -1;
    private GalleryAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        init();
    }


    private void init() {
        mlist = new ArrayList<>();

        populateDataList();

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        if (mlist.size() > 0) {
            blankLayout.setVisibility(View.GONE);

            adapter = new GalleryAdapter(MainActivity.this, this, mlist, true);
            recyclerView.setAdapter(adapter);
        }

//        recyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));

//        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//
//                if (!isLongPressEnable) {
////                    reset();
//
//                } else {
//                    swipeLayout.setRefreshing(false);
//                }
//
//
//            }
//        });

    }


    private void populateDataList() {

        ImageModel imageModel = new ImageModel(1, "https://api.androidhive.info/json/movies/starwars.jpg", false);
        ImageModel imageModel1 = new ImageModel(2, "https://api.androidhive.info/json/movies/shape_of_water.jpg", false);

        ArrayList<ImageModel> imageModels = new ArrayList<>();
        imageModels.add(imageModel);
        imageModels.add(imageModel1);
        SectionModel model = new SectionModel("Week 1", imageModels);
        mlist.add(model);
        ImageModel imageModel2 = new ImageModel(3, "https://api.androidhive.info/json/movies/baahubali2.jpg", false);
        ImageModel imageModel3 = new ImageModel(4, "https://api.androidhive.info/json/movies/thor_ragnarok.jpg", false);
        ImageModel imageModel4 = new ImageModel(5, "https://api.androidhive.info/json/movies/justice_league.jpg", false);
        ImageModel imageModel5 = new ImageModel(6, "https://api.androidhive.info/json/movies/jumanji.jpg", false);
        ImageModel imageModel6 = new ImageModel(7, "https://api.androidhive.info/json/movies/spiderman_homecoming.jpg", false);
        ArrayList<ImageModel> imageModels1 = new ArrayList<>();
        imageModels1.add(imageModel2);
        imageModels1.add(imageModel3);
        imageModels1.add(imageModel4);
        imageModels1.add(imageModel5);
        imageModels1.add(imageModel6);
        SectionModel model1 = new SectionModel("Week 2", imageModels1);
        mlist.add(model1);

        ImageModel imageModel7 = new ImageModel(8, "https://api.androidhive.info/json/movies/disaster_artist.jpg", false);
        ImageModel imageModel8 = new ImageModel(9, "https://api.androidhive.info/json/movies/lady_bird.jpg", false);
        ImageModel imageModel9 = new ImageModel(10, "https://api.androidhive.info/json/movies/coco.jpg", false);
        ArrayList<ImageModel> imageModels2 = new ArrayList<>();
        imageModels2.add(imageModel7);
        imageModels2.add(imageModel8);
        imageModels2.add(imageModel9);

        SectionModel model2 = new SectionModel("Week 3", imageModels2);
        mlist.add(model2);

    }


    @OnClick({R.id.selected_item_delete_tv, R.id.selected_item_clear_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.selected_item_delete_tv:
                showDeleteConfirmationDialog();
                break;
            case R.id.selected_item_clear_tv:
                clearSelection();
                break;
        }
    }


    /**
     * Method to show progressbar
     */
    private void showProgressbar() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        if (progressBar != null) {
            progressBar.setVisibility(View.VISIBLE);
            blankLayout.setVisibility(View.GONE);

        }

    }

    /**
     * Method to hide progressbar
     */
    private void hideProgressbar() {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        if (progressBar != null) {
            progressBar.setVisibility(View.INVISIBLE);
            blankLayout.setVisibility(View.VISIBLE);

        }

    }


    @Override
    public void onItemClicked(List<SectionModel> updatedModelList) {


        updateSelectedItemCounter(updatedModelList);


    }

    @Override
    public void onImageClicked(ImageModel bumpModel, int Childpos, int parentPos, String week) {

        if (!isLongPressEnable) {


            // TODO OPEN DETAIL ACTIVITY
            selectedParentPos = parentPos;
            selectedChildPos = Childpos;

        }

    }

    private void updateSelectedItemCounter(List<SectionModel> updatedModelList) {
        int selectedCounter = 0;

        if (updatedModelList != null && updatedModelList.size() > 0) {
            for (int i = 0; i < updatedModelList.size(); i++) {

                selectedCounter += updatedModelList.get(i).getSelectedItemCount();
            }
        }


        selectedItemCountTv.setText(selectedCounter + " item selected");


    }

    @Override
    public void onItemLongClick(List<SectionModel> updatedModelList) {

        isLongPressEnable = true;

        actionLayout.setVisibility(View.VISIBLE);
        updateSelectedItemCounter(updatedModelList);


    }

    private void showDeleteConfirmationDialog() {

        new AlertDialog.Builder(MainActivity.this)
                .setMessage("Are you sure want to delete image?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        deleteSelectedItems();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();

    }

    /**
     * Delete selected images
     */
    private void deleteSelectedItems() {

        if (adapter != null) {
            if (adapter.getSelectedItemID().size() > 0) {

                removeSelectedItems();
            } else {
                Snackbar.make(parentLayout, "Please select at least one item to delete", Snackbar.LENGTH_SHORT);
            }

        }
    }

    /**
     * Remove selected item as per position
     *
     * @param parentPos parent item position
     * @param childPos  image position
     */
    private void removeSelectedItems(int parentPos, int childPos) {
        try {
            if (mlist != null && mlist.size() > 0) {

                try {
                    if (mlist.get(parentPos).getItemArrayList().size() == 1) {
                        mlist.remove(parentPos);
                        adapter.notifyItemRemoved(parentPos);

                    } else {
                        GalleryAdapter.ItemViewHolder viewHolder = (GalleryAdapter.ItemViewHolder) recyclerView.findViewHolderForAdapterPosition(parentPos);
                        if (viewHolder != null) {
                            RecyclerView imageRecyclerView = viewHolder.getRecyclerView();
                            ItemRecyclerViewAdapter itemRecyclerViewAdapter = (ItemRecyclerViewAdapter) imageRecyclerView.getAdapter();
                            if (itemRecyclerViewAdapter != null) {
                                mlist.get(parentPos).getItemArrayList().remove(childPos);
                                itemRecyclerViewAdapter.setUpdatedItems(mlist.get(parentPos).getItemArrayList());

                            }
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
            selectedChildPos = -1;
            selectedParentPos = -1;


            if (mlist.size() > 0) {
//                Timber.e("Remain Item In List=> %d", mlist.size());
                actionLayout.setVisibility(View.GONE);
                isLongPressEnable = false;
                blankLayout.setVisibility(View.GONE);
                selectedItemCountTv.setText(getResources().getString(R.string.no_item_selected));

            } else {

                actionLayout.setVisibility(View.GONE);

                isLongPressEnable = false;
                blankLayout.setVisibility(View.VISIBLE);


            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Remove all selected items from adapter
     */
    private void removeSelectedItems() {
        try {
            if (mlist != null && mlist.size() > 0) {
                for (int i = 0; i < mlist.size(); i++) {

                    if (mlist.get(i).isSelected()) {
                        mlist.remove(i);
                        adapter.notifyItemRemoved(i);

                        i = -1;

                    } else {
                        GalleryAdapter.ItemViewHolder viewHolder = (GalleryAdapter.ItemViewHolder) recyclerView.findViewHolderForAdapterPosition(i);
                        if (viewHolder != null) {
                            RecyclerView imageRecyclerView = viewHolder.getRecyclerView();
                            ItemRecyclerViewAdapter itemRecyclerViewAdapter = (ItemRecyclerViewAdapter) imageRecyclerView.getAdapter();
                            if (itemRecyclerViewAdapter != null) {
                                itemRecyclerViewAdapter.onChildItemRemoved();

                            }
                        }

                    }


                }


            }


            if (mlist != null) {

                if (mlist.size() > 0) {
                    clearSelection();
//                Timber.e("Remain Item In List=> %d", mlist.size());
                    actionLayout.setVisibility(View.GONE);
                    isLongPressEnable = false;
                    blankLayout.setVisibility(View.GONE);
                    selectedItemCountTv.setText(getResources().getString(R.string.no_item_selected));

                } else {

                    actionLayout.setVisibility(View.GONE);

                    isLongPressEnable = false;
                    blankLayout.setVisibility(View.VISIBLE);


                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Clear all selection
     */
    private void clearSelection() {
        if (mlist != null && mlist.size() > 0) {
            for (int i = 0; i < mlist.size(); i++) {
                mlist.get(i).setEnable(false);
                mlist.get(i).setSelected(false);


                for (int j = 0; j < mlist.get(i).getItemArrayList().size(); j++) {

                    mlist.get(i).getItemArrayList().get(j).setSelected(false);

                    GalleryAdapter.ItemViewHolder viewHolder = (GalleryAdapter.ItemViewHolder) recyclerView.findViewHolderForAdapterPosition(i);
                    if (viewHolder != null) {
                        RecyclerView imageRecyclerView = viewHolder.getRecyclerView();
                        imageRecyclerView.getAdapter().notifyItemChanged(j);
                    }

                }
                adapter.notifyItemChanged(i);


            }

            selectedItemCountTv.setText(getResources().getString(R.string.no_item_selected));
            actionLayout.setVisibility(View.GONE);
            isLongPressEnable = false;


        }
    }
}
