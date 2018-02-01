package nts.uk.ctx.pereg.dom.person.info.selectionitem.radio;

import lombok.Getter;
import nts.uk.ctx.pereg.dom.person.info.singleitem.DataTypeState;
import nts.uk.ctx.pereg.dom.person.info.singleitem.DataTypeValue;
@Getter
public class SelectionRadio extends DataTypeState {
	
	private ItemButton itemButton;
	
	private SelectionRadio(ItemButton itemButton) {
		super();
		this.dataTypeValue = DataTypeValue.SELECTION_RADIO;
		this.itemButton = itemButton;
	}

	public static SelectionRadio createFromJavaType(int itemButton) {
		return new SelectionRadio(ItemButton.valueOf(String.valueOf(itemButton)));
	}

}
