/**
 * 
 */
package nts.uk.screen.at.app.ksu001.eventinformationandpersonal;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.schedule.dom.employeeinfo.rank.EmployeeRank;
import nts.uk.ctx.at.schedule.dom.employeeinfo.rank.EmployeeRankRepository;
import nts.uk.ctx.at.schedule.dom.employeeinfo.rank.Rank;
import nts.uk.ctx.at.schedule.dom.employeeinfo.rank.RankRepository;
import nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam.BelongScheduleTeam;
import nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam.BelongScheduleTeamRepository;
import nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam.ScheduleTeam;
import nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam.ScheduleTeamRepository;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.event.CompanyEvent;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.event.CompanyEventRepository;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.event.WorkplaceEvent;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.event.WorkplaceEventRepository;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.holiday.PublicHoliday;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.holiday.PublicHolidayRepository;
import nts.uk.ctx.at.schedule.dom.shift.management.DateInformation;
import nts.uk.ctx.at.schedule.dom.shift.specificdaysetting.CompanySpecificDateItem;
import nts.uk.ctx.at.schedule.dom.shift.specificdaysetting.CompanySpecificDateRepository;
import nts.uk.ctx.at.schedule.dom.shift.specificdaysetting.SpecificDateItem;
import nts.uk.ctx.at.schedule.dom.shift.specificdaysetting.SpecificDateItemNo;
import nts.uk.ctx.at.schedule.dom.shift.specificdaysetting.SpecificDateItemRepository;
import nts.uk.ctx.at.schedule.dom.shift.specificdaysetting.WorkplaceSpecificDateItem;
import nts.uk.ctx.at.schedule.dom.shift.specificdaysetting.WorkplaceSpecificDateRepository;
import nts.uk.ctx.at.schedule.dom.workschedule.displaysetting.DisplayControlPersonalCondition;
import nts.uk.ctx.at.schedule.dom.workschedule.displaysetting.DisplayControlPersonalConditionRepo;
import nts.uk.ctx.at.schedule.dom.workschedule.displaysetting.PersonalCondition;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle.EmpMedicalWorkStyleHistoryItem;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle.EmpMedicalWorkStyleHistoryRepository;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle.NurseClassification;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle.NurseClassificationRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author laitv 
 * <<ScreenQuery>> 期間中のイベント情報と個人条件を取得する
 * 
 */
@Stateless
public class EventInfoAndPersonalConditionsPeriod {

	@Inject
	private WorkplaceSpecificDateRepository workplaceSpecificDateRepo;
	@Inject
	private CompanySpecificDateRepository companySpecificDateRepo;
	@Inject
	private WorkplaceEventRepository workplaceEventRepo;
	@Inject
	private CompanyEventRepository companyEventRepo;
	@Inject
	private PublicHolidayRepository publicHolidayRepo;
	@Inject
	private SpecificDateItemRepository specificDateItemRepo;
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
	
	public DataSpecDateAndHolidayDto getData(EventInfoAndPerCondPeriodParam param) {

		// step 1
		// ・List<Temporary「年月日情報」>
		List<DateInformation> listDateInfo = new ArrayList<DateInformation>();

		RequireImpl require = new RequireImpl(workplaceSpecificDateRepo, companySpecificDateRepo, workplaceEventRepo,
				companyEventRepo, publicHolidayRepo, specificDateItemRepo);
		DatePeriod period = new DatePeriod(param.startDate, param.endDate);
		
		period.datesBetween().stream().forEach(date -> {
			DateInformation dateInformation = null;
			dateInformation = DateInformation.create(require, date, param.targetOrgIdenInfor);
			listDateInfo.add(dateInformation);
		});

		// step2 Optional.of(workplaceId), Optional.of(workplaceGroupId)
		Optional<DisplayControlPersonalCondition> displayControlPerCond = displayControlPerCondRepo.get(AppContexts.user().companyId());
		if (!displayControlPerCond.isPresent()) {
			// step 3
			return new DataSpecDateAndHolidayDto(listDateInfo, new ArrayList<>(), Optional.empty());
		}

		RequireImplDispControlPerCond requireImplDispControlPerCond = new RequireImplDispControlPerCond(
				belongScheduleTeamRepo, scheduleTeamRepo, employeeRankRepo, rankRepo, empMedicalWorkStyleHistoryRepo,
				nurseClassificationRepo);

		List<PersonalCondition> listPersonalCond = displayControlPerCond.get().acquireInforDisplayControlPersonalCondition(requireImplDispControlPerCond, param.endDate,
						param.listSid);
		// step 4
		return new DataSpecDateAndHolidayDto(listDateInfo, listPersonalCond, displayControlPerCond);

	}
	
	@AllArgsConstructor
	private static class RequireImpl implements DateInformation.Require {

		@Inject
		private WorkplaceSpecificDateRepository workplaceSpecificDateRepo;
		@Inject
		private CompanySpecificDateRepository companySpecificDateRepo;
		@Inject
		private WorkplaceEventRepository workplaceEventRepo;
		@Inject
		private CompanyEventRepository companyEventRepo;
		@Inject
		private PublicHolidayRepository publicHolidayRepo;
		@Inject
		private SpecificDateItemRepository specificDateItemRepo;

		@Override
		public Optional<WorkplaceSpecificDateItem> getWorkplaceSpecByDate(String workplaceId, GeneralDate specificDate) {
			return workplaceSpecificDateRepo.get(workplaceId, specificDate);
		}

		@Override
		public Optional<CompanySpecificDateItem> getComSpecByDate(GeneralDate specificDate) {
			return companySpecificDateRepo.get(AppContexts.user().companyId(), specificDate);
		}

		@Override
		public Optional<WorkplaceEvent> findByPK(String workplaceId, GeneralDate date) {
			Optional<WorkplaceEvent> data = workplaceEventRepo.findByPK(workplaceId, date);
			return data;
		}

		@Override
		public Optional<CompanyEvent> findCompanyEventByPK(GeneralDate date) {
			Optional<CompanyEvent> data = companyEventRepo.findByPK(AppContexts.user().companyId(), date);
			return data;
		}

		@Override
		public Optional<PublicHoliday> getHolidaysByDate(GeneralDate date) {
			return publicHolidayRepo.getHolidaysByDate(AppContexts.user().companyId(), date);
		}

		@Override
		public List<SpecificDateItem> getSpecifiDateByListCode(List<SpecificDateItemNo> lstSpecificDateItemNo) {
			if (lstSpecificDateItemNo.isEmpty()) {
				return new ArrayList<>();
			}
			List<SpecificDateItem> data = specificDateItemRepo.getSpecifiDateByListCode(AppContexts.user().companyId(),
					lstSpecificDateItemNo);
			return data;
		}
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
