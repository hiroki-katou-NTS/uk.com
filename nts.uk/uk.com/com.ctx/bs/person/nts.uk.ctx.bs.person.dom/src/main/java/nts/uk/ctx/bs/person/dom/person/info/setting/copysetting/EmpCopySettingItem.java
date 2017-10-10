package nts.uk.ctx.bs.person.dom.person.info.setting.copysetting;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

@Getter
public class EmpCopySettingItem extends AggregateRoot {

	String perInfoItemDefId;

	String categoryId;

	public EmpCopySettingItem(String perInfoItemDefId, String categoryId) {
		this.perInfoItemDefId = perInfoItemDefId;
		this.categoryId = categoryId;
	}

}
