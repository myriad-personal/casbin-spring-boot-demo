# Casbin Spring Boot Demo

A simple Spring Boot application demonstrating the usage of casbin with role claims such as we have with our Okta JWTs.
The idea is to demonstrate how an application may use a small core set of enterprise roles and other identity claims 
from a central identity authority and use them to build a rich domain specific authorization context.

This application doesn't integrate with a real source of identity and all the data is kept in memory only (no persistence).
It's a bare-bones demo of how we may leverage Casbin for authorization.

## Usage

1. Clone the repository locally.
1. Take the [application.properties.template](application.properties.template) and save it as `application.properties` in the root, next to the template.
   * adjust the paths to the files -- I've not found a way to NOT specify the fully qualified path.
1. Launch the application however you prefer to launch spring boot applications with a maven build.
1. Send requests with the following headers:
    1. `user-subject`: the value can be whatever
    1. `user-roles`: any set of coma separated values
        * you will want to look at the [policy](src/main/resources/casbin/policy.csv) and the controllers ([PatientController](src/main/java/com/example/casbinspringbootdemo/PatientController.java) and [DoctorController](src/main/java/com/example/casbinspringbootdemo/DoctorController.java)) to see what you're working with.
        * you should also look at the [JCasbinAuthzFilter](src/main/java/com/example/casbinspringbootdemo/casbin/JCasbinAuthzFilter.java), specifically the `doFilter()` and `authorized()` methods.
        * finally, you could import the [insomnia_export.yaml](insomnia_export.yaml) into the [Insomnia Rest Client](https://insomnia.rest/download/core/?) and have the requests pre-configured.