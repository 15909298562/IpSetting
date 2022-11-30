@echo off
%6 mshta vbscript:CreateObject("Shell.Application").ShellExecute("cmd.exe","/c %~s0 %1 %2 %3 %4 %5 ::","","runas",1)(window.close)

netsh interface ipv4 set address %1 static %2 %3 %4
netsh interface ipv4 set dnsservers %1 static %5 primary

echo success