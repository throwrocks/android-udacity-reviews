package rocks.athrow.android_udacity_reviews.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import rocks.athrow.android_udacity_reviews.R;
import rocks.athrow.android_udacity_reviews.data.SummaryObject;

/**
 * ProjectSummaryAdapter
 * Created by josel on 8/20/2016.
 */
public class ProjectSummaryAdapter extends RecyclerView.Adapter<ProjectSummaryAdapter.ViewHolder> {
    final ArrayList<SummaryObject> summaryObjects;

    public ProjectSummaryAdapter(ArrayList<SummaryObject> summaryObjects) {
        this.summaryObjects = summaryObjects;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.project_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mItem = summaryObjects.get(position);
        String projectName = holder.mItem.getProjectName();
        String projectCount = holder.mItem.getProjectCount();
        String projectRevenue = holder.mItem.getProjectRevenue();
        holder.projectNameView.setText(projectName);
        holder.projectCountView.setText(projectCount);
        holder.projectRevenueView.setText(projectRevenue);

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public SummaryObject mItem;
        public final TextView projectNameView;
        public final TextView projectCountView;
        public final TextView projectRevenueView;

        public ViewHolder(View view) {
            super(view);
            projectNameView = (TextView) view.findViewById(R.id.project_name);
            projectCountView = (TextView) view.findViewById(R.id.project_count);
            projectRevenueView = (TextView) view.findViewById(R.id.project_revenue);
        }
    }

    @Override
    public int getItemCount() {
        return summaryObjects.size();
    }

}
