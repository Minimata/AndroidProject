package ch.hearc.gcapp.garbages.categories;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ch.hearc.gcapp.R;
import ch.hearc.gcapp.garbages.GarbageStore;

/**
 * Our garbage category adapter for list views containing garbage categories.
 *
 * We want to display the following text per row:
 *
 *   - GARBAGE_CATEGORY_NAME (GARBAGE_CAN_NAME)
 */
public class GarbageCategoryAdapter extends BaseAdapter implements Filterable {

    /**
     * The context (activity) in which this adapter is used.
     */
    private Context context;

    /**
     * The filtered garbage categories; the garbage categories currently shown on the list.
     */
    private List<GarbageCategory> filteredGarbageCategories;

    /**
     * The filter object, handling the filtering of our garbage categories.
     */
    private Filter garbageCategoryFilter;

    public GarbageCategoryAdapter(Context context) {
        super();

        this.context = context;

        construct();
    }

    private void construct() {
        // Display all garbage categories by default
        filteredGarbageCategories = GarbageStore.GARBAGE_CATEGORIES;

        // Create our garbage categories' filter
        garbageCategoryFilter = new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                if (constraint == null) {
                    // No constraint -> show all

                    filteredGarbageCategories = GarbageStore.GARBAGE_CATEGORIES;
                } else {
                    filteredGarbageCategories = new ArrayList<>();

                    for (GarbageCategory garbageCategory : GarbageStore.GARBAGE_CATEGORIES) {
                        // Filter by garbage category name

                        if (garbageCategory.getName().toLowerCase().contains(constraint.toString().toLowerCase())) {
                            filteredGarbageCategories.add(garbageCategory);
                        }
                    }
                }

                FilterResults filterResults = new FilterResults();

                filterResults.values = filteredGarbageCategories;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                // New filtering -> notify the list adapter (BaseAdapter) that its content changed
                notifyDataSetChanged();
            }
        };
    }

    @Override
    public int getCount() {
        return filteredGarbageCategories.size();
    }

    @Override
    public Object getItem(int position) {
        return filteredGarbageCategories.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        GarbageCategoryHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.garbage_category_list_row, parent, false);

            holder = new GarbageCategoryHolder();

            holder.nameTextView = (TextView) convertView.findViewById(R.id.nameTextView);
            holder.garbageCanTextView = (TextView) convertView.findViewById(R.id.garbageCanTextView);

            /*
             * We have 2 views, but we only can set one object (tag) into our convertView object.
             *
             * Now we see the whole purpose of our "XyzHolder" wrapper classes.
             */
            convertView.setTag(holder);
        } else {
            holder = (GarbageCategoryHolder) convertView.getTag();
        }

        GarbageCategory garbageCategory = filteredGarbageCategories.get(position);

        holder.nameTextView.setText(garbageCategory.getName());
        holder.garbageCanTextView.setText(
                String.format(
                        context.getResources().getString(R.string.garbage_can_list_row),
                        garbageCategory.getGarbageCanName()
                )
        );

        return convertView;
    }

    @Override
    public Filter getFilter() {
        return garbageCategoryFilter;
    }

    /**
     * Wrapper class for our garbage category views.
     */
    private static class GarbageCategoryHolder {
        TextView nameTextView;
        TextView garbageCanTextView;
    }
}
