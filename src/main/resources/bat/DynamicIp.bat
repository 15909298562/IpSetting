@echo off
%1 mshta vbscript:CreateObject("Shell.Application").ShellExecute("cmd.exe","/c %~s0 ::","","runas",1)(window.close)

set adapter=
For /f "skip=2 tokens=4" %%i in ('netsh interface show interface') do (
	set adapter=%%i
)

netsh interface ipv4 set address name=%adapter% source=dhcp
netsh interface ipv4 set dnsservers name=%adapter% source=dhcp

echo success