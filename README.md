# Casbin Spring Boot Demo

A simple Spring Boot application demonstrating the usage 
of casbin. Specifically addressing how we would leverage
an external authorization source (like a JWT) in order to
create dynamic authorization policies. In other words, we
want to manage identity claims centrally, like in Okta, 
and then we want to use those claims to build a policy 
on-the-fly that we can then verify the request against.

Note, I do not intend to actually integrate with an authorization
provider. Instead, I will simply mock some claims into an
Authentication and make it available from the SecurityContext
and thereby leverage all the spring Authentication mechanisms.
