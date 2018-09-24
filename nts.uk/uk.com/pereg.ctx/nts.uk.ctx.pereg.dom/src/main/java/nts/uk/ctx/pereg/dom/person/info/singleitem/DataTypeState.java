package nts.uk.ctx.pereg.dom.person.info.singleitem;

import java.math.BigDecimal;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.pereg.dom.person.info.dateitem.DateItem;
import nts.uk.ctx.pereg.dom.person.info.numericitem.NumericItem;
import nts.uk.ctx.pereg.dom.person.info.selectionitem.NumericButton;
import nts.uk.ctx.pereg.dom.person.info.selectionitem.ReadOnly;
import nts.uk.ctx.pereg.dom.person.info.selectionitem.ReadOnlyButton;
import nts.uk.ctx.pereg.dom.person.info.selectionitem.ReferenceTypeState;
import nts.uk.ctx.pereg.dom.person.info.selectionitem.ReferenceTypes;
import nts.uk.ctx.pereg.dom.person.info.selectionitem.RelatedCategory;
import nts.uk.ctx.pereg.dom.person.info.selectionitem.SelectionButton;
import nts.uk.ctx.pereg.dom.person.info.selectionitem.SelectionItem;
import nts.uk.ctx.pereg.dom.person.info.selectionitem.SelectionRadio;
import nts.uk.ctx.pereg.dom.person.info.stringitem.StringItem;
import nts.uk.ctx.pereg.dom.person.info.timeitem.TimeItem;
import nts.uk.ctx.pereg.dom.person.info.timepointitem.TimePointItem;

public class DataTypeState extends AggregateRoot {
	@Getter
	protected DataTypeValue dataTypeValue;
	
	public ReferenceTypes getReferenceTypes() {
		return null;
	}
	
	public String getReferenceCode() {
		return null;
	}

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
			Integer decimalPart, BigDecimal numericItemMin, BigDecimal numericItemMax) {
		return NumericItem.createFromJavaType(numericItemMinus, numericItemAmount, integerPart, decimalPart,
				numericItemMin, numericItemMax);
	}

	public static DataTypeState createSelectionItem(ReferenceTypeState referenceTypeState) {
		return SelectionItem.createFromJavaType(referenceTypeState);
	}

	public static DataTypeState createSelectionRadio(ReferenceTypeState referenceTypeState) {
		return SelectionRadio.createFromJavaType(referenceTypeState);
	}

	public static DataTypeState createSelectionButton(ReferenceTypeState referenceTypeState) {
		return SelectionButton.createFromJavaType(referenceTypeState);
	}

	public static DataTypeState createReadonly(String readText) {
		return ReadOnly.createFromJavaType(readText);
	}

	public static DataTypeState createRelatedCategory(String relatedCtgText) {
		return RelatedCategory.createFromJavaType(relatedCtgText);
	}

	public static DataTypeState createNumbericButton(int numericItemMinus, int numericItemAmount, int integerPart,
			int decimalPart, BigDecimal numericItemMin, BigDecimal numericItemMax) {
		return NumericButton.createFromJavaType(numericItemMinus, numericItemAmount, integerPart, decimalPart,
				numericItemMin, numericItemMax);
	}

	public static DataTypeState createReadonlyButton(String readText) {
		return ReadOnlyButton.createFromJavaType(readText);
	}
	
}
