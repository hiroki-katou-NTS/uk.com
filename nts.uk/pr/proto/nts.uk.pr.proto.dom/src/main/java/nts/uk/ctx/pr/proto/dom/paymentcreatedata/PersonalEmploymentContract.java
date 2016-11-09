package nts.uk.ctx.pr.proto.dom.paymentcreatedata;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.pr.proto.dom.enums.PayrollSystem;
import nts.uk.shr.com.primitive.PersonId;

/**
 * Aggregate Root: Personal employment contract 
 *
 */
public class PersonalEmploymentContract extends AggregateRoot {
	
	/**
	 * Payroll system.
	 */
	@Getter
	private PayrollSystem payrollSystem;

	/**
	 * Personal Id
	 */
	@Getter
	private PersonId personId;

	public PersonalEmploymentContract(PayrollSystem payrollSystem, PersonId personId) {
		super();
		this.payrollSystem = payrollSystem;
		this.personId = personId;
	}

	/**
	 * Payroll system by DAILY || DAY
	 * @return true if payroll system = 2 || 3 else false
	 */
	public boolean isPayrollSystemDailyOrDay() {
		return this.payrollSystem == PayrollSystem.DAILY || this.payrollSystem == PayrollSystem.DAY;
	}
}
