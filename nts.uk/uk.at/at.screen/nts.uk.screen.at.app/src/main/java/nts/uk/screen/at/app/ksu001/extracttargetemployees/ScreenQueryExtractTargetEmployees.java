/**
 *
 */
package nts.uk.screen.at.app.ksu001.extracttargetemployees;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.function.dom.adapter.annualworkschedule.EmployeeInformationAdapter;
import nts.uk.ctx.at.function.dom.adapter.annualworkschedule.EmployeeInformationImport;
import nts.uk.ctx.at.function.dom.adapter.annualworkschedule.EmployeeInformationQueryDtoImport;
import nts.uk.ctx.at.schedule.dom.adapter.classification.SyClassificationAdapter;
import nts.uk.ctx.at.schedule.dom.adapter.jobtitle.PositionImport;
import nts.uk.ctx.at.schedule.dom.adapter.jobtitle.SyJobTitleAdapter;
import nts.uk.ctx.at.schedule.dom.employeeinfo.employeesort.EmpClassifiImport;
import nts.uk.ctx.at.schedule.dom.employeeinfo.employeesort.EmployeePosition;
import nts.uk.ctx.at.schedule.dom.employeeinfo.employeesort.SortSetting;
import nts.uk.ctx.at.schedule.dom.employeeinfo.employeesort.SortSettingRepository;
import nts.uk.ctx.at.schedule.dom.employeeinfo.rank.EmployeeRank;
import nts.uk.ctx.at.schedule.dom.employeeinfo.rank.EmployeeRankRepository;
import nts.uk.ctx.at.schedule.dom.employeeinfo.rank.RankPriority;
import nts.uk.ctx.at.schedule.dom.employeeinfo.rank.RankRepository;
import nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam.BelongScheduleTeam;
import nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam.BelongScheduleTeamRepository;
import nts.uk.ctx.at.shared.app.find.supportmanagement.supportableemployee.SupportableEmployeeFinder;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalworkstyle.EmpMedicalWorkFormHisItem;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalworkstyle.EmpMedicalWorkStyleHistoryRepository;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalworkstyle.NurseClassification;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalworkstyle.NurseClassificationRepository;
import nts.uk.ctx.at.shared.dom.supportmanagement.supportableemployee.SupportableEmployee;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.EmployeeSearchCallSystemType;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.GetEmpCanReferService;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.RegulationInfoEmpQuery;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.adapter.WorkplaceGroupAdapter;
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
	private RegulationInfoEmployeePub regulInfoEmpPub;
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
	@Inject
	private SupportableEmployeeFinder supportableEmpFinder;

	final static String SPACE = " ";
	final static String ZEZO_TIME = "00:00";
	final static String DATE_TIME_FORMAT = "yyyy/MM/dd HH:mm";

	public List<EmployeeInformationImport> getListEmp(ExtractTargetEmployeesParam param) {

		// step 1 get domainSv 組織を指定して参照可能な社員を取得する
		RequireGetEmpImpl requireGetEmpImpl = new RequireGetEmpImpl();
		String epmloyeeId = AppContexts.user().employeeId();
		TargetOrgIdenInfor targetOrgIdenInfor = param.targetOrgIdenInfor; // step2
		// danh sach nhân viên thuộc trực thuộc workplace
		List<String> listSidByOrg = GetEmpCanReferService.getByOrg(requireGetEmpImpl, epmloyeeId, param.systemDate, param.period, targetOrgIdenInfor);
		
		// step3 call ScreenQuery 応援者を取得する
		// list này chỉ bao gồm nhân viên đi support và nhân viên đến support
		List<SupportableEmployee> allEmpployeeSupport = supportableEmpFinder.get(listSidByOrg, null, param.period);
		// list nhân viên trực thuộc workplace(workplaceGroup) đi support
		List<String> listEmployeeGotoSupport = allEmpployeeSupport.stream()
				.map(i -> i.getEmployeeId().toString())
				.filter(x -> listSidByOrg.contains(x)).collect(Collectors.toList());
		// list nhân viên đên support cho phòng ban
		List<String> listEmployeeCometoSupport = allEmpployeeSupport.stream()
				.map(i -> i.getEmployeeId().toString())
				.filter(x -> !listSidByOrg.contains(x)).collect(Collectors.toList());
		
		// danh sach sid bao gồm sid của workplace và của những nhân viên đến support
		List<String> allSID = Stream.concat(listSidByOrg.stream(), listEmployeeCometoSupport.stream()).collect(Collectors.toList());
		
		// step 4: create 取得したい社員情報
		EmployeeInformationQueryDtoImport input = new EmployeeInformationQueryDtoImport(allSID, param.systemDate, false, false, false, false, false, false);

		// step5 <<Public>> 社員の情報を取得する
		List<EmployeeInformationImport> listEmployeeInformation = empInfoAdapter.getEmployeeInfo(input);
		
		//2020/9/7　発注済み step 6
		//※スケ①-5_スケ修正(職場別)
		if(listEmployeeInformation.isEmpty()){
			throw new BusinessException("Msg_1779");
		}
		
		// set supportType
		// list info nhân viên trực thuộc workplace(workplaceGroup)
		List<EmployeeInformationImport> listEmployeeInformationByOrg = listEmployeeInformation.stream()
				.filter(i -> listSidByOrg.contains(i.getEmployeeId())).collect(Collectors.toList());
		updateSupportType(listEmployeeInformationByOrg, SupportType.DO_NOT_GO_TO_SUPPORT.value, listEmployeeGotoSupport);
		
		// list info nhân viên đên support cho phòng ban
		List<EmployeeInformationImport> listEmpComeToSupport = listEmployeeInformation.stream()
				.filter(i -> !listSidByOrg.contains(i.getEmployeeId())).collect(Collectors.toList());
		updateSupportType(listEmpComeToSupport, SupportType.COME_TO_SUPPORT.value, new ArrayList<>());
		
		
		// sort
		// sort list nhân viên đến support (luôn ở step này để dưới UI không phải sort nữa)
		listEmpComeToSupport.sort( Comparator.comparing(EmployeeInformationImport :: getEmployeeCode)); 
		listEmployeeInformationByOrg.sort( Comparator.comparing(EmployeeInformationImport :: getEmployeeCode));

		// step 7 call AR_並び替え設定.
		RequireSortEmpImpl requireSortEmpImpl = new RequireSortEmpImpl(belongScheduleTeamRepo,
				employeeRankRepo, rankRepo, syJobTitleAdapter, syClassificationAdapter, empMedicalWorkStyleHisRepo,
				nurseClassificationRepo);
		// 並び替える(Require, 年月日, List<社員ID>)
		Optional<SortSetting> sortSetting = sortSettingRepo.get(AppContexts.user().companyId());
		// if $並び替え設定.empty---return 社員IDリスト
		if (!sortSetting.isPresent()) {
			return Stream.concat(listEmployeeInformationByOrg.stream(), listEmpComeToSupport.stream())
                    .collect(Collectors.toList());
		}

		// sort list nhân viên trực thuộc workplace|workplaceGroup
		List<String> listSIDByOrg = listEmployeeInformationByOrg.stream().map(m -> m.getEmployeeId()).collect(Collectors.toList());
		List<String> listSidOrder = sortSetting.get().sort(requireSortEmpImpl, param.systemDate, listSIDByOrg);

		listEmployeeInformationByOrg.sort(Comparator.comparing(v-> listSidOrder.indexOf(v.getEmployeeId())));
		
		// cộng 2 list nhân viên đã sort 
		return Stream.concat(listEmployeeInformationByOrg.stream(), listEmpComeToSupport.stream())
                .collect(Collectors.toList());
	}
	
	private void updateSupportType(List<EmployeeInformationImport> listEmpInfor, int supportType, List<String> listEmpGotoSupport) {
		
		if (supportType == SupportType.COME_TO_SUPPORT.value) {
			
			listEmpInfor.forEach(e -> e.setSupportType(SupportType.COME_TO_SUPPORT.value));
		} else {

			listEmpInfor.forEach(e -> {
				if (listEmpGotoSupport.contains(e.getEmployeeId())) {
					e.setSupportType(SupportType.GO_TO_SUPPORT.value);
				} else {
					e.setSupportType(SupportType.DO_NOT_GO_TO_SUPPORT.value);
				}
			});
		}
	}

	@AllArgsConstructor
	private class RequireGetEmpImpl implements GetEmpCanReferService.Require {

		@Override
		public List<String> getEmpCanReferByWorkplaceGroup(String empId, GeneralDate date, DatePeriod period, String workplaceGroupID) {
			List<String> data = workplaceGroupAdapter.getReferableEmp(empId, date, period, workplaceGroupID);
			return data;
		}

		@Override
		public List<String> getAllEmpCanReferByWorkplaceGroup(String empId, GeneralDate date, DatePeriod period) {
			// don't have to implement it
			return null;
		}

		@Override
		public List<String> sortEmployee(List<String> lstmployeeId, EmployeeSearchCallSystemType sysAtr, Integer sortOrderNo,
				GeneralDate referenceDate, Integer nameType) {
			List<String> data = regulInfoEmpPub.sortEmployee(
					AppContexts.user().companyId(),
					lstmployeeId,
					sysAtr.value,
					sortOrderNo,
					nameType,
					GeneralDateTime.fromString(referenceDate.toString() + SPACE + ZEZO_TIME, DATE_TIME_FORMAT));
			return data;
		}

		@Override
		public String getRoleID() {

			return AppContexts.user().roles().forAttendance();
		}

		@Override
		public List<String> searchEmployee(RegulationInfoEmpQuery q, String roleId) {
			EmployeeSearchQueryDto query = EmployeeSearchQueryDto.builder()
					.baseDate(GeneralDateTime.fromString(q.getBaseDate().toString() + SPACE + ZEZO_TIME, DATE_TIME_FORMAT))
					.referenceRange(q.getReferenceRange().value)
					.systemType(q.getSystemType().value)
					.filterByWorkplace(q.getFilterByWorkplace())
					.workplaceCodes(q.getWorkplaceIds())
					.filterByEmployment(false)
					.employmentCodes(new ArrayList<String>())
					.filterByDepartment(false)
					.departmentCodes(new ArrayList<String>())
					.filterByClassification(false)
					.classificationCodes(new ArrayList<String>())
					.filterByJobTitle(false)
					.jobTitleCodes(new ArrayList<String>())
					.filterByWorktype(false)
					.worktypeCodes(new ArrayList<String>())
					.filterByClosure(false)
					.closureIds(new ArrayList<Integer>())
					.periodStart( GeneralDateTime.fromString(q.getPeriodStart() + SPACE + ZEZO_TIME, DATE_TIME_FORMAT) )
					.periodEnd( GeneralDateTime.fromString(q.getPeriodEnd() + SPACE + ZEZO_TIME, DATE_TIME_FORMAT) )
					.includeIncumbents(true)
					.includeWorkersOnLeave(true)
					.includeOccupancy(true)
					.includeRetirees(false)
					.includeAreOnLoan(false)
					.includeGoingOnLoan(false)
					.retireStart(GeneralDateTime.now())
					.retireEnd(GeneralDateTime.now())
					.sortOrderNo(null)
					.nameType(null)

					.build();
			List<RegulationInfoEmployeeExport> data = regulInfoEmpPub.find(query);
			List<String> resultList = data.stream().map(item -> item.getEmployeeId())
					.collect(Collectors.toList());
			return resultList;
		}

	}

	@AllArgsConstructor
	private static class RequireSortEmpImpl implements SortSetting.Require {

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
		public List<BelongScheduleTeam> getScheduleTeam(List<String> empIDs) {
			return belongScheduleTeamRepo.get(AppContexts.user().companyId(), empIDs);
		}

		@Override
		public List<EmployeeRank> getEmployeeRanks(List<String> lstSID) {
			return employeeRankRepo.getAll(lstSID);
		}

		@Override
		public List<EmployeePosition> getPositionEmps(GeneralDate ymd, List<String> lstEmp) {
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
		public Optional<RankPriority> getRankPriorities() {
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
