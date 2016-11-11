package nts.uk.ctx.pr.proto.dom.paymentdata;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
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
	 * Create instance using Java type parameters.
	 * 
	 * @return PersonalEmploymentContract
	 */
	public static PersonalEmploymentContract createFromJavaType(int payrollSystem, String personId) {
		// return new
		// PersonalEmploymentContract(PayrollSystem.valueOf(payrollSystem), new
		// PersonId(personId));
		return null;
	}

	/**
	 * Payroll system by DAILY || DAY
	 * 
	 * @return true if payroll system = 2(DAILY) || 3(DAY) else false
	 */
	public boolean isPayrollSystemDailyOrDay() {
		return this.payrollSystem == PayrollSystem.DAILY || this.payrollSystem == PayrollSystem.DAY;
	}
}
