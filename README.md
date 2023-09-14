# Blog Engine powered by Spring Boot

## Introduction
This project is a simple REST application for serving a blog. It uses Spring Boot, Spring Data JPA and some other helper libraries.

- `POST` at `/posts/{id}/comments` which should:
  - Save a new comment with current date and time for post with passed `{id}`
  - Return `201 Created` if comment is created successfully for post with passed `{id}`
  - Return `404 Not Found` if post with passed `{id}` does not exist
    

- `GET` at `/posts/{id}/comments` which should:
  - Return all comments sorted by creation date in descending order for a post with passed `{id}`
  - Return empty list if a post with passed `{id}` does not exists or when it does not contain any comments
