package tacos.web;

//import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import tacos.TacoOrder;
import tacos.data.IngredientRepository;
//import tacos.data.JdbcOrderRepository;
import tacos.data.OrderRepository;

@RequestMapping("/orders")
@Controller
@Slf4j
@SessionAttributes("tacoOrder")
public class OrderController {
	
	//private JdbcOrderRepository jdbcOrderRepo; // tworzymy obiekt na bazie interfejsu
	// który skorzysta z zaimplementowanych metod tego interfejsu 
	// czerpiąc je z kodu klasy implementującej. (Kontener Spring'a zarządza)
	
	private IngredientRepository ingredientRepository;
	private OrderRepository orderRepository;
	
	public OrderController(IngredientRepository ingredientRepository,
			OrderRepository orderRepository) {
		this.ingredientRepository = ingredientRepository;
		this.orderRepository = orderRepository;
	}
	
	@GetMapping("/current")
	public String orderForm() {
		return "orderForm";
	}
	
	@PostMapping
	public String processOrder(@Valid TacoOrder order,
			Errors errors, SessionStatus sessionStatus){
		if(errors.hasErrors()) {
			return "orderForm";
		}
		orderRepository.save(order);
//		log.info("Order submitted: {}", order);
		sessionStatus.setComplete(); // kończymy sesję
		
		return "redirect:/";
	}
}
