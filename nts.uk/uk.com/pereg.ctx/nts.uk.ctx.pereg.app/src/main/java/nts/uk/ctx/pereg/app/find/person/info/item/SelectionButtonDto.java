package nts.uk.ctx.pereg.app.find.person.info.item;

import nts.uk.ctx.pereg.dom.person.info.singleitem.DataTypeValue;

public class SelectionButtonDto extends DataTypeStateDto {
	public static SelectionButtonDto createFromJavaType() {
		return new SelectionButtonDto();
	}

	public SelectionButtonDto() {
		super();
		this.dataTypeValue = DataTypeValue.SELECTION_BUTTON.value;
	}
}
