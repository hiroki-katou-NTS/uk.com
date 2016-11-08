package nts.uk.ctx.pr.proto.dom.createdata;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

public class PersonalEmploymentContract extends AggregateRoot {
	
	/**
	 * Payroll system.
	 */
	@Getter
	private PayrollSystem payrollSystem;
	
}
