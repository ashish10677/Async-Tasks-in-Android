package com.example.ashish.asynctasks;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {


    EditText etNumber;
    Button btnsub;
    TextView tvResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etNumber = (EditText)findViewById(R.id.etNumber);
        btnsub = (Button)findViewById(R.id.btnsub);
        tvResult = (TextView)findViewById(R.id.tvResult);

        tvResult.setVisibility(View.GONE);
    }
    public void rollthedice(View v) {

        int nrTimes = Integer.parseInt(etNumber.getText().toString().trim());
        new ProcessDiceInBackground().execute(nrTimes);
    }


    public class ProcessDiceInBackground extends AsyncTask<Integer, Integer, String> {

        ProgressDialog dialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            dialog = new ProgressDialog(MainActivity.this);
            dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            dialog.setMax(Integer.parseInt(etNumber.getText().toString().trim()));
            dialog.show();
        }

        @Override
        protected String doInBackground(Integer... params) {
            int o = 0, t = 0, tt = 0, f = 0, ff = 0, s = 0, randomNumber;
            String results;
            double currentProgress = 0;
            double prevProgress = 0;
            Random random = new Random();
            int i;
            for (i = 0; i < params[0]; i++) {

                currentProgress = (double) i / params[0];

                if (currentProgress - prevProgress >= 0.02){
                    publishProgress(i);
                    prevProgress = currentProgress;
                }
                randomNumber = random.nextInt(6) + 1;
                switch (randomNumber){

                    case 1:
                        o++;
                        break;

                    case 2:
                        t++;
                        break;

                    case 3:
                        tt++;
                        break;

                    case 4:
                        f++;
                        break;

                    case 5:
                        ff++;
                        break;

                    default:
                        s++;
                        break;

                }
            }

            results = "1: "+o+"\n2: "+t+"\n3: "+tt+"\n4: "+f+"\n5: "+ff+"\n6: "+s;
            return results;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

            dialog.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            dialog.dismiss();

            tvResult.setText(s);
            tvResult.setVisibility(View.VISIBLE);

            Toast.makeText(MainActivity.this,"Process Completed",Toast.LENGTH_SHORT).show();
        }
    }
}
