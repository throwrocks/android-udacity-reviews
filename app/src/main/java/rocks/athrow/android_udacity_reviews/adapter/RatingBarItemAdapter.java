package rocks.athrow.android_udacity_reviews.adapter;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import rocks.athrow.android_udacity_reviews.R;

/**
 * Simple adapter example for custom items in the dialog
 */
class RatingBarItemAdapter extends RecyclerView.Adapter<RatingBarItemAdapter.ButtonVH> {

    private final int[] itemsForSpecificProject;

    RatingBarItemAdapter(int[] itemsForSpecificProject) {
        this.itemsForSpecificProject = itemsForSpecificProject;
    }

    @Override
    public ButtonVH onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view =
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.material_dialog_custom_layout, parent, false);
        return new ButtonVH(view, this);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ButtonVH holder, int position) {
        // format string to have 3 decimal places
        double percentage;
        float itemsForSpecificProjectSum = 0;
        for (int i=0;i<itemsForSpecificProject.length;i++) {
            itemsForSpecificProjectSum += itemsForSpecificProject[i];
        }
        if (itemsForSpecificProject[position] == 0) {
            percentage = 0;
        } else {
            percentage = (((double) itemsForSpecificProject[position]/itemsForSpecificProjectSum)*100);
        }
        // "%.3f" means 3 decimal places
        holder.count.setText(itemsForSpecificProject[position] + " (" + String.format("%.3f", percentage) + "%)");
        holder.rating.setRating(position+1);
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    static class ButtonVH extends RecyclerView.ViewHolder {

        final TextView count;
        final RatingBar rating;
        final RatingBarItemAdapter adapter;

        ButtonVH(View itemView, RatingBarItemAdapter adapter) {
            super(itemView);
            count = itemView.findViewById(R.id.tv_count);
            rating = itemView.findViewById(R.id.rating_bar);

            this.adapter = adapter;
        }
    }
}
