package nts.uk.ctx.bs.employee.dom.employeeinfo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;

@Getter
@AllArgsConstructor
public class Employee extends AggregateRoot {

	/** The CompanyId */
	private String companyId;

	/** The personId */
	private String pId;

	/** The employeeId */
	private String sId;

	/** The employeeCode */
	private EmployeeCode sCd;

	/** The Company Mail */
	private EmployeeMail companyMail;

	/** The Company Mobile Mail - 会社携帯メールアドレス */
	private EmployeeMail mobileMail;

	/** The Company Mobile */
	private CompanyMobile companyMobile;

	/** TheJob Entry History */
	private JobEntryHistory jobEntryHistory;

	/** The HiringType */
	private HiringType hiringType;

	/** The RetireDate */
	private GeneralDate retirementDate;

	/** The EntryDate */
	private GeneralDate joinDate;

	/** The AdoptDate */
	private GeneralDate adoptDate;

	
	public static Employee createFromJavaType(String companyId, String pId, String sId, String sCd, String companyMail,
			GeneralDate retirementDate, GeneralDate joinDate) {
		return new Employee(companyId, pId, sId, new EmployeeCode(sCd), new EmployeeMail(companyMail), retirementDate,
				joinDate);
	}

	public static Employee createFromJavaTypeFullArg(String companyId, String pId, String employeeId,
			String employeeCode, String companyMail, String mobileMail, String companyMobile, String jobEntryHistory,
			int hiringType, GeneralDate retirementDate, GeneralDate joinDate, GeneralDate adoptDate) {
		return new Employee(companyId, pId, employeeId, new EmployeeCode(employeeCode), new EmployeeMail(companyMail),
				new EmployeeMail(mobileMail), new CompanyMobile(companyMobile), new JobEntryHistory(jobEntryHistory),
				new HiringType(hiringType), retirementDate, joinDate, adoptDate);
	}

	public Employee(String companyId, String pId, String sId, EmployeeCode sCd, EmployeeMail companyMail,
			GeneralDate retirementDate, GeneralDate joinDate) {
		super();
		this.companyId = companyId;
		this.pId = pId;
		this.sId = sId;
		this.sCd = sCd;
		this.companyMail = companyMail;
		this.retirementDate = retirementDate;
		this.joinDate = joinDate;
	}
}
