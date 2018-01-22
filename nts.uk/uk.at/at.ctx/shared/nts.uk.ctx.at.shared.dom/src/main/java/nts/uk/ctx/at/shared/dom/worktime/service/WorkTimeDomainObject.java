package nts.uk.ctx.at.shared.dom.worktime.service;

import nts.arc.validate.Validatable;

public class WorkTimeDomainObject implements Validatable {

	@Override
	public void validate() {
		WorkTimeValidationSevice.validate(this);
	}
}
