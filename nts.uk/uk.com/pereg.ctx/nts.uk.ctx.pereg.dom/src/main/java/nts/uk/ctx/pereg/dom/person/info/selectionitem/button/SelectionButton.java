package nts.uk.ctx.pereg.dom.person.info.selectionitem.button;

import lombok.Getter;
import nts.uk.ctx.pereg.dom.person.info.singleitem.DataTypeState;
import nts.uk.ctx.pereg.dom.person.info.singleitem.DataTypeValue;
@Getter
public class SelectionButton extends DataTypeState {
	private ItemButton itemButton;
	private SelectionButton(ItemButton itemButton) {
		super();
		this.dataTypeValue = DataTypeValue.SELECTION_BUTTON;
		this.itemButton = itemButton;
		
	}
	
	public static SelectionButton createFromJavaType(String buttonName) {
		return new SelectionButton(new ItemButton(buttonName));
	}

}
