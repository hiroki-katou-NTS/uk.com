package nts.uk.ctx.pereg.app.find.copysetting.item;

import lombok.experimental.Value;

@Value
public class CopySettingItemDto {
	private String id;
	private String perInfoCtgId;
	private String itemName;
	private boolean alreadyItemDefCopy;
}
