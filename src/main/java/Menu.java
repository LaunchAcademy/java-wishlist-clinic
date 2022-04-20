
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

public class Menu {

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
      System.out.println(this.toString());
      System.out.print("> ");
      try {
        input = MenuOption.valueOf(scanner.nextLine());
      } catch (IllegalArgumentException error) {
        System.out.println("Please choose a valid option.");
      }

      if (input == MenuOption.a) {
        ValidatorFactory vf = Validation.buildDefaultValidatorFactory();
        Validator validator = vf.getValidator();

        Set<ConstraintViolation<Product>> violations;
        do {
          System.out.println("What is the name of the product?");
          String productName = scanner.nextLine();

          System.out.println("What is the price?");
          double productPrice = scanner.nextDouble();
          scanner.nextLine();

          System.out.println("What is the url?");
          String productUrl = scanner.nextLine();

          // create and persist the product
          Product newProduct = new Product();

          newProduct.setName(productName);
          newProduct.setPrice(productPrice);
          newProduct.setUrl(productUrl);

          violations = validator.validate(newProduct);
          if (violations.size() > 0) {
            System.out.println("\nUh oh...");
            for (ConstraintViolation<Product> violation : violations) {
              System.out.println(violation.getPropertyPath() + ": " + violation.getMessage());
            }
          } else {
            em.getTransaction().begin();
            em.persist(newProduct);
            em.getTransaction().commit();

          }
        } while (violations.size() > 0);

      } else if (input == MenuOption.b) {
        // retrieve a list of all products
        TypedQuery<Product> query = em.createQuery("SELECT p FROM Product p ORDER BY name",
            Product.class);
        List<Product> products = query.getResultList();
        System.out.println("\nAll Products:");
        for (Product product : products) {
          System.out.println(
              product.getName() + ", " + String.format("$%.2f", product.getPrice()) + ", "
                  + product.getUrl());
        }
      }
    } while (input != MenuOption.c);
    em.close();
    emf.close();
    System.out.println("Thanks! Come back soon ya hear!");
  }
}
