package nts.uk.ctx.pereg.app.find.person.info.item;

import java.math.BigDecimal;

import lombok.Getter;
import nts.uk.ctx.pereg.dom.person.info.selectionitem.ReferenceTypeState;
import nts.uk.ctx.pereg.dom.person.info.singleitem.DataTypeValue;

@Getter
public class DataTypeStateDto {

	protected int dataTypeValue;

	public static DataTypeStateDto createTimeItemDto(long max, long min) {
		return TimeItemDto.createFromJavaType(max, min);
	}

	public static DataTypeStateDto createStringItemDto(int stringItemLength, int stringItemType,
			int stringItemDataType) {
		return StringItemDto.createFromJavaType(stringItemLength, stringItemType, stringItemDataType);
	}

	public static DataTypeStateDto createTimePointItemDto(int timePointItemMin, int timePointItemMax) {
		return TimePointItemDto.createFromJavaType(timePointItemMin, timePointItemMax);
	}

	public static DataTypeStateDto createDateItemDto(int dateItemType) {
		return DateItemDto.createFromJavaType(dateItemType);
	}

	public static DataTypeStateDto createNumericItemDto(int numericItemMinus, int numericItemAmount, int integerPart,
			int decimalPart, BigDecimal numericItemMin, BigDecimal numericItemMax) {
		return NumericItemDto.createFromJavaType(numericItemMinus, numericItemAmount, integerPart, decimalPart,
				numericItemMin, numericItemMax);
	}

	public static DataTypeStateDto createSelectionItemDto(ReferenceTypeState refTypeState) {
		return SelectionItemDto.createFromJavaType(refTypeState, DataTypeValue.SELECTION.value);

	}

	public static DataTypeStateDto createSelectionButtonDto() {
		return SelectionButtonDto.createFromJavaType();

	}

	public static DataTypeStateDto createSelectionRadioDto(ReferenceTypeState refTypeState) {
		return SelectionRadioDto.createFromJavaType(refTypeState, DataTypeValue.SELECTION_RADIO.value);

	}
}
