package com.net.doctor

import android.util.Log
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.xbill.DNS.DClass
import org.xbill.DNS.Message
import org.xbill.DNS.Name
import org.xbill.DNS.Record
import org.xbill.DNS.Resolver
import org.xbill.DNS.SimpleResolver
import org.xbill.DNS.Type
import java.net.InetAddress

class NetDoctor {
    private val TAG = NetDoctor::class.java.simpleName

    val dns: MutableList<String> = mutableListOf(
        "8.8.8.8",//Google Public DNS
        "9.9.9.9",//IBM Quad9
        "208.67.222.222",//OpenDNS
        "216.146.36.36",//ORACLE Dyn Public DNS
    )

    /**
     * replace all inner dns servers
     */
    fun withDns(vararg dnsServers: String): NetDoctor {
        if (dnsServers.isNotEmpty()) {
            dns.clear()
            dns.addAll(dnsServers)
        }
        return this
    }

    /**
     * start detecting dns hijacking for specified domain
     */
    fun dnscheck(domain: String, report: NetReport?): NetDoctor {
        GlobalScope.launch() {
            Log.d(TAG, "start looking up ip for domain[$domain] on dns server: $dns")
            val ipList = mutableListOf<List<String>>()
            //使用指定DNS服务查询IP
            dns.forEach {
                val dnsIpList = dnslookup(domain, it)
                Log.d(TAG, "dns[$it] lookup result: $domain -> $dnsIpList")
                if (dnsIpList.isNotEmpty()) {
                    ipList.add(dnsIpList)
                }
            }
            //使用LocalDNS查询IP
            val defaultIpAddress = InetAddress.getAllByName(domain)
            val defaultIpList = mutableListOf<String>()
            defaultIpAddress.forEach {
                defaultIpList.add(it.hostAddress)
            }
            Log.d(TAG, "dns[local] lookup result: $domain -> $defaultIpList")
            if (defaultIpList.isNotEmpty()) {
                ipList.add(defaultIpList)
            }
            Log.d(TAG, "dns lookup ip list: $domain -> $ipList")
            //不同DNS服务查询到的IP都是一样，认为被劫持
            if (isIpListSame(ipList)) {
                Log.d(TAG, "ip list is all the same")
                report?.onReport(true)
            } else {
                Log.d(TAG, "ip list is not all the same")
                report?.onReport(false)
            }
        }
        return this
    }

    private fun isIpListSame(ipList: List<List<String>>): Boolean {
        if (ipList.size < 2) {
            return false
        }
        for ((i, ipList1) in ipList.withIndex()) {
            for ((k, ip1) in ipList1.withIndex()) {
                for ((j, ipList2) in ipList.withIndex()) {
                    if (i != j) {
                        Log.d(TAG, "compare ip[$i][$k]: $ip1 with ip_list[$j]-$ipList2")
                        if (!ipList2.contains(ip1)) {
                            return false
                        }
                    }
                }
            }
        }
        return true
    }

    private fun dnslookup(domain: String, dnsServer: String): List<String> {
        val queryRecord =
            Record.newRecord(Name.fromString("$domain."), Type.A, DClass.IN)
        val queryMessage: Message = Message.newQuery(queryRecord)
        val resolver: Resolver = SimpleResolver(dnsServer)
        val ipList = mutableListOf<String>()
        try {
            val answer = resolver.send(queryMessage)
            val records = answer?.getSection(1)
            records?.forEach {
                //A记录：域名对应的IPv4 地址
                //CNAME: 域名对应的别名记录
                //这里只要A记录
                if (it.type == Type.A) {
                    ipList.add(it.rdataToString())
                }
            }
            Log.d(TAG, "$answer")
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ipList
    }

}