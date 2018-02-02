package nts.uk.ctx.pereg.dom.person.info.selectionitem;

import lombok.Getter;
import nts.uk.ctx.pereg.dom.person.info.singleitem.DataTypeState;
import nts.uk.ctx.pereg.dom.person.info.singleitem.DataTypeValue;

@Getter
public class SelectionRadio extends DataTypeState {

	private SelectionRadio() {
		super();
		this.dataTypeValue = DataTypeValue.SELECTION_RADIO;
	}

	public static SelectionRadio createFromJavaType() {
		return new SelectionRadio();
	}

}
