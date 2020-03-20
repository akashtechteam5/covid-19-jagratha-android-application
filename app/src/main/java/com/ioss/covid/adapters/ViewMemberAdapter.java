package com.ioss.covid.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.ioss.covid.R;
import com.ioss.covid.core.AppConfig;
import com.ioss.covid.interfaces.ViewUsersListener;
import com.ioss.covid.model.ViewMembersModel.MemberItem;
import com.ioss.covid.utilities.Utility;

import java.util.ArrayList;
import java.util.List;

public class ViewMemberAdapter extends RecyclerView.Adapter<ViewMemberAdapter.ViewHolder> {

    private Context context;
    private List<MemberItem> dataList;
    private ViewUsersListener listener;

    public ViewMemberAdapter(Context context) {
        this.context = context;
        dataList=new ArrayList<>();
    }

    public ViewMemberAdapter setListener(ViewUsersListener listener){

        this.listener=listener;
        return this;
    }

    public ViewMemberAdapter addItem(MemberItem memberItem){
        dataList.add(memberItem);
        notifyDataSetChanged();
        return this;
    }

    public ViewMemberAdapter setList(List<MemberItem> dataList){
        this.dataList=dataList;
        notifyDataSetChanged();
        return this;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.layout_rrt_view_member_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setData(dataList.get(position));
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView nameTV,ageTV;
        private CardView statusCV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTV=(TextView)itemView.findViewById(R.id.nameTV);
            ageTV=(TextView)itemView.findViewById(R.id.ageTV);
            statusCV=(CardView) itemView.findViewById(R.id.statusCV);

            Utility.setFonts(AppConfig.openSansRegular,nameTV);
            Utility.setFonts(AppConfig.openSansLight,ageTV);

            itemView.setOnClickListener(this);
        }

        public void setData(MemberItem item){

            nameTV.setText(item.getName());
            ageTV.setText("Age :"+item.getAge());
            statusCV.setCardBackgroundColor(Color.parseColor(item.getHealthStatusColour()));
        }

        @Override
        public void onClick(View v) {
            if(listener == null || dataList.size() <1) return;
            listener.onClickUserItem(dataList.get(getAdapterPosition()));
        }
    }
}
