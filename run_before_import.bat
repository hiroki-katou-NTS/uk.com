@ECHO OFF
ECHO Start building!

cd nts.uk/uk.base
call ./gradlew tsc
cd ../uk.hr
call ./gradlew tsc
cd ../uk.pr
call ./gradlew tsc
cd ../uk.at
call ./gradlew tsc
cd ../uk.com
call ./gradlew tsc

cd ../uk.hr
call ./gradlew initd
cd ../uk.pr
call ./gradlew initd
cd ../uk.at
call ./gradlew initd
cd ../uk.com
call ./gradlew initd

cd ../uk.hr
call ./gradlew upver
cd ../uk.pr
call ./gradlew upver
cd ../uk.at
call ./gradlew upver
cd ../uk.com
call ./gradlew upver

ECHO Done building!
PAUSE