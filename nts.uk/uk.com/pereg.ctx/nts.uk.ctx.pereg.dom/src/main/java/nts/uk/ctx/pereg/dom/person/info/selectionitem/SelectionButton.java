package nts.uk.ctx.pereg.dom.person.info.selectionitem;

import lombok.Getter;
import nts.uk.ctx.pereg.dom.person.info.singleitem.DataTypeState;
import nts.uk.ctx.pereg.dom.person.info.singleitem.DataTypeValue;

@Getter
public class SelectionButton extends DataTypeState {
	
	private ButtonName buttonName;
	private SelectionButton(ButtonName buttonName) {
		super();
		
		this.dataTypeValue = DataTypeValue.SELECTION_BUTTON;
		
		this.buttonName = buttonName;

	}

	public static SelectionButton createFromJavaType(ButtonName buttonName) {
		return new SelectionButton(buttonName);
	}

}
