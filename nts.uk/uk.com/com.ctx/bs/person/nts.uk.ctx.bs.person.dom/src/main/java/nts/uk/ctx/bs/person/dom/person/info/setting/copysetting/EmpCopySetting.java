package nts.uk.ctx.bs.person.dom.person.info.setting.copysetting;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

@Getter
public class EmpCopySetting extends AggregateRoot {

	String categoryId;
	String companyId;

	public EmpCopySetting(String categoryId, String companyId) {
		this.categoryId = categoryId;
		this.companyId = companyId;
	}

}
