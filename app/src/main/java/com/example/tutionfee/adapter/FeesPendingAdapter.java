package com.example.tutionfee.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.tutionfee.FeesPendingActivity;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tutionfee.DisplayContacts;
import com.example.tutionfee.R;
import com.example.tutionfee.data.MyDbHandler;
import com.example.tutionfee.data.model.Contact;

import java.util.ArrayList;
import java.util.List;

public class FeesPendingAdapter extends RecyclerView.Adapter<FeesPendingAdapter.ViewHolder> {
    private Context context;
    private List<Contact> contactList;
    int id=1;
    FeesPendingActivity fees;
    public FeesPendingAdapter() {
    }
    public FeesPendingAdapter(Context context, List<Contact> contactList) {
        this.context = context;
        this.contactList = contactList;
    }
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_pending,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Contact contact=contactList.get(position);
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
        TextView contactName;
        ImageView icon_buttom;
        Button paid;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            paid=itemView.findViewById(R.id.paid);
            contactName = itemView.findViewById(R.id.name);
            phoneNumber = itemView.findViewById(R.id.date);
            id=itemView.findViewById(R.id.idPending);

//            icon_buttom = itemView.findViewById(R.id.icon_button);
            paid.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    Contact contact = contactList.get(pos);
                    MyDbHandler db=new MyDbHandler(context);
                    db.deletePending(contact);
                    db.addFeesDetails(contact);
                    Toast.makeText(context.getApplicationContext(), contact.getName()+" Paid",Toast.LENGTH_LONG).show();
//                    db.close();
                    List<Contact> contactList1=new ArrayList<>();
                    contactList1=db.getAllStudentsPending();
                    contactList.clear();
                    contactList.addAll(contactList1);
                    notifyDataSetChanged();
//                    fees.refresh();
//                    context.startActivity(new Intent(context,DisplayContacts.class));
                }
            });
        }
        @Override
        public void onClick(View view) {
            int pos = this.getAdapterPosition();
            Contact contact = contactList.get(pos);
            Toast.makeText(view.getContext(), "Clicked " + String.valueOf(contact.getName()), Toast.LENGTH_LONG).show();

            Intent intent = new Intent(context, DisplayContacts.class);
            intent.putExtra("name", contact.getName());
//            intent.putExtra("phone", contact.getPhoneNumber());
            context.startActivity(intent);
        }
    }
    public void updateData(List<Contact> newDataList) {
        contactList.clear(); // Clear the existing data
        contactList.addAll(newDataList); // Add the new data
        notifyDataSetChanged(); // Notify the adapter about the data change
    }

}
