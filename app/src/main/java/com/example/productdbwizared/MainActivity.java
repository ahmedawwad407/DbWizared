package com.example.productdbwizared;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    EditText editTextProductName;
    EditText editTextProductPrice;
    TextView textData1;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextProductName = findViewById(R.id.ProductName);
        editTextProductPrice = findViewById(R.id.ProductPrice);
        textData1 = findViewById(R.id.textData1);
    }

    public void saveToFirbase(View v) {
        String productName = editTextProductName.getText().toString();
        String productPrice = editTextProductPrice.getText().toString();

        Map<String, Object> product = new HashMap<>();
        product.put("product Name", productName);
        product.put("product Price", productPrice);
        db.collection("products")
                .add(product)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getApplicationContext(), "successfully", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Failure", Toast.LENGTH_LONG).show();

                    }
                });
    }

    public void getDataFromFirestore(View v) {
        db.collection("products")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot documentSnapshots) {
                        if (documentSnapshots.isEmpty()) {
                            Toast.makeText(getApplicationContext(), "isEmpty", Toast.LENGTH_LONG).show();
                            return;
                        } else {
                            for (int x = 0; x <= documentSnapshots.size() - 1; x++) {
                                String data = documentSnapshots.getDocuments().get(x).getData().toString();
                                String id = documentSnapshots.getDocuments().get(x).getId();
                                Log.d("a", "ProductId: " + id + " => " + "ProductData: " + data + "\n");
                                // textData1.setText("ProductId: " + id + "\n" +"ProductData: "+ data);
                            }

                        }
                    }
                })
                .addOnFailureListener(e ->
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show());
    }
}