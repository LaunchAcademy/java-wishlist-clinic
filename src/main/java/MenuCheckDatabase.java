import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

public class MenuCheckDatabase {

  public static final String ADD_A_PRODUCT = "Add a Product";
  public static final String LIST_ALL_PRODUCTS = "Display all products";
  public static final String QUIT_TEXT = "Quit";

  public enum MenuOption {
    a(ADD_A_PRODUCT),
    b(LIST_ALL_PRODUCTS),
    c(QUIT_TEXT);

    private final String optionText;

    MenuOption(String optionText) {
      this.optionText = optionText;
    }

    public String toString() {
      return this.name() + ". " + this.optionText;
    }
  }

  @Override
  public String toString() {
    String output = "";
    for (MenuOption option : MenuOption.values()) {
      output += option.toString() + "\n";
    }
    return output;
  }

  public void promptUntilDone() {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.launchacademy.wishlist");
    EntityManager em = emf.createEntityManager();
    MenuOption input = null;
    Scanner scanner = new Scanner(System.in);
    do {
      System.out.println(this);
      System.out.print("> ");
      try {
        input = MenuOption.valueOf(scanner.nextLine());
      } catch (IllegalArgumentException error) {
        System.out.println("Please choose a valid option.");
      }

      if (input == MenuOption.a) {
        // these two lines will look exactly the same every time
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        boolean continueLoop = true;
        Set<ConstraintViolation<Product>> violations = new HashSet<>();

        do {
          System.out.println("What is the name of the product?");
          String productName = scanner.nextLine();
          TypedQuery<Product> query = em.createQuery("SELECT p FROM Product p WHERE name =: productName",Product.class);
          query.setParameter("productName",productName);
          List<Product> products = query.getResultList();
          if (products.size() > 0){
            System.out.println("Sorry that product already exists");
          }else {
            System.out.println("What is the price?");
            double productPrice = scanner.nextDouble();
            scanner.nextLine();

            System.out.println("What is the url?");
            String productUrl = scanner.nextLine();

            // create and persist the product
            Product newProduct = new Product();
            newProduct.setUrl(productUrl);
            newProduct.setPrice(productPrice);
            newProduct.setName(productName);


            // the things that will change in the below line:
            // 1. our type of object (here, Product)
            // 2. our variable with the new object we created (here, newProduct)
            violations = validator.validate(newProduct);

            // if there are any violations
            if(violations.size() > 0) {
              // show us the errors
              for(ConstraintViolation<Product> violation : violations) {
                System.out.println(violation.getPropertyPath() + ": " + violation.getMessage());
              }
            } else {
              // save the object
              em.getTransaction().begin();
              em.persist(newProduct);
              em.getTransaction().commit();
              continueLoop = false;
            }

            System.out.println(newProduct.getId());
          }
        } while (continueLoop);
      } else if (input == MenuOption.b) {
        TypedQuery<Product> query = em.createQuery("FROM Product ORDER BY LOWER(name)", Product.class);
        List<Product> products = query.getResultList();
        for (Product product : products) {
          System.out
              .println(product.getName() + ", " + product.getPrice() + ", " + product.getUrl());
        }
      }
    } while (input != MenuOption.c);
    em.close();
    emf.close();
    System.out.println("Thanks! Come back soon ya hear!");
  }
}
