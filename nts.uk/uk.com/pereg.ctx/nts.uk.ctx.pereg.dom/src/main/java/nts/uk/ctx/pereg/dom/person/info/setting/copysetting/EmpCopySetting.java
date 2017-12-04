package nts.uk.ctx.pereg.dom.person.info.setting.copysetting;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

@Getter
public class EmpCopySetting extends AggregateRoot {

	String categoryId;
	String companyId;

	private EmpCopySetting(String categoryId, String companyId) {
		this.categoryId = categoryId;
		this.companyId = companyId;
	}

	public static EmpCopySetting createFromJavaType(String categoryId, String companyId) {

		return new EmpCopySetting(categoryId, companyId);

	}

}
