@echo off
%1 mshta vbscript:CreateObject("Shell.Application").ShellExecute("cmd.exe","/c %~s0 ::","","runas",1)(window.close)

chcp 65001
set adapter=
For /f "skip=2 tokens=2,4" %%i in ('netsh interface show interface') do (
	if "%%i"=="Connected" (if "%%j"=="本地连接" (set adapter=%%j) else (if "%%j"=="WLAN" (set adapter=%%j)))
)

netsh interface ipv4 set dnsservers name=%adapter% source=dhcp
netsh interface ipv4 set address name=%adapter% source=dhcp

echo success