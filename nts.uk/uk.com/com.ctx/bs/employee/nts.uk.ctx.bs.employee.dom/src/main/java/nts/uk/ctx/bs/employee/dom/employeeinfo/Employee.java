package nts.uk.ctx.bs.employee.dom.employeeinfo;

import java.util.List;

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

	/** The List JobEntryHistory */
	private List<JobEntryHistory> listEntryJobHist;

	public static Employee createFromJavaType(String companyId, String pId, String sId, String sCd, String companyMail,
			String mobileMail, String companyMobile, List<JobEntryHistory> listEntryJobHist) {
		return new Employee(companyId, pId, sId, new EmployeeCode(sCd), new EmployeeMail(companyMail),
				new EmployeeMail(mobileMail), new CompanyMobile(companyMobile), listEntryJobHist);
	}
}
