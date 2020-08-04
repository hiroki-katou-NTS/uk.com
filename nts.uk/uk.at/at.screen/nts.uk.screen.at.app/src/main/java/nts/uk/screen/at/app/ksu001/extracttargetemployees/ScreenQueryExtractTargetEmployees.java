/**
 * 
 */
package nts.uk.screen.at.app.ksu001.extracttargetemployees;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.function.dom.adapter.RegulationInfoEmployeeAdapter;
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
import nts.uk.ctx.at.schedule.dom.employeeinfo.medicalworkstyle.EmpMedicalWorkFormHisItem;
import nts.uk.ctx.at.schedule.dom.employeeinfo.medicalworkstyle.NurseClassification;
import nts.uk.ctx.at.schedule.dom.employeeinfo.rank.EmployeeRank;
import nts.uk.ctx.at.schedule.dom.employeeinfo.rank.EmployeeRankRepository;
import nts.uk.ctx.at.schedule.dom.employeeinfo.rank.RankPriority;
import nts.uk.ctx.at.schedule.dom.employeeinfo.rank.RankRepository;
import nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam.BelongScheduleTeam;
import nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam.BelongScheduleTeamRepository;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.GetEmpCanReferBySpecOrganizationService;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.RegulationInfoEmpQuery;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.adapter.WorkplaceGroupAdapter;
import nts.uk.query.model.department.DepartmentAdapter;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author laitv
 * <<ScreenQuery>> 対象社員を抽出する
 *
 */
@Stateless
public class ScreenQueryExtractTargetEmployees {
	
	@Inject
	private EmployeeInformationAdapter empInfoAdapter;
	
	@Inject
	private WorkplaceGroupAdapter workplaceGroupAdapter;
	@Inject
	private RegulationInfoEmployeeAdapter regulationInfoEmployeeAdap;
	
	final static String SPACE = " ";
	final static String ZEZO_TIME = "00:00";
	final static String DATE_TIME_FORMAT = "yyyy/MM/dd HH:mm";
	
	public List<EmployeeInformationImport> getListEmp(ExtractTargetEmployeesParam param) {
		
		// step 1 get domainSv 組織を指定して参照可能な社員を取得する
		RequireGetEmpImpl requireGetEmpImpl = new RequireGetEmpImpl(workplaceGroupAdapter, regulationInfoEmployeeAdap);
		String epmloyeeId = AppContexts.user().employeeId();
		TargetOrgIdenInfor targetOrgIdenInfor = param.targetOrgIdenInfor;
		List<String> sids = GetEmpCanReferBySpecOrganizationService.getListEmpID(requireGetEmpImpl, param.baseDate,epmloyeeId , targetOrgIdenInfor);
		
		// step 2, 3
		EmployeeInformationQueryDtoImport input = new EmployeeInformationQueryDtoImport(sids, param.baseDate, false, false, false, false, false, false);

		List<EmployeeInformationImport> listEmp = empInfoAdapter.getEmployeeInfo(input);
		
		// step 4 gọi domainSv 社員を並び替える.
		
		
		return listEmp;
		
	}
	
	@AllArgsConstructor
	private static class RequireGetEmpImpl implements GetEmpCanReferBySpecOrganizationService.Require {
		
		@Inject
		private WorkplaceGroupAdapter workplaceGroupAdapter;
		@Inject
		private RegulationInfoEmployeeAdapter regulationInfoEmployeeAdap;
		
		
		
		@Override
		public List<String> getReferableEmp(GeneralDate date, String empId, String workplaceGroupID) {
			List<String> data = workplaceGroupAdapter.getReferableEmp( date, empId, workplaceGroupID);
			return data;
		}

		@Override
		public List<String> sortEmployee(List<String> lstmployeeId, Integer sysAtr, Integer sortOrderNo,
				GeneralDate referenceDate, Integer nameType) {
			List<String> data = regulationInfoEmployeeAdap.sortEmployee(AppContexts.user().companyId(), lstmployeeId, sysAtr, sortOrderNo, nameType, 
					GeneralDateTime.fromString(referenceDate.toString() + SPACE + ZEZO_TIME, DATE_TIME_FORMAT));
			return data;
		}

		@Override
		public String getRoleID(GeneralDate date, String employId) {
			
			return null;
		}

		@Override
		public List<String> searchEmployee(RegulationInfoEmpQuery regulationInfoEmpQuery, String roleId) {
			
			
			return null;
		}
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
		public List<EmpClassifiImport> get(GeneralDate ymd, List<String> lstEmpId) {
			List<EmpClassifiImport> data = syClassificationAdapter.getByListSIDAndBasedate(ymd, lstEmpId);
			return data;
		}
		
		@Override
		public Optional<RankPriority> getRankPriority() {
			return rankRepo.getRankPriority(AppContexts.user().companyId());
		}

		@Override
		public List<EmpMedicalWorkFormHisItem> get(List<String> listEmp, GeneralDate referenceDate) {
			
			return null;
		}

		@Override
		public List<NurseClassification> getListCompanyNurseCategory() {
			
			return null;
		}



	}
}
