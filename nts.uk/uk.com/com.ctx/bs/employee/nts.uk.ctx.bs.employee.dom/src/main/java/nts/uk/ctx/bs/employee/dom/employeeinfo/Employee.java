package nts.uk.ctx.bs.employee.dom.employeeinfo;

import java.time.temporal.ChronoUnit;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
// 社員基本情報
public class Employee extends AggregateRoot {

	/** The CompanyId 会社ID */
	private String companyId;

	/** The personId 個人ID */
	private String pId;

	/** The employeeId 社員ID */
	private String sId;

	/** The employeeCode 社員コード */
	private EmployeeCode sCd;

	/** The Company Mail 会社メールアドレス */
	private EmployeeMail companyMail;

	/** The Company Mobile Mail - 会社携帯メールアドレス */
	private EmployeeMail mobileMail;

	/** The Company Mobile 会社携帯電話番号 */
	private CompanyMobile companyMobile;

	/** The List JobEntryHistory 入社履歴 */
	private List<JobEntryHistory> listEntryJobHist;

	public static Employee createFromJavaType(String companyId, String pId, String sId, String sCd, String companyMail,
			String mobileMail, String companyMobile) {
		return new Employee(companyId, pId, sId, new EmployeeCode(sCd), new EmployeeMail(companyMail),
				new EmployeeMail(mobileMail), new CompanyMobile(companyMobile), null);
	}

	public GeneralDate getJoinDate() {
		GeneralDate joinDate = listEntryJobHist.get(0).getJoinDate();
		for (JobEntryHistory jobHis : listEntryJobHist) {
			if (jobHis.getJoinDate().after(joinDate)) {
				joinDate = jobHis.getJoinDate();
			}
		}
		return joinDate;
	}

	public GeneralDate getRetirementDate() {
		GeneralDate retirementDate = listEntryJobHist.get(0).getRetirementDate();
		for (JobEntryHistory jobHis : listEntryJobHist) {
			if (jobHis.getRetirementDate().after(retirementDate)) {
				retirementDate = jobHis.getRetirementDate();
			}
		}
		return retirementDate;
	}

	// calculate year of entire in current company
	public int getDaysOfEntire() {
		if (listEntryJobHist.isEmpty()) {
			return 0;
		}

		return listEntryJobHist.stream()
				.map(m -> ChronoUnit.DAYS.between(m.getJoinDate().localDate(), m.getAdoptDate().localDate()))
				.mapToInt(m -> Math.abs(m.intValue())).sum();
	}
}
