package nts.uk.ctx.bs.person.dom.person.info.setting.copysetting;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

@Getter
public class EmpCopySettingItem extends AggregateRoot {

	String perInfoItemDefId;

	String categoryId;

	private EmpCopySettingItem(String perInfoItemDefId, String categoryId) {
		this.perInfoItemDefId = perInfoItemDefId;
		this.categoryId = categoryId;
	}

	public static EmpCopySettingItem createFromJavaType(String perInfoItemDefId, String categoryId) {

		return new EmpCopySettingItem(perInfoItemDefId, categoryId);

	}

}
