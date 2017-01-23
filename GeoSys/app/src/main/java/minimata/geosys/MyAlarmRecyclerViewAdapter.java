package minimata.geosys;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import minimata.geosys.AlarmFragment.OnListFragmentInteractionListener;
import minimata.geosys.dummy.DummyContent.DummyItem;
import minimata.geosys.models.Area;

import java.util.ArrayList;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyAlarmRecyclerViewAdapter extends RecyclerView.Adapter<MyAlarmRecyclerViewAdapter.ViewHolder> {

    private final ArrayList<Area> areas;
    private final OnListFragmentInteractionListener mListener;
    private MainActivity parent;

    public MyAlarmRecyclerViewAdapter(ArrayList<Area> areas, OnListFragmentInteractionListener listener, Activity parent) {
        this.areas = areas;
        mListener = listener;
        this.parent = (MainActivity) parent;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_alarm, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.area = areas.get(position);

//        holder.mIdView.setText(mValues.get(position).id);
        holder.mContentView.setText("Alarm " + areas.get(position).getId());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    parent.editArea(holder.getAdapterPosition());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return areas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final View mView;
        final TextView mIdView;
        final TextView mContentView;

        Area area;

        ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.id);
            mContentView = (TextView) view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
