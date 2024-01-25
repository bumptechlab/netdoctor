package com.example.vest.sdk.netdoctor

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.vest.sdk.netdoctor.ui.theme.NetdoctorTheme
import com.net.doctor.NetDoctor
import com.net.doctor.NetReport

class MainActivity : ComponentActivity() {

    val TAG = MainActivity::class::java.javaClass.simpleName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NetdoctorTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }
        }
        checkDomain("www.baidu.com")
    }

    fun checkDomain(domain: String) {
        NetDoctor()
            //114DNS：114.114.114.114
            //阿里公共DNS解析服务：223.5.5.5
            //.withDns("114.114.114.114", "223.5.5.5")//可以自定义dns服务器用于替换内置dns服务
            .dnscheck(domain, object : NetReport {
                override fun onReport(isDnsHijack: Boolean) {
                    if (isDnsHijack) {
                        Log.d(TAG, "$domain is hijacked")
                    } else {
                        Log.d(TAG, "$domain is not hijacked")
                    }
                }
            })
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NetdoctorTheme {
        Greeting("Android")
    }
}