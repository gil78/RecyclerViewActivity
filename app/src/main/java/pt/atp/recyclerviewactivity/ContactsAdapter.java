package pt.atp.recyclerviewactivity;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ContactsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<String> lista;

    public ContactsAdapter(List<String> lista){
        this.lista =lista;
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
        String nome = lista.get(position);
        ((LineHolder)holder).title.setText(nome);

    }

    @Override
    public int getItemCount() {
        return lista.size();
    }
}
