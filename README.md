# VMJ: Variability Modules for Java

## Introduction
VMJ is an architectural pattern to develop delta-oriented software product line (SPL) with Java.
We use Java module system (available from Java 9 or later) that is combined with design patterns.
Code implementation in this repository is a part of artifacts that is submitted to 
15th International Working Conference on Variability Modelling of Software-Intensive Systems (VaMoS 2021).

### How to Implment with VMJ
- A product line is implemented as a Java Project.
- Core modules and delta modules are implemented as Java modules. 
Modification is achieved by using the decorator pattern. 
- Configuration Knowledge is defined in file [ck.properties](ck.properties)
- A product is implemented as a Java module. <br />
Feature selection is defined
in the module module declaration (file module-info.java).
A package with a main class is created  
to simulate the running process when the product is generated.
- Factory pattern is used to construct a core product
and its variants by passing product identifers. <br /> 
Factory class can be generated using a [template](templateFact.java) with a [script](genfactory.sh)
- Product generation is conducted with build script [genproduct.sh](genproduct.sh). <br />
If product validation is success, the product is built successfully. 
Choose your product, build the artifacts, and run the product. 

### How to Build and Run (Product Generation)
#### Using Bash Script
**Requirement**: Java 11 or later

```bash
Build Product: bash genproduct.sh [ProductModuleName] [MainClass]

Run: java --module-path [ProductModuleName] -m [ProductModuleName]
        or run.sh [ProductModuleName]

# If you want to build any module (one by one):
Build Module: bash genproduct.sh [ModuleName]
```

#### Using Docker
```bash
docker build --tag aisco/java-example:latest .
docker run --rm -it aisco/java-example:latest
```

### AISCO Case Study
We use case study Adaptive Information System for Charity Organization (AISCO).
<br /> AISCO features:
1. Program: Activity 
2. Program: Operational
3. Financial Report: Income (_mandatory_)
4. Financial Report: Expense
5. Financial Report: IncomeWithFrequency
6. Financial Report: ExpenseWithFrequency
6. Donation: via DirectPayment
7. Donation: via Payment Gateway (requires another [product line](https://gitlab.com/RSE-Lab-Fasilkom-UI/student-research/disertasi/s3---maya-retno/simulasi-payment-gateway) )

There are three products:
1. Charity School. Features: Activity, Operational, Income, Expense, DonationViaPaymentGateway.
2. Yayasan Pandhu. Features: Activity, Income, IncomeWithFrequency, ExpenseWithFrequency.
3. Hilfuns. Features: Activity, Income, and also requires product
`PaymentCharity` from `PaymentGateway` product line. 

### Structure Directory. 
Modules inside `src` folder:
- aisco.financialreport.core
- aisco.financialreport.income
- aisco.financialreport.expense
- aisco.financialreport.frequency
- aisco.program.core
- aisco.program.activity
- aisco.program.operational
- aisco.program.category
- aisco.donation.core
- aisco.donation.paymentgateway
- aisco.product.charityschool (Product: CharitySchool)
- aisco.product.yayasanpandhu (Product: YayasanPandhu)
- aisco.product.hilfuns (Product: Hilfuns)

Notes: 
- external modules are available inside folder `external`

#### Example Product CharitySchool:
Build: `bash genproduct.sh aisco.product.charityschool CharitySchool`

Run: `java --module-path aisco.product.charityschool -m aisco.product.charityschool`

The examples can also be run inside a Docker container:

```bash
docker build --tag aisco/java-example:latest .
docker run --rm -it aisco/java-example:latest

# Now inside the interactive terminal

## To build'Charity School' product example:
bash genproduct.sh aisco.product.charityschool CharitySchool

## To run 'Charity School' product example:
java --module-path aisco.product.charityschool -m aisco.product.charityschool
```