package nts.uk.ctx.pereg.dom.person.info.singleitem;

import lombok.Getter;
import nts.uk.ctx.pereg.dom.person.info.item.ItemType;
import nts.uk.ctx.pereg.dom.person.info.item.ItemTypeState;

@Getter
public class SingleItem extends ItemTypeState {

	protected DataTypeState dataTypeState;

	private SingleItem(DataTypeState dataTypeState) {
		super();
		this.itemType = ItemType.SINGLE_ITEM;
		this.dataTypeState = dataTypeState;
	}

	public static SingleItem createFromJavaType(DataTypeState dataTypeState) {
		return new SingleItem(dataTypeState);
	}
	
	public boolean isComboItem() {
		DataTypeValue dataType = dataTypeState.getDataTypeValue();
		return dataType == DataTypeValue.SELECTION || dataType == DataTypeValue.SELECTION_BUTTON
				|| dataType == DataTypeValue.SELECTION_RADIO;
	}
	
	public boolean isSelectionItem() {
		return dataTypeState.getDataTypeValue() == DataTypeValue.SELECTION;
	}
}
