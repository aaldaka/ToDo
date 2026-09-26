# Todo App

Spring Boot Homework

**GitHub repo:** https://github.com/aaldaka/ToDo.git

## Configs

1. It runs on port 2000 with the `dev` profile active
2. Base URL: `http://localhost:2000/todo`
## Design decisions

**Spring Profiles for dev config**
I split the datasource settings, server port, and hibernate config into `application-dev.properties` instead of leaving everything in the main `application.properties`. That way the base file just says which profile is active, and all the actual dev-specific stuff (db credentials, port, etc) lives separately. Makes it easier to add a different profile later (like a prod one) without touching the main config.

**`/todo` as the base path instead of `/api`**
I went with `/todo` since it made more sense for this specific app rather than a generic `/api` prefix.

**Custom exceptions instead of generic errors**
Made `InformationNotFoundException` (404) and `InformationExistException` (409) so the API gives actual meaningful errors.
s.
**Category → Item relationship, and the cascade mess**
Items belong to categories through a `@ManyToOne`/`@OneToMany` mapping. I looked into `orphanRemoval` and `cascade = CascadeType.ALL` on the category side because I wanted deleting a category to automatically delete all its items too, which is what `orphanRemoval` is supposed to help with.

Adding `cascade = CascadeType.ALL` on top of `orphanRemoval` ended up clashing with the item delete endpoint though - deleting a single item directly through its own repository would just do nothing to the database, even though the response looked fine and no errors showed up anywhere. Once I removed `cascade = CascadeType.ALL` and kept just `orphanRemoval = true`, the item delete worked properly again (should probably just follow Mr Saad's instructions).

## What went right

Category CRUD came together pretty smoothly once I got the pattern down - once I had `createCategory`, `getCategories`, `updateCategory`, and `deleteCategory` working right, building out the same thing for Item was a lot faster since it followed the same structure, just scoped under a category id.

## Challenges faced

- Typo'd the repository package name (`repositry` instead of `repository`) which caused a confusing "main method not found" error when I tried running the wrong file directly.
- Mistakenly put `@RequestBody`/`@RequestParam` on service layer method parameters instead of just the controller, getting used to which part interacts with who in the first 2 days was annoying.
- The cascade/orphanRemoval issue mentioned above - probably the most confusing bug of this whole lab since there was no error message anywhere, the delete request just returned 200 with the right data but never actually removed anything from the database.
## Favorite part
 
Building and linking everything together, starting with the Model, then the repo and controller and lastly the service.
I like the clean separation of concerns and adding functionality as layers rather than mashing everything together in one or two files.
