package com.safiyaakhtar.application.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.safiyaakhtar.application.R;
import com.safiyaakhtar.application.Webservice.Config;

import java.util.ArrayList;

import static android.R.attr.id;

public class Fetch_Syllabus extends AppCompatActivity {
    ListView listView;
    ArrayList<String> syllabus_list = new ArrayList<>();
    ArrayList<String> notice_list = new ArrayList<>();
    ArrayList<String> book_list = new ArrayList<>();
    ArrayList<String> blank_list = new ArrayList<>();
    ArrayList<String> question_list = new ArrayList<>();
    ArrayAdapter<String> adpt;
    String syllabus_file1, notice_file1, book_file1, question_file1;
    String syllabus = "", syllabus_file = "";
    String notice = "";
    String notice_file = "";
    String book = "";
    String date = "";
    String book_file = "";
    String question = "";
    String question_file = "";
    String blank = "No Data Found ";
    String check;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fetch_data);
        listView = (ListView) findViewById(R.id.list_view);

        //    ******* get Intent Values ********
        book = getIntent().getExtras().getString(Config.KEY_Book);
        check= getIntent().getExtras().getString("id");
        book_file = getIntent().getExtras().getString(Config.KEY_Book_File);
        syllabus = getIntent().getExtras().getString(Config.KEY_Syllabus);
        syllabus_file = getIntent().getExtras().getString(Config.KEY_Syllabus_File);
        notice = getIntent().getExtras().getString(Config.KEY_Notice);
        notice_file = getIntent().getExtras().getString(Config.KEY_Notice_File);
        question = getIntent().getExtras().getString(Config.KEY_Question);
        question_file = getIntent().getExtras().getString(Config.KEY_Question_File);
        date = getIntent().getExtras().getString(Config.KEY_Date);
        question = getIntent().getExtras().getString(Config.KEY_Question);
        question_file = getIntent().getExtras().getString(Config.KEY_Question_File);
        if (check.equals("syllabus")){
            syllabus();
        }
       else if (check.equals("notice")){
            notice();
        }
        else if (check.equals("book")){
            book();
        }
        else if (check.equals("qpaper")) {
            question();
        }
    }
    public void syllabus() {
        if (syllabus.equals("")) {
            listView.setAdapter(adpt);
        } else {
            syllabus_list.add(syllabus);
            // Toast.makeText(this, syllabus + "/n"+syllabus_file + "/n" +syllabus_file1, Toast.LENGTH_SHORT).show();
            //***** url**********
            if (!syllabus_file.startsWith("http://") && !syllabus_file.startsWith("https://")) {
                syllabus_file = "http://itsallaboutme.pe.hu" + syllabus_file;
                syllabus_file1 = syllabus_file.replace("home/u707236822/public_html/", "");
                syllabus_file1 = syllabus_file1.replace("/.", "/");
                syllabus_file1 = syllabus_file1.replace("http://itsallaboutme.pe.hu /", "http://itsallaboutme.pe.hu/");
            }
           // syllabus_list.add(syllabus_file1);
            adpt = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, syllabus_list);
            listView.setAdapter(adpt);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent;
                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse(syllabus_file1));
                    startActivity(intent);
                }
            });
        }
    }

    public void book() {
        book_list.add(book);
        //***** url**********
        if (!book_file.startsWith("http://") && !book_file.startsWith("https://")) {
            book_file = "http://itsallaboutme.pe.hu" + book_file;
            book_file1 = book_file.replace("home/u707236822/public_html/", "");
            book_file1 = book_file1.replace("/.", "/");
            book_file1 = book_file1.replace("http://itsallaboutme.pe.hu /", "http://itsallaboutme.pe.hu/");
        }
      //  book_list.add(book_file1);
        adpt = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, book_list);
        listView.setAdapter(adpt);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent;
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse(book_file1));
                startActivity(intent);
            }
        });

    }

    public void question() {
        question_list.add(question);
        //***** url**********
        if (!question_file.startsWith("http://") && !question_file.startsWith("https://")) {
            question_file = "http://itsallaboutme.pe.hu" + question_file;
            question_file1 = question_file.replace("home/u707236822/public_html/", "");
            question_file1 = question_file1.replace("/.", "/");
            question_file1 = question_file1.replace("http://itsallaboutme.pe.hu /", "http://itsallaboutme.pe.hu/");
        }
       // question_list.add(question_file1);
        adpt = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, question_list);
        listView.setAdapter(adpt);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent;
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse(question_file1));
                startActivity(intent);
            }
        });
    }
    public void notice() {
        notice_list.add(notice);
        //***** url**********
        if (!notice_file.startsWith("http://") && !notice_file.startsWith("https://")) {
            notice_file = "http://itsallaboutme.pe.hu" + notice_file;
            notice_file1 = notice_file.replace("home/u707236822/public_html/", "");
            notice_file1 = notice_file1.replace("/.", "/");
            notice_file1 = notice_file1.replace("http://itsallaboutme.pe.hu /", "http://itsallaboutme.pe.hu/");
        }
       // notice_list.add(notice_file1);
       // notice_list.add(date);
        adpt = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, notice_list);
        listView.setAdapter(adpt);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent;
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse(notice_file1));
                startActivity(intent);
            }
        });
    }




}
