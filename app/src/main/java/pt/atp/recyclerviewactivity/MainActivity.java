package pt.atp.recyclerviewactivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private List<String> nomes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nomes = new ArrayList<>();
        nomes.add("Bruno");
        nomes.add("Paulo");
        nomes.add("José");
        nomes.add("Maria");
        nomes.add("Manuel");
        nomes.add("Francisca");
        nomes.add("Pedro");
        nomes.add("João");

        RecyclerView recycler = (RecyclerView)findViewById(R.id.my_recycler);

        ContactsAdapter adapter = new ContactsAdapter(nomes);

        recycler.setAdapter(adapter);
        // Set layout manager to position the items
        recycler.setLayoutManager(new LinearLayoutManager(this));

    }

}