package pt.atp.recyclerviewactivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.util.ArrayList;

public class ContactView extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_view);

        MyDBAdapter dbAdapter = new MyDBAdapter(this);
        dbAdapter.open();

        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "myContactsRoom.db").allowMainThreadQueries().build();

        EditText name=((EditText)findViewById(R.id.txtName));
        EditText phone = ((EditText)findViewById(R.id.txtPhone));
        Button btnSave = ((Button)findViewById(R.id.btnSave));

        Bundle extras = getIntent().getExtras();

        if(extras.getString("TYPE").equals("update")){
            Contact c = ((Contact)extras.get("CONTACT_OBJECT"));

            name.setText(c.getName());
            phone.setText(c.getPhoneNumber());
        }

        ContactDao contactDao = db.contactDao();

        btnSave.setOnClickListener((view)->{
            if(extras.getString("TYPE").equals("update")) {
                Contact c = ((Contact) extras.get("CONTACT_OBJECT"));
                c.setName(name.getText().toString());
                c.setPhoneNumber(phone.getText().toString());
                //dbAdapter.updateContact(c.getId(),c);
                contactDao.update(c);

                finish();
            }
            if(extras.getString("TYPE").equals("insert")) {
                Contact c = new Contact(name.getText().toString(),phone.getText().toString());
               //dbAdapter.insertContact(c);
                contactDao.insert(c);
                finish();
            }
        });
    }
}
