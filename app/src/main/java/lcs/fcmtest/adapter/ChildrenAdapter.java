package lcs.fcmtest.adapter;

import android.support.annotation.NonNull;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import lcs.fcmtest.R;

public class ChildrenAdapter extends RecyclerView.Adapter<ChildrenAdapter.ChildrenHolder>
    implements IDataReady{

    private Map<String,String> names = new HashMap<String,String>();
    @NonNull
    @Override
    public ChildrenHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.children_holder,viewGroup, false);
        return new ChildrenHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ChildrenHolder childrenHolder, int i) {
        String[] keys = names.keySet().toArray(new String[names.size()]);
        childrenHolder.textView.setText(names.get(keys[i]));
    }

    @Override
    public int getItemCount() {
        if (names == null)
            return 0;
        return names.size();
    }

    @Override
    public void dataSync(Map<String,String> list) {
        names.clear();
        names.putAll(list);
        notifyDataSetChanged();
    }

    class ChildrenHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView textView;
        public ChildrenHolder(@NonNull View itemView) {
            super(itemView);
            cardView = (CardView)itemView;
            textView = (TextView)cardView.findViewById(R.id.children_name);
        }
    }
}
