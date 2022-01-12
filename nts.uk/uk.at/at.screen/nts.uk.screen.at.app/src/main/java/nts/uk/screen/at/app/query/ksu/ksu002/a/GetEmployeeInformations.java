package nts.uk.screen.at.app.query.ksu.ksu002.a;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.adapter.workplace.EmployeeInfoImported;
import nts.uk.ctx.at.record.dom.adapter.workplace.SyWorkplaceAdapter;
import nts.uk.ctx.bs.employee.pub.workplace.AffWorkplaceHistoryItemExport;
import nts.uk.ctx.bs.employee.pub.workplace.master.WorkplacePub;
import nts.uk.screen.at.app.query.ksu.ksu002.a.dto.EmployeeInfoDto;
import nts.uk.screen.at.app.query.ksu.ksu002.a.dto.GetEmployeeInformationsDto;
import nts.uk.shr.com.context.AppContexts;

/**
 * DS: 社員情報リストを取得する
 * UKDesign.UniversalK.就業.KSU_スケジュール.KSU002_個人スケジュール修正(個人別).A:メイン画面.メニュー別OCD.社員情報リストを取得する
 * 
 * @author chungnt
 *
 */

@Stateless
public class GetEmployeeInformations {

	@Inject
	private WorkplacePub workplacePub;
	
	@Inject
	private SyWorkplaceAdapter syWorkplaceAdapter;
	
	public GetEmployeeInformationsDto getEmployeeInformations() {
		GetEmployeeInformationsDto result = new GetEmployeeInformationsDto();
		List<String> workPlaceIDs = new ArrayList<>();

		AffWorkplaceHistoryItemExport workPlaceInfo = workplacePub.getAffWkpHistItemByEmpDate(AppContexts.user().employeeId(), GeneralDate.today());
		workPlaceIDs.add(workPlaceInfo.getWorkplaceId());
		
		List<EmployeeInfoImported> employeeInfo = syWorkplaceAdapter.getLstEmpByWorkplaceIdsAndPeriod(workPlaceIDs, new DatePeriod(GeneralDate.today(), GeneralDate.today()));
		
		employeeInfo.stream().forEach(f -> {
			result.employeeInfos.add(new EmployeeInfoDto(f.getSid(), f.getEmployeeCode(), f.getEmployeeName()));
		});
		
		return result;
	}
}
