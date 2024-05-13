package tacos.data;

import java.util.Optional;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import tacos.Ingredient;
import tacos.IngredientUDT;

@Component
public class StringToIngredientConverter {
	private IngredientRepository ingredientRepository;
	
	public StringToIngredientConverter(IngredientRepository ingredientRepository) {
		this.ingredientRepository = ingredientRepository;
	}
	
	
	public IngredientUDT convert(String id) {
		Optional<Ingredient> ingredient = ingredientRepository.findById(id);
		if(ingredient.isEmpty()) {
			return null;
		}
		
		return ingredient.map(i -> {
			return new IngredientUDT(i.getName(), i.getType());
		}).get();
	}
}
