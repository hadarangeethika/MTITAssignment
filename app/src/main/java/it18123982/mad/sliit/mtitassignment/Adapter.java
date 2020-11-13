package it18123982.mad.sliit.mtitassignment;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> implements Filterable{

    LayoutInflater inflater;
    List<Joke> jokes;
    List<Joke> jokesAll;

    public Adapter(Context ctx, List<Joke> jokes){
        this.inflater = LayoutInflater.from(ctx);
        this.jokes = jokes;
        this.jokesAll = new ArrayList<>(jokes);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.custom_list_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //bind data
        holder.jokeType.setText(jokes.get(position).getType());
        holder.jokeSetup.setText(jokes.get(position).getSetup());
        holder.jokePunchline.setText(jokes.get(position).getPunchline());
    }

    @Override
    public int getItemCount() {
        return jokes.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        //run on background thread
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<Joke> filteredList = new ArrayList<>();

            if (charSequence.toString().isEmpty()){
                filteredList.addAll(jokesAll);
            }else {
                String filterPattern = charSequence.toString().toLowerCase().trim();
                for (Joke joke: jokesAll){
                    if (joke.getType().toLowerCase().contains(filterPattern)){
                        filteredList.add(joke);
                    }
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;

            return filterResults;
        }

        //run on an ui thread
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            jokes.clear();
            jokes.addAll((Collection<? extends Joke>) filterResults.values);
            notifyDataSetChanged();
        }
    };

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView jokeType, jokeSetup, jokePunchline;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            jokeType = itemView.findViewById(R.id.joke_type);
            jokeSetup = itemView.findViewById(R.id.joke_setup);
            jokePunchline = itemView.findViewById(R.id.joke_punchline);

            itemView.setBackgroundColor(Color.GRAY);
        }
    }

}
