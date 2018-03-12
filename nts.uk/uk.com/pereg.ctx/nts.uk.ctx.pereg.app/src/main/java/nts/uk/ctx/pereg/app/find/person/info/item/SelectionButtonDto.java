package nts.uk.ctx.pereg.app.find.person.info.item;

import lombok.Getter;
import nts.uk.ctx.pereg.dom.person.info.selectionitem.ReferenceTypes;
import nts.uk.ctx.pereg.dom.person.info.singleitem.DataTypeValue;

@Getter
public class SelectionButtonDto extends SelectionItemDto {

	private ReferenceTypes referenceType;

	public SelectionButtonDto(ReferenceTypes referenceType) {
		super(referenceType);
		this.dataTypeValue = DataTypeValue.SELECTION_BUTTON.value;
	}
}
