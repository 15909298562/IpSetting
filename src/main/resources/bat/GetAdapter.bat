@echo off
chcp 65001
set adapter=
For /f "skip=2 tokens=2,4" %%i in ('netsh interface show interface') do (
	if "%%i"=="Connected" (if "%%j"=="本地连接" (set adapter=%%j) else (if "%%j"=="WLAN" (set adapter=%%j)))
)
echo adapter=%adapter%