package nts.uk.ctx.hr.notice.dom.report.valueImported;

import lombok.Getter;

@Getter
public class SelectionRadioImport extends SelectionItemImport {

	private ReferenceTypesImport referenceType;

	public SelectionRadioImport(ReferenceTypesImport referenceType) {
		super(referenceType);
		this.dataTypeValue = DataTypeValueImport.SELECTION_RADIO.value;
	}

}
