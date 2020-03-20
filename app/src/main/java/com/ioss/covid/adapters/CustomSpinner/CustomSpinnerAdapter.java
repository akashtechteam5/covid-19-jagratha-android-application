package com.ioss.covid.adapters.CustomSpinner;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ioss.covid.R;

import java.util.ArrayList;
import java.util.List;

import static com.ioss.covid.core.AppConfig.openSansRegular;

public class CustomSpinnerAdapter extends BaseAdapter {

    private List<Object> dataList;
    private LayoutInflater inflater;
    private boolean hasTitle=false;

    public CustomSpinnerAdapter(Context context, ArrayList<Object> dataList) {
        inflater = LayoutInflater.from(context);
        this.dataList=dataList;
    }
    public CustomSpinnerAdapter hasTitle(boolean hasTitle){
        this.hasTitle=hasTitle;
        return this;
    }

    public CustomSpinnerAdapter clear(){
        dataList.clear();
        notifyDataSetChanged();
        return this;
    }

    public CustomSpinnerAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        dataList=new ArrayList<>();
    }

    public CustomSpinnerAdapter addItem(SpinnerItem item) {
        dataList.add(item);
        notifyDataSetChanged();
        return this;
    }

    public void setList(List<Object> dataList){
        this.dataList=dataList;
        notifyDataSetChanged();
    }


    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {

        TextView text1 = (TextView) inflater.inflate(R.layout.layout_custom_spinner_item_dropdown, parent, false);
        text1.setTypeface(openSansRegular);
        SpinnerItem spinnerItem = (SpinnerItem) dataList.get(position);
        text1.setText(spinnerItem.getName());

        if (position == 0 && hasTitle) {
            text1.setTextColor(Color.GRAY);
        }

        return text1;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        TextView text1 = (TextView) inflater.inflate(R.layout.layout_custom_spinner_item_dropdown, viewGroup, false);
        text1.setTypeface(openSansRegular);
        SpinnerItem spinnerItem = (SpinnerItem) dataList.get(position);
        if (position == 0 && hasTitle) {
            text1.setTextColor(Color.GRAY);
        }
        text1.setText(spinnerItem.getName());
        return text1;
    }

    public String getSpinnerItemId(int position){
        SpinnerItem spinnerItem = (SpinnerItem) dataList.get(position);
        return spinnerItem.getId();
    }

    public String getSelectionText(int position) {
        SpinnerItem spinnerItem = (SpinnerItem) dataList.get(position);
        return spinnerItem.getName();
    }

    public int getPosition(String id) {
        for (int i = 0; i<dataList.size();i++){
            SpinnerItem spinnerItem = (SpinnerItem) dataList.get(i);
            if (id.equalsIgnoreCase(spinnerItem.getId())){
                return i;
            }
        }
        return 0;
    }


}
