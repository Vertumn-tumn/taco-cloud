package tacos;

import org.h2.tools.Server;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import tacos.Ingredient.Type;
import tacos.data.IngredientRepository;
import tacos.data.OrderRepository;
import tacos.data.TacoRepository;
import tacos.data.UserRepository;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;

@SpringBootApplication
public class TacoCloudApplication {

    public static void main(String[] args) {
        SpringApplication.run(TacoCloudApplication.class, args);
    }

    @Bean
    public CommandLineRunner dataLoader(IngredientRepository repo,
                                        UserRepository userRepo,
                                        OrderRepository orderRepo,
                                        TacoRepository tacoRepo,
                                        PasswordEncoder encoder) {
        return args -> {
            repo.deleteAll(); // TODO: Quick hack to avoid tests from stepping on each other with constraint violations
            Ingredient flourTortilla = new Ingredient("FLTO", "Flour Tortilla", Type.WRAP);
            repo.save(flourTortilla);
            Ingredient cornTortilla = new Ingredient("COTO", "Corn Tortilla", Type.WRAP);
            repo.save(cornTortilla);
            Ingredient groundBeef = new Ingredient("GRBF", "Ground Beef", Type.PROTEIN);
            repo.save(groundBeef);
            Ingredient carnitas = new Ingredient("CARN", "Carnitas", Type.PROTEIN);
            repo.save(carnitas);
            Ingredient dicedTomatoes = new Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES);
            repo.save(dicedTomatoes);
            Ingredient lettuce = new Ingredient("LETC", "Lettuce", Type.VEGGIES);
            repo.save(lettuce);
            Ingredient cheddar = new Ingredient("CHED", "Cheddar", Type.CHEESE);
            repo.save(cheddar);
            Ingredient monterreyJack = new Ingredient("JACK", "Monterrey Jack", Type.CHEESE);
            repo.save(monterreyJack);
            Ingredient salsa = new Ingredient("SLSA", "Salsa", Type.SAUCE);
            repo.save(salsa);
            Ingredient sourCream = new Ingredient("SRCR", "Sour Cream", Type.SAUCE);
            repo.save(sourCream);

            Taco taco1 = new Taco();
            taco1.setName("recept1");
            taco1.setIngredients(Arrays.asList(cornTortilla, flourTortilla, salsa));
            //tacoRepo.save(taco1);

            Taco taco2 = new Taco();
            taco2.setName("recept2");
            taco2.setIngredients(Arrays.asList(cheddar, sourCream, salsa));
            //tacoRepo.save(taco2);

            Taco taco3 = new Taco();
            taco3.setName("recept3");
            taco3.setIngredients(Arrays.asList(carnitas, sourCream, dicedTomatoes));
            //tacoRepo.save(taco3);

            User user1 = new User(
                    "John",
                    encoder.encode("123"),
                    "Johnson",
                    "don",
                    "don",
                    "UFC",
                    "123",
                    "89108271343");
            userRepo.save(user1);

            TacoOrder order1 = new TacoOrder();
            order1.setPlacedAt(new Date());
            order1.setDeliveryName("don");
            order1.setDeliveryCity("don");
            order1.setDeliveryStreet("don");
            order1.setDeliveryState("UFC");
            order1.setDeliveryZip("123");
            order1.setCcNumber("4000001234567899");
            order1.setCcCVV("123");
            order1.setCcExpiration("09/22");
            order1.setTacos(Arrays.asList(taco1, taco2, taco3));
            order1.setUser(userRepo.findByUsername("John"));
            orderRepo.save(order1);
        };
    }

    @Bean(initMethod = "start", destroyMethod = "stop")
    public Server inMemoryH2DatabaseaServer() throws SQLException {
        return Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "9090");
    }
}
