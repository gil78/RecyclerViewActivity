package pt.atp.recyclerviewactivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ContactsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private ArrayList<Contact> contacts;

    //BD
    private MyDBAdapter dbAdapter;
    private Context context;

    //ROOM
    private AppDatabase db;

    /*public ContactsAdapter(ArrayList<Contact> lista){
        this.lista =lista;
    }*/

    public ContactsAdapter(ArrayList<Contact> lista, Context context, MyDBAdapter dbAdapter,AppDatabase db){
        this.contacts =lista;
        this.context = context;
        this.dbAdapter = dbAdapter;
        this.db = db;
    }

    @NonNull
    @Override
    // Usually involves inflating a layout from XML and returning the holder
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LineHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.line_view, parent, false));
    }

    @Override
    // Involves populating data into the item through holder
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) { //Coloquei como parâmetro o LineHolder que extende de View Holder

        Contact contact = contacts.get(position);

        Log.w("DATA",contact.getName() + "; "+ contact.getPhoneNumber());

        ((LineHolder)holder).title.setText(contact.getName() + "; "+ contact.getPhoneNumber());

        //BD
        ((LineHolder)holder).deleteButton.setOnClickListener((view)->{
            this.notifyItemRemoved(position);
            //dbAdapter.removeContact(contact.getId());
            db.contactDao().delete(contact);
            contacts.remove(contact);

        });
        ((LineHolder)holder).moreButton.setOnClickListener((view)->{
            Intent intent = new Intent(context,ContactView.class);
            intent.putExtra("CONTACT_OBJECT",contact);
            intent.putExtra("TYPE","update");

            context.startActivity(intent);
        });

        ((LineHolder)holder).title.setOnClickListener((view)->{
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            intent.setData(Uri.parse("tel:"+contact.getPhoneNumber()));
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {

        //return lista.length;
        return contacts.size();
    }
}
