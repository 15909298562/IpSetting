@echo off & setlocal enabledelayedexpansion
::setlocal enabledelayedexpansion
::启用延迟变量的扩展,因为下面for循环中一条复合语句内的变量findAdapter出现在多个位置,当该变量更新时对其它语句可见
chcp 65001

::获取当前正在使用的网路适配器
set usedAdapter=
For /f "skip=2 tokens=2,4" %%i in ('netsh interface show interface') do (
	if "%%i"=="Connected" (if "%%j"=="本地连接" (set usedAdapter=%%j) else (if "%%j"=="WLAN" (set usedAdapter=%%j)))
)

::通过网络适配器找到IP地址
set ipaddr=
set adapter=%usedAdapter%
set findAdapter=no
for /f "skip=3 tokens=1,2,3,4,14" %%i in ('ipconfig') do (
	if "!ipaddr!"=="" (
		if "!findAdapter!"=="no" (
			echo %%l | findstr "%adapter%" >nul && set findAdapter=yes
		) else (
			echo %%i | findstr "IPv4" >nul && set ipaddr=%%m
		)
	)
)
echo success
echo @IPv4=!ipaddr!@