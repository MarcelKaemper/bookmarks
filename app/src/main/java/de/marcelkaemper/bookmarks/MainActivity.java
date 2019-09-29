package de.marcelkaemper.bookmarks;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<String> items = new ArrayList<String>();
        final ArrayAdapter<String> adapter;

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        ListView lv = findViewById(R.id.listView);
        lv.setAdapter(adapter);

        final File path = getApplicationContext().getFilesDir();
        final File file = new File(path, "my-file-name.txt");

        //deleteFile(file.getName());

        loadList(adapter, file);


        Button b = (Button) findViewById(R.id.button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                RadioGroup rg = findViewById(R.id.radiogroup);
                EditText et = findViewById(R.id.editText2);
                RadioButton rb = findViewById(rg.getCheckedRadioButtonId());

                try {
                    FileOutputStream stream = new FileOutputStream(file, true);
                    stream.write((et.getText()+": "+rb.getText()).getBytes());
                    stream.write("\n".getBytes());
                    stream.close();
                } catch (Exception e) {
                    Log.d("OnClick", e.getMessage());
                }

                loadList(adapter, file);
            }
        });
    }
    void loadList(ArrayAdapter<String> adapter, File file) {
        adapter.clear();
        int length = (int) file.length();

        byte[] bytes = new byte[length];
        try {
            FileInputStream in = openFileInput(file.getName());
            InputStreamReader inputStreamReader = new InputStreamReader(in);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String line;

            StringBuilder sb = new StringBuilder();

            while ((line = bufferedReader.readLine()) != null) {
                adapter.add(line);
                Log.d("Line: ", line);
            }

            adapter.notifyDataSetChanged();

            inputStreamReader.close();
        } catch (Exception e) {

        }
    }
}

