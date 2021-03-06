/**
 * 
 */
package nts.uk.screen.at.app.ksu001.orderemployee;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.employeeinfo.rank.EmployeeRank;
import nts.uk.ctx.at.schedule.dom.employeeinfo.rank.EmployeeRankRepository;
import nts.uk.ctx.at.schedule.dom.employeeinfo.rank.Rank;
import nts.uk.ctx.at.schedule.dom.employeeinfo.rank.RankRepository;
import nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam.BelongScheduleTeam;
import nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam.BelongScheduleTeamRepository;
import nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam.ScheduleTeam;
import nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam.ScheduleTeamRepository;
import nts.uk.ctx.at.schedule.dom.workschedule.displaysetting.DisplayControlPersonalCondition;
import nts.uk.ctx.at.schedule.dom.workschedule.displaysetting.DisplayControlPersonalConditionRepo;
import nts.uk.ctx.at.schedule.dom.workschedule.displaysetting.PersonalCondition;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle.EmpMedicalWorkStyleHistoryItem;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle.EmpMedicalWorkStyleHistoryRepository;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle.NurseClassification;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle.NurseClassificationRepository;
import nts.uk.screen.at.app.ksu001.eventinformationandpersonal.PersonalConditionsDto;
import nts.uk.screen.at.app.ksu001.extracttargetemployees.EmployeeInformationDto;
import nts.uk.screen.at.app.ksu001.extracttargetemployees.SupportType;
import nts.uk.screen.at.app.ksu001.start.OrderEmployeeParam;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author laitv
 *
 */
@Stateless
public class GetDataAfterSortEmp {
	
	@Inject
	private SortEmployees sortEmployees;
	
	@Inject
	private DisplayControlPersonalConditionRepo displayControlPerCondRepo;
	@Inject
	private BelongScheduleTeamRepository belongScheduleTeamRepo;
	@Inject
	private ScheduleTeamRepository scheduleTeamRepo;
	@Inject
	private EmployeeRankRepository employeeRankRepo;
	@Inject
	private RankRepository rankRepo;
	@Inject
	private EmpMedicalWorkStyleHistoryRepository empMedicalWorkStyleHistoryRepo;
	@Inject
	private NurseClassificationRepository nurseClassificationRepo;
	
	private static final String DATE_FORMAT = "yyyy/MM/dd";
	
	public  DataAfterSortEmpDto getData(OrderEmployeeParam param) {
		// order l???i list nh??n vi??n thu??c workplace th??i, c??n list nh??n vi??n ????n support th?? order theo scd l?? ???????c r???i.
		List<EmployeeInformationDto> listEmpInfoByOrg = param.listEmpInfo.stream()
				.filter(e -> e.supportType != SupportType.COME_TO_SUPPORT.value).collect(Collectors.toList());		
		// list nh??n vi??n ?????n support (kh??ng thu???c workplace)
		List<EmployeeInformationDto> listEmpInfoSupport = param.listEmpInfo.stream()
				.filter(e -> e.supportType == SupportType.COME_TO_SUPPORT.value).collect(Collectors.toList());
		
		listEmpInfoSupport.sort( Comparator.comparing(EmployeeInformationDto :: getEmployeeCode));


		listEmpInfoByOrg.sort( Comparator.comparing(EmployeeInformationDto :: getEmployeeCode));
		
		param.setListEmpInfo(listEmpInfoByOrg);
		
		List<String> listSidByOrgOrder = sortEmployees.getListEmp(param);
		
		// s???p x??p l???i list nh??n vi??n thu???c workplace theo list sid ???? ???????c order t??? thu???t to??n tr??n
		listEmpInfoByOrg.sort(Comparator.comparing( v -> listSidByOrgOrder.indexOf(v.employeeId)));
		
		Optional<DisplayControlPersonalCondition> displayControlPerCond = displayControlPerCondRepo.get(AppContexts.user().companyId());
		if (!displayControlPerCond.isPresent()) {
			return new DataAfterSortEmpDto(
					Stream.concat(listEmpInfoByOrg.stream(), listEmpInfoSupport.stream()).collect(Collectors.toList())
					, new ArrayList<>() );
		}
		
		RequireImplDispControlPerCond requireImplDispControlPerCond = new RequireImplDispControlPerCond(
				belongScheduleTeamRepo, scheduleTeamRepo, employeeRankRepo, rankRepo, empMedicalWorkStyleHistoryRepo,
				nurseClassificationRepo);

		List<PersonalCondition> listPersonalCond = displayControlPerCond.get().acquireInforDisplayControlPersonalCondition(
				requireImplDispControlPerCond, 
				GeneralDate.fromString(param.endDate, DATE_FORMAT),
				param.listEmpInfo.stream().map(i -> i.employeeId).collect(Collectors.toList()));
		
		List<PersonalConditionsDto> listPersonalConditions = listPersonalCond.stream().map(i -> {
			return new PersonalConditionsDto(i);
		}).collect(Collectors.toList());
		
		return new DataAfterSortEmpDto(
				Stream.concat(listEmpInfoByOrg.stream(), listEmpInfoSupport.stream()).collect(Collectors.toList()), 
                listPersonalConditions
                );
	}
	
	@AllArgsConstructor
	private static class RequireImplDispControlPerCond implements DisplayControlPersonalCondition.Require {
		@Inject
		private BelongScheduleTeamRepository belongScheduleTeamRepo;
		@Inject
		private ScheduleTeamRepository scheduleTeamRepo;
		@Inject
		private EmployeeRankRepository employeeRankRepo;
		@Inject
		private RankRepository rankRepo;
		@Inject
		private EmpMedicalWorkStyleHistoryRepository empMedicalWorkStyleHistoryRepo;
		@Inject
		private NurseClassificationRepository nurseClassificationRepo;

		@Override
		public List<BelongScheduleTeam> get(List<String> lstEmpId) {
			List<BelongScheduleTeam> data = belongScheduleTeamRepo.get(AppContexts.user().companyId(), lstEmpId);
			return data;
		}

		@Override
		public List<ScheduleTeam> getAllSchedule(List<String> listWKPGRPID) {
			List<ScheduleTeam> data = scheduleTeamRepo.getAllSchedule(AppContexts.user().companyId(), listWKPGRPID);
			return data;
		}

		@Override
		public List<EmployeeRank> getAll(List<String> lstSID) {
			List<EmployeeRank> data = employeeRankRepo.getAll(lstSID);
			return data;
		}

		@Override
		public List<Rank> getListRank() {
			List<Rank> data = rankRepo.getListRank(AppContexts.user().companyId());
			return data;
		}

		@Override
		public List<EmpMedicalWorkStyleHistoryItem> getEmpMedicalWorkStyleHistoryItem(List<String> listEmp, GeneralDate referenceDate) {
			List<EmpMedicalWorkStyleHistoryItem> data = empMedicalWorkStyleHistoryRepo.get(listEmp, referenceDate);
			return data;
		}

		@Override
		public List<NurseClassification> getListCompanyNurseCategory() {
			List<NurseClassification> data = nurseClassificationRepo
					.getListCompanyNurseCategory(AppContexts.user().companyId());
			return data;
		}
	}

}
