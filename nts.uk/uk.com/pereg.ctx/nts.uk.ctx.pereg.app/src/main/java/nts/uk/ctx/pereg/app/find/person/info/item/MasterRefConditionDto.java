package nts.uk.ctx.pereg.app.find.person.info.item;

import lombok.Getter;
import nts.uk.ctx.pereg.dom.person.info.selectionitem.ReferenceTypes;

@Getter
public class MasterRefConditionDto extends SelectionItemDto {

	private String masterType;

	private MasterRefConditionDto(String masterType) {
		super(ReferenceTypes.DESIGNATED_MASTER);
		this.masterType = masterType;
	}

	public static MasterRefConditionDto createFromJavaType(String masterType) {
		return new MasterRefConditionDto(masterType);
	}
}
