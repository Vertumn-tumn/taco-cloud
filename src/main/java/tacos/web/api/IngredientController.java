package tacos.web.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import tacos.Ingredient;

@Slf4j
@RestController
@RequestMapping(path = "api/ingredients", produces = "application/json")
@CrossOrigin(origins = "*")
public class IngredientController {

    private RestTemplate restTemplate;

    public IngredientController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ingredient> getIngredientById(@PathVariable String id) {
        ResponseEntity<Ingredient> entity = new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        try {
            Ingredient ing = restTemplate.getForObject("http://localhost:8080/data-api/ingredients/{id}",
                    Ingredient.class, id);
            Ingredient ingredient = new Ingredient(id, ing.getName(), ing.getType());
            return new ResponseEntity<>(ingredient, HttpStatus.OK);
        } catch (HttpClientErrorException errorException) {
            if (errorException.getStatusCode().value() == HttpStatus.NOT_FOUND.value())
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return entity;
    }

    @PutMapping
    public void updateIngredient(@RequestBody Ingredient ingredient) {
        log.info("Let i tell you about ingredient: " + ingredient.toString());
        restTemplate.put("http://localhost:8080/data-api/ingredients/{id}",
                ingredient, ingredient.getId());
    }

    @DeleteMapping("/{id}")
    public void deleteIngredient(@PathVariable String id) {
        restTemplate.delete("http://localhost:8080/data-api/ingredients/{id}",
                id);
    }

    @PostMapping
    public Ingredient createIngredient(@RequestBody Ingredient ingredient) {
        return restTemplate.postForObject("http://localhost:8080/data-api/ingredients",
                ingredient, Ingredient.class);
    }
}

