# Authentication Server API Documentation
This document provides instructions on how to interact with the Authentication Server API endpoints.

#### Register User - To register a new user, send a POST request to /register endpoint with the following JSON payload:
``` 
curl --location --request POST 'https://authserver-mczz.onrender.com/register' \
--header 'Content-Type: application/json' \
--data-raw '{
    "username": "your_username",
    "password": "your_password",
    "email": "your_email@example.com"
}' 
```
> Request Body - 
    username (String, required): User's username (unique).
    password (String, required): User's password.
    email (String, required): User's email address (unique and validated).
> 
> Responses - 
    200 OK: User registered successfully.
    400 Bad Request: Invalid request format or user already exists.

#### Get Authentication Token - To obtain an authentication token after registration, send a POST request to /get-token endpoint with the following JSON payload:

```
curl --location --request POST 'https://authserver-mczz.onrender.com/get-token' \
--header 'Content-Type: application/json' \
--data-raw '{
    "username": "your_username",
    "password": "your_password"
}'
```
> Request Body - 
        username (String, required): User's username.
        password (String, required): User's password.
> 
> Responses - 
        200 OK: Authentication successful, returns a JWT token.
        400 Bad Request: Invalid username or password.

#### Authenticate Using Token -To authenticate using the obtained token, send a GET request to /authenticate endpoint with the Authorization header containing the token:
```
curl --location --request GET 'https://authserver-mczz.onrender.com/authenticate' \
--header 'Authorization: Bearer your_token'
```

> Request Headers - 
    Authorization (String, required): Bearer token obtained from /get-token.
> 
> Responses - 
    200 OK: Token is valid.
    401 Unauthorized: Token is invalid or expired.
> 
> Custom Error Responses - 
    400 Bad Request: Invalid request format or existing user.
    401 Unauthorized: Invalid or expired token.

### Models
###### SignupRequest
```
public class SignupRequest {
    private String username;
    private String password;
    private String email;
    private List<Authority> authorities;
}
```

###### LoginRequest
```
public class LoginRequest {
    private String username;
    private String password;
}
```
###### ResponseToken
```
public class ResponseToken {
    private String accessToken;
    private String tokenType;
    private String expiresAt;
}
```
###### ValidInvalidTokenResponse
```
public class ValidInvalidTokenResponse {
    private String message;
    private String user;
    private boolean isAuthenticated;
}
```
