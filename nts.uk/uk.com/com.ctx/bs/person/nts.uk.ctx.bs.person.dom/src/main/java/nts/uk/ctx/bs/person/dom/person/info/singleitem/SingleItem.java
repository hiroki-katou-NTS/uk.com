package nts.uk.ctx.bs.person.dom.person.info.singleitem;

import java.math.BigDecimal;

import lombok.Getter;
import nts.uk.ctx.bs.person.dom.person.info.item.ItemType;
import nts.uk.ctx.bs.person.dom.person.info.item.ItemTypeState;

@Getter
public class SingleItem extends ItemTypeState {

	protected DataTypeState dataTypeState;

	private SingleItem() {
		super();
		this.itemType = ItemType.SINGLE_ITEM;
	}

	public static SingleItem createFromJavaType() {
		return new SingleItem();
	}

	private void setDataTypeState(DataTypeState dataTypeState) {
		this.dataTypeState = dataTypeState;
	}

	public void setTimeItem(long max, long min) {
		setDataTypeState(DataTypeState.createTimeItem(max, min));
	}

	public void setStringItem(int stringItemLeng, int stringItemType, int stringItemDataType) {
		setDataTypeState(DataTypeState.createStringItem(stringItemLeng, stringItemType, stringItemDataType));
	}

	public void setTimePointItem(long timePointItemMin, long timePointItemMax) {
		setDataTypeState(DataTypeState.createTimePointItem(timePointItemMin, timePointItemMax));
	}

	public void setDateItem(int dateItemType) {
		setDataTypeState(DataTypeState.createDateItem(dateItemType));
	}

	public void setNumericItem(int numericItemMinus, int numericItemAmount, int integerPart, int decimalPart,
			BigDecimal numericItemMin, BigDecimal numericItemMax) {
		setDataTypeState(DataTypeState.createNumericItem(numericItemMinus, numericItemAmount, integerPart, decimalPart,
				numericItemMin, numericItemMax));
	}

	public void setSelectionItem() {
		setDataTypeState(DataTypeState.createSelectionItem());
	}
}
