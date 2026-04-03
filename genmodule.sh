#!/bin/bash
set -e

function buildjar()
{
if [ ! -f "$product/$1.jar" ]; then
javac -d classes --module-path $product $(find src/$1 -name "*.java") src/$1/module-info.java 
jar --create --file $product/$1.jar -C classes .
rm -r classes
echo "  $1.jar is created"
else 
echo "  $1.jar is ready"
fi
}

function composedelta(){
file="ck.properties"
while IFS== read key value || [[ -n "$value" ]] ;
    do
    if [ "$1" == "$key" ]; then
    for i in $(echo $value | tr "," "\n")
        do
        buildjar  $i
        echo "    requires $i;" >> $MODINFO
        done
    echo "}" >> $MODINFO;
    fi
    done < "$file"
}

function gen_class()
{
FILE="$DIR/FinancialReportImpl.java"
cp src/$1/aisco/financialreport/frequency/FinancialReportImpl.java $DIR/FinancialReportImpl.java
sed -i "1s/.*/package $MOD;/" $FILE
}

MOD=$1
product=$2
DIR="src/$MOD/$(echo $MOD | tr "." "/")"

if [ -d "$DIR" ]; then rm -r $DIR; fi
mkdir -p $DIR
MODINFO="src/$MOD/module-info.java"
echo "module $MOD {
    exports $MOD;
    requires aisco.financialreport.core;" > $MODINFO

composedelta $1

if [ $1 == aisco.financialreport.incomewithfrequency ]; then
gen_class "aisco.financialreport.frequency"
elif [ $1 == aisco.financialreport.expensewithfrequency ]; then
gen_class "aisco.financialreport.frequency"
else 
echo "   please check your config file"
fi 