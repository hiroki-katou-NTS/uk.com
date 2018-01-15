package nts.uk.ctx.pereg.dom.person.info.setting.copysetting;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.pereg.dom.person.info.item.IsRequired;

@Getter
public class EmpCopySettingItem extends AggregateRoot {

	private String perInfoCtgId;

	private String categoryCode;

	private String itemDefId;

	private String itemCode;

	private String itemName;

	private IsRequired isRequired;

	public EmpCopySettingItem(String perInfoCtgId, String categoryCode, String itemDefId, String itemCode,
			String itemName, IsRequired isRequired) {
		super();
		this.perInfoCtgId = perInfoCtgId;
		this.categoryCode = categoryCode;
		this.itemDefId = itemDefId;
		this.itemCode = itemCode;
		this.itemName = itemName;
		this.isRequired = isRequired;
	}

	public static EmpCopySettingItem createFromJavaType(String perInfoCtgId, String categoryCode, String itemDefId,
			String itemCode, String itemName, int isRequired) {

		return new EmpCopySettingItem(perInfoCtgId, categoryCode, itemDefId, itemCode, itemName,
				EnumAdaptor.valueOf(isRequired, IsRequired.class));

	}

}
