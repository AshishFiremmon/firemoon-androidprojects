package Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.test_11.doctorappointmentapp.R;

import java.util.ArrayList;

/**
 * Created by firemoonpc_11 on 03-06-2016.
 */
public class DiseaseAdapter extends BaseAdapter {
    Context con;
ArrayList<String> disease_list=new ArrayList<>();
    public DiseaseAdapter(Context context,
                          ArrayList<String> save)

    {
        this.con = context;
        this.disease_list=save;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return disease_list.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        TextView tv1, tv2;
        Button btn1;
        LayoutInflater inflater;
        inflater = LayoutInflater.from(con);
        View row1 = inflater.inflate(R.layout.list_item, parent, false);
        tv1 = (TextView) row1.findViewById(R.id.product_name);
          tv1.setText(disease_list.get(position));
        return row1;
    }

}
