package ru.alexleru.a3l2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import io.reactivex.functions.Consumer;
import io.reactivex.subjects.PublishSubject;


public class MainActivity extends AppCompatActivity {
    private EditText editText;
    private TextView textView;
    PublishSubject<String> observable;
    Consumer<String> observer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        observer();
    }

    private void observer() {
        observable = PublishSubject.create();
        observer = new Consumer<String>() {
            @Override
            public void accept(String s) {
                textView.append(s);
            }
        };
        observable.subscribe(observer);
    }

    private void initView() {
        editText = findViewById(R.id.edit_field);
        textView = findViewById(R.id.text_field);
        editText.addTextChangedListener(initTextWatcher());
    }

    private TextWatcher initTextWatcher(){
        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() == 0){
                    Toast.makeText(getApplicationContext(),
                            "Нет текста для передачи Слушателю", Toast.LENGTH_LONG).show();
                } else {
                    observable.onNext(s.toString() + ", ");
                }
            }
        };
        return watcher;
    }
}
