package nts.uk.ctx.pereg.dom.person.info.selectionitem;

import lombok.Getter;
import nts.uk.ctx.pereg.dom.person.info.singleitem.DataTypeState;
import nts.uk.ctx.pereg.dom.person.info.singleitem.DataTypeValue;

@Getter
public class SelectionButton extends DataTypeState {
	private SelectionButton() {
		super();
		this.dataTypeValue = DataTypeValue.SELECTION_BUTTON;

	}

	public static SelectionButton createFromJavaType() {
		return new SelectionButton();
	}

}
