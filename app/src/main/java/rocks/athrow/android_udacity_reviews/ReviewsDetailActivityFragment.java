package rocks.athrow.android_udacity_reviews;

import android.app.Activity;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A placeholder fragment containing a simple view.
 */
public class ReviewsDetailActivityFragment extends Fragment {

    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_REVIEW_ID = "review_id";
    public static final String ARG_PROJECT_NAME = "project_name";
    public static final String ARG_USER_NAME = "user_name";
    public static final String ARG_RESULT = "result";
    public static final String ARG_COMPLETED_AT= "completed_at";
    private String projectName;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ReviewsDetailActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get the review data from the intent extras
        projectName = getArguments().getString(ARG_PROJECT_NAME);
        Log.e("bundle ", "" + getArguments());
        Log.e("project name: ", "" + projectName);

        if (getArguments().containsKey(ARG_PROJECT_NAME)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.


            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar);
            if (appBarLayout != null) {
                appBarLayout.setTitle("title");
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_reviews_detail, container, false);


        // Get the views
        TextView projectNameView = (TextView) rootView.findViewById(R.id.review_detail_project_name);
        // Set the views
        projectNameView.setText(projectName);

        return rootView;
    }
}
