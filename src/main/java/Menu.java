
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.validation.Constraint;
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
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        System.out.println("What is the name of the product?");
        String productName = scanner.nextLine();

        System.out.println("What is the price?");
        double productPrice = scanner.nextDouble();
        scanner.nextLine();

        System.out.println("What is the url?");
        String productUrl = scanner.nextLine();

        System.out.println("What's the email");
        String productEmail = scanner.nextLine();

        Product newProduct = new Product();
        newProduct.setName(productName);
        newProduct.setPrice(productPrice);
        newProduct.setUrl(productUrl);
        newProduct.setEmail(productEmail);

        Set<ConstraintViolation<Product>> violationSet = validator.validate(newProduct);
        if (violationSet.size() >0){
          for (ConstraintViolation violation : violationSet){
            System.out.println(violation.getPropertyPath() + ": " + violation.getMessage());
          }
          input = MenuOption.a;
          System.out.println("Please enter valid info");
          scanner.nextLine();
        } else {
          em.getTransaction().begin();
          em.persist(newProduct);
          em.getTransaction().commit();
          System.out.println(newProduct.getId());
        }
        factory.close();
      } else if (input == MenuOption.b) {
        TypedQuery<Product> query = em.createQuery("select p from Product p order by name", Product.class);
        List<Product> products = query.getResultList();
        for (Product product: products){
          System.out.println(product.getName() + ", " + product.getPrice() + ", " + product.getUrl() + ", " + product.getEmail());
        }
      }
    } while (input != MenuOption.c);
    em.close();
    emf.close();
    System.out.println("Thanks! Come back soon ya hear!");
  }
}
