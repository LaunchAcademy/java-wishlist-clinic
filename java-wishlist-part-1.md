You're going to create a wishlist of all of your favorite products on the internet.

## Getting Started

```no-highlight
et get java-wishlist-part-1
cd java-wishlist-part-1
createdb java_wishlist
idea .
```

Look over the user stories and draw out an ER diagram to help understand the table structures you'll need to create.
Create your tables as you need them in a `schema.sql` file.
Remember to import your schema any time you update it.

```bash
$ psql java_wishlist < [path]/schema.sql
```

## Core User Stories

```no-highlight
As a wish list owner
I need to know what properties my products have
So that I can work with them easily
```

Acceptance Criteria

- Create a Product entity with the following properties
  - id (long, not null, 50 character max length)
  - name (String, not null)
  - price (double, not null)
  - url (String)
- Ensure that your entity is a Bean

You have been provided with a `Menu` class. You will need to fill in the implementation details listed below. You can run your implementation by running the main method in `WishListRunner.java`

### Day One

#### Add a Product

```no-highlight
As a wish list maintainer
I want to add a product
So that I can keep track of the items I want
```

Acceptance Criteria:

- I have an option on a main menu to add a product to my wish list
- I want to persist the name, price, and URL of the product
- The item should be saved in the database

#### List Products in my Wish List

```no-highlight
As a wish list maintainer
I want to list all of the products in my wish list
So that I can remember all the items I added
```

Acceptance Criteria:

- I have an option on a main menu to list all of my products on my wish list
- Items added are ordered by name in ascending order
- I can see the name, price, and URL for each product

### Day Two

#### Categorize a Product

*It's recommended you read through the material on associations before tackling this user story.*

```no-highlight
As a wish list maintainer
I want to categorize my products
So that I can remember why I want them
```

Acceptance Criteria:

- When adding a product, I can optionally supply a category name
- If I supply a category name, the category is persisted to the database
- The category name should not be duplicated in the database
- When listing out my products, the category name is displayed along with each product

Implementation Details:

- Normalize the category in a separate table, and relate your products to those categories
- Ensure no duplicate category names are inserted into the category table

#### List Products Per Category

```no-highlight
As a wish list maintainer
I want to list products per category
So that I can know what items are in a specific category
```

Acceptance Criteria:

- I have an option on the main menu to "View Products In a Category"
- When I select that option, I'm presented with a numbered list of all categories
- When I select a number, I am presented with only the products associated with that category

#### Count of Products Per Category

```no-highlight
As a wish list maintainer
I want to know the number of products per category
So that I can see how well I've populated each category
```

Acceptance Criteria:

- When in the menu where I want to "View Products In a Category", for each category, the number of products is indicated alongside each category in parens after the category name

## Non-Core User Stories

#### Delete a Product

```no-highlight
As a wish list maintainer
I want to delete a product
So that I can remove it when I've purchased it
```

Acceptance Criteria:

- I have an option on the main menu to delete a product
- When I select the option, I'm given a numbered list of products to delete
- I can enter a number and the product correlating to that number is deleted from the database

#### Delete a Category

```no-highlight
As a wish list maintainer
I want to delete a category
So that I no longer associate products with that category
```

Acceptance Criteria:

- I have an option on the main menu to delete a category
- When I select the option, I'm given a numbered list of categories to delete
- I can enter a number and the category correlating to that number is deleted from the database
- All product relationships with that category are removed
