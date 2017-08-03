package find.person.info.item;

import lombok.Value;

@Value
public class ReferenceTypeStateDto {
	private MasterRefConditionDto masterRefCondition;
	private CodeNameRefTypeDto codeNameRefType;
	private EnumRefConditionDto enumRefCondition;
}
