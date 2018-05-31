package nts.uk.ctx.pereg.dom.person.info.selectionitem;

import lombok.Getter;
import nts.uk.ctx.pereg.dom.person.info.singleitem.DataTypeState;
import nts.uk.ctx.pereg.dom.person.info.singleitem.DataTypeValue;

@Getter
public class SelectionRadio extends DataTypeState {
	private ReferenceTypeState referenceTypeState;
	private SelectionRadio(ReferenceTypeState referenceTypeState) {
		super();
		this.dataTypeValue = DataTypeValue.SELECTION_RADIO;
		this.referenceTypeState = referenceTypeState;
	}
	
	@Override
	public ReferenceTypes getReferenceTypes() {
		return referenceTypeState.getReferenceType();
	}
	
	@Override
	public String getReferenceCode() {
		return this.referenceTypeState.getReferenceCode();
	}

	public static SelectionRadio createFromJavaType(ReferenceTypeState refType) {
		return new SelectionRadio(refType);
	}

}
