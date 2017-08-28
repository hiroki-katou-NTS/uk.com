package command.person.info.item;

import java.math.BigDecimal;

import lombok.Getter;

@Getter
public class SingleItemCommand {
	private int dataType;

	// StringItem property
	private int stringItemLength;
	private int stringItemType;
	private int stringItemDataType;

	// NumericItem property
	private int numericItemMinus;
	private int numericItemAmount;
	private int integerPart;
	private int decimalPart;
	private BigDecimal numericItemMin;
	private BigDecimal numericItemMax;

	// DateItem property
	private int dateItemType;

	// TimeItem property
	private long timeItemMax;
	private long timeItemMin;

	// TimePointItem property
	private int timePointItemMin;
	private int timePointItemMax;

	// SelectionItem property
	private int referenceType;
	private String referenceCode;
}
