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
import com.sanwajas.schoolschedule.Entities.TermEntity;
import com.sanwajas.schoolschedule.R;

import java.util.List;

public class TermAdapter extends RecyclerView.Adapter<TermAdapter.TermHolder> {

    List<TermEntity> mAllTerms;
    Context context;

    public TermAdapter(Context ct, List<TermEntity> allTerms){
        context = ct;
        mAllTerms = allTerms;
    }

    @NonNull
    @Override
    public TermHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.term_item, parent, false);
        return new TermHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TermHolder holder, int position) {
        //holder.name.setText("text");
        //holder.start.setText("Start");
        //holder.end.setText("End");
        holder.name.setText(mAllTerms.get(position).getTermName());
        holder.start.setText(mAllTerms.get(position).getStartDate());
        holder.end.setText(mAllTerms.get(position).getEndDate());
    }

    @Override
    public int getItemCount() {
        return mAllTerms.size();
    }

    public class TermHolder extends RecyclerView.ViewHolder{
        TextView name;
        TextView start;
        TextView end;

        public TermHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textTermName);
            start = itemView.findViewById(R.id.textTermStart);
            end = itemView.findViewById(R.id.textTermEnd);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAbsoluteAdapterPosition();
                    final TermEntity current = mAllTerms.get(position);
                    Intent intent = new Intent(context, TermDetailActivity.class);
                    intent.putExtra("id", String.valueOf(current.getTermID()));
                    intent.putExtra("termName", String.valueOf(current.getTermName()));
                    intent.putExtra("startTermDate", String.valueOf(current.getStartDate()));
                    intent.putExtra("endTermDate", String.valueOf(current.getEndDate()));
                    context.startActivity(intent);
                }
            });
        }
    }
}
