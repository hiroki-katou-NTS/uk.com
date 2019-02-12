package nts.uk.file.com.app.person.matrix.datasource;

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
public class DataTypeStateDataSource {

	protected int dataTypeValue;

	public static DataTypeStateDataSource createTimeItemDataSource(long max, long min) {
		return TimeItemDataSource.createFromJavaType(max, min);
	}

	public static DataTypeStateDataSource createStringItemDataSource(int stringItemLength, int stringItemType,
			int stringItemDataType) {
		return StringItemDataSource.createFromJavaType(stringItemLength, stringItemType, stringItemDataType);
	}

	public static DataTypeStateDataSource createTimePointItemDataSource(int timePointItemMin, int timePointItemMax) {
		return TimePointItemDataSource.createFromJavaType(timePointItemMin, timePointItemMax);
	}

	public static DataTypeStateDataSource createDateItemDataSource(int dateItemType) {
		return DateItemDataSource.createFromJavaType(dateItemType);
	}

	public static DataTypeStateDataSource createNumericItemDataSource(int numericItemMinus, int numericItemAmount,
			int integerPart, Integer decimalPart, BigDecimal numericItemMin, BigDecimal numericItemMax) {
		return NumericItemDataSource.createFromJavaType(numericItemMinus, numericItemAmount, integerPart, decimalPart,
				numericItemMin, numericItemMax);
	}

	public static DataTypeStateDataSource createSelectionItemDataSource(ReferenceTypeState refTypeState) {
		return SelectionItemDataSource.createFromJavaType(refTypeState, DataTypeValue.SELECTION.value);

	}

	public static DataTypeStateDataSource createSelectionButtonDataSource(ReferenceTypeState refTypeState) {
		return SelectionButtonDataSource.createFromJavaType(refTypeState, DataTypeValue.SELECTION_BUTTON.value);

	}

	public static DataTypeStateDataSource createSelectionRadioDataSource(ReferenceTypeState refTypeState) {
		return SelectionRadioDataSource.createFromJavaType(refTypeState, DataTypeValue.SELECTION_RADIO.value);

	}
	
	public static DataTypeStateDataSource createReadOnly(String readText) {
		return ReadOnlyDataSource.createFromJavaType(readText);
	}

	public static DataTypeStateDataSource createReadOnlyButton(String readText) {
		return ReadOnlyButtonDataSource.createFromJavaType(readText);
	}

	public static DataTypeStateDataSource createRelatedCategory(String relateCtgCode) {
		return RelatedCategoryDataSource.createFromJavaType(relateCtgCode);
	}

	public static DataTypeStateDataSource createNumericButtonDataSource(int numericItemMinus, int numericItemAmount,
			int integerPart, int decimalPart, BigDecimal numericItemMin, BigDecimal numericItemMax) {
		return NumericButtonDataSource.createFromJavaType(numericItemMinus, numericItemAmount, integerPart, decimalPart,
				numericItemMin, numericItemMax);
	}

	public static DataTypeStateDataSource createDataSource(DataTypeState dataTypeState) {
		DataTypeValue dataTypeValue = dataTypeState.getDataTypeValue();
		switch (dataTypeValue) {
		case STRING:
			StringItem strItem = (StringItem) dataTypeState;
			return DataTypeStateDataSource.createStringItemDataSource(strItem.getStringItemLength().v(),
					strItem.getStringItemType().value, strItem.getStringItemDataType().value);
		case NUMERIC:
			NumericItem numItem = (NumericItem) dataTypeState;
			Integer decimalPart = numItem.getDecimalPart() == null ? null : numItem.getDecimalPart().v();

			BigDecimal numericItemMin = numItem.getNumericItemMin() != null ? numItem.getNumericItemMin().v() : null;
			BigDecimal numericItemMax = numItem.getNumericItemMax() != null ? numItem.getNumericItemMax().v() : null;
			return DataTypeStateDataSource.createNumericItemDataSource(numItem.getNumericItemMinus().value,
					numItem.getNumericItemAmount().value, numItem.getIntegerPart().v(), decimalPart, numericItemMin,
					numericItemMax);
		case DATE:
			DateItem dItem = (DateItem) dataTypeState;
			return DataTypeStateDataSource.createDateItemDataSource(dItem.getDateItemType().value);
		case TIME:
			TimeItem tItem = (TimeItem) dataTypeState;
			return DataTypeStateDataSource.createTimeItemDataSource(tItem.getMax().v(), tItem.getMin().v());
		case TIMEPOINT:
			TimePointItem tPointItem = (TimePointItem) dataTypeState;
			return DataTypeStateDataSource.createTimePointItemDataSource(tPointItem.getTimePointItemMin().v(),
					tPointItem.getTimePointItemMax().v());
		case SELECTION:
			SelectionItem sItem = (SelectionItem) dataTypeState;
			return DataTypeStateDataSource.createSelectionItemDataSource(sItem.getReferenceTypeState());

		case SELECTION_RADIO:
			SelectionRadio rItem = (SelectionRadio) dataTypeState;
			return DataTypeStateDataSource.createSelectionRadioDataSource(rItem.getReferenceTypeState());

		case SELECTION_BUTTON:
			SelectionButton bItem = (SelectionButton) dataTypeState;
			return DataTypeStateDataSource.createSelectionButtonDataSource(bItem.getReferenceTypeState());

		case READONLY:
			ReadOnly rOnlyItem = (ReadOnly) dataTypeState;
			return DataTypeStateDataSource.createReadOnly(rOnlyItem.getReadText().v());

		case RELATE_CATEGORY:
			RelatedCategory reCtgDataSource = (RelatedCategory) dataTypeState;
			return DataTypeStateDataSource.createRelatedCategory(reCtgDataSource.getRelatedCtgCode().v());

		case NUMBERIC_BUTTON:
			NumericButton numberButton = (NumericButton) dataTypeState;
			BigDecimal numericButtonMin = numberButton.getNumericItemMin() != null
					? numberButton.getNumericItemMin().v()
					: null;
			BigDecimal numericButtonMax = numberButton.getNumericItemMax() != null
					? numberButton.getNumericItemMax().v()
					: null;
			return DataTypeStateDataSource.createNumericButtonDataSource(numberButton.getNumericItemMinus().value,
					numberButton.getNumericItemAmount().value, numberButton.getIntegerPart().v(),
					numberButton.getDecimalPart().v(), numericButtonMin, numericButtonMax);

		case READONLY_BUTTON:
			ReadOnlyButton rOnlyButton = (ReadOnlyButton) dataTypeState;
			return DataTypeStateDataSource.createReadOnlyButton(rOnlyButton.getReadText().v());
		default:
			return null;
		}
	}

}
