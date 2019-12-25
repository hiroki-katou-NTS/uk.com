package nts.uk.ctx.pereg.pub.person.info.item;

import lombok.Getter;

@Getter
public class SelectionButtonExport extends SelectionItemExport {

	private ReferenceTypesExport referenceType;

	public SelectionButtonExport(ReferenceTypesExport referenceType) {
		super(referenceType);
		this.dataTypeValue = DataTypeValueExport.SELECTION_BUTTON.value;
	}
}
