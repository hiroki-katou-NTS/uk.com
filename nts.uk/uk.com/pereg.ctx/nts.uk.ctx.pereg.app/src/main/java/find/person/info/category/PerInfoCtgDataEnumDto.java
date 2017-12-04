package find.person.info.category;

import java.util.List;

import lombok.Value;
import nts.arc.enums.EnumConstant;

@Value
public class PerInfoCtgDataEnumDto {
	private List<EnumConstant> historyTypes;
	private List<PerInfoCtgShowDto> categoryList;
}
