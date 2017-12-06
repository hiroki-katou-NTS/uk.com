package find.person.info.item;

import java.util.List;

import find.person.setting.selectionitem.PerInfoSelectionItemDto;
import lombok.Value;
import nts.arc.enums.EnumConstant;

@Value
public class PerInfoItemDefFullEnumDto {
	private List<EnumConstant> dataTypeEnum;
	private List<EnumConstant> stringItemTypeEnum;
	private List<EnumConstant> stringItemDataTypeEnum;
	private List<EnumConstant> dateItemTypeEnum;
	private List<PerInfoSelectionItemDto> selectionItemLst;
	private List<PerInfoItemDefShowListDto> personInfoItemList;
}
