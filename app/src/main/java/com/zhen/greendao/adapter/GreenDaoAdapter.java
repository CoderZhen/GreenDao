package com.zhen.greendao.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhen.greendao.R;
import com.zhen.greendao.entity.Student;

import java.util.List;

public class GreenDaoAdapter extends RecyclerView.Adapter<GreenDaoAdapter.ViewHolder> {

    List<Student> list;

    public GreenDaoAdapter(List<Student> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Student student = list.get(position);
        holder.item_id.setText(String.valueOf(student.getId()));
        holder.item_name.setText(student.getName());
        holder.item_age.setText(String.valueOf(student.getAge()));
        holder.item_gender.setText(student.getGender());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private AppCompatTextView item_id, item_name, item_age, item_gender;

        ViewHolder(View itemView) {
            super(itemView);
            item_id = itemView.findViewById(R.id.item_id);
            item_name = itemView.findViewById(R.id.item_name);
            item_age = itemView.findViewById(R.id.item_age);
            item_gender = itemView.findViewById(R.id.item_gender);
        }
    }
}
