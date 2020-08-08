package nts.uk.screen.at.app.query.kdl.kdl006.a;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.auth.dom.adapter.role.RoleAdaptor;
import nts.uk.ctx.at.auth.dom.adapter.role.RoleInformationImport;
import nts.uk.ctx.at.auth.dom.adapter.user.UserAuthAdapter;
import nts.uk.ctx.at.auth.dom.adapter.workplace.AffWorkplaceHistoryItemImport;
import nts.uk.ctx.at.auth.dom.adapter.workplace.AuthWorkPlaceAdapter;
import nts.uk.ctx.at.auth.dom.adapter.workplace.WorkplaceManagerImport;
import nts.uk.ctx.at.auth.dom.employmentrole.GetListWorkplacesByEmpsService;
import nts.uk.ctx.at.auth.dom.employmentrole.dto.ClosureInformation;
import nts.uk.ctx.at.auth.dom.employmentrole.dto.RollInformation;
import nts.uk.ctx.at.auth.dom.employmentrole.dto.WorkPlaceAuthorityDto;
import nts.uk.ctx.at.auth.dom.employmentrole.dto.WorkplaceManagerDto;
import nts.uk.ctx.at.record.dom.workrecord.workrecord.EmploymentConfirmed;
import nts.uk.ctx.at.record.dom.workrecord.workrecord.EmploymentConfirmedRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureHistory;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureInfo;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.CurrentMonth;
import nts.uk.ctx.at.shared.dom.workrule.closure.UseClassification;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;
import nts.uk.ctx.sys.auth.dom.wplmanagementauthority.WorkPlaceAuthority;
import nts.uk.ctx.sys.auth.dom.wplmanagementauthority.WorkPlaceAuthorityRepository;
import nts.uk.query.model.employee.EmployeeInformation;
import nts.uk.query.model.employee.EmployeeInformationQuery;
import nts.uk.query.model.employee.EmployeeInformationRepository;
import nts.uk.query.model.workplace.WorkplaceAdapter;
import nts.uk.query.model.workplace.WorkplaceInfoImport;
import nts.uk.screen.at.app.query.kdl.kdl006.a.dto.ClosureInforDto;
import nts.uk.screen.at.app.query.kdl.kdl006.a.dto.WorkPlaceConfirmDto;
import nts.uk.shr.com.context.AppContexts;

/**UKDesign.UniversalK.就業.KDL_ダイアログ.KDL014_打刻参照ダイアログ.メニュー別OCD.打刻実績を参照する.打刻実績の取得処理*/
@Stateless
public class WorkConfirmationFinder {

	@Inject
	private ClosureRepository closureRepository;
	
	@Inject
	private ClosureService closureService;
	
	@Inject
	private UserAuthAdapter userAuthAdapter;
	
	@Inject
	private RoleAdaptor roleAdaptor;
	
	@Inject
	private WorkPlaceAuthorityRepository workPlaceAuthRepo;
	
	@Inject
	private AuthWorkPlaceAdapter authWorkPlaceAdapter;
	
	@Inject
	private WorkplaceAdapter WorkplaceExportService;
	
	@Inject
	private EmploymentConfirmedRepository employmentConfirmedRepo;
	
	@Inject
	private EmployeeInformationRepository employeeInformationRepo;

