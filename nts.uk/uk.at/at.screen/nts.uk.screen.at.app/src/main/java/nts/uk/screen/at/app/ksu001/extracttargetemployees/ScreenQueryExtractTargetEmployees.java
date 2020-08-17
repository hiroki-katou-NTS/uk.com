/**
 * 
 */
package nts.uk.screen.at.app.ksu001.extracttargetemployees;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
import nts.uk.ctx.at.schedule.dom.employeeinfo.medicalworkstyle.EmpMedicalWorkStyleHistoryRepository;
import nts.uk.ctx.at.schedule.dom.employeeinfo.medicalworkstyle.NurseClassification;
import nts.uk.ctx.at.schedule.dom.employeeinfo.medicalworkstyle.NurseClassificationRepository;
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
import nts.uk.ctx.sys.auth.dom.algorithm.AcquireUserIDFromEmpIDService;
import nts.uk.query.pub.employee.EmployeeSearchQueryDto;
import nts.uk.query.pub.employee.RegulationInfoEmployeeExport;
import nts.uk.query.pub.employee.RegulationInfoEmployeePub;
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
	private RegulationInfoEmployeeAdapter regulInfoEmployeeAdap;
	@Inject
	private RegulationInfoEmployeePub regulInfoEmpPub;
	@Inject
	private AcquireUserIDFromEmpIDService acquireUserIDFromEmpIDService;
	
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
	
	final static String SPACE = " ";
	final static String ZEZO_TIME = "00:00";
	final static String DATE_TIME_FORMAT = "yyyy/MM/dd HH:mm";
	
	public List<EmployeeInformationImport> dataSample(ExtractTargetEmployeesParam param) {
		List<String> sids = Arrays.asList("fc4304be-8121-4bad-913f-3e48f4e2a752",
				"338c26ac-9b80-4bab-aa11-485f3c624186", "89ea1474-d7d8-4694-9e9b-416ea1d6381c",
				"ae7fe82e-a7bd-4ce3-adeb-5cd403a9d570", "8f9edce4-e135-4a1e-8dca-ad96abe405d6",
				"9787c06b-3c71-4508-8e06-c70ad41f042a", "62785783-4213-4a05-942b-c32a5ffc1d63",
				"4859993b-8065-4789-90d6-735e3b65626b", "aeaa869d-fe62-4eb2-ac03-2dde53322cb5",
				"70c48cfa-7e8d-4577-b4f6-7b715c091f24", "c141daf2-70a4-4f4b-a488-847f4686e848");
		// step 2, 3
		EmployeeInformationQueryDtoImport input = new EmployeeInformationQueryDtoImport(sids, param.baseDate, false,
				false, false, false, false, false);
		List<EmployeeInformationImport> listEmp = empInfoAdapter.getEmployeeInfo(input);
		// step 4 gọi domainSv 社員を並び替える.
		return listEmp;
	}
	
	
	public List<EmployeeInformationImport> getListEmp(ExtractTargetEmployeesParam param) {
		
		// step 1 get domainSv 組織を指定して参照可能な社員を取得する
		RequireGetEmpImpl requireGetEmpImpl = new RequireGetEmpImpl(workplaceGroupAdapter, regulInfoEmployeeAdap, regulInfoEmpPub, acquireUserIDFromEmpIDService);
		String epmloyeeId = AppContexts.user().employeeId();
		TargetOrgIdenInfor targetOrgIdenInfor = param.targetOrgIdenInfor;
		List<String> sids = GetEmpCanReferBySpecOrganizationService.getListEmpID(requireGetEmpImpl, param.baseDate,epmloyeeId , targetOrgIdenInfor);
		
		// step 2, 3
		EmployeeInformationQueryDtoImport input = new EmployeeInformationQueryDtoImport(sids, param.baseDate, false, false, false, false, false, false);

		List<EmployeeInformationImport> listEmp = empInfoAdapter.getEmployeeInfo(input);
		List<String> sids2 = listEmp.stream().map(m -> m.getEmployeeId()).collect(Collectors.toList());
		
		// step 4 gọi domainSv 社員を並び替える.
		RequireSortEmpImpl requireSortEmpImpl = new RequireSortEmpImpl(sortSettingRepo, belongScheduleTeamRepo,
				employeeRankRepo, rankRepo, syJobTitleAdapter, syClassificationAdapter, empMedicalWorkStyleHisRepo,
				nurseClassificationRepo);	
		// 並び順に基づいて社員を並び替える(Require, 年月日, List<社員ID>)
		List<String> listSidOrder = SortEmpService.sortEmpTheirOrder(requireSortEmpImpl, param.baseDate, sids2);		
				
		listEmp.sort(Comparator.comparingInt(listSidOrder::indexOf));
		
		return listEmp;
		
	}
	
	@AllArgsConstructor
	private static class RequireGetEmpImpl implements GetEmpCanReferBySpecOrganizationService.Require {
		
		@Inject
		private WorkplaceGroupAdapter workplaceGroupAdapter;
		@Inject
		private RegulationInfoEmployeeAdapter regulInfoEmpAdap;
		@Inject
		private RegulationInfoEmployeePub regulInfoEmpPub;
		@Inject
		private AcquireUserIDFromEmpIDService acquireUserIDFromEmpIDService;
		
		@Override
		public List<String> getReferableEmp(GeneralDate date, String empId, String workplaceGroupID) {
			List<String> data = workplaceGroupAdapter.getReferableEmp( date, empId, workplaceGroupID);
			return data;
		}

		@Override
		public List<String> sortEmployee(List<String> lstmployeeId, Integer sysAtr, Integer sortOrderNo,
				GeneralDate referenceDate, Integer nameType) {
			List<String> data = regulInfoEmpAdap.sortEmployee(AppContexts.user().companyId(), lstmployeeId, sysAtr, sortOrderNo, nameType, 
					GeneralDateTime.fromString(referenceDate.toString() + SPACE + ZEZO_TIME, DATE_TIME_FORMAT));
			return data;
		}

		@Override
		public String getRoleID(GeneralDate date, String employId) {
			// (Lấy userID từ employeeID)
			Optional<String> userID = acquireUserIDFromEmpIDService.getUserIDByEmpID(employId);
			if (!userID.isPresent()) {
				return null;
			}
			String roleId = AppContexts.user().roles().forAttendance();
			return roleId;
		}

		@Override
		public List<String> searchEmployee(RegulationInfoEmpQuery q, String roleId) {
			EmployeeSearchQueryDto query = EmployeeSearchQueryDto.builder()
					.baseDate(GeneralDateTime.fromString(q.getBaseDate().toString() + SPACE + ZEZO_TIME, DATE_TIME_FORMAT))
					.referenceRange(q.getReferenceRange())
					.filterByEmployment(q.getFilterByEmployment())
					.employmentCodes(q.getEmploymentCodes())
					.filterByDepartment(q.getFilterByDepartment())
					.departmentCodes(q.getDepartmentCodes())
					.filterByWorkplace(q.getFilterByWorkplace())
					.workplaceCodes(q.getWorkplaceIds())
					.filterByClassification(q.getFilterByClassification())
					.classificationCodes(q.getClassificationCodes())
					.filterByJobTitle(q.getFilterByJobTitle())
					.jobTitleCodes(q.getJobTitleCodes())
					.filterByWorktype(q.getFilterByWorktype())
					.worktypeCodes(q.getWorktypeCodes())
					.filterByClosure(q.getFilterByClosure())
					.closureIds(q.getClosureIds())
					.periodStart(GeneralDateTime.fromString(q.getPeriodStart().toString() + SPACE + ZEZO_TIME, DATE_TIME_FORMAT))
					.periodEnd(GeneralDateTime.fromString(q.getPeriodEnd().toString() + SPACE + ZEZO_TIME, DATE_TIME_FORMAT))
					.includeIncumbents(q.getIncludeIncumbents())
					.includeWorkersOnLeave(q.getIncludeWorkersOnLeave())
					.includeOccupancy(q.getIncludeOccupancy())
					.includeRetirees(q.getIncludeRetirees())
					.includeAreOnLoan(q.getIncludeAreOnLoan())
					.includeGoingOnLoan(q.getIncludeGoingOnLoan())
					.retireStart(GeneralDateTime.fromString(q.getRetireStart().toString() + SPACE + ZEZO_TIME, DATE_TIME_FORMAT))
					.retireEnd(GeneralDateTime.fromString(q.getRetireEnd().toString() + SPACE + ZEZO_TIME, DATE_TIME_FORMAT))
					.sortOrderNo(q.getSortOrderNo())
					.nameType(q.getNameType())
					.systemType(q.getSystemType())
					.build();
			List<RegulationInfoEmployeeExport> data = regulInfoEmpPub.find(query);
			List<String> resultList = data.stream().map(item -> item.getEmployeeId())
					.collect(Collectors.toList());
			return resultList;
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
		public List<EmpClassifiImport> get(GeneralDate ymd, List<String> lstEmpId) {
			List<EmpClassifiImport> data = syClassificationAdapter.getByListSIDAndBasedate(ymd, lstEmpId);
			return data;
		}
		
		@Override
		public Optional<RankPriority> getRankPriority() {
			Optional<RankPriority> data = rankRepo.getRankPriority(AppContexts.user().companyId());
			return data;
		}

		@Override
		public List<EmpMedicalWorkFormHisItem> get(List<String> listEmp, GeneralDate referenceDate) {
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
