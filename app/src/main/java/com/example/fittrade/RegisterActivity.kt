package com.example.fittrade

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.fittrade.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.mainsignButton.setOnClickListener {

            val userName = binding.etUser.text.toString()
            val email = binding.etEmail.text.toString()
            val pass = binding.etPass.text.toString()
            val conPass = binding.etConpass.text.toString()
            val phnNo = binding.etPhone.text.toString()
            val place = binding.etPlace.text.toString()


            if(userName.isNotEmpty() && phnNo.isNotEmpty() && place.isNotEmpty() && email.isNotEmpty() && pass.isNotEmpty() && conPass.isNotEmpty()){

                if (pass == conPass){

                    firebaseAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener {
                        if (it.isSuccessful){
                            databaseReference = FirebaseDatabase.getInstance().getReference("User")
                            val user = User(userName, email, phnNo, place)
                            databaseReference.child(userName).push().setValue(user).addOnSuccessListener {
                                Toast.makeText(this, "Saved successfully", Toast.LENGTH_SHORT).show()
                            }
                            startActivity(Intent(this, BMI_calculation::class.java))
                        }
                        else{
                            Toast.makeText(this , it.exception.toString() , Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                else{
                    Toast.makeText(this , "Password is not matching", Toast.LENGTH_SHORT).show()
                }
            }
            else{
                Toast.makeText(this , "Empty Fields Are Not Allowed" , Toast.LENGTH_SHORT).show()
            }

        }
    }

//   override fun onStart() {
//        super.onStart()
//        if(firebaseAuth.currentUser != null){
//            startActivity(Intent(this, LoginActivity::class.java))
//        }
//   }


}