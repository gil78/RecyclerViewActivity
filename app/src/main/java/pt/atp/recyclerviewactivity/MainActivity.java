package pt.atp.recyclerviewactivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;
import com.google.android.material.floatingactionbutton.*;

public class MainActivity extends AppCompatActivity {



    private ArrayList<Contact> contacts;
    private RecyclerView recycler;
    private MyDBAdapter dbAdapter;

    @Override
    protected void onResume() {
        super.onResume();
        contacts.clear();
        populateList(contacts);
        recycler.getAdapter().notifyDataSetChanged();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       /* contacts = new ArrayList<>();
        contacts.add(new Contact("Bruno","3434344123"));
        contacts.add(new Contact("Paulo","3233234434"));
        contacts.add(new Contact("Maria","8768756565"));*/

        recycler = (RecyclerView)findViewById(R.id.my_recycler);

        dbAdapter = new MyDBAdapter(this);
        dbAdapter.open();


        /*dbAdapter.insertContact(new Contact("Bruno","3434344123"));
        dbAdapter.insertContact(new Contact("Paulo","3233234434"));
        dbAdapter.insertContact(new Contact("Maria","8768756565"));*/

        contacts = new ArrayList<>();

        populateList(contacts);

        ContactsAdapter adapter = new ContactsAdapter(contacts,this,dbAdapter);

        recycler.setAdapter(adapter);
        // Set layout manager to position the items
        recycler.setLayoutManager(new LinearLayoutManager(this));

        ((FloatingActionButton)findViewById(R.id.floatingActionButton)).setOnClickListener((view)->{
            Intent intent = new Intent(this,ContactView.class);

            intent.putExtra("TYPE","insert");

           startActivity(intent);
        });

    }

    private void populateList(ArrayList<Contact> contacts){
        Cursor cursor = dbAdapter.getAllContacts();

        while(cursor.moveToNext()){
            contacts.add(new Contact(cursor.getLong(0),cursor.getString(1),cursor.getString(2)));

        }
    }


}