package nts.uk.ctx.pereg.pub.person.info.item;

import java.math.BigDecimal;

import lombok.Getter;

@Getter
public class DataTypeStateExport {

	protected int dataTypeValue;

	public static DataTypeStateExport createTimeItemDto(long max, long min) {
		return TimeItemExport.createFromJavaType(max, min);
	}

	public static DataTypeStateExport createStringItemDto(int stringItemLength, int stringItemType,
			int stringItemDataType) {
		return StringItemExport.createFromJavaType(stringItemLength, stringItemType, stringItemDataType);
	}

	public static DataTypeStateExport createTimePointItemDto(int timePointItemMin, int timePointItemMax) {
		return TimePointItemExport.createFromJavaType(timePointItemMin, timePointItemMax);
	}

	public static DataTypeStateExport createDateItemDto(int dateItemType) {
		return DateItemExport.createFromJavaType(dateItemType);
	}

	public static DataTypeStateExport createNumericItemDto(int numericItemMinus, int numericItemAmount, int integerPart,
			Integer decimalPart, BigDecimal numericItemMin, BigDecimal numericItemMax) {
		return NumericItemExport.createFromJavaType(numericItemMinus, numericItemAmount, integerPart, decimalPart,
				numericItemMin, numericItemMax);
	}
	
	// start code thêm 4 item kiểu mới 19.3
	
	public static DataTypeStateExport createReadOnly(String readText) {
		return ReadOnlyExport.createFromJavaType(readText);
	}
	
	public static DataTypeStateExport createReadOnlyButton(String readText) {
		return ReadOnlyButtonExport.createFromJavaType(readText);
	}
	
	public static DataTypeStateExport createRelatedCategory(String relateCtgCode) {
		return RelatedCategoryExport.createFromJavaType(relateCtgCode);
	}
	
	public static DataTypeStateExport createNumericButtonDto(int numericItemMinus, int numericItemAmount, int integerPart,
			int decimalPart, BigDecimal numericItemMin, BigDecimal numericItemMax) {
		return NumericButtonExport.createFromJavaType(numericItemMinus, numericItemAmount, integerPart, decimalPart,
				numericItemMin, numericItemMax);
	}
	//end

	
}
