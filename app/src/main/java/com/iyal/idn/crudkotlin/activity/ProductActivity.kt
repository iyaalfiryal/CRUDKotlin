package com.iyal.idn.crudkotlin.activity

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.iyal.idn.crudkotlin.R
import com.iyal.idn.crudkotlin.model.PersonItem
import com.iyal.idn.crudkotlin.remote.APIUtils
import com.iyal.idn.crudkotlin.remote.ProductService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ProductActivity : AppCompatActivity() {
    var productService: ProductService? = null
    var edtName: EditText? = null
    var edtPrice: EditText? = null
    var edtDesc: EditText? = null
    var edtId: EditText? = null
    var btnSave: Button? = null
    var btnDel: Button? = null
    var txtId: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.product)


        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        edtName = findViewById(R.id.edt_name)
        edtPrice = findViewById(R.id.edt_price)
        edtDesc = findViewById(R.id.edt_desc)
        btnSave = findViewById(R.id.btn_save)
        btnDel = findViewById(R.id.btn_delete)
        edtId = findViewById(R.id.edt_id)
        txtId = findViewById(R.id.txt_id)
        productService = APIUtils.productService
        val extras = intent.extras
        val productName = extras!!.getString("name")
        val productPrice = extras.getString("price")
        val productDesc = extras.getString("desc")
        val productID = extras.getString("id")
        edtId?.setText(productID)
        edtName?.setText(productName)
        edtPrice?.setText(productPrice)
        edtDesc?.setText(productDesc)
        if (productID != null && productID.trim { it <= ' ' }.length > 0) {
            edtId?.setFocusable(false)
        } else {
            txtId?.setVisibility(View.INVISIBLE)
            edtId?.setVisibility(View.INVISIBLE)
            btnDel?.setVisibility(View.INVISIBLE)
        }
        btnSave?.setOnClickListener(View.OnClickListener {
            val name = edtName?.getText().toString()
            val price = edtPrice?.getText().toString()
            val desc = edtDesc?.getText().toString()
            if (productID != null && productID.trim { it <= ' ' }.length > 0) {
                updateProduct(productID.toInt(), name, price, desc)
            } else {
                addProduct(name, price, desc)
            }
        })
        btnDel?.setOnClickListener(View.OnClickListener {
            deleteProduct(productID!!.toInt())
            val intent = Intent(
                this@ProductActivity,
                MainActivity::class.java
            )
            startActivity(intent)
        })
    }

    fun addProduct(name: String?, price: String?, desc: String?) {
        val call: Call<PersonItem?>? = productService?.addProduct(name, price, desc)
        call?.enqueue(object : Callback<PersonItem?> {
            override fun onResponse(
                call: Call<PersonItem?>,
                response: Response<PersonItem?>
            ) {
                if (response.isSuccessful()) {
                    Toast.makeText(
                        this@ProductActivity, "product added succesfully",
                        Toast.LENGTH_SHORT
                    ).show()
                    val intent = Intent(this@ProductActivity, MainActivity::class.java)
                    startActivity(intent)
                }
            }

            override fun onFailure(
                call: Call<PersonItem?>,
                t: Throwable
            ) {
                Log.e("ERROR: ", t.message!!)
            }
        })
    }

    private fun updateProduct(
        id: Int,
        name: String,
        price: String,
        desc: String
    ) {
        val call: Call<PersonItem?>? = productService?.updateProduct(id, name, price, desc)
        call?.enqueue(object : Callback<PersonItem?> {
            override fun onResponse(
                call: Call<PersonItem?>,
                response: Response<PersonItem?>
            ) {
                if (response.isSuccessful()) {
                    Toast.makeText(this@ProductActivity, "Product Updated", Toast.LENGTH_SHORT)
                        .show()
                    val intent = Intent(this@ProductActivity, MainActivity::class.java)
                    startActivity(intent)
                }
            }

            override fun onFailure(
                call: Call<PersonItem?>,
                t: Throwable
            ) {
                Log.e("ERROR: ", t.message!!)
            }
        })
    }

    private fun deleteProduct(id: Int) {
        val call: Call<PersonItem?>? = productService?.deleteProduct(id)
        call?.enqueue(object : Callback<PersonItem?> {
            override fun onResponse(
                call: Call<PersonItem?>,
                response: Response<PersonItem?>
            ) {
                if (response.isSuccessful()) {
                    Toast.makeText(
                        this@ProductActivity, "Product deleted",
                        Toast.LENGTH_SHORT
                    ).show()
                    val intent = Intent(this@ProductActivity, MainActivity::class.java)
                    startActivity(intent)
                }
            }

            override fun onFailure(
                call: Call<PersonItem?>,
                t: Throwable
            ) {
                Log.e("ERROR: ", t.message!!)
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}