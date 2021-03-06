# `seed-jee` — Seed for JEE Systelab projects

This project is an application skeleton for a typical [JEE][JEE] backend application. You can use it
to quickly bootstrap your projects and dev environment.

The seed contains a Patient Management sample application and is preconfigured to install the JEE
framework and a bunch of development and testing tools for instant development gratification.

The app doesn't do much, just shows how to use different JEE standards and other suggested tools together:

* Bean Validation.
* JAXB
* CDI
* JPA
* EJB
* JAX-RS
* JWT
* CORS
* Swagger
* Allure with JUnit

## Getting Started

To get you started you can simply clone the `seed-jee` repository and install the dependencies:

### Prerequisites

You need [git][git] to clone the `seed-jee` repository.

You will need [Java™ SE Development Kit 8][jdk-download] and [Maven][maven].

### Clone `seed-jee`

Clone the `seed-jee` repository using git:

```bash
git clone https://github.com/systelab/seed-jee.git
cd seed-jee
```

If you just want to start a new project without the `seed-jee` commit history then you can do:

```bash
git clone --depth=1 https://github.com/systelab/seed-jee.git <your-project-name>
```

The `depth=1` tells git to only pull down one commit worth of historical data.

### Install Dependencies

In order to install the dependencies you must run:

```bash
mvn install
```

To generate the reports including the test report, you must run:

```bash
mvn site
```
In order to run the application, you must install a WildFly and deploy the generated war file. 

Another option could be to change the pom.xml file and remove the jacoco from the <cargo.jvmargs>:

```
<cargo.jvmargs>${cargo.container.debug.jvmargs}</cargo.jvmargs>
```

And after that run:

```bash
mvn clean package cargo:run
```

Finally browse to: http://127.0.0.1:13080/seed/swagger/ (the port could changes as it is defined in the pom file).

Use 'quentinada' as username and password.

Note: If you are using the angular seed, remember to set the API_BASE_PATH in the environment to match the same port.

[git]: https://git-scm.com/
[maven]: https://maven.apache.org/download.cgi
[jdk-download]: http://www.oracle.com/technetwork/java/javase/downloads
[JEE]: http://www.oracle.com/technetwork/java/javaee/tech/index.html
