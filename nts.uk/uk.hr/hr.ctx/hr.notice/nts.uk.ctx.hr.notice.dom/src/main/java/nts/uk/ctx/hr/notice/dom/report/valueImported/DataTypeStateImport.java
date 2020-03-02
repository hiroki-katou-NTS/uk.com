package nts.uk.ctx.hr.notice.dom.report.valueImported;

import java.math.BigDecimal;

import lombok.Getter;

@Getter
public class DataTypeStateImport {

	protected int dataTypeValue;

	public static DataTypeStateImport createTimeItemDto(long max, long min) {
		return TimeItemImport.createFromJavaType(max, min);
	}

	public static DataTypeStateImport createStringItemDto(int stringItemLength, int stringItemType,
			int stringItemDataType) {
		return StringItemImport.createFromJavaType(stringItemLength, stringItemType, stringItemDataType);
	}

	public static DataTypeStateImport createTimePointItemDto(int timePointItemMin, int timePointItemMax) {
		return TimePointItemImport.createFromJavaType(timePointItemMin, timePointItemMax);
	}

	public static DataTypeStateImport createDateItemDto(int dateItemType) {
		return DateItemImport.createFromJavaType(dateItemType);
	}

	public static DataTypeStateImport createNumericItemDto(int numericItemMinus, int numericItemAmount, int integerPart,
			Integer decimalPart, BigDecimal numericItemMin, BigDecimal numericItemMax) {
		return NumericItemImport.createFromJavaType(numericItemMinus, numericItemAmount, integerPart, decimalPart,
				numericItemMin, numericItemMax);
	}
	
	// start code thêm 4 item kiểu mới 19.3
	
	public static DataTypeStateImport createReadOnly(String readText) {
		return ReadOnlyImport.createFromJavaType(readText);
	}
	
	public static DataTypeStateImport createReadOnlyButton(String readText) {
		return ReadOnlyButtonImport.createFromJavaType(readText);
	}
	
	public static DataTypeStateImport createRelatedCategory(String relateCtgCode) {
		return RelatedCategoryImport.createFromJavaType(relateCtgCode);
	}
	
	public static DataTypeStateImport createNumericButtonDto(int numericItemMinus, int numericItemAmount, int integerPart,
			int decimalPart, BigDecimal numericItemMin, BigDecimal numericItemMax) {
		return NumericButtonImport.createFromJavaType(numericItemMinus, numericItemAmount, integerPart, decimalPart,
				numericItemMin, numericItemMax);
	}
	//end

	
}
