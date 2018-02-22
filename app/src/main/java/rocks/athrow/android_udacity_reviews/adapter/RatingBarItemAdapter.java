package rocks.athrow.android_udacity_reviews.adapter;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
        String ratingsText;
        float ratings;
        for (int anItemsForSpecificProject : itemsForSpecificProject) {
            itemsForSpecificProjectSum += anItemsForSpecificProject;
        }

        if (position == 5) {
            // show average
            ratings = calculateAverageRatings(itemsForSpecificProject);
            ratingsText = calculateAverageRatings(itemsForSpecificProject) + " (AVERAGE)";
        } else {
            ratings = position + 1;
            if (itemsForSpecificProject[position] == 0) {
                percentage = 0;
            } else {
                percentage = (((double) itemsForSpecificProject[position] / itemsForSpecificProjectSum) * 100);
            }

            // "%.3f" means 3 decimal places
            ratingsText = itemsForSpecificProject[position] + " (" + String.format("%.3f", percentage) + "%)";
        }

        holder.count.setText(ratingsText);
        holder.rating.setRating(ratings);
    }

    private float calculateAverageRatings(int[] ratingsArray) {
        int starSum = ratingsArray[0] + ratingsArray[1] * 2 + ratingsArray[2] * 3 + ratingsArray[3] * 4 + ratingsArray[4] * 5;
        int ratingsSum = ratingsArray[0] + ratingsArray[1] + ratingsArray[2] + ratingsArray[3] + ratingsArray[4];
        if (ratingsSum == 0) {
            return 0;
        }
        Log.i("Adapter", "result:" + starSum / ratingsSum);
        return Float.parseFloat((String.format("%.3f", ((float)starSum/ratingsSum))));
    }

    @Override
    public int getItemCount() {
        return 6;
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