	public List<ClosureInforDto> DisplayOfWorkConfirmationDialog() {
		String cid = AppContexts.user().companyId();
		List<Closure> lstClosure = closureRepository.findAllActive(cid, UseClassification.UseClass_Use);
		if(lstClosure.isEmpty()) {
			return null;
		}
		List<ClosureInforDto> closureList = new ArrayList<>();
		
		for (Closure closure : lstClosure) {
			List<DatePeriod> lstDatePeriod = closure.getPeriodByYearMonth(closure.getClosureMonth().getProcessingYm());
			Optional<ClosureHistory> closureHistory = closure.getHistoryByYearMonth(closure.getClosureMonth().getProcessingYm());

			GeneralDate startDateMin = lstDatePeriod.stream().map(x -> x.start()).sorted((x, y) -> x.compareTo(y))
					.findFirst().orElse(null);
			GeneralDate endDateMax = lstDatePeriod.stream().map(x -> x.end()).sorted((x, y) -> y.compareTo(x))
					.findFirst().orElse(null);
			if (endDateMax != null && endDateMax != null) {
				closureList.add(new ClosureInforDto(closure.getClosureId().value,
						closure.getClosureMonth().getProcessingYm().v(), closureHistory.isPresent()?closureHistory.get().getClosureName().v() : "", new DatePeriod(startDateMin, endDateMax)));
			}
		}
		closureList.sort(Comparator.comparing(ClosureInforDto::getClosureId));
		
		return closureList; 
		
//		Optional<ClosureInforDto> closure = closureList.stream().filter(c-> c.getClosureId() == closureId).findFirst();
//		if(closure.isPresent()) {
//			return new WorkConfirmationDto(closureList, this.getWorkPlace(closure.get()));
//		}else {
//			return new WorkConfirmationDto(closureList, this.getWorkPlace(closureList.get(0)));
//		}
	}
	
	public List<WorkPlaceConfirmDto> getWorkPlace(ClosureInforDto closure) {
		String cid = AppContexts.user().companyId();
		String employeeId = AppContexts.user().employeeId();
		RequireImpl require = new RequireImpl(closureService, closureRepository, userAuthAdapter, roleAdaptor, workPlaceAuthRepo, authWorkPlaceAdapter, authWorkPlaceAdapter);
		
		List<String> workplace = GetListWorkplacesByEmpsService.get(require, cid, employeeId, Optional.of(closure.closureId));
		
		List<WorkplaceInfoImport> listWorkplaceInfo = WorkplaceExportService.getWorkplaceInfoByWkpIds(cid, workplace, closure.end);
		
		List<EmploymentConfirmed> employmentConfirmedList = employmentConfirmedRepo.get(cid, workplace, ClosureId.valueOf(closure.closureId), new YearMonth(closure.yearMonth));
		
		EmployeeInformationQuery param = EmployeeInformationQuery.builder()
				.employeeIds(employmentConfirmedList.stream().map(c->c.getEmployeeId()).collect(Collectors.toList()))
				.referenceDate(closure.end)
				.toGetClassification(false)
				.toGetDepartment(false)
				.toGetEmployment(false)
				.toGetEmploymentCls(false)
				.toGetPosition(false)
				.toGetWorkplace(false)
				.build();
		
		List<EmployeeInformation> employeeInformationList = employeeInformationRepo.find(param);
		
		List<WorkPlaceConfirmDto> workPlaceConfirmList = new ArrayList<>();
		
		for (WorkplaceInfoImport workplaceInfor : listWorkplaceInfo) {
			Optional<EmploymentConfirmed> employmentConfirmed = employmentConfirmedList.stream().filter(c->c.getWorkplaceId().equals(workplaceInfor.getWorkplaceId())).findFirst();
			String confirmEmployeeName = null;
			if(employmentConfirmed.isPresent()) {
				Optional<EmployeeInformation> employee = employeeInformationList.stream().filter(c->c.getEmployeeId().equals(employmentConfirmed.get().getEmployeeId())).findFirst();
				if(employee.isPresent()) {
					confirmEmployeeName = employee.get().getBusinessName();
				}
			}
			workPlaceConfirmList.add(
					new WorkPlaceConfirmDto(
							workplaceInfor.getWorkplaceId(), 
							workplaceInfor.getWorkplaceDisplayName(), 
							employmentConfirmed.isPresent() ? true : false, 
							employmentConfirmed.isPresent() ? employmentConfirmed.get().getEmployeeId():null, 
							confirmEmployeeName, 
							employmentConfirmed.isPresent() ? Optional.of(employmentConfirmed.get().getDate()) : Optional.empty()));
		}
		
		return workPlaceConfirmList;
	}
	
	@AllArgsConstructor
	private class RequireImpl implements GetListWorkplacesByEmpsService.Require {
		
		private ClosureService closureService;
		
		private ClosureRepository closureRepository;
		
		private UserAuthAdapter userAuthAdapter;
		
		private RoleAdaptor roleAdaptor;
		
