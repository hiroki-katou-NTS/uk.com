/**
 * 
 */
package nts.uk.screen.at.app.ksu001;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.RequiredArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.function.dom.adapter.annualworkschedule.EmployeeInformationAdapter;
import nts.uk.ctx.at.function.dom.adapter.annualworkschedule.EmployeeInformationImport;
import nts.uk.ctx.at.function.dom.adapter.annualworkschedule.EmployeeInformationQueryDtoImport;
import nts.uk.ctx.at.schedule.dom.adapter.jobtitle.PositionImport;
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
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.ctx.bs.employee.pub.jobtitle.EmployeeJobHistExport;
import nts.uk.ctx.bs.employee.pub.jobtitle.JobTitleExport;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.ctx.at.schedule.dom.adapter.jobtitle.SyJobTitleAdapter;

/**
 * @author laitv
 * ScreenQuery : 対象社員を抽出する
 * path: UKDesign.UniversalK.就業.KSU_スケジュール.KSU001_個人スケジュール修正(職場別).A：個人スケジュール修正（職場別）.メニュー別OCD.対象社員を抽出する
 */
@Stateless
public class ExtractTargetEmployees {
	
	@Inject
	private EmployeeInformationAdapter empInfoAdapter;
	
	
	/**
	 * 
	 * @param baseDate 基準日: 年月日
	 * @param targetOrgIdenInfor 対象組織: 対象組織識別情報
	 */
	public void getListEmployee(GeneralDate baseDate, TargetOrgIdenInfor targetOrgIdenInfor){
		
		//step 1 get domainSv 組織を指定して参照可能な社員を取得する
		// wkplId 9d68af4d-437d-4362-a118-65899039c38f
		List<String> sids =  Arrays.asList("fc4304be-8121-4bad-913f-3e48f4e2a752",
				"338c26ac-9b80-4bab-aa11-485f3c624186",
				"89ea1474-d7d8-4694-9e9b-416ea1d6381c");
		
		// step 2, 3
		EmployeeInformationQueryDtoImport input =new EmployeeInformationQueryDtoImport(sids, baseDate, 
				false, false, false, false, false, false);
		
		List<EmployeeInformationImport> listEmp = empInfoAdapter.getEmployeeInfo(input);
		
		// step 4 gọi domainSv 社員を並び替える.
		
		
	}
	
	@RequiredArgsConstructor
	private static class RequireImpl implements SortEmpService.Require {
		
		private final SortSettingRepository sortSettingRepo;
		
		private final BelongScheduleTeamRepository belongScheduleTeamRepo;
		
		private final EmployeeRankRepository employeeRankRepo;
		
		private final RankRepository rankRepo;
		
		private final SyJobTitleAdapter syJobTitleAdapter;


		@Override
		public Optional<SortSetting> get() {
			return sortSettingRepo.get(AppContexts.user().companyId());
		}

		@Override
		public Optional<BelongScheduleTeam> get(String companyID, List<String> empIDs) {
			return belongScheduleTeamRepo.get(companyID, empIDs);
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
			
			return null;
		}
		
		
		
	
		
	}

}
