package Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.fareye.divyanshu.dynamicdatabase.FormMaster;
import com.fareye.divyanshu.dynamicdatabase.R;
import com.fareye.divyanshu.dynamicdatabase.ViewForms.ViewVariousForms;

import java.util.ArrayList;

/**
 * Created by abhishek on 14/7/17.
 */

public class FormViewAdapter extends RecyclerView.Adapter<FormViewAdapter.FormViewHolder> {

    ArrayList<FormMaster> formMastersArrayList;
    Context context;
    FormAdapterClickListener clickListener;


    public FormViewAdapter(Context context, ArrayList<FormMaster> formMastersArrayList) {
        Log.d("FormViewAdapter", "FormViewAdapter()");
        this.formMastersArrayList = formMastersArrayList;
        this.context = context;
        this.clickListener = (ViewVariousForms) context;
    }

    @Override
    public FormViewAdapter.FormViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d("FormViewAdapter", "onCreateViewHolder()");
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_element_form, parent, false);
        return new FormViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(FormViewAdapter.FormViewHolder holder, int position) {
        Log.d("FormViewAdapter", "onBindViewHolder()");
        holder.setIsRecyclable(false);
        FormMaster form = formMastersArrayList.get(position);
        holder.setUser(form);
        holder.formNameTv.setText(form.getName());
    }

    @Override
    public int getItemCount() {
        return formMastersArrayList.size();
    }

    public class FormViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView formNameTv;
        public FormMaster formMaster;

        public FormViewHolder(View itemView) {
            super(itemView);
            Log.d("FormViewHolder", "FormViewHolder()");
            formNameTv = itemView.findViewById(R.id.form_name);
            itemView.setClickable(true);
            itemView.setOnClickListener(this);
        }

        public void setUser(FormMaster formMaster) {
            this.formMaster = formMaster;
        }

        @Override
        public void onClick(View view) {
            if (clickListener != null) {
                clickListener.onClick(view, formMaster);
            }
        }
    }
}
