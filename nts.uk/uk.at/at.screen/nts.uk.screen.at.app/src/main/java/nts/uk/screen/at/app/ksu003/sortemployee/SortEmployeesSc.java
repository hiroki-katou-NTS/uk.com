package nts.uk.screen.at.app.ksu003.sortemployee;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.adapter.classification.SyClassificationAdapter;
import nts.uk.ctx.at.schedule.dom.adapter.jobtitle.PositionImport;
import nts.uk.ctx.at.schedule.dom.adapter.jobtitle.SyJobTitleAdapter;
import nts.uk.ctx.at.schedule.dom.employeeinfo.employeesort.EmpClassifiImport;
import nts.uk.ctx.at.schedule.dom.employeeinfo.employeesort.EmployeePosition;
import nts.uk.ctx.at.schedule.dom.employeeinfo.employeesort.SortEmpService;
import nts.uk.ctx.at.schedule.dom.employeeinfo.employeesort.SortSetting;
import nts.uk.ctx.at.schedule.dom.employeeinfo.employeesort.SortSettingRepository;
import nts.uk.ctx.at.schedule.dom.employeeinfo.medicalworkstyle.EmpMedicalWorkFormHisItem;
import nts.uk.ctx.at.schedule.dom.employeeinfo.medicalworkstyle.EmpMedicalWorkStyleHistoryRepository;
import nts.uk.ctx.at.schedule.dom.employeeinfo.medicalworkstyle.NurseClassification;
import nts.uk.ctx.at.schedule.dom.employeeinfo.medicalworkstyle.NurseClassificationRepository;
import nts.uk.ctx.at.schedule.dom.employeeinfo.rank.EmployeeRank;
import nts.uk.ctx.at.schedule.dom.employeeinfo.rank.EmployeeRankRepository;
import nts.uk.ctx.at.schedule.dom.employeeinfo.rank.RankPriority;
import nts.uk.ctx.at.schedule.dom.employeeinfo.rank.RankRepository;
import nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam.BelongScheduleTeam;
import nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam.BelongScheduleTeamRepository;
import nts.uk.shr.com.context.AppContexts;
/**
 * 社員を並び替える
 * UKDesign.UniversalK.就業.KSU_スケジュール.KSU003_個人スケジュール修正(日付別).A：個人スケジュール修正(日付別).メニュー別OCD.社員を並び替える
 * @author phongtq
 *
 */
@Stateless
public class SortEmployeesSc {
	
	@Inject
	private  SortSettingRepository sortSettingRepo;
	@Inject
	private  BelongScheduleTeamRepository belongScheduleTeamRepo;
	@Inject
	private  EmployeeRankRepository employeeRankRepo;
	@Inject
	private  RankRepository rankRepo;
	@Inject
	private  SyJobTitleAdapter syJobTitleAdapter;
	@Inject
	private  SyClassificationAdapter syClassificationAdapter;
	@Inject
	private EmpMedicalWorkStyleHistoryRepository empMedicalWorkStyleHisRepo;
	@Inject
	private NurseClassificationRepository nurseClassificationRepo;
	
	public List<String> sortEmployee(SortEmployeeParam param){
		
		GeneralDate ymd = GeneralDate.fromString(param.getYmd(), "yyyy/MM/dd");
		List<String> lstEmpId = param.getLstEmpId();
		List<String> lstSortEmployee = new ArrayList<>();
		
		// 1 .並び順に基づいて社員を並び替える(Require, 年月日, List<社員ID>)
		RequireSortEmpImpl empImpl = new RequireSortEmpImpl(sortSettingRepo, belongScheduleTeamRepo, employeeRankRepo, 
				rankRepo, syJobTitleAdapter, syClassificationAdapter, empMedicalWorkStyleHisRepo, nurseClassificationRepo);
		lstSortEmployee = SortEmpService.sortEmpTheirOrder(empImpl, ymd, lstEmpId);
		
		return lstSortEmployee;
	}
	
	@AllArgsConstructor
	private static class RequireSortEmpImpl implements SortEmpService.Require {
		
		@Inject
		private  SortSettingRepository sortSettingRepo;
		@Inject
		private  BelongScheduleTeamRepository belongScheduleTeamRepo;
		@Inject
		private  EmployeeRankRepository employeeRankRepo;
		@Inject
		private  RankRepository rankRepo;
		@Inject
		private  SyJobTitleAdapter syJobTitleAdapter;
		@Inject
		private  SyClassificationAdapter syClassificationAdapter;
		@Inject
		private EmpMedicalWorkStyleHistoryRepository empMedicalWorkStyleHisRepo;
		@Inject
		private NurseClassificationRepository nurseClassificationRepo;

		@Override
		public Optional<SortSetting> get() {
			return sortSettingRepo.get(AppContexts.user().companyId());
		}

		@Override
		public List<BelongScheduleTeam> get(List<String> empIDs) {
			return belongScheduleTeamRepo.get(AppContexts.user().companyId(), empIDs);
		}

		@Override
		public List<EmployeeRank> getAll(List<String> lstSID) {
			return employeeRankRepo.getAll(lstSID);
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
		public List<EmpClassifiImport> getEmpClassifications(GeneralDate ymd, List<String> lstEmpId) {
			List<EmpClassifiImport> data = syClassificationAdapter.getByListSIDAndBasedate(ymd, lstEmpId);
			return data;
		}
		
		@Override
		public Optional<RankPriority> getRankPriority() {
			Optional<RankPriority> data = rankRepo.getRankPriority(AppContexts.user().companyId());
			return data;
		}

		@Override
		public List<EmpMedicalWorkFormHisItem> getEmpClassifications(List<String> listEmp, GeneralDate referenceDate) {
			List<EmpMedicalWorkFormHisItem> data = empMedicalWorkStyleHisRepo.get(listEmp, referenceDate);
			return data;
		}

		@Override
		public List<NurseClassification> getListCompanyNurseCategory() {
			List<NurseClassification> data = nurseClassificationRepo.getListCompanyNurseCategory(AppContexts.user().companyId());
			return data;
		}
	}

}
