# Security concepts

## UserDetails Service

Used to load user from the database 
has a method loadUserByUsername() which returns a UserDetailsObject which contains information about the particular user


## AuthenticationManager

Used to authenticate. Has an authenticate() method which takes an Authentication object and returns and authentication object upon successful authentication

## Securityfilterchain

## JWT 

composed of 3 parts: header, payload, signature

HEader contains the signing algorithm e.g. RSA
and type of token e.g. Bearer

Payload: contains information about the user who is authenticated e.g. subject, issu

Signature - created using the header and payload

Can rely on the information in JWTs being trustworthy because they are digitally signed, in this case using a public/private RSA key pair


## Authenticationprovider

used to authenticate provided user information against those persisted e.g. in a database

# Rsakeyproperties

# Daoauthenticationmanager

uses a DAO to get user information from a database
uses UserDetailsSevice to look up the username, password and granted authority

public and private keys

# Passwordencoder 

 used to hash the password

# Jwtencoder 

encodes the JWTs

# Jwtdecoder 

used to deode the JWTs



# Jwtclaimsset 

contains the information that the JWTs hold themselves

# RSA Key encryption
Sender encrypts the data they are sending using the algorithm
REceiver receives the data and decrypts it

public key = used for encryption

private key = used for decryption

If the sender wants to send a message, they need to get hold of the receiver's public key to encrypt the data before sending
Receiver receives the message, and decrypts the data using their private key



# Authorities

# Roles

# GrantedAuthority