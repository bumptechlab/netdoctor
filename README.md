# netdoctor

## 简介

- 这是一款用于检测域名DNS劫持的SDK，域名DNS劫持的情况很复杂，这里处理的情况只是冰山一角，还未达到商用目的。   
- SDK大致原理：使用DnsJava库从不同的DNS服务器上解析出域名IP（相当于在pc上执行nslookup命令），如果来自不同DNS服务器的域名IP都一样，那么认定为被劫持。   
比如：以下对百度的检测结果，IP在114dns和AliDns上检测都不一样，肯定是不会被劫持的，可以多检测几个公共DNS。
    ```shell
    > nslookup www.baidu.com 114.114.114.114
    Server:		114.114.114.114
    Address:	114.114.114.114#53
    
    Non-authoritative answer:
    www.baidu.com	canonical name = www.a.shifen.com.
    Name:	www.a.shifen.com
    Address: 153.3.238.110
    Name:	www.a.shifen.com
    Address: 153.3.238.102
    
    > nslookup www.baidu.com 223.5.5.5
    Server:		223.5.5.5
    Address:	223.5.5.5#53
    
    Non-authoritative answer:
    www.baidu.com	canonical name = www.a.shifen.com.
    Name:	www.a.shifen.com
    Address: 180.101.50.188
    Name:	www.a.shifen.com
    Address: 180.101.50.242
    ```
- 更多DNS知识：   
https://blog.csdn.net/crazymakercircle/article/details/120521694
- SDK内置了四个全球比较有名的DNS服务器，也可以自行修改内置DNS服务器。

| DNS服务商                | DNS服务器地址       |
|-----------------------|:---------------|
| Google Public DNS     | 8.8.8.8        |
| IBM Quad9             | 9.9.9.9        |
| OpenDNS               | 208.67.222.222 |
| ORACLE Dyn Public DNS | 216.146.36.36  |

## SDK集成步骤

1. 在app模块build.gradle引入依赖

```
implementation("io.github.bumptechlab:netdoctor:1.0.0")
```

2. 代码实现

``` kotlin
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
```

## 版本说明

### 1.0.0

- 发布初始版本

### 1.0.1

- 更改withDns方法功能：用于替换内置dns服务

