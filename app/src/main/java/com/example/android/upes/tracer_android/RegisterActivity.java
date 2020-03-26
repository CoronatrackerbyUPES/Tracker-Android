package com.example.android.upes.tracer_android;

//import android.support.v7.app.AppCompatActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    EditText name_et,numberOfFamilyMembers_et,age_et,lossOfWages_et,presentAddress_et,phonenumber_et;
    Button registerbutton;
    String name,numberOfFamilyMembers,age,lossOfWages,presentAddress,phonenumber;
    Context mCtx;
    //Database
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("RegisterData");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Declarations for XML elements in Register Activity
        name_et= (EditText)findViewById(R.id.name_editText);
        numberOfFamilyMembers_et = (EditText)findViewById(R.id.noOfFamilyMembers_editText);
        age_et = (EditText)findViewById(R.id.age_editText);
        lossOfWages_et = (EditText)findViewById(R.id.lossOfWage_editText);
        presentAddress_et =(EditText)findViewById(R.id.presentAddress_editText);
        phonenumber_et = (EditText)findViewById(R.id.phoneNumber_editText);
        registerbutton = (Button)findViewById(R.id.buttonRegister);
        //---------------------------------------------------------



        registerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = name_et.getText().toString().toLowerCase();
                numberOfFamilyMembers = numberOfFamilyMembers_et.getText().toString();
                age = age_et.getText().toString();
                lossOfWages = lossOfWages_et.getText().toString();
                presentAddress = presentAddress_et.getText().toString();
                phonenumber = phonenumber_et.getText().toString();


                if(name.isEmpty() || numberOfFamilyMembers.isEmpty() || age.isEmpty() || lossOfWages.isEmpty() || presentAddress.isEmpty() || phonenumber.isEmpty() || !isValidMobile(phonenumber)){
                    if (!isValidMobile(phonenumber)) {
                        Toast.makeText(RegisterActivity.this, "Phone Number invalid", Toast.LENGTH_SHORT).show();
                        phonenumber_et.setError("Enter a 10 digit phone number");
                        phonenumber_et.requestFocus();
                    }
                    else
                        Toast.makeText(RegisterActivity.this, "Please enter all the details", Toast.LENGTH_SHORT).show();


                }
                 else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    builder.setTitle("Are you sure?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {


                                myRef.child(phonenumber).child("name").setValue(name);
                                myRef.child(phonenumber).child("nooffamilymembers").setValue(numberOfFamilyMembers);
                                myRef.child(phonenumber).child("age").setValue(age);
                                myRef.child(phonenumber).child("lossofwages").setValue(lossOfWages);
                                myRef.child(phonenumber).child("presentaddress").setValue(presentAddress);
                                myRef.child(phonenumber).child("phonenumber").setValue(phonenumber);

                                //clearing the form

                                name_et.setText("");
                                numberOfFamilyMembers_et.setText("");
                                age_et.setText("");
                                lossOfWages_et.setText("");
                                presentAddress_et.setText("");
                                phonenumber_et.setText("");
                            }



                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();


                }

            }
        });


    }
    private boolean validate(String entry){
        if(!entry.isEmpty()){
            return true;
        }
        else
            return false;


    }
    private boolean isValidMobile(String phone) {
        return android.util.Patterns.PHONE.matcher(phone).matches();
    }
}
