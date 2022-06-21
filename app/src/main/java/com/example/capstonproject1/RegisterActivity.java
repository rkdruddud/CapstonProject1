package com.example.capstonproject1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth; // 파이어베이스 인증
    private DatabaseReference mDatabaseRef; // 실시간 데이터베이스
    private EditText mName, mPw, mPwTest, mHpNumber, mId;
    private Button mBtnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("CapstonProject1");

        mName = (EditText) findViewById(R.id.editTextTextPersonName3);
        mPw = (EditText)findViewById(R.id.editTextTextPassword22);
       mPwTest = (EditText)findViewById(R.id.editTextTextPassword3);
       mId = (EditText)findViewById(R.id.editTextTextId12);
       mHpNumber = (EditText) findViewById(R.id.editTextPhone);
        mBtnRegister = findViewById(R.id.complitebtn);

        String strId = mId.getText().toString();
        String strPw = mPw.getText().toString();
        String strPwTest = mPwTest.getText().toString();
        String strName = mName.getText().toString();
        String strHpNumber = mHpNumber.getText().toString();

        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               if(!strId.equals("") && !strPw.equals("")){
                   createUser(strId,strPw,strName,strHpNumber);


               }
               else{
                   Toast.makeText(RegisterActivity.this,"이메일과 비밀번호를 입력하시오.",Toast.LENGTH_SHORT).show();
               }
                //FirebaseAuth 진행



               /* Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                RegisterActivity.this.startActivity(intent);*/

            }
        });
    }

    private void createUser(String mId, String mPw, String mName, String mHpNumber) {

        mFirebaseAuth.createUserWithEmailAndPassword(mId,mPw).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
                    UserAccount account = new UserAccount();

                    account.setIdToken(firebaseUser.getUid());
                    account.setId(firebaseUser.getEmail());
                    account.setPassword(mPw);

                    mDatabaseRef.child("UserAccount").child(firebaseUser.getUid()).setValue(account);

                    Toast.makeText(RegisterActivity.this, "회원가입에 성공하셨습니다.", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else {
                    Toast.makeText(RegisterActivity.this, "회원가입에 실패하셨습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:{
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}