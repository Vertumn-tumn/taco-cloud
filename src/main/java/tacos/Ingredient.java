package tacos;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.rest.core.annotation.RestResource;

@Data
@Entity
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Ingredient {
    @Id
    @RestResource
    private String id;
    private String name;
    private Type type;

    public enum Type {
        WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
    }
}
