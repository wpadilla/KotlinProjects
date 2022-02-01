package com.example.usersp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.usersp.databinding.ActivityMainBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity(), OnClickListener {

    override fun onClick(user: User) {
        Toast.makeText(this, user.getFullName(), Toast.LENGTH_LONG).show()
    }

    private lateinit var userAdapter: UserAdapter
    private lateinit var linearLayoutManager: RecyclerView.LayoutManager
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        userAdapter = UserAdapter(getUsers(), this)
        linearLayoutManager = LinearLayoutManager(this)

        val preferences = getPreferences(Context.MODE_PRIVATE)

        val isFirstTime = preferences.getBoolean(getString(R.string.sp_first_time), true)
        Log.i("username setted!!!", "${preferences.getString(getString(R.string.username), "klk")}")
        if(isFirstTime) {
            val dialogView = layoutInflater.inflate(R.layout.register_dialog, null);
            MaterialAlertDialogBuilder(this)
                .setTitle(R.string.fr_dialog_title)
                .setView(dialogView)
                .setCancelable(false)
                .setPositiveButton(R.string.ft_dialog_confirm, { dialogInterface, i ->
                    val username = dialogView.findViewById<TextInputEditText>(R.id.etUsername)
                        .text.toString();

                    with(preferences.edit()){
                        putBoolean(getString(R.string.sp_first_time), false)
                        putString(getString(R.string.username), username)
                            .apply()
                    }

                }).show()

        }

        Log.i("SP=", "= ${isFirstTime}")
        binding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = linearLayoutManager
            adapter = userAdapter
        }
    }

    private fun getUsers(): MutableList<User> {
        val users = mutableListOf<User>()

        val luz = User(1, "Luz", "Peña de Padilla", "https://c8.alamy.com/compes/bfenxy/moggy-cat-ejemplar-hembra-adulto-sentado-studio-bfenxy.jpg")
        val williams = User(2, "Williams", "Padilla de Peña", "https://live.staticflickr.com/2685/4444501798_eced02f431_b.jpg")

        users.add(luz)
        users.add(williams)

        return users;
    }
}