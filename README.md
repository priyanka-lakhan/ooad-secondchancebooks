# SecondChanceBooks.com

SecondChanceBooks.com is a collaborative platform where users can buy, sell, and rent books. The project is inspired by the most common problem faced by book readers -- "Not enough shelf-space".

# Introduction
SecondChanceBooks.com is inspired by a common problem faced by book lovers.
"The urge to buy books even though you still have too many books to read at home." Rie VdWarth. This is the first problem mentioned on various blogs (https://storiesunfolded.com/2014/03/12/problems-that-book-readers-have/, http://www.thestorypedia.com/entertainment/problems-faced-by-book-lovers/, https://www.goodreads.com/blog/show/845-20-problems-only-book-lovers-understand, and many more).
SecondChanceBooks.com will be a one-stop book sharing application that will allow users to list their unused books to be shared with an option to purchase/rent by the ones who are in need. This platform will allow the registered users to purchase/rent a book at lower rates as compared to buying a new book. If someone needs a book for a fixed duration, they can rent available books as well.
For a voracious reader, it’s always a big task to replace old titles with new ones in their collection. SecondChanceBooks.com will make their life easier by providing a single platform to sell and buy books.

# Modules:
- Registration
- Authentication on every access using JWT Token Authentication
- Login using username and password
- Forget password
- Update user profile
- Add books with informative fields, description
- Search and filter Books by Genre and Author
- Add book-listing for sell/rent
- Search most recent book listings
- Notifications if buyer is interested in your book(s)
- View/update existing book listings


## Tech-Stack
- Java Spring Boot
- MySQL
- HTML
- CSS
- jQuery
- Docker

## How to Run Code
Pre-requisites

- Java
- JAVA_HOME path
- Docker

## To run without Docker
Execute the following command in project root directory
`./gradlew clean run`

## To run with Docker
Execute the following command from project root directory
`build_and_docker.sh`
This command builds the code, builds docker image for server and MySQL and docker-compose the application

## Swagger Documentation
(http://localhost:8080/swagger-ui.html)

## Accessing UI
(http://localhost:8080/assets/pages/default.html)