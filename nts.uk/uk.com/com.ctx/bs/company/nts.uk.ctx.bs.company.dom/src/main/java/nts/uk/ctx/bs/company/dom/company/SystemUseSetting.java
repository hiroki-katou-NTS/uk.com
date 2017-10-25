package nts.uk.ctx.bs.company.dom.company;

import nts.arc.layer.dom.DomainObject;

public class SystemUseSetting extends DomainObject {

	private SystemUseClassification personalSystem;

	private SystemUseClassification employmentSystem;

	private SystemUseClassification payrollSystem;

	public SystemUseSetting(SystemUseClassification personalSystem, SystemUseClassification employmentSystem,
			SystemUseClassification payrollSystem) {
		super();
		this.personalSystem = personalSystem;
		this.employmentSystem = employmentSystem;
		this.payrollSystem = payrollSystem;
	}
	

}
