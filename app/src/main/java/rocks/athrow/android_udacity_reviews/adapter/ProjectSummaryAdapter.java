package rocks.athrow.android_udacity_reviews.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;

import java.util.ArrayList;

import rocks.athrow.android_udacity_reviews.R;
import rocks.athrow.android_udacity_reviews.data.SummaryObject;

/**
 * ProjectSummaryAdapter
 * Created by josel on 8/20/2016.
 */
public class ProjectSummaryAdapter extends RecyclerView.Adapter<ProjectSummaryAdapter.ViewHolder> {
    private final ArrayList<SummaryObject> summaryObjects;

    private Context mContext;

    public ProjectSummaryAdapter(ArrayList<SummaryObject> summaryObjects, Context context) {
        this.summaryObjects = summaryObjects;
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.project_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = summaryObjects.get(position);
        String projectName = holder.mItem.getProjectName();
        String projectCount = holder.mItem.getProjectCount();
        String projectRevenue = holder.mItem.getProjectRevenue();
        String projecHours = holder.mItem.getProjectHours();
        holder.projectNameView.setText(projectName);
        holder.projectCountView.setText(projectCount);
        holder.projectRevenueView.setText(projectRevenue);
        holder.projectHoursView.setText(projecHours);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int[] ratingsArrayForSpecificProject = holder.mItem.getRatingsForSpecificProject();
                int[] ratingsArrayForProjects = holder.mItem.getRatingsForProjects();

                final RatingBarItemAdapter adapter = new RatingBarItemAdapter(ratingsArrayForSpecificProject, ratingsArrayForProjects);
                new MaterialDialog.Builder(mContext).title(holder.mItem.getProjectName()).adapter(adapter, null).show();
            }
        });
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        SummaryObject mItem;
        final TextView projectNameView;
        final TextView projectCountView;
        final TextView projectRevenueView;
        final TextView projectHoursView;

        ViewHolder(View view) {
            super(view);
            projectNameView = view.findViewById(R.id.project_name);
            projectCountView = view.findViewById(R.id.project_count);
            projectHoursView = view.findViewById(R.id.project_hours);
            projectRevenueView = view.findViewById(R.id.project_revenue);
        }
    }

    @Override
    public int getItemCount() {
        return summaryObjects.size();
    }

}
