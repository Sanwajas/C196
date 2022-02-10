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
import com.sanwajas.schoolschedule.Entities.ClassEntity;
import com.sanwajas.schoolschedule.R;

import java.util.List;

public class ClassAdapter extends RecyclerView.Adapter<ClassAdapter.ClassHolder> {

    List<ClassEntity> mAllClasses;
    Context context;

    public ClassAdapter(Context ct, List<ClassEntity> allClasses){
        context = ct;
        mAllClasses = allClasses;
    }

    @NonNull
    @Override
    public ClassHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.class_item, parent, false);
        return new ClassHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClassHolder holder, int position) {

        holder.name.setText(mAllClasses.get(position).getClassName());
        holder.start.setText(mAllClasses.get(position).getStartDate());
        holder.end.setText(mAllClasses.get(position).getEndDate());
    }

    public void resetList(List<ClassEntity> allClasses){
        mAllClasses = allClasses;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mAllClasses.size();
    }

    public class ClassHolder extends RecyclerView.ViewHolder{
        TextView name;
        TextView start;
        TextView end;

        public ClassHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textClassName);
            start = itemView.findViewById(R.id.textClassStart);
            end = itemView.findViewById(R.id.textClassEnd);
            itemView.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View v) {
                int position = getAbsoluteAdapterPosition();
                final ClassEntity current = mAllClasses.get(position);
                Intent intent = new Intent(context, ClassDetailActivity.class);
                intent.putExtra("id", String.valueOf(current.getClassID()));
                intent.putExtra("name", String.valueOf(current.getClassName()));
                intent.putExtra("term", String.valueOf(current.getTermID()));
                intent.putExtra("start", String.valueOf(current.getStartDate()));
                intent.putExtra("end", String.valueOf(current.getEndDate()));
                intent.putExtra("status", String.valueOf(current.getClassStatus()));
                intent.putExtra("instructorName",String.valueOf(current.getInstructorName()));
                intent.putExtra("instructorPhone",String.valueOf(current.getInstructorPhone()));
                intent.putExtra("instructorEMail",String.valueOf(current.getInstructorEmail()));
                intent.putExtra("notes",String.valueOf(current.getClassNotes()));
                context.startActivity(intent);
                }
            });
        }
    }
}
