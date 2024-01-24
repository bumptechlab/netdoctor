# netdoctor

## 简介

这是一款用于检测域名DNS劫持的SDK，域名DNS劫持的情况很复杂，这里处理的情况只是冰山一角，还未达到商用目的。   
SDK大致原理：使用DnsJava库从不同的DNS服务器上解析出域名IP（相当于在pc上执行nslookup命令），如果来自不同DNS服务器的域名IP都一样，那么认定为被劫持。   
SDK内置了四个全球比较有名的DNS服务器，也可以自行增加DNS服务器。

- Google Public DNS: "8.8.8.8"
- IBM Quad9: "9.9.9.9"
- OpenDNS: "208.67.222.222"
- ORACLE Dyn Public DNS: "216.146.36.36"

## SDK集成步骤

1. 在app模块build.gradle引入依赖

```
implementation("io.github.bumptechlab:netdoctor:1.0.0")
```

2. 代码实现

``` kotlin
    fun checkDomain(domain: String) {
    NetDoctor()
        .withDns("114.114.114.114")//内置了四个公共DNS服务器地址，使用withDns也可以增加
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
```

## 版本说明

### 1.0.0

- 发布初始版本

