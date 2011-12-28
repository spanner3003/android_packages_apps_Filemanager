package com.cyanogenmod.filemanager;

import java.io.File;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;

public class FilemanagerActivity extends Activity {

	File mCurrentFile = Environment.getExternalStorageDirectory();
	
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
