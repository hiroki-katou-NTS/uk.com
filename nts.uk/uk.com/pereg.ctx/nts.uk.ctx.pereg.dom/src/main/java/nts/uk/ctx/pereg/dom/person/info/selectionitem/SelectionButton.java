package nts.uk.ctx.pereg.dom.person.info.selectionitem;

import lombok.Getter;
import nts.uk.ctx.pereg.dom.person.info.singleitem.DataTypeState;
import nts.uk.ctx.pereg.dom.person.info.singleitem.DataTypeValue;

@Getter
public class SelectionButton extends DataTypeState {
	
	private ReferenceTypeState referenceTypeState;
	private SelectionButton(ReferenceTypeState referenceTypeState) {
		super();
		this.referenceTypeState = referenceTypeState;
		this.dataTypeValue = DataTypeValue.SELECTION_BUTTON;

	}
	
	@Override
	public ReferenceTypes getReferenceTypes() {
		return referenceTypeState.getReferenceType();
	}
	
	@Override
	public String getReferenceCode() {
		return this.referenceTypeState.getReferenceCode();
	}

	public static SelectionButton createFromJavaType(ReferenceTypeState referenceTypeState) {
		return new SelectionButton(referenceTypeState);
	}

}
