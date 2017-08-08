package find.person.info.item;

import lombok.Getter;
import nts.uk.ctx.bs.person.dom.person.info.singleitem.DataTypeValue;

@Getter
public class SelectionItemDto extends DataTypeStateDto {

	private ReferenceTypeStateDto referenceTypeState;

	private SelectionItemDto(ReferenceTypeStateDto referenceTypeState) {
		super();
		this.dataTypeValue = DataTypeValue.SELECTION.value;
		this.referenceTypeState = referenceTypeState;
	}

	public static SelectionItemDto createFromJavaType(ReferenceTypeStateDto referenceTypeState) {
		return new SelectionItemDto(referenceTypeState);
	}
}
