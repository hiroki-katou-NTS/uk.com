package find.person.info.item;

import lombok.Getter;

@Getter
public class ReferenceTypeStateDto {
	
	protected int referenceType;
	
	public static ReferenceTypeStateDto createMasterRefDto(String masterType) {
		return MasterRefConditionDto.createFromJavaType(masterType);
	}

	public static ReferenceTypeStateDto createCodeNameRefDto(String typeCode) {
		return CodeNameRefTypeDto.createFromJavaType(typeCode);
	}

	public static ReferenceTypeStateDto createEnumRefDto(String enumName) {
		return EnumRefConditionDto.createFromJavaType(enumName);
	}
	
}
