package find.person.info.item;

import lombok.Getter;
import nts.uk.ctx.bs.person.dom.person.info.selectionitem.ReferenceType;

@Getter
public class MasterRefConditionDto extends ReferenceTypeStateDto {
	
	private String masterType;
	
	private MasterRefConditionDto(String masterType) {
		super();
		this.referenceType = ReferenceType.DESIGNATED_MASTER.value;
		this.masterType = masterType;
	}

	public static MasterRefConditionDto createFromJavaType(String masterType) {
		return new MasterRefConditionDto(masterType);
	}
}
