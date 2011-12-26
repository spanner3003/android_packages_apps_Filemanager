package com.cyanogenmod.filemanager;

import java.io.File;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;

public class FilemanagerActivity extends Activity {

	File mCurrentFile = Environment.getExternalStorageDirectory();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		EditText pathField = (EditText) findViewById(R.id.pathEdit);
		pathField.setText(mCurrentFile.getAbsolutePath());
		pathField.addTextChangedListener(new TextWatcher() {
			String oldText = new String();

			public void afterTextChanged(Editable s) {
				if (isPathValid(s.toString()))
					setPath(s.toString());
				else {
					EditText pathField = (EditText) findViewById(R.id.pathEdit);
					pathField.setText(oldText);
				}
			}

			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				oldText = s.toString();
			}

			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}
		});

		Button button = (Button) findViewById(R.id.sdcardButton);
		button.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				setPath(Environment.getExternalStorageDirectory().getAbsolutePath());
			}
		});

		button = (Button) findViewById(R.id.rootButton);
		button.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				setPath("/");
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
		FileSystemAdapter fsa = new FileSystemAdapter(this, mCurrentFile.getAbsolutePath());
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
