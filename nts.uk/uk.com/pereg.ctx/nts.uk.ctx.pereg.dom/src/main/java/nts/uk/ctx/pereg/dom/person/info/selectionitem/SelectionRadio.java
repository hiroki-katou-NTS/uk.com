package nts.uk.ctx.pereg.dom.person.info.selectionitem;

import lombok.Getter;
import nts.uk.ctx.pereg.dom.person.info.singleitem.DataTypeState;
import nts.uk.ctx.pereg.dom.person.info.singleitem.DataTypeValue;

@Getter
public class SelectionRadio extends DataTypeState {
	private ReferenceTypeState referenceTypeState;

<<<<<<< HEAD
	private RadioName radioName;
	private SelectionRadio(RadioName radioName) {
		super();
		this.dataTypeValue = DataTypeValue.SELECTION_RADIO;
		
		this.radioName = radioName;
	}

	public static SelectionRadio createFromJavaType(RadioName radioName) {
		return new SelectionRadio(radioName);
=======
	private SelectionRadio(ReferenceTypeState referenceTypeState) {
		super();
		this.dataTypeValue = DataTypeValue.SELECTION_RADIO;
		this.referenceTypeState = referenceTypeState;
	}

	public static SelectionRadio createFromJavaType(ReferenceTypeState refType) {
		return new SelectionRadio(refType);
>>>>>>> 7b6cd570859aa45d31b703bda626d00d0e3f6b70
	}

}
