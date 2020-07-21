/**
 * 
 */
package nts.uk.screen.at.app.ksu001.extracttargetemployees;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import lombok.RequiredArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.function.dom.adapter.annualworkschedule.EmployeeInformationAdapter;
import nts.uk.ctx.at.function.dom.adapter.annualworkschedule.EmployeeInformationImport;
import nts.uk.ctx.at.function.dom.adapter.annualworkschedule.EmployeeInformationQueryDtoImport;
import nts.uk.ctx.at.schedule.dom.adapter.classification.SyClassificationAdapter;
import nts.uk.ctx.at.schedule.dom.adapter.jobtitle.PositionImport;
import nts.uk.ctx.at.schedule.dom.adapter.jobtitle.SyJobTitleAdapter;
import nts.uk.ctx.at.schedule.dom.employeeinfo.employeesort.EmpClassifiImport;
import nts.uk.ctx.at.schedule.dom.employeeinfo.employeesort.EmployeePosition;
import nts.uk.ctx.at.schedule.dom.employeeinfo.employeesort.SortEmpService;
import nts.uk.ctx.at.schedule.dom.employeeinfo.employeesort.SortSetting;
import nts.uk.ctx.at.schedule.dom.employeeinfo.employeesort.SortSettingRepository;
import nts.uk.ctx.at.schedule.dom.employeeinfo.rank.EmployeeRank;
import nts.uk.ctx.at.schedule.dom.employeeinfo.rank.EmployeeRankRepository;
import nts.uk.ctx.at.schedule.dom.employeeinfo.rank.RankPriority;
import nts.uk.ctx.at.schedule.dom.employeeinfo.rank.RankRepository;
import nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam.BelongScheduleTeam;
import nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam.BelongScheduleTeamRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author laitv
 * <<ScreenQuery>> 対象社員を抽出する
 *
 */
public class ScreenQueryExtractTargetEmployees {
	
	@Inject
	private EmployeeInformationAdapter empInfoAdapter;
	
	public List<EmployeeInformationImport> getListEmp(ExtractTargetEmployeesParam param) {
		
		// step 1 get domainSv 組織を指定して参照可能な社員を取得する
		// wkplId 9d68af4d-437d-4362-a118-65899039c38f
		List<String> sids = Arrays.asList(
				"fc4304be-8121-4bad-913f-3e48f4e2a752",
				"338c26ac-9b80-4bab-aa11-485f3c624186", 
				"89ea1474-d7d8-4694-9e9b-416ea1d6381c",
				"ae7fe82e-a7bd-4ce3-adeb-5cd403a9d570",
				"8f9edce4-e135-4a1e-8dca-ad96abe405d6",
				"9787c06b-3c71-4508-8e06-c70ad41f042a",
				"62785783-4213-4a05-942b-c32a5ffc1d63",
				"4859993b-8065-4789-90d6-735e3b65626b",
				"aeaa869d-fe62-4eb2-ac03-2dde53322cb5",
				"70c48cfa-7e8d-4577-b4f6-7b715c091f24",
				"c141daf2-70a4-4f4b-a488-847f4686e848");

		// step 2, 3
		EmployeeInformationQueryDtoImport input = new EmployeeInformationQueryDtoImport(sids, param.baseDate, false, false,
				false, false, false, false);

		List<EmployeeInformationImport> listEmp = empInfoAdapter.getEmployeeInfo(input);
		
		// step 4 gọi domainSv 社員を並び替える.
		
		return listEmp;
		
	}
	
	@RequiredArgsConstructor
	private static class RequireImpl implements SortEmpService.Require {
		
		private final SortSettingRepository sortSettingRepo;
		
		private final BelongScheduleTeamRepository belongScheduleTeamRepo;
		
		private final EmployeeRankRepository employeeRankRepo;
		
		private final RankRepository rankRepo;
		
		private final SyJobTitleAdapter syJobTitleAdapter;
		
		private final SyClassificationAdapter syClassificationAdapter;


		@Override
		public Optional<SortSetting> get() {
			return sortSettingRepo.get(AppContexts.user().companyId());
		}

		@Override
		public Optional<BelongScheduleTeam> get(List<String> empIDs) {
			return belongScheduleTeamRepo.get(AppContexts.user().companyId(), empIDs);
		}

		@Override
		public List<EmployeeRank> getAll(List<String> lstSID) {
			return employeeRankRepo.getAll(lstSID);
		}

		@Override
		public Optional<RankPriority> getRankPriority(String companyId) {
			return rankRepo.getRankPriority(companyId);
		}

		@Override
		public List<EmployeePosition> getPositionEmp(GeneralDate ymd, List<String> lstEmp) {
			List<EmployeePosition> data = syJobTitleAdapter.findSJobHistByListSIdV2(lstEmp, ymd);
			return data;
		}

		@Override
		public List<PositionImport> getCompanyPosition(GeneralDate ymd) {
			List<PositionImport> data = syJobTitleAdapter.findAll(AppContexts.user().companyId(), ymd);
			return data;
		}

		@Override
		public List<EmpClassifiImport> get(GeneralDate ymd, List<String> lstEmpId) {
			List<EmpClassifiImport> data = syClassificationAdapter.getByListSIDAndBasedate(ymd, lstEmpId);
			return data;
		}
	}
}
