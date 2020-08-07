package nts.uk.screen.at.app.query.kdp.kdp003.a;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.app.find.dialog.employmentsystem.EmployeeBasicInfoDto;
import nts.uk.ctx.at.request.app.find.dialog.employmentsystem.EmploymentSystemFinder;
import nts.uk.ctx.bs.employee.pub.workplace.AffWorkplaceExport;
import nts.uk.ctx.bs.employee.pub.workplace.master.WorkplacePub;

/**
 * @author vuongnv <<ScreenQuery>> 打刻入力(氏名選択)の設定を取得する
 */
@Stateless
public class GetListEmployeeHaveBeenStamped {	
	@Inject
	private WorkplacePub workplacePub;
	
	@Inject
	private EmploymentSystemFinder employmentFinder;

	public List<EmployeeStampData> getListEmployee(String companyId, List<String> workplaceId, GeneralDate baseDate) {
		GeneralDate startDate = GeneralDate.ymd(baseDate.year(), baseDate.month(), 1);
		GeneralDate endDate = GeneralDate.ymd(baseDate.year(), baseDate.month(), 1).addMonths(1).addDays(-1);

		// note: アルゴリズム「期間内に特定の職場に所属している社員一覧を取得」を実行する
		List<AffWorkplaceExport> affwork = workplacePub.getByLstWkpIdAndPeriod(workplaceId, startDate,
				endDate);

		List<String> employeeIds = affwork.stream().map(m -> m.getEmployeeId()).collect(Collectors.toList());
		
		List<EmployeeBasicInfoDto> employements = employmentFinder.getEmployeeData(employeeIds, "");

		return employeeIds.stream().map(empid -> {
			Optional<EmployeeBasicInfoDto> emb = employements.stream().filter(f -> f.employeeId.equals(empid)).findFirst();
			
			return EmployeeStampData.builder()
					.employeeId(empid)
					.employeeCode(emb.map(m -> m.employeeCode).orElse(""))
					.employeeName(emb.map(m -> m.businessName).orElse(""))
					.build();
		}).collect(Collectors.toList());
	}
}
