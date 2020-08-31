package nts.uk.screen.at.app.query.kdp.kdp003.a;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.pub.workplace.AffWorkplaceExport;
import nts.uk.ctx.bs.employee.pub.workplace.master.WorkplacePub;
import nts.uk.query.model.employee.EmployeeInformation;
import nts.uk.query.model.employee.EmployeeInformationQuery;
import nts.uk.query.model.employee.EmployeeInformationRepository;
import nts.uk.screen.at.app.query.kdp.kdp004.a.GetFingerStampSetting;
import nts.uk.screen.at.app.query.kdp.kdp004.a.GetFingerStampSettingDto;

/**
 * @author vuongnv <<ScreenQuery>> 打刻入力(氏名選択)の設定を取得する
 * 
 *   Get 共有打刻の打刻設定
 *   Get 打刻後の実績表示
 *   
 */
@Stateless
public class GetListEmployeeHaveBeenStamped {
	@Inject
	private WorkplacePub workplacePub;
	
	@Inject
	private GetFingerStampSetting stampSetting;
	
	@Inject
	private EmployeeInformationRepository empInfoRepo;

	public List<EmployeeStampData> getListEmployee(String companyId, List<String> workplaceId, GeneralDate baseDate) {
		GeneralDate startDate = GeneralDate.ymd(baseDate.year(), baseDate.month(), 1);
		GeneralDate endDate = GeneralDate.ymd(baseDate.year(), baseDate.month(), 1).addMonths(1).addDays(-1);
		
		// note: 打刻後の日別実績を表示するか設定する
		GetFingerStampSettingDto fingerStampSetting = stampSetting.getFingerStampSetting(companyId);
		
		// note: 共有打刻の打刻設定.氏名選択利用する＝false
		if (fingerStampSetting.getStampSetting() == null || !fingerStampSetting.getStampSetting().isNameSelectArt()) {
			return new ArrayList<EmployeeStampData>();
		}
		
		// note: アルゴリズム「期間内に特定の職場に所属している社員一覧を取得」を実行する
		List<AffWorkplaceExport> affwork = workplacePub.getByLstWkpIdAndPeriod(workplaceId, startDate, endDate);

		// note: get list employee id for request 社員の情報を取得する」を実行する
		List<String> employeeIds = affwork.stream().map(m -> m.getEmployeeId()).collect(Collectors.toList());

		EmployeeInformationQuery query = EmployeeInformationQuery.builder()
				.employeeIds(employeeIds)
				.referenceDate(baseDate)
				.toGetClassification(false)
				.toGetDepartment(false)
				.toGetEmployment(false)
				.toGetEmploymentCls(false)
				.toGetPosition(false)
				.toGetWorkplace(false)
				.build();

		// note: アルゴリズム「<<Public>> 社員の情報を取得する」を実行する
		List<EmployeeInformation> export = empInfoRepo.find(query);
		
		return new HashSet<>(employeeIds).stream().map(empid -> {
			Optional<EmployeeInformation> emb = export.stream().filter(f -> f.getEmployeeId().equals(empid))
					.findFirst();

			return EmployeeStampData.builder()
					.employeeId(empid).employeeCode(emb.map(m -> m.getEmployeeCode()).orElse(""))
					.employeeName(emb.map(m -> m.getBusinessName()).orElse(""))
					.employeeNameKana(emb.map(m -> m.getBusinessNameKana()).orElse(""))
					.build();
		}).collect(Collectors.toList());
	}
}
