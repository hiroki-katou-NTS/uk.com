package nts.uk.ctx.pr.proto.dom.paymentdata;

import java.util.Date;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.core.dom.company.CompanyCode;
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

	/**
	 * Company Code
	 */
	@Getter
	private CompanyCode companyCode;

	/**
	 * Start Date
	 */
	@Getter
	private Date strD;

	/**
	 * End Date
	 */
	@Getter
	private Date endD;

	public PersonalEmploymentContract(PayrollSystem payrollSystem, PersonId personId, CompanyCode companyCode,
			Date strD, Date endD) {
		super();
		this.payrollSystem = payrollSystem;
		this.personId = personId;
		this.companyCode = companyCode;
		this.strD = strD;
		this.endD = endD;
	}

	/**
	 * Create instance using Java type parameters.
	 * 
	 * @return PersonalEmploymentContract
	 */
	public static PersonalEmploymentContract createFromJavaType(int payrollSystem, String personId) {
		// return new
		// PersonalEmploymentContract(EnumAdaptor.valueOf(payrollSystem,
		// PayrollSystem.class), new PersonId(personId));
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
