@echo off
chcp 65001
set adapter=
For /f "skip=2 tokens=2,4" %%i in ('netsh interface show interface') do (
	if "%%i"=="Connected" (set adapter=%%j)
)
echo adapter=%adapter%