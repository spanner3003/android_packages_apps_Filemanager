package com.cyanogenmod.filemanager;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class FileSystemAdapter extends BaseAdapter {
	Context mContext;
	File mFile;
	ArrayList<File> mFilesInFile;

	public FileSystemAdapter(Context c, String path) {
		mContext = c;
		mFile = new File(path);
		File[] directories = mFile.listFiles(new FileFilter() {
			public boolean accept(File pathname) {
				if (pathname.isDirectory())
					return true;
				return false;
			}
		});
		Arrays.sort(directories);

		File[] files = mFile.listFiles(new FileFilter() {
			public boolean accept(File pathname) {
				if (pathname.isFile())
					return true;
				return false;
			}
		});
		Arrays.sort(files);

		mFilesInFile = new ArrayList<File>(directories.length + files.length);
		Collections.addAll(mFilesInFile, directories);
		Collections.addAll(mFilesInFile, files);
	}

	public int getCount() {
		return mFilesInFile.size();
	}

	public Object getItem(int position) {
		return null;
	}

	public long getItemId(int position) {
		return 0;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		TextView view;
		
		if(convertView != null) {
			view = (TextView)convertView;
		} else {
			LayoutInflater li = ((Activity) mContext).getLayoutInflater();
			view = (TextView) li.inflate(R.layout.icon, null);
		}
		
		File file = mFilesInFile.get(position);
		view.setText(file.getName());

		int resId;
		if (file.isDirectory())
			if (file.getName().equals(Environment.DIRECTORY_MUSIC)) {
				resId = R.drawable.folder_music;
			} else if (file.getName()
					.equals(Environment.DIRECTORY_PICTURES)) {
				resId = R.drawable.folder_image;
			} else if (file.getName().equals(Environment.DIRECTORY_MOVIES)) {
				resId = R.drawable.folder_video;
			} else {
				resId = R.drawable.folder;
			}
		else
			resId = R.drawable.file;
		
		Drawable icon = mContext.getResources().getDrawable(resId);
		view.setCompoundDrawablesWithIntrinsicBounds(null, icon, null, null);

		return view;
	}
}
