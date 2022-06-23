package com.greymatter.sprint

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dev.pinkroom.walletconnectkit.WalletConnectKit
import dev.pinkroom.walletconnectkit.WalletConnectKitConfig
import kotlinx.android.synthetic.main.activity_connect_wallet.*

class ConnectWalletActivity : AppCompatActivity() {
    val config = WalletConnectKitConfig(
        context = this,
        bridgeUrl = "wss://bridge.walletconnect.org",
        appUrl = "dappurl.com",
        appName = "DApp Name",
        appDescription = "My first Android DApp!"
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_connect_wallet)
        val walletConnectKit = WalletConnectKit.Builder(config).build()
        walletConnectButton.start(walletConnectKit, ::onConnected, ::onDisconnected)


    }
    private fun onConnected(address: String) {
        tvAddress.text = address;
        println("You are connected with account: $address")
    }

    private fun onDisconnected() {
        println("Account disconnected!")
    }
}