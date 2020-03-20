package com.ioss.covid.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.ioss.covid.R;
import com.ioss.covid.itemClass.SymptomsItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SymptomsAdapter extends RecyclerView.Adapter<SymptomsAdapter.ViewModel> {

    private Context context;
    private List<SymptomsItem> dataList;

    public SymptomsAdapter(Context context) {
        this.context = context;
        dataList=new ArrayList<>();
    }

    public SymptomsAdapter addItem(SymptomsItem item){
        dataList.add(item);
        notifyDataSetChanged();
        return this;
    }

    @NonNull
    @Override
    public ViewModel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.layout_symptoms_item,parent,false);
        return new ViewModel(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewModel holder, int position) {
        holder.setData(dataList.get(position));
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewModel extends RecyclerView.ViewHolder {

        private TextView questionTV;
        private RadioGroup answerRG;
        private RadioButton yesRB,noRB;

        public ViewModel(@NonNull View itemView) {
            super(itemView);
            questionTV=(TextView)itemView.findViewById(R.id.questionTV);
            answerRG=(RadioGroup) itemView.findViewById(R.id.answerRG);
            yesRB=(RadioButton) itemView.findViewById(R.id.yesRB);
            noRB=(RadioButton) itemView.findViewById(R.id.noRB);

            answerRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                   if(checkedId==R.id.yesRB){
                       dataList.get(getAdapterPosition()).setAns(context.getString(R.string.yes));
                   }else if(checkedId==R.id.noRB){
                       dataList.get(getAdapterPosition()).setAns(context.getString(R.string.no));
                   }
                }
            });
        }

        void setData(SymptomsItem item){
            questionTV.setText(getAdapterPosition()+1+". "+item.getQuestion());
            String ans=item.getAns();
            if(ans.equalsIgnoreCase("yes")){
                yesRB.setChecked(true);
            }else if(ans.equalsIgnoreCase("no")){
                noRB.setChecked(true);
            }
        }
    }
    
    public JSONArray getSymptonArray(){
        
        JSONArray array=new JSONArray();
        for(int i=0;i<dataList.size();i++){
            SymptomsItem item=dataList.get(i);
            JSONObject jsonObject=new JSONObject();
            if(item.getAns().length()==0){
                Toast.makeText(context,context.getString(R.string.please_ans_for_qn_s,i+1+""),Toast.LENGTH_SHORT).show();
                return null;
            }
            try {
                jsonObject.put("id",item.getId());
                jsonObject.put("ans",item.getAns());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            array.put(jsonObject);
        }
        return array;
    }
}
