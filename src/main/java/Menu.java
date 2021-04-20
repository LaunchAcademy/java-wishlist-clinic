
import java.util.List;
import java.util.Scanner;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

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
        System.out.println("What is the name of the product?");
        String productName = scanner.nextLine();

        System.out.println("What is the price?");
        double productPrice = scanner.nextDouble();
        scanner.nextLine();

        System.out.println("What is the url?");
        String productUrl = scanner.nextLine();

      // create and persist the product
        System.out.println(newProduct.getId());
      } else if (input == MenuOption.b) {
      // retrieve a list of all products
      }
    } while (input != MenuOption.c);
    em.close();
    emf.close();
    System.out.println("Thanks! Come back soon ya hear!");
  }
}
