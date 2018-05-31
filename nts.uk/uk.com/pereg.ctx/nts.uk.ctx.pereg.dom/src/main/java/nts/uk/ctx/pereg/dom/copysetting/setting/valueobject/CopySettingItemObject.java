package nts.uk.ctx.pereg.dom.copysetting.setting.valueobject;

import lombok.Getter;

@Getter
public class CopySettingItemObject {

	private String perInfoCtgId;

	private String itemDefId;

	private String itemCode;

	private String itemName;

	private boolean alreadyCopy;

	public CopySettingItemObject(String perInfoCtgId, String itemDefId, String itemName, String itemCode,
			Boolean isAlreadyCopy) {
		super();
		this.perInfoCtgId = perInfoCtgId;
		this.itemDefId = itemDefId;
		this.itemName = itemName;
		this.itemCode = itemCode;
		this.alreadyCopy = isAlreadyCopy;
	}

	public static CopySettingItemObject createFromJavaType(String perInfoCtgId, String itemDefId, String itemCode,
			String itemName, Boolean isAlreadyCopy) {
		return new CopySettingItemObject(perInfoCtgId, itemDefId, itemCode, itemName, isAlreadyCopy);
	}

}
