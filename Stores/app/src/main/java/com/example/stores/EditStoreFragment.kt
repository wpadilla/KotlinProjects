package com.example.stores

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.stores.databinding.FragmentEditStoreBinding
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [EditStoreFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EditStoreFragment : Fragment() {

    lateinit var mBinding: FragmentEditStoreBinding
    private var mActivity: MainActivity? = null
    private var mIsEditMode: Boolean = false
    private var mStoreEntity: StoreEntity? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        mBinding = FragmentEditStoreBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = arguments?.getLong(getString(R.string.store_id), 0)

        if(id != null && id != 0L) {
            mIsEditMode = true
            getStore(id)
        } else {
            mIsEditMode = false
            mStoreEntity = StoreEntity(name= "",
                                        phone = "",
                                        website = "",
                                        storeImg = "")
        }
        mActivity = activity as? MainActivity
        mActivity?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        mActivity?.supportActionBar?.title = getString(R.string.edit_store_add)
        setHasOptionsMenu(true)

        mBinding.etStoreImg.addTextChangedListener {
            Glide.with(this)
                .load(mBinding.etStoreImg.text.toString())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(mBinding.imgStore)

        }

        mBinding.etName.addTextChangedListener { validateFields(mBinding.tilName) }
        mBinding.etPhone.addTextChangedListener { validateFields(mBinding.tilName) }
        mBinding.etStoreImg.addTextChangedListener { validateFields(mBinding.tilStoreImg) }

    }

    private fun getStore(id: Long) {
        doAsync {
            mStoreEntity = StoreApplication.database.storeDao().getStore(id)

            uiThread {
                if(mStoreEntity != null) setStore(mStoreEntity!!)
            }
        }
    }

    private fun setStore(store: StoreEntity) {
        with(mBinding){
            etName.text = store.name.editable()
            etPhone.text = store.name.editable()
            etWebsite.text = store.website.editable()
            etStoreImg.text = store.storeImg.editable()
        }

    }

    fun String.editable(): Editable = Editable.Factory.getInstance().newEditable(this)

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.save_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            android.R.id.home -> {
                mActivity?.onBackPressed()
                true
            }
            R.id.action_save -> {

                if(mStoreEntity != null && validateFields(mBinding.tilStoreImg, mBinding.tilPhone, mBinding.tilName)) {

                    with(mStoreEntity!!) {
                        name = mBinding.etName.text.toString().trim()
                        phone = mBinding.etPhone.text.toString().trim()
                        website = mBinding.etWebsite.text.toString().trim()
                        storeImg = mBinding.etStoreImg.text.toString().trim()
                    }

                    doAsync {
                        if(mIsEditMode) StoreApplication.database.storeDao().updateStore(mStoreEntity!!)
                        else mStoreEntity!!.id = StoreApplication.database.storeDao().addStore(mStoreEntity!!)

                        hideKeyboard()

                        uiThread {
                            if(mIsEditMode){
                                mActivity?.updateStore(mStoreEntity!!)
                                Snackbar.make(mBinding.root, R.string.store_update_success_message, Snackbar.LENGTH_LONG)
                                    .show()
                            }
                            else {
                                mActivity?.addStore(mStoreEntity!!)

                                Toast.makeText(
                                    mActivity,
                                    R.string.store_save_success_message,
                                    Toast.LENGTH_LONG
                                ).show()

                                mActivity?.onBackPressed()
                            }


                        }
                    }
                }
                true
            } else -> super.onOptionsItemSelected(item)
        }
    }

    private fun validateFields(vararg fields: TextInputLayout): Boolean {
        var isValid = true;

        for(field in fields ){
            if(field.editText?.text.toString().trim().isEmpty()) {
                field.error = getString(R.string.helper_required)
                field.editText?.requestFocus()
                isValid = false
            } else field.error = null
        }

        return isValid
    }

    fun hideKeyboard(){
       val imm = mActivity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        if(view !== null) {
            imm.hideSoftInputFromWindow(requireView().windowToken, 0)
        }
    }

    override fun onDestroyView() {
        hideKeyboard()
        super.onDestroyView()
    }
    override fun onDestroy() {
        mActivity?.supportActionBar?.setDisplayHomeAsUpEnabled(false)
        mActivity?.supportActionBar?.title = getString(R.string.app_name)
        setHasOptionsMenu(false)
        mActivity?.hideFab(true)
        super.onDestroy()
    }
}