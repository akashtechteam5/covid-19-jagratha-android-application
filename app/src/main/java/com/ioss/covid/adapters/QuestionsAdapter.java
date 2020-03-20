package com.ioss.covid.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ioss.covid.R;
import com.ioss.covid.itemClass.QuestionItem;
import com.ioss.covid.itemClass.SymptomsItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class QuestionsAdapter extends RecyclerView.Adapter<QuestionsAdapter.ViewHolder> {

    private Context context;
    private List<QuestionItem> dataList;

    public QuestionsAdapter(Context context) {
        this.context = context;
        dataList=new ArrayList<>();
    }

    public QuestionsAdapter addItem(QuestionItem item){

        dataList.add(item);
        notifyDataSetChanged();
        return this;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.layout_question_item,parent,false);
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

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView questionTV;
        private RadioGroup answerRG;
        private EditText answerET;
        private View mainView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            questionTV=(TextView)itemView.findViewById(R.id.questionTV);
            answerRG=(RadioGroup) itemView.findViewById(R.id.answerRG);
            answerET=(EditText) itemView.findViewById(R.id.answerET);
            mainView=itemView;
        }

        public void setData(QuestionItem item){
            questionTV.setText(getAdapterPosition()+1+". "+item.getQuestion());
            if(item.getType().equalsIgnoreCase("radio")){
                answerRG.setVisibility(View.VISIBLE);
                answerET.setVisibility(View.GONE);
                addRadioButtons(6);
            }else if(item.getType().equalsIgnoreCase("text")){
                answerRG.setVisibility(View.GONE);
                answerET.setVisibility(View.VISIBLE);
            }else {
                answerRG.setVisibility(View.GONE);
                answerET.setVisibility(View.GONE);
            }
        }

        public void addRadioButtons(int number) {
            for (int i = 1; i <= number; i++) {
                RadioButton rdbtn = new RadioButton(context);
                rdbtn.setId(View.generateViewId());
                rdbtn.setText("Radio " + rdbtn.getId());
                rdbtn.setChecked(true);
//                rdbtn.setOnClickListener(context);
                answerRG.addView(rdbtn);
            }
        }

    }

    public JSONArray getAnswers(){
        JSONArray array=new JSONArray();
        for(int i=0;i<dataList.size();i++){
            QuestionItem item=dataList.get(i);
            JSONObject jsonObject=new JSONObject();
            if(item.getAnswers().size()==0){
                Toast.makeText(context,context.getString(R.string.please_ans_for_qn_s,i+1+""),Toast.LENGTH_SHORT).show();
                return null;
            }
            try {
                jsonObject.put("id",item.getId());
                JSONArray ansArray=new JSONArray();

                for(int k=0;k<item.getAnswers().size();k++) {
                    ansArray.put(item.getAnswers().get(k));
                }
                jsonObject.put("ans", ansArray);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            array.put(jsonObject);
        }
        return array;
    }

}
