package com.example.tutionfee.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tutionfee.R;
import com.example.tutionfee.data.model.Contact;

import java.util.List;

public class FeesListAdapter extends RecyclerView.Adapter<FeesListAdapter.ViewHolder> {
    private Context context;
    private List<Contact> contactList;


    public FeesListAdapter(Context context, List<Contact> contactList) {
        this.context = context;
        this.contactList = contactList;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_dates,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Contact contact=contactList.get(position);
        holder.contactName.setText(contact.getPhoneNumber());

    }


    @Override
    public int getItemCount() {
        return contactList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView phoneNumber;
        TextView contactName;
        ImageView icon_buttom;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            contactName = itemView.findViewById(R.id.name);
//            phoneNumber = itemView.findViewById(R.id.phone_number);

        }

        @Override
        public void onClick(View view) {

        }
    }
}
