package find.person.info.item;

import java.math.BigDecimal;

import lombok.Value;

@Value
public class NumericItemDto {
	private int dataTypeValue;
	private int numericItemMinus;
	private int numericItemAmount;
	private int integerPart;
	private int decimalPart;
	private BigDecimal NumericItemMin;
	private BigDecimal NumericItemMax;
}
