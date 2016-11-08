package nts.uk.ctx.pr.proto.dom.paymentcreatedata;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.pr.proto.dom.enums.PayrollSystem;

public class PersonalEmploymentContract extends AggregateRoot {
	
	/**
	 * Payroll system.
	 */
	@Getter
	private PayrollSystem payrollSystem;
	
}
