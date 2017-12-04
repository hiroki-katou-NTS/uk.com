package nts.uk.ctx.pereg.dom.person.info.selectionitem;

import lombok.Getter;
import nts.uk.ctx.pereg.dom.person.info.singleitem.DataTypeState;
import nts.uk.ctx.pereg.dom.person.info.singleitem.DataTypeValue;

@Getter
public class SelectionItem extends DataTypeState {

	private ReferenceTypeState referenceTypeState;

	private SelectionItem(ReferenceTypeState referenceTypeState) {
		super();
		this.dataTypeValue = DataTypeValue.SELECTION;
		this.referenceTypeState = referenceTypeState;
	}

	public static SelectionItem createFromJavaType(ReferenceTypeState referenceTypeState) {
		return new SelectionItem(referenceTypeState);
	}
}
