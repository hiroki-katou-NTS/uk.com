package nts.uk.ctx.pereg.app.find.person.info.item;

import nts.uk.ctx.pereg.dom.person.info.singleitem.DataTypeValue;

public class SelectionRadioDto extends DataTypeStateDto{
	public static SelectionRadioDto createFromJavaType() {
		return new SelectionRadioDto();
	}
	public SelectionRadioDto() {
		super();
		this.dataTypeValue = DataTypeValue.SELECTION_RADIO.value;
	}
}
