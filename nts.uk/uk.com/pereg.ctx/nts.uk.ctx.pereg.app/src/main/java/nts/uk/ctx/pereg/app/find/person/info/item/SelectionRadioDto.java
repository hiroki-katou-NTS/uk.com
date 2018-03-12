package nts.uk.ctx.pereg.app.find.person.info.item;

import lombok.Getter;
import nts.uk.ctx.pereg.dom.person.info.selectionitem.ReferenceTypes;
import nts.uk.ctx.pereg.dom.person.info.singleitem.DataTypeValue;

@Getter
public class SelectionRadioDto extends SelectionItemDto {

	private ReferenceTypes referenceType;

	public SelectionRadioDto(ReferenceTypes referenceType) {
		super(referenceType);
		this.dataTypeValue = DataTypeValue.SELECTION_RADIO.value;
	}

}
