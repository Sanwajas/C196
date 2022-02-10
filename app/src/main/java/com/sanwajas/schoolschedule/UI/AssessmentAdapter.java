package com.sanwajas.schoolschedule.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sanwajas.schoolschedule.Entities.AssessmentEntity;

import com.sanwajas.schoolschedule.R;

import java.util.List;

public class AssessmentAdapter extends RecyclerView.Adapter<AssessmentAdapter.AssessmentHolder> {

    List<AssessmentEntity> mAllAssessments;
    Context context;

    public AssessmentAdapter(Context ct, List<AssessmentEntity> allAssessments){
        context = ct;
        mAllAssessments = allAssessments;
    }

    @NonNull
    @Override
    public AssessmentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.assessment_item, parent, false);
        return new AssessmentHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull AssessmentHolder holder, int position) {

        holder.name.setText(mAllAssessments.get(position).getAssessmentName());
        holder.type.setText(mAllAssessments.get(position).getAssessmentType());
        holder.end.setText(mAllAssessments.get(position).getEndDate());
    }
    public void resetList(List<AssessmentEntity> allAssessments){
        mAllAssessments = allAssessments;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mAllAssessments.size();
    }

    public class AssessmentHolder extends RecyclerView.ViewHolder{
        TextView name;
        TextView type;
        TextView end;

        public AssessmentHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textAssessmentName);
            type = itemView.findViewById(R.id.textAssessmentType);
            end = itemView.findViewById(R.id.textAssessmentEnd);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAbsoluteAdapterPosition();
                    final AssessmentEntity current = mAllAssessments.get(position);
                    Intent intent = new Intent(context, AssessmentDetailActivity.class);
                    intent.putExtra("id" , String.valueOf(current.getAssessmentID()));
                    intent.putExtra("name",String.valueOf(current.getAssessmentName()));
                    intent.putExtra("course",String.valueOf(current.getClassID()));
                    intent.putExtra("type",String.valueOf(current.getAssessmentType()));
                    intent.putExtra("end", String.valueOf(current.getEndDate()));
                    context.startActivity(intent);
                }
            });
        }
    }
}
