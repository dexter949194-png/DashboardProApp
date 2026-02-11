@ECHO OFF
SETLOCAL

SET DIRNAME=%~dp0
IF "%DIRNAME%" == "" SET DIRNAME=.
SET APP_BASE_NAME=%~n0
SET APP_HOME=%DIRNAME%

REM Resolve any "." and ".." in APP_HOME to make it shorter.
FOR %%i IN ("%APP_HOME%") DO SET APP_HOME=%%~fi

SET DEFAULT_JVM_OPTS=

IF EXIST "%APP_HOME%\gradle\wrapper\gradle-wrapper.jar" (
    SET CLASSPATH=%APP_HOME%\gradle\wrapper\gradle-wrapper.jar
) ELSE (
    ECHO ERROR: gradle-wrapper.jar not found.
    EXIT /B 1
)

SET JAVA_EXE=java.exe
%JAVA_EXE% -version >NUL 2>&1
IF ERRORLEVEL 1 (
    ECHO ERROR: Java not found. Please install Java and set JAVA_HOME.
    EXIT /B 1
)

"%JAVA_EXE%" %DEFAULT_JVM_OPTS% %JAVA_OPTS% %GRADLE_OPTS% ^
 -classpath "%CLASSPATH%" org.gradle.wrapper.GradleWrapperMain %*

ENDLOCAL
