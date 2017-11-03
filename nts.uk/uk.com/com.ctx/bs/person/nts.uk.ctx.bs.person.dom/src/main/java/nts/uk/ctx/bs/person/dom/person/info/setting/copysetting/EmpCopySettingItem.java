package nts.uk.ctx.bs.person.dom.person.info.setting.copysetting;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.bs.person.dom.person.info.item.IsRequired;

@Getter
public class EmpCopySettingItem extends AggregateRoot {

	String itemCode;

	String itemName;

	IsRequired isRequired;

	public EmpCopySettingItem(String itemCode, String itemName, IsRequired isRequired) {
		super();
		this.itemCode = itemCode;
		this.itemName = itemName;
		this.isRequired = isRequired;
	}

	public static EmpCopySettingItem createFromJavaType(String itemCode, String itemName, int isRequired) {

		return new EmpCopySettingItem(itemCode, itemName, EnumAdaptor.valueOf(isRequired, IsRequired.class));

	}

}
