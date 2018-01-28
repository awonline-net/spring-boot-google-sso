Spring Boot Security with Google Sign-In and custom authorities provider

To run this example you need an Google Account. After creating one register your application in Google Developers Console https://console.developers.google.com. 
The redirect URI for this sample application has to be set to: http://localhost:8080/login

After registering your application replace the assigned ClientId and ClientSecret in application properties (application.yml)

The sample application provides two endpoints:
- public accessible: http://localhost:8080/public
- secured: http://localhost:8080/secured

To access the secured endpoint you have to login with your Google Account. 

You can extend the CustomSocialAuthoritiesExtractor class (inner class in SecurityConfig) if you want to provide custom authorities for the authenticated user.
To get the user detials follow the code in MessageController's securedMethod(...).

For production usage:
- use SSL for your endpoints
- disable logging (in application.yml)
