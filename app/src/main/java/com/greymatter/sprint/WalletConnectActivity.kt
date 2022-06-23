package com.greymatter.sprint

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.greymatter.sprint.utils.Constant
import dev.pinkroom.walletconnectkit.WalletConnectKit
import dev.pinkroom.walletconnectkit.WalletConnectKitConfig
import kotlinx.android.synthetic.main.activity_wallet_connect.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jsoup.Jsoup

class WalletConnectActivity : AppCompatActivity() {


    private val config by lazy {
        WalletConnectKitConfig(
            context = this,
            bridgeUrl = "wss://bridge.aktionariat.com:8887",
            appUrl = "walletconnectkit.com",
            appName = "WalletConnect Kit",
            appDescription = "This is the Swiss Army toolkit for WalletConnect!"
        )
    }
    private val walletConnectKit by lazy { WalletConnectKit.Builder(config).build() }
    var session: Session? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wallet_connect)
        session = Session(this@WalletConnectActivity)
        initViews()
        btnView.setOnClickListener {
            walletBalance.visibility = View.VISIBLE
            walletBalance.text = session?.getData(
                Constant.BALANCE
            )

        }
    }

//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        walletConnectKit.address?.let {
//            menuInflater.inflate(R.menu.toolbar_menu, menu)
//        }
//        return super.onCreateOptionsMenu(menu)
//    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when (item.itemId) {
//            R.id.disconnectView -> onDisconnectClicked()
//        }
//        return super.onOptionsItemSelected(item)
//    }

    private fun initViews() {
        loginView.start(walletConnectKit, ::onConnected, ::onDisconnected)
        //initPerformTransactionView()
    }



    @SuppressLint("StringFormatInvalid")
    private fun onConnected(address: String){
        loginView.visibility = View.GONE
        connectedView.visibility = View.VISIBLE
        connectedAddressView.text = "Address - "+address
        walletBalance.visibility = View.GONE

        val url = Constant.BASE_URL + "getwalletbalance.html?"+address
        var balance = ""
        GlobalScope.launch(Dispatchers.IO) {
            val doc = Jsoup.connect(url).get()
            balance = doc.title()
            setvalue(balance)
            Log.d("ADDRESS",address)

        }



        invalidateOptionsMenu()
    }

    private fun setvalue(balance: String) {
        Log.d("BALANCE",balance)
        setbalvalue(balance)

    }

    private fun setbalvalue(balance: String) {

        session?.setData(
            Constant.BALANCE,
            balance
        )
        var bal = session?.getData(
            Constant.BALANCE
        )
        if (bal.equals("")){
            finish()
        }


    }

    private fun onDisconnected(){
        session?.setData(
            Constant.BALANCE,
            ""
        )
        loginView.visibility = View.VISIBLE
        connectedView.visibility = View.GONE
        invalidateOptionsMenu()
    }
//
//    private fun initPerformTransactionView() = with(binding) {
//        performTransactionView.setOnClickListener {
//            val toAddress = toAddressView.text.toString()
//            val value = valueView.text.toString()
//            lifecycleScope.launch {
//                runCatching { walletConnectKit.performTransaction(toAddress, value) }
//                    .onSuccess { showMessage("Transaction done!") }
//                    .onFailure { showMessage(it.message ?: it.toString()) }
//            }
//        }
//    }
//
//    private fun onDisconnectClicked() {
//        walletConnectKit.removeSession()
//    }
//
//    private fun showMessage(message: String) {
//        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
//    }
}