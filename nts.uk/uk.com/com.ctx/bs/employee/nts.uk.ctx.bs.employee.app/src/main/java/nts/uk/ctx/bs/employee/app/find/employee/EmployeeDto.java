package nts.uk.ctx.bs.employee.app.find.employee;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.dom.employeeinfo.Employee;
import nts.uk.ctx.bs.employee.dom.employeeinfo.JobEntryHistory;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EmployeeDto {
	private String personId;
	private String employeeId;
	private String employeeCode;
	private String employeeMail;
	private GeneralDate retirementDate;
	private GeneralDate joinDate;

	// 入社履歴
	// calc year of Entire from listHistoryEntire
	private int daysOfEntire;
	
	private int daysOfTemporaryAbsence;

	public static EmployeeDto fromDomain(Employee domain) {

		// get first history for retirement date and join date info
		List<JobEntryHistory> hists = domain.getListEntryJobHist();
		JobEntryHistory firstHisJob = hists.size() > 0 ? hists.get(0) : null;

		return new EmployeeDto(domain.getPId(), domain.getSId(), domain.getSCd().v(), domain.getCompanyMail().v(),
				firstHisJob != null ? firstHisJob.getRetirementDate() : null,
				firstHisJob != null ? firstHisJob.getJoinDate() : null, domain.getDaysOfEntire(), domain.getDaysOfTemporaryAbsence());
	}
}
