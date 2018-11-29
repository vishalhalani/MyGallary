package com.vishal.mygallery;

import android.os.Parcel;
import android.os.Parcelable;



public class ImageModel implements Parcelable {


    int id;


    String file_name;


    boolean isSelected = false;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public ImageModel(int id, String file_name, boolean isSelected) {
        this.id = id;
        this.file_name = file_name;
        this.isSelected = isSelected;
    }

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    @SuppressWarnings("unused")
    public static final Creator<ImageModel> CREATOR = new Creator<ImageModel>() {
        @Override
        public ImageModel createFromParcel(Parcel in) {
            return new ImageModel(in);
        }

        @Override
        public ImageModel[] newArray(int size) {
            return new ImageModel[size];
        }
    };

    protected ImageModel(Parcel in) {
        id = in.readInt();

        file_name = in.readString();
        isSelected = in.readByte() != 0x00;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);

        dest.writeString(file_name);
        dest.writeByte((byte) (isSelected ? 0x01 : 0x00));
    }
}