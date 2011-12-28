package com.cyanogenmod.filemanager;

import java.io.File;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;

public class FilemanagerActivity extends Activity {

	File mCurrentFile = Environment.getExternalStorageDirectory();

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		final EditText pathField = (EditText) findViewById(R.id.pathEdit);
		pathField.setText(mCurrentFile.getAbsolutePath());
		pathField.setOnKeyListener(new OnKeyListener() {
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if ((event.getAction() == KeyEvent.ACTION_DOWN)
						&& (keyCode == KeyEvent.KEYCODE_ENTER)) {
					String text = pathField.getText().toString();
					if (isPathValid(text))
						setPath(text);
					return true;
				}
				return false;
			}
		});

		final GridView gridView = (GridView) findViewById(R.id.fileGrid);
		gridView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				File file = new File((String) arg1.getTag());
				if(file.isDirectory())
					setPath(file.getAbsolutePath());
			}
		});

	}

	@Override
	protected void onStart() {
		super.onStart();
		updateGrid();
	}

	public void setPath(String path) {
		if (mCurrentFile.getAbsolutePath().equals(path))
			return;

		mCurrentFile = new File(path);
		EditText pathField = (EditText) findViewById(R.id.pathEdit);
		pathField.setText(mCurrentFile.getAbsolutePath());

		updateGrid();
	}

	private void updateGrid() {
		FileSystemAdapter fsa = new FileSystemAdapter(this,
				mCurrentFile.getAbsolutePath());
		GridView gridView = (GridView) findViewById(R.id.fileGrid);
		gridView.setAdapter(fsa);
	}

	private boolean isPathValid(String path) {
		File file = new File(path);
		if (file.isDirectory())
			return true;
		else
			return false;
	}
}
