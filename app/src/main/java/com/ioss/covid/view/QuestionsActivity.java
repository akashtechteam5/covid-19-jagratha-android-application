package com.ioss.covid.view;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.ioss.covid.R;
import com.ioss.covid.adapters.QuestionsAdapter;
import com.ioss.covid.core.CoreActivity;
import com.ioss.covid.databinding.ActivityQuestionsBinding;
import com.ioss.covid.itemClass.QuestionItem;
import com.ioss.covid.viewModel.QuestionsViewModel;

public class QuestionsActivity extends CoreActivity {

    private QuestionsViewModel viewModel;
    private ActivityQuestionsBinding binding;
    private QuestionsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindView();
        setAdapter();

        binding.submitBT.setOnClickListener(onClicksubmit);
    }

    private void bindView() {
        viewModel = ViewModelProviders.of(this).get(QuestionsViewModel.class);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_questions);
        binding.setLifecycleOwner(this);
        binding.setViewmodel(viewModel);
        setProgress(viewModel);
    }

    private void setAdapter() {
        adapter = new QuestionsAdapter(context);
        adapter.addItem(new QuestionItem("1", "fdsfdsdsfdfdsfdsfdsfff", "radio"))
                .addItem(new QuestionItem("1", "fdsfdsdsfdfdsfdsfdsfff", "dfgd"))
                .addItem(new QuestionItem("1", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", "text"));
        binding.questionsRV.setLayoutManager(new LinearLayoutManager(context));
        binding.questionsRV.setAdapter(adapter);
    }

    View.OnClickListener onClicksubmit = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (adapter.getAnswers() == null) return;
            Toast.makeText(context, adapter.getAnswers().toString(), Toast.LENGTH_SHORT).show();
        }
    };

}
