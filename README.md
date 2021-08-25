# 概述

本人工作电脑 Ubuntu + VirtualBox（Win 7），Win 7 中跑部分特殊软件（QQ、WeChat 等），由于其他因素，需要手机热点（或手机桥接网络），经常需要在三端(手机)同步信息，采用方式 Ubuntu 跟 VirtualBox 间，设置共享剪切板（但时有失效），然后在虚拟机中通过微信或 QQ 发送给自己，来进行手机端同步，操作起来太过繁琐，因此编写此工具用以在局域网内共享剪切板信息。

逻辑：监听系统剪切板，当剪切板有新信息时，发送广播信息，各端接受信息，并将信息设置到剪切板中。

注：暂时只支持剪切板中字符的共享，后续考虑支持文件共享;
热点（或桥接网络）中的设备用 255.255.255.255 进行广播，热点（或桥接）手机广播地址使用 192.168.43.255

<video id="video" controls="" preload="none" poster="http://om2bks7xs.bkt.clouddn.com/2017-08-26-Markdown-Advance-Video.jpg">
    <source id="mp4" src="./1.mp4" type="video/mp4">
</video>
![avatar](./clipboard-sync-lan.png)



