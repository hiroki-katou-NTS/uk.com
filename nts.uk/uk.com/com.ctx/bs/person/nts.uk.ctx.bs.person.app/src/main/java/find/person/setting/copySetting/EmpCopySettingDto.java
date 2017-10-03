package find.person.setting.copySetting;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Value;
import nts.uk.ctx.bs.person.dom.person.info.setting.copySetting.EmpCopySetting;

@Value
public class EmpCopySettingDto {

	String categoryId;
	List<EmpCopySettingItemDto> itemList;

	public static EmpCopySettingDto fromDomain(EmpCopySetting domain) {

		return new EmpCopySettingDto(domain.getCategoryId(), domain.getItemList().stream()
				.map(x -> EmpCopySettingItemDto.fromDomain(x)).collect(Collectors.toList()));

	}

}
