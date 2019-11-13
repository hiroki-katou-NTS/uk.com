package nts.uk.ctx.hr.notice.app.find.report;

import java.math.BigDecimal;

import lombok.Getter;

@Getter
public class DataTypeStateReportDto {
	protected int dataTypeValue;
	public static DataTypeStateReportDto createTimeItemDto(long max, long min) {
		return TimeItemReportDto.createFromJavaType(max, min);
	}

	public static DataTypeStateReportDto createStringItemDto(int stringItemLength, int stringItemType,
			int stringItemDataType) {
		return StringItemReportDto.createFromJavaType(stringItemLength, stringItemType, stringItemDataType);
	}

	public static DataTypeStateReportDto createTimePointItemDto(int timePointItemMin, int timePointItemMax) {
		return TimePointItemReportDto.createFromJavaType(timePointItemMin, timePointItemMax);
	}

	public static DataTypeStateReportDto createDateItemDto(int dateItemType) {
		return DateItemReportDto.createFromJavaType(dateItemType);
	}

	public static DataTypeStateReportDto createNumericItemDto(int numericItemMinus, int numericItemAmount, int integerPart,
			Integer decimalPart, BigDecimal numericItemMin, BigDecimal numericItemMax) {
		return NumericItemReportDto.createFromJavaType(numericItemMinus, numericItemAmount, integerPart, decimalPart,
				numericItemMin, numericItemMax);
	}

//	public static DataTypeStateReportDto createSelectionItemDto(ReferenceTypeStateReport refTypeState) {
//		return SelectionItemReportDto.createFromJavaType(refTypeState, DataTypeValueReport.SELECTION.value);
//
//	}
//
//	public static DataTypeStateReportDto createSelectionButtonDto(ReferenceTypeState refTypeState) {
//		return SelectionButtonReportDto.createFromJavaType(refTypeState, DataTypeValueReport.SELECTION_BUTTON.value);
//
//	}
//
//	public static DataTypeStateReportDto createSelectionRadioDto(ReferenceTypeState refTypeState) {
//		return SelectionRadioReportDto.createFromJavaType(refTypeState, DataTypeValueReport.SELECTION_RADIO.value);
//
//	}
	
	// start code thêm 4 item kiểu mới 19.3
	
	public static DataTypeStateReportDto createReadOnly(String readText) {
		return ReadOnlyReportDto.createFromJavaType(readText);
	}
	
	public static DataTypeStateReportDto createReadOnlyButton(String readText) {
		return ReadOnlyButtonReportDto.createFromJavaType(readText);
	}
	
	public static DataTypeStateReportDto createRelatedCategory(String relateCtgCode) {
		return RelatedCategoryReportDto.createFromJavaType(relateCtgCode);
	}
	
	public static DataTypeStateReportDto createNumericButtonDto(int numericItemMinus, int numericItemAmount, int integerPart,
			int decimalPart, BigDecimal numericItemMin, BigDecimal numericItemMax) {
		return NumericButtonReportDto.createFromJavaType(numericItemMinus, numericItemAmount, integerPart, decimalPart,
				numericItemMin, numericItemMax);
	}
	//end
}
