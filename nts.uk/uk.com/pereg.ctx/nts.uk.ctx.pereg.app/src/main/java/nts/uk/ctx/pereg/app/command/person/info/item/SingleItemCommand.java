package nts.uk.ctx.pereg.app.command.person.info.item;

import java.math.BigDecimal;

import lombok.Value;

@Value
public class SingleItemCommand {
	private Integer dataType;
	// StringItem property
	private Integer stringItemLength;
	private Integer stringItemType;
	private Integer stringItemDataType;

	// NumericItem property
	private Integer numericItemMinus;
	private Integer numericItemAmount;
	private Integer integerPart;
	private Integer decimalPart;
	private BigDecimal numericItemMin;
	private BigDecimal numericItemMax;

	// DateItem property
	private Integer dateItemType;

	// TimeItem property
	private Integer timeItemMin;
	private Integer timeItemMax;

	// TimePointItem property
	private Integer timePointItemMin;
	private Integer timePointItemMax;

	// SelectionItem property
	private Integer referenceType;
	// selectionItemId
	private String referenceCode; 
	private String selectionItemId;
}
