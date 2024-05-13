package tacos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.hibernate.validator.constraints.CreditCardNumber;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import com.datastax.oss.driver.api.core.uuid.Uuids;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Table("orders")
public class TacoOrder implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@PrimaryKey // I think, it is a partition key, because its first PrimaryKey
	private final UUID id = Uuids.timeBased();
	
	private Date placedAt = new Date();
	
	@NotBlank(message = "Delivery name is required")
	private String deliveryName; // nazwa przesyłki
	
	@NotBlank(message = "Street is required")
	private String deliveryStreet; // ulica
	
	@NotBlank(message = "City is required")
	private String deliveryCity; // miasto
	
	@NotBlank(message = "State is required")
	private String deliveryState; // status dostawy
	
	@NotBlank(message = "Zip code is required")
	private String deliveryZip; // kod pocztowy

	@CreditCardNumber(message = "Not a valid credit card number")
	private String ccNumber; // numer karty kredytowej
	
	@Pattern(regexp = "^(0[1-9]|1[0-2])(/)([2-9][0-9])$", message = "Must be formatted MM/YY")
	private String ccExpiration; // data ważności karty (kiedy wygasa)
	
	@Digits(integer=3, fraction=0, message = "Invalid CVV")
	private String ccCVV; // (CVC) czyli kod weryfikacyjny karty (3-4 cyfrowy kod znajdujacy sie na odwrocie karty)

	@Column("tacos")
	private List<TacoUDT> tacos = new ArrayList<>();

	public void addTaco(TacoUDT taco) {
		this.tacos.add(taco);
	}
}
