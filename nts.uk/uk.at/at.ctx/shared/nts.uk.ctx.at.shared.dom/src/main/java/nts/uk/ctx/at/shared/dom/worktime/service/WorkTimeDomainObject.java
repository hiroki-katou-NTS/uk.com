package nts.uk.ctx.at.shared.dom.worktime.service;

import lombok.Getter;
import nts.arc.error.BundledBusinessException;
import nts.arc.validate.Validatable;

public class WorkTimeDomainObject implements Validatable {

	@Getter
	protected BundledBusinessException bundledBusinessExceptions; 
	
	public WorkTimeDomainObject() {
		this.bundledBusinessExceptions = BundledBusinessException.newInstance();
	}
	
	@Override
	public void validate() {
		WorkTimeValidationSevice.validate(this);
	}
}
