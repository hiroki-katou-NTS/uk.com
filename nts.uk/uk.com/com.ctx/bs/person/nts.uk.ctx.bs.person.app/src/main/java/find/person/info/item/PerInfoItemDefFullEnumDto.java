package find.person.info.item;

import java.util.List;

import lombok.Value;
import nts.arc.enums.EnumConstant;

@Value
public class PerInfoItemDefFullEnumDto {
	private List<EnumConstant> dataTypeEnum;
	private List<EnumConstant> stringItemTypeEnum;
	private List<EnumConstant> stringItemDataTypeEnum;
	private List<EnumConstant> dateItemTypeEnum;
	private List<PerInfoItemDefShowListDto> personInfoItemList;
}
