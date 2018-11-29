package com.vishal.mygallery;

import java.util.List;

public interface OnImageItemClickListener {

    public void onItemClicked(List<SectionModel> mlist);
    public void onItemLongClick (List<SectionModel> mlist);
    public void onImageClicked(ImageModel model, int childPos, int parentPos, String sectionLabel);
}
