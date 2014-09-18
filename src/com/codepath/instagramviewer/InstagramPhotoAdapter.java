package com.codepath.instagramviewer;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pkmmte.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

public class InstagramPhotoAdapter extends ArrayAdapter<InstagramPhoto> {
	public InstagramPhotoAdapter(Context context, List<InstagramPhoto> photos){
		super(context, android.R.layout.simple_list_item_1, photos);
	}
	//getview method(int position)
	//Default, takes the model(InstagramPhoto) and calls toString of the override model
	
	//takes a data item at the a position and converts it to a row in the listView 
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		InstagramPhoto photo = getItem(position);
		//take the data source at position(i.e 0)
		//get the data item
		
		//check if we are using a recycled view
		if(convertView == null) {
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_photo, parent, false);
		}
		
		//lookup a subview with the template
		TextView tvCaption = (TextView) convertView.findViewById(R.id.tvCaption);
		ImageView imgPhoto = (ImageView) convertView.findViewById(R.id.imgPhoto);
		TextView tvUsername = (TextView) convertView.findViewById(R.id.tvUserName);
		TextView tvLikes = (TextView) convertView.findViewById(R.id.tvLikes);
		
		ImageView imgProfilePic = (ImageView) convertView.findViewById(R.id.profile_pic);
		
		//populate the subview (TextFeild, ImageView) with correct data
		tvCaption.setText(photo.caption);
		tvUsername.setText(photo.username);
		tvLikes.setText(photo.likesCount + " Likes");
		
		//Set the image height before loading
		imgPhoto.getLayoutParams().height = photo.imageHeight;
		//Reset the image from the recycled view
		imgPhoto.setImageResource(0);
		
		//Ask for the photo to be added to the image View bases on photo URL
		//Background: Send a network request to the url, download the image bytes, convert the bitmap, resizing the image, insert the bitmap into the image view 
		Picasso.with(getContext()).load(photo.imageUrl).into(imgPhoto);
		
		imgProfilePic.setImageResource(0);
		Picasso.with(getContext()).load(photo.profile_pic_url).into(imgProfilePic);
		
		return convertView;
		//return the view for the data item
	}
}
