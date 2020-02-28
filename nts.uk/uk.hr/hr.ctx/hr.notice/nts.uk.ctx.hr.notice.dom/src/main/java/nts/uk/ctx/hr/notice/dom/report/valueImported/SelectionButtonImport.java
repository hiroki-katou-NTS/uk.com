package nts.uk.ctx.hr.notice.dom.report.valueImported;

import lombok.Getter;

@Getter
public class SelectionButtonImport extends SelectionItemImport {

	private ReferenceTypesImport referenceType;

	public SelectionButtonImport(ReferenceTypesImport referenceType) {
		super(referenceType);
		this.dataTypeValue = DataTypeValueImport.SELECTION_BUTTON.value;
	}
}
