package com.example.tutionfee.adapter;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tutionfee.DisplayContacts;
import com.example.tutionfee.R;
import com.example.tutionfee.data.model.Contact;

import java.util.List;
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
     private Context context;
     private List<Contact> contactList;
    public RecyclerViewAdapter(Context context, List<Contact> contactList) {
        this.context = context;
        this.contactList = contactList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row,parent,false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Contact contact=contactList.get(position);
//        holder.id.setText(contact.getId());
        holder.id.setText(String.valueOf(position+1));
        holder.contactName.setText(contact.getName());
        holder.phoneNumber.setText(contact.getPhoneNumber());
    }
    @Override
    public int getItemCount() {
        return contactList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView phoneNumber,id;
        TextView  contactName;
        ImageView icon_buttom;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            id=itemView.findViewById(R.id.id);
            contactName=itemView.findViewById(R.id.name);
            phoneNumber=itemView.findViewById(R.id.phone_number);
//            icon_buttom=itemView.findViewById(R.id.icon_button);
        }
        @Override
        public void onClick(View view) {
            int pos =this.getAdapterPosition();
            Contact contact=contactList.get(pos);
//            Toast.makeText(view.getContext(),"Clicked "+String.valueOf(contact.getName()),Toast.LENGTH_LONG).show();
            Intent intent=new Intent(context, DisplayContacts.class);
            intent.putExtra("name",contact.getName());
            intent.putExtra("phone",contact.getPhoneNumber());
//            intent.putExtra("contact",contact);
            context.startActivity(intent);
        }
    }
}
