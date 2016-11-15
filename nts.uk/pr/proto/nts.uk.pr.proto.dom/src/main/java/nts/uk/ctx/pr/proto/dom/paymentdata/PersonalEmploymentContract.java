package nts.uk.ctx.pr.proto.dom.paymentdata;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
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
	private GeneralDate strD;

	/**
	 * End Date
	 */
	@Getter
	private GeneralDate endD;

	public PersonalEmploymentContract(PayrollSystem payrollSystem, PersonId personId, CompanyCode companyCode,
			GeneralDate strD, GeneralDate endD) {
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
	public static PersonalEmploymentContract createFromJavaType(int payrollSystem, String personId, String companyCode,
			GeneralDate strD, GeneralDate endD) {
		return new PersonalEmploymentContract(EnumAdaptor.valueOf(payrollSystem, PayrollSystem.class),
				new PersonId(personId), new CompanyCode(companyCode), strD, endD);
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
