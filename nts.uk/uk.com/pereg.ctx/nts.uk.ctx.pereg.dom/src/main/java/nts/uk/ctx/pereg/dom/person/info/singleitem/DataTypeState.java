package nts.uk.ctx.pereg.dom.person.info.singleitem;

import java.math.BigDecimal;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.pereg.dom.person.info.dateitem.DateItem;
import nts.uk.ctx.pereg.dom.person.info.numericitem.NumericItem;
import nts.uk.ctx.pereg.dom.person.info.selectionitem.ReferenceTypeState;
import nts.uk.ctx.pereg.dom.person.info.selectionitem.SelectionButton;
import nts.uk.ctx.pereg.dom.person.info.selectionitem.SelectionItem;
import nts.uk.ctx.pereg.dom.person.info.selectionitem.SelectionRadio;
import nts.uk.ctx.pereg.dom.person.info.stringitem.StringItem;
import nts.uk.ctx.pereg.dom.person.info.timeitem.TimeItem;
import nts.uk.ctx.pereg.dom.person.info.timepointitem.TimePointItem;

public class DataTypeState extends AggregateRoot {
	@Getter
	protected DataTypeValue dataTypeValue;

	public static DataTypeState createTimeItem(int max, int min) {
		return TimeItem.createFromJavaType(max, min);
	}

	public static DataTypeState createStringItem(int stringItemLength, int stringItemType, int stringItemDataType) {
		return StringItem.createFromJavaType(stringItemLength, stringItemType, stringItemDataType);
	}

	public static DataTypeState createTimePointItem(int timePointItemMin, int timePointItemMax) {
		return TimePointItem.createFromJavaType(timePointItemMin, timePointItemMax);
	}

	public static DataTypeState createDateItem(int dateItemType) {
		return DateItem.createFromJavaType(dateItemType);
	}

	public static DataTypeState createNumericItem(int numericItemMinus, int numericItemAmount, int integerPart,
			int decimalPart, BigDecimal numericItemMin, BigDecimal numericItemMax) {
		return NumericItem.createFromJavaType(numericItemMinus, numericItemAmount, integerPart, decimalPart,
				numericItemMin, numericItemMax);
	}

	public static DataTypeState createSelectionItem(ReferenceTypeState referenceTypeState) {
		return SelectionItem.createFromJavaType(referenceTypeState);
	}

	public static DataTypeState createSelectionRadio() {
		return SelectionRadio.createFromJavaType();
	}

	public static DataTypeState createSelectionButton() {
		return SelectionButton.createFromJavaType();
	}
}
