package com.example.chattingapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.chattingapp.databinding.ActivityLoginBinding
import com.example.chattingapp.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUp : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var firebaseAuth:FirebaseAuth
    private lateinit var dbRef:DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()


        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()
        

        binding.signUpBtn.setOnClickListener {
            val name = binding.nameEt.text.toString()
            val email = binding.emailEt.text.toString()
            val password = binding.passwordEt.text.toString()
            
            signup(name, email, password)
        }

    }

    private fun signup(name: String, email: String, password: String) {
        if(name.isEmpty() || password.isEmpty() || email.isEmpty()){
            Toast.makeText(this, "Empty fields are not allowed!", Toast.LENGTH_SHORT).show()
            return
        }
        if(password.length<6){
            Toast.makeText(this, "Password must be atleast 6 characters", Toast.LENGTH_SHORT).show()
            return
        }
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if(it.isSuccessful){
                Toast.makeText(this, "Account Created", Toast.LENGTH_SHORT).show()

                addUserToDatabase(name, email, firebaseAuth.currentUser?.uid!!)
                val intent = Intent(this, Login::class.java)

                finish()
                startActivity(intent)
            }
            else{
                Toast.makeText(this, "Some error occurred", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun addUserToDatabase(name: String, email: String, uid: String) {
        dbRef = FirebaseDatabase.getInstance().getReference()
        dbRef.child("user").child(uid).setValue(User(name, email, uid))
    }


}