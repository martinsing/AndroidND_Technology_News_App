package com.vegetablecorp.technews;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Megatron on 2017-07-19.
 */

public class ArticleAdapter extends ArrayAdapter<Article> {

    // Create a constant tag for so we know log messages came from this class
    private static final String LOG_TAG = "ArticleAdapter";
    /**
     * Constructs a new {@link ArticleAdapter}.
     *
     * @param context of the app
     * @param articles   is the list of articles, which is the data source of the adapter
     */
    public ArticleAdapter(Context context, ArrayList<Article> articles) {
        super(context, 0, articles);
    }

    /**
     * Returns a list item view that displays information about the article at the given position
     * in the list.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if there is an existing list item view (called convertView) that we can reuse,
        // otherwise, if convertView is null, then inflate a new list item layout.
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        // Find the item at the given position in the list
        Article currentArticle = getItem(position);

        // Section
        TextView section = (TextView) listItemView.findViewById(R.id.lbl_txt_section);
        String s = currentArticle.getmSectionName();
        section.setText(s);
        Log.i(LOG_TAG, s);

        // WebTitle
        TextView title = (TextView) listItemView.findViewById(R.id.lbl_txt_title);
        title.setText(currentArticle.getmWebTitle());

        // WebPublicationDate
        // Split the Web Publication Date into the Date and the Time of publication
        String date;
        String time;
        String publicationDate = currentArticle.getmWebPublicationDate();
        String[] parts = publicationDate.split("T");
        date = parts[0];
        time = parts[1];
        // Remove formatting
        time = time.replace("Z", "");
        TextView webPublicationDate = (TextView) listItemView.findViewById(R.id.webPublicationDate);
        webPublicationDate.setText(date);
        TextView webPublicationTime = (TextView) listItemView.findViewById(R.id.webPublicationTime);
        webPublicationTime.setText(time);

        // Return the list item view that is now showing the appropriate data
        return listItemView;
    }
}


