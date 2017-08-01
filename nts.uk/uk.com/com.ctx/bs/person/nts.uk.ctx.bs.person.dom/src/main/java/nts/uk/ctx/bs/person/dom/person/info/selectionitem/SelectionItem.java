package nts.uk.ctx.bs.person.dom.person.info.selectionitem;

import lombok.Getter;
import nts.uk.ctx.bs.person.dom.person.info.singleitem.DataTypeState;
import nts.uk.ctx.bs.person.dom.person.info.singleitem.DataTypeValue;

@Getter
public class SelectionItem extends DataTypeState {

	private ReferenceTypeState referenceTypeState;

	private SelectionItem() {
		super();
		this.dataTypeValue = DataTypeValue.SELECTION;
	}

	public static SelectionItem createFromJavaType() {
		return new SelectionItem();
	}

	private void setReferenceTypeState(ReferenceTypeState referenceTypeState) {
		this.referenceTypeState = referenceTypeState;
	}

	public void setMasterReferenceCondition(String masterType) {
		setReferenceTypeState(ReferenceTypeState.createMasterReferenceCondition(masterType));
	}

	public void setCodeNameReferenceType(String typeCode) {
		setReferenceTypeState(ReferenceTypeState.createCodeNameReferenceType(typeCode));
	}

	public void setEnumReferenceCondition(String enumName) {
		setReferenceTypeState(ReferenceTypeState.createEnumReferenceCondition(enumName));
	}
}
