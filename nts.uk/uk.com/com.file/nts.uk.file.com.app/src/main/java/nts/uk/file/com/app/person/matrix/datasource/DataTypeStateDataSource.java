package nts.uk.file.com.app.person.matrix.datasource;

import java.math.BigDecimal;

import lombok.Getter;

@Getter
public class DataTypeStateDataSource {

	protected int dataTypeValue;
	// timeItem
	private long max;
	private long min;
	
	//String
	private int stringItemLength;
	private int stringItemType;
	private int stringItemDataType;

	//TimePoint
	private int timePointItemMin;
	private int timePointItemMax;
	
	//DateItem
	private int dateItemType;
	
	//NumericItem, NumericButton
	private int numericItemMinus;
	private int numericItemAmount;
	private	int integerPart;
	private Integer decimalPart;
	private BigDecimal numericItemMin;
	private BigDecimal numericItemMax;
	
	//SelectionItem, SelectionButton, SelectionRadio
	private String referenceType;
	private String masterType;
	private String typeCode;
	private String enumName;
	
	//ReadOnly, ReadOnlyButton, ReadOnlyButton
	private String readText;
	
	//RelatedCategory
	private String relatedCtgCode;
}
