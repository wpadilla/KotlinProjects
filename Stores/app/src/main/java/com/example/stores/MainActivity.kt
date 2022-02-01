package com.example.stores

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.stores.databinding.ActivityMainBinding
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class MainActivity : AppCompatActivity(), OnClickListener, MainAux {

    private lateinit var mBinding: ActivityMainBinding
    private lateinit var mAdapter: StoreAdapter
    private lateinit var mGridLayout: GridLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        setUpRecyclerView()

        mBinding.fab.setOnClickListener { launchEditFragment() }
    }

    private fun launchEditFragment(args: Bundle? = null) {
        val fragment = EditStoreFragment()

        if(args != null) fragment.arguments = args

        val fragmentTransaction = supportFragmentManager.beginTransaction()

        fragmentTransaction.add(R.id.containerMain, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()

        hideFab(false)
    }

    private fun setUpRecyclerView() {
        mAdapter = StoreAdapter(mutableListOf<StoreEntity>(), this)
        mGridLayout = GridLayoutManager(this, 2)

        getStores()

        mBinding.recyclerView.apply {
            setHasFixedSize(true)
            adapter = mAdapter
            layoutManager = mGridLayout
        }

    }

    
    private fun getStores() {
        doAsync {
            val stores = StoreApplication.database.storeDao().getAllStores()

            uiThread {
                mAdapter.setStore(stores)
            }
        }
    }


    /*
    * onClickListener
    * */
    override fun onClick(storeId: Long) {
        val args = Bundle()

        args.putLong(getString(R.string.store_id), storeId)

        launchEditFragment(args)
    }

    override fun onFavoriteStore(storeEntity: StoreEntity) {
        storeEntity.isFavorite = !storeEntity.isFavorite;

        doAsync {
            StoreApplication.database.storeDao().updateStore(storeEntity)

            uiThread {
                mAdapter.update(storeEntity)
            }

        }
    }

    override fun onDeleteStore(storeEntity: StoreEntity) {
        doAsync {
            StoreApplication.database.storeDao().deleteStore(storeEntity)

            uiThread {
                mAdapter.delete(storeEntity)
            }
        }
    }

    /*
    *
    * MainAux implementations
    *
    * */
    override fun hideFab(notHide: Boolean) {
        if(notHide) {
            mBinding.fab.show()
        } else {
            mBinding.fab.hide()
        }
    }

    override fun addStore(store: StoreEntity) {
        mAdapter.add(store)
    }

    override fun updateStore(store: StoreEntity) {
        mAdapter.update(store)
    }
}