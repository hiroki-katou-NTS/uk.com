package nts.uk.ctx.pereg.app.find.copysetting.item;

import lombok.Value;
import nts.uk.ctx.pereg.dom.copysetting.setting.valueobject.CopySettingItemObject;

@Value
public class CopySettingItemDto {
	private String id;
	private String itemCd;
	private String perInfoCtgId;
	private String itemName;
	private boolean alreadyItemDefCopy;
	private String itemParentCd;

	public static CopySettingItemDto createFromDomain(CopySettingItemObject item) {
		return new CopySettingItemDto(item.getItemDefId(), item.getItemCode(), item.getPerInfoCtgId(),
				item.getItemName(), item.isAlreadyCopy(), null);
	}
}
