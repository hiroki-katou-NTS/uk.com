package find.person.setting.copysetting;

import lombok.Value;
import nts.uk.ctx.bs.person.dom.person.info.setting.copysetting.EmpCopySettingItem;

@Value
public class EmpCopySettingItemDto {
	String perInfoItemDefId;

	public static EmpCopySettingItemDto fromDomain(EmpCopySettingItem domain) {
		return new EmpCopySettingItemDto(domain.getPerInfoItemDefId());
	}

}
