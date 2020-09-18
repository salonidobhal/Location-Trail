package com.example.locationtrail;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.List;

//This class extends the ArrayAdaptor class which converts an ArrayList
// of objects into View items to be loaded into the ListView. It is used if any time we want to show a vertical
// list of scrollable items we will use a ListView which has data populated using an Adapter .

public class LocationList extends ArrayAdapter<Loc> {
    private Activity context;
    private List<Loc> locationList;

    public LocationList(Activity context, List<Loc> locationList){
        super(context, R.layout.location, locationList);
        this.context=context;
        this.locationList=locationList;
    }
    //Here the layoutInflator is used to map the textViews of a layout resource into a list
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater= context.getLayoutInflater();
        View listViewItem=inflater.inflate(R.layout.location, null, true);
        TextView textViewLat= (TextView) listViewItem.findViewById(R.id.latitude);
        TextView textViewLong= (TextView) listViewItem.findViewById(R.id.longitude);
        TextView textViewTime= (TextView) listViewItem.findViewById(R.id.time);
        Loc location= locationList.get(position);
        textViewLat.setText(location.getLatitude());
        textViewLong.setText(location.getLongitude());
        textViewTime.setText(location.getTime());
        return listViewItem;
    }
}
