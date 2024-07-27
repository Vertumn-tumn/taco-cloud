package sia.tacocloud.data;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;
import sia.tacocloud.Ingredient;

import java.util.Optional;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {
}
