package nts.uk.ctx.pereg.app.find.person.info.item;

import java.math.BigDecimal;

import lombok.Getter;
import nts.uk.ctx.pereg.dom.person.info.dateitem.DateItem;
import nts.uk.ctx.pereg.dom.person.info.numericitem.NumericItem;
import nts.uk.ctx.pereg.dom.person.info.selectionitem.NumericButton;
import nts.uk.ctx.pereg.dom.person.info.selectionitem.ReadOnly;
import nts.uk.ctx.pereg.dom.person.info.selectionitem.ReadOnlyButton;
import nts.uk.ctx.pereg.dom.person.info.selectionitem.ReferenceTypeState;
import nts.uk.ctx.pereg.dom.person.info.selectionitem.RelatedCategory;
import nts.uk.ctx.pereg.dom.person.info.selectionitem.SelectionButton;
import nts.uk.ctx.pereg.dom.person.info.selectionitem.SelectionItem;
import nts.uk.ctx.pereg.dom.person.info.selectionitem.SelectionRadio;
import nts.uk.ctx.pereg.dom.person.info.singleitem.DataTypeState;
import nts.uk.ctx.pereg.dom.person.info.singleitem.DataTypeValue;
import nts.uk.ctx.pereg.dom.person.info.stringitem.StringItem;
import nts.uk.ctx.pereg.dom.person.info.timeitem.TimeItem;
import nts.uk.ctx.pereg.dom.person.info.timepointitem.TimePointItem;

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
			Integer decimalPart, BigDecimal numericItemMin, BigDecimal numericItemMax) {
		return NumericItemDto.createFromJavaType(numericItemMinus, numericItemAmount, integerPart, decimalPart,
				numericItemMin, numericItemMax);
	}

	public static DataTypeStateDto createSelectionItemDto(ReferenceTypeState refTypeState) {
		return SelectionItemDto.createFromJavaType(refTypeState, DataTypeValue.SELECTION.value);

	}

	public static DataTypeStateDto createSelectionButtonDto(ReferenceTypeState refTypeState) {
		return SelectionButtonDto.createFromJavaType(refTypeState, DataTypeValue.SELECTION_BUTTON.value);

	}

	public static DataTypeStateDto createSelectionRadioDto(ReferenceTypeState refTypeState) {
		return SelectionRadioDto.createFromJavaType(refTypeState, DataTypeValue.SELECTION_RADIO.value);

	}
	
	// start code thêm 4 item kiểu mới 19.3
	
	public static DataTypeStateDto createReadOnly(String readText) {
		return ReadOnlyDto.createFromJavaType(readText);
	}
	
	public static DataTypeStateDto createReadOnlyButton(String readText) {
		return ReadOnlyButtonDto.createFromJavaType(readText);
	}
	
	public static DataTypeStateDto createRelatedCategory(String relateCtgCode) {
		return RelatedCategoryDto.createFromJavaType(relateCtgCode);
	}
	
	public static DataTypeStateDto createNumericButtonDto(int numericItemMinus, int numericItemAmount, int integerPart,
			int decimalPart, BigDecimal numericItemMin, BigDecimal numericItemMax) {
		return NumericButtonDto.createFromJavaType(numericItemMinus, numericItemAmount, integerPart, decimalPart,
				numericItemMin, numericItemMax);
	}
	//end
	
	public static DataTypeStateDto createDto(DataTypeState dataTypeState) {
		DataTypeValue dataTypeValue = dataTypeState.getDataTypeValue();
		switch (dataTypeValue) {
		case STRING:
			StringItem strItem = (StringItem) dataTypeState;
			return DataTypeStateDto.createStringItemDto(strItem.getStringItemLength().v(),
					strItem.getStringItemType().value, strItem.getStringItemDataType().value);
		case NUMERIC:
			NumericItem numItem = (NumericItem) dataTypeState;
			Integer decimalPart = numItem.getDecimalPart() == null? null: numItem.getDecimalPart().v();
			
			BigDecimal numericItemMin = numItem.getNumericItemMin() != null ? numItem.getNumericItemMin().v() : null;
			BigDecimal numericItemMax = numItem.getNumericItemMax() != null ? numItem.getNumericItemMax().v() : null;
			return DataTypeStateDto.createNumericItemDto(numItem.getNumericItemMinus().value,
					numItem.getNumericItemAmount().value, numItem.getIntegerPart().v(), decimalPart,
					numericItemMin, numericItemMax);
		case DATE:
			DateItem dItem = (DateItem) dataTypeState;
			return DataTypeStateDto.createDateItemDto(dItem.getDateItemType().value);
		case TIME:
			TimeItem tItem = (TimeItem) dataTypeState;
			return DataTypeStateDto.createTimeItemDto(tItem.getMax().v(), tItem.getMin().v());
		case TIMEPOINT:
			TimePointItem tPointItem = (TimePointItem) dataTypeState;
			return DataTypeStateDto.createTimePointItemDto(tPointItem.getTimePointItemMin().v(),
					tPointItem.getTimePointItemMax().v());
		case SELECTION:
			SelectionItem sItem = (SelectionItem) dataTypeState;
			return DataTypeStateDto.createSelectionItemDto(sItem.getReferenceTypeState());

		case SELECTION_RADIO:
			SelectionRadio rItem = (SelectionRadio) dataTypeState;
			return DataTypeStateDto.createSelectionRadioDto(rItem.getReferenceTypeState());

		case SELECTION_BUTTON:
			SelectionButton bItem = (SelectionButton) dataTypeState;
			return DataTypeStateDto.createSelectionButtonDto(bItem.getReferenceTypeState());

		case READONLY:
			ReadOnly rOnlyItem = (ReadOnly) dataTypeState;
			return DataTypeStateDto.createReadOnly(rOnlyItem.getReadText().v());

		case RELATE_CATEGORY:
			RelatedCategory reCtgDto = (RelatedCategory) dataTypeState;
			return DataTypeStateDto.createRelatedCategory(reCtgDto.getRelatedCtgCode().v());

		case NUMBERIC_BUTTON:
			NumericButton numberButton = (NumericButton) dataTypeState;
			BigDecimal numericButtonMin = numberButton.getNumericItemMin() != null ? numberButton.getNumericItemMin().v() : null;
			BigDecimal numericButtonMax = numberButton.getNumericItemMax() != null ? numberButton.getNumericItemMax().v() : null;
			return DataTypeStateDto.createNumericButtonDto(numberButton.getNumericItemMinus().value,
					numberButton.getNumericItemAmount().value, numberButton.getIntegerPart().v(), numberButton.getDecimalPart().v(),
					numericButtonMin, numericButtonMax);

		case READONLY_BUTTON:
			ReadOnlyButton rOnlyButton = (ReadOnlyButton) dataTypeState;
			return DataTypeStateDto.createReadOnlyButton(rOnlyButton.getReadText().v());
		default:
			return null;
		}
	}
	
}
