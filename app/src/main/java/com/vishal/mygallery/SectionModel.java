package com.vishal.mygallery;

import java.util.ArrayList;
import java.util.List;

public class SectionModel {

    //    int viewType;
    private String sectionLabel;
    private List<ImageModel> itemArrayList;
    private boolean isSelected = false;
    private boolean isEnable = false;


    public SectionModel(String sectionLabel, List<ImageModel> itemArrayList) {
        this.sectionLabel = sectionLabel.toUpperCase().replace("_", " ");

        this.itemArrayList = itemArrayList;
    }


    public String getSectionLabel() {


        return sectionLabel;
    }


    public List<ImageModel> getItemArrayList() {
        return itemArrayList;
    }

    public ArrayList<Integer> getallItemsIds() {
        ArrayList<Integer> selectedItemId = new ArrayList<>();
        for (ImageModel m : itemArrayList) {


            selectedItemId.add(m.getId());


        }
        return selectedItemId;
    }

    public void setItemArrayList(List<ImageModel> itemArrayList) {
        this.itemArrayList = itemArrayList;
    }

    public boolean isEnable() {
        return isEnable;
    }

    public void setEnable(boolean enable) {
        isEnable = enable;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isAllSelected() {
        boolean isAllSelected = true;
        for (ImageModel m : itemArrayList) {

            if (!m.isSelected()) {
                isAllSelected = false;
                break;
            }
        }


        return isAllSelected;

    }

    public boolean isAllDeSelected() {
        boolean isDeAllSelected = true;
        for (ImageModel m : itemArrayList) {

            if (m.isSelected()) {
                isDeAllSelected = false;
                break;
            }
        }
        return isDeAllSelected;

    }

    public void clearSelection() {
        setSelected(false);
        setEnable(false);
        for (ImageModel m : itemArrayList) {

            m.setSelected(false);
        }
    }

    public int getSelectedItemCount() {
        int counter = 0;
        if (isSelected()) {
            counter = itemArrayList.size();
        } else {
            for (ImageModel m : itemArrayList) {

                if (m.isSelected()) {
                    counter++;
                }
            }
        }
        return counter;
    }
}
