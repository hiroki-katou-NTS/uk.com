package nts.uk.ctx.pereg.pub.person.info.item;

import lombok.Getter;

@Getter
public class SelectionRadioExport extends SelectionItemExport {

	private ReferenceTypesExport referenceType;

	public SelectionRadioExport(ReferenceTypesExport referenceType) {
		super(referenceType);
		this.dataTypeValue = DataTypeValueExport.SELECTION_RADIO.value;
	}

}