		private WorkPlaceAuthorityRepository workPlaceAuthRepo;
		
		private AuthWorkPlaceAdapter authWorkPlaceAdapter;
		
		private AuthWorkPlaceAdapter workPlaceAdapter;
		
		@Override
		public Optional<String> getUserID(String employeeId) {
			return userAuthAdapter.getUserIDByEmpID(employeeId);
		}
		
		@Override
		public Optional<RollInformation> getRole(String userID, int roleType, GeneralDate referenceDate, String companyId) {
			RoleInformationImport dataImport =  roleAdaptor.getRoleIncludCategoryFromUserID(userID, roleType, referenceDate, companyId);
			if (dataImport == null) {
				return Optional.empty();
			}
			return Optional.of(new RollInformation(dataImport.getRoleCharge(), dataImport.getRoleId()));
		}
		
		@Override
		public Optional<WorkPlaceAuthorityDto> getWorkAuthority(String roleId, String companyId, Integer functionNo) {
			Optional<WorkPlaceAuthority> result = workPlaceAuthRepo.getWorkPlaceAuthorityById(companyId, roleId, functionNo);
			if(result.isPresent()) {
				return Optional.of(new WorkPlaceAuthorityDto(result.get().getRoleId(), result.get().getCompanyId(), result.get().getFunctionNo().v(), result.get().isAvailability()));
			}
			return Optional.empty();
		}
		
		@Override
		public OptionalInt getEmployeeReferenceRange(String roleId) {
			OptionalInt result = roleAdaptor.findEmpRangeByRoleID(roleId);
			return result;
		}
		@Override
		public List<String> getListWorkPlaceIDNoWkpAdmin(GeneralDate referenceDate, int empRange, String employeeID) {
			List<String> result = authWorkPlaceAdapter.getListWorkPlaceIDNoWkpAdmin(employeeID, empRange, referenceDate);
			return result;
		}
		
		@Override
		public List<WorkplaceManagerDto> getWorkplaceManager(String employeeID, GeneralDate baseDate) {
			List<WorkplaceManagerImport> dataImport = authWorkPlaceAdapter.findListWkpManagerByEmpIdAndBaseDate(employeeID, baseDate);
			if (dataImport.isEmpty()) {
				return new ArrayList<>();
			}
			
			List<WorkplaceManagerDto> result = dataImport.stream().map(i -> {
				WorkplaceManagerDto export = new WorkplaceManagerDto(i.getWorkplaceManagerId(), i.getEmployeeId(), i.getWorkplaceId(), i.getHistoryPeriod());
				return export;
			}).collect(Collectors.toList());
			
			return result;
		}
		
		@Override
		public List<AffWorkplaceHistoryItemImport> getAffiliatedEmployees(String workplaceId, GeneralDate baseDate) {
			List<AffWorkplaceHistoryItemImport> listWkpItem =  workPlaceAdapter.getWorkHisItemfromWkpIdAndBaseDate(workplaceId, baseDate);
			return listWkpItem;
		}
		
		@Override
		public List<ClosureInformation> getProcessCloseCorrespondToEmps(List<String> sIds, GeneralDate baseDate) {
			List<ClosureInformation> result = new ArrayList<>();
			for (String employeeId : sIds) {
				Closure closure = closureService.getClosureDataByEmployee(employeeId, baseDate);
				ClosureInformation closureInformation = new ClosureInformation(employeeId, closure!=null ? closure.getClosureId().value : null);
				result.add(closureInformation);
			}
			return result;
		}
		
		@Override
		public List<ClosureInfo> getCurrentMonthForAllClosure() {
			return closureService.getAllClosureInfo();
		}
		
		//指定した締めの当月期間を算出する
		@Override
		public Optional<DatePeriod> getCurrentMonthPeriod(Integer closureId) {
			String companyId = AppContexts.user().companyId();
			Optional<Closure> closure = closureRepository.findById(companyId, closureId);
			if(closure.isPresent()) {
				CurrentMonth currentMonth = closure.get().getClosureMonth();
				return Optional.of(closureService.getClosurePeriod(closureId, currentMonth.getProcessingYm()));
			}
			return Optional.empty();
		}

	}
}
