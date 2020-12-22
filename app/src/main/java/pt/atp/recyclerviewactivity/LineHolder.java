package pt.atp.recyclerviewactivity;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

//Class que é responsável por relacionar os campos presentes em cada linha
public class LineHolder extends RecyclerView.ViewHolder {

    public TextView title;
    public ImageButton moreButton;
    public ImageButton deleteButton;

    public LineHolder(View itemView){
        super(itemView);
        title = (TextView) itemView.findViewById(R.id.line_view_title);
        moreButton = (ImageButton) itemView.findViewById(R.id.line_view_update);
        deleteButton = (ImageButton) itemView.findViewById(R.id.line_view_delete);
    }
}
