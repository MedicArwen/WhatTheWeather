package apps.medicarwen.com.whattheweather.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;

import apps.medicarwen.com.whattheweather.DataAccess.DataAccessCallOpenWeather;
import apps.medicarwen.com.whattheweather.R;
import apps.medicarwen.com.whattheweather.models.City;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {

    private ArrayList<City> mArrayListCities;
    private Context mContext;

    public FavoriteAdapter(Context context, ArrayList<City> cities) {
        mContext = context;
        mArrayListCities = cities;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView mTextViewCity;
        public ImageView mImageViewWeather;
        public TextView mTextViewTemperature;
        public TextView mTextViewDescription;
        public City mCity;

        public int position;

        public ViewHolder(View view) {
            super(view);

            view.setOnLongClickListener(mOnLongClickListener);
            view.setTag(this);

            mTextViewCity = (TextView) view.findViewById(R.id.text_view_item_city);
            mImageViewWeather = (ImageView) view.findViewById(R.id.image_view_item_weather);
            mTextViewTemperature = (TextView) view.findViewById(R.id.text_view_item_temperature);
            mTextViewDescription = (TextView) view.findViewById(R.id.text_view_item_details);
        }

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_city_weather, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        City city = mArrayListCities.get(position);

        holder.mTextViewCity.setText(city.mName);
        //holder.mImageViewWeather.setImageResource(city.mWeatherResIconGrey);
        holder.mTextViewTemperature.setText(city.mInfoAir.getTemperature());
        holder.mTextViewDescription.setText(city.mMeteo.description);
        holder.mCity = city;
        holder.position = position;
    }

    @Override
    public int getItemCount() {
        return mArrayListCities.size();
    }


    private View.OnLongClickListener mOnLongClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {

            ViewHolder holder = (ViewHolder) v.getTag();
            final int position = holder.position;

            final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setMessage("Supprimer " + holder.mTextViewCity.getText().toString() + " ?");
            builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    mArrayListCities.remove(position);
                    notifyDataSetChanged();
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, mArrayListCities.size());
                    DataAccessCallOpenWeather.saveCityList(mContext,mArrayListCities);
                }
            });

            builder.setNegativeButton(android.R.string.cancel, null);

            builder.create().show();
            return true;
        }
    };

}



