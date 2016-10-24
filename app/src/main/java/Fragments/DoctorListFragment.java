package Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.test_11.doctorappointmentapp.R;

import web.IResponse;

/**
 * Created by firemoon2 on 10/24/2016.
 */


public class DoctorListFragment extends Fragment implements IResponse{
    private View mView;
    ListView doctorList;
    Button map,filter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(mView == null){
            mView = inflater.inflate(R.layout.doctor_search, container, false);

        }else{
            ((ViewGroup) mView.getParent()).removeView(mView);
        }

    return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        findIdAndListeners();
        doctorList.setAdapter(new dataListAdapter());
    }

    private void findIdAndListeners() {

        doctorList= (ListView) mView.findViewById(R.id.listViewForCard);
        map= (Button) mView.findViewById(R.id.map);
        filter= (Button) mView.findViewById(R.id.filter);

        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }


    @Override
    public void onComplete(String result, int i) {

    }

    class dataListAdapter extends BaseAdapter {
        String[] Title, Detail;
        int[] imge;

        dataListAdapter() {
            Title = null;
            Detail = null;
            imge=null;
        }

        public dataListAdapter(String[] text, String[] text1,int[] text3) {
            Title = text;
            Detail = text1;
            imge = text3;

        }

        public int getCount() {
            // TODO Auto-generated method stub
            return 5;
        }

        public Object getItem(int arg0) {
            // TODO Auto-generated method stub
            return null;
        }

        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = getActivity().getLayoutInflater();
            View row;
            row = inflater.inflate(R.layout.inflatecard, parent, false);
            TextView title, detail;
            ImageView i1;
            title = (TextView) row.findViewById(R.id.description);
            i1=(ImageView)row.findViewById(R.id.imageClicking);
//            title.setText(Title[position]);
//            i1.setImageResource(imge[position]);

            return (row);
        }
    }
}


