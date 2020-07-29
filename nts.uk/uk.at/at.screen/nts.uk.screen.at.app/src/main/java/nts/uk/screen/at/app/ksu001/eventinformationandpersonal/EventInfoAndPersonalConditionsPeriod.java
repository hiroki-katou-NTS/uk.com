/**
 * 
 */
package nts.uk.screen.at.app.ksu001.eventinformationandpersonal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.DayOfWeek;
import nts.uk.ctx.at.schedule.dom.employeeinfo.medicalworkstyle.EmpMedicalWorkFormHisItem;
import nts.uk.ctx.at.schedule.dom.employeeinfo.medicalworkstyle.EmpMedicalWorkStyleHistoryRepository;
import nts.uk.ctx.at.schedule.dom.employeeinfo.medicalworkstyle.LicenseClassification;
import nts.uk.ctx.at.schedule.dom.employeeinfo.medicalworkstyle.NurseClassification;
import nts.uk.ctx.at.schedule.dom.employeeinfo.medicalworkstyle.NurseClassificationRepository;
import nts.uk.ctx.at.schedule.dom.employeeinfo.rank.EmployeeRank;
import nts.uk.ctx.at.schedule.dom.employeeinfo.rank.EmployeeRankRepository;
import nts.uk.ctx.at.schedule.dom.employeeinfo.rank.Rank;
import nts.uk.ctx.at.schedule.dom.employeeinfo.rank.RankRepository;
import nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam.BelongScheduleTeam;
import nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam.BelongScheduleTeamRepository;
import nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam.ScheduleTeam;
import nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam.ScheduleTeamRepository;
import nts.uk.ctx.at.schedule.dom.schedule.setting.displaycontrol.PersonSymbolQualify;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.event.CompanyEvent;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.event.CompanyEventRepository;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.event.EventName;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.event.WorkplaceEvent;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.event.WorkplaceEventRepository;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.holiday.PublicHoliday;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.holiday.PublicHolidayRepository;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.specificdate.item.SpecificDateItem;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.specificdate.primitives.SpecificDateItemNo;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.specificdate.primitives.SpecificName;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.specificdate.repository.SpecificDateItemRepository;
import nts.uk.ctx.at.schedule.dom.shift.management.DateInformation;
import nts.uk.ctx.at.schedule.dom.shift.specificdayset.company.CompanySpecificDateItem;
import nts.uk.ctx.at.schedule.dom.shift.specificdayset.company.CompanySpecificDateRepository;
import nts.uk.ctx.at.schedule.dom.shift.specificdayset.workplace.WorkplaceSpecificDateItem;
import nts.uk.ctx.at.schedule.dom.shift.specificdayset.workplace.WorkplaceSpecificDateRepository;
import nts.uk.ctx.at.schedule.dom.workschedule.displaysetting.ConditionATRWorkSchedule;
import nts.uk.ctx.at.schedule.dom.workschedule.displaysetting.DisplayControlPersonalCondition;
import nts.uk.ctx.at.schedule.dom.workschedule.displaysetting.DisplayControlPersonalConditionRepo;
import nts.uk.ctx.at.schedule.dom.workschedule.displaysetting.PersonInforDisplayControl;
import nts.uk.ctx.at.schedule.dom.workschedule.displaysetting.PersonalCondition;
import nts.uk.ctx.at.schedule.dom.workschedule.displaysetting.QualificationCD;
import nts.uk.ctx.at.schedule.dom.workschedule.displaysetting.WorkscheQualifi;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.enumcommon.NotUseAtr;

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
	
	public DataSpecDateAndHolidayDto get(EventInfoAndPerCondPeriodParam param) {
		
		// step 1
		// ・List<Temporary「年月日情報」>
		List<DateInformation> listDateInfo = new ArrayList<DateInformation>();
		/* 
		 * RequireImpl require = new RequireImpl(workplaceSpecificDateRepo, companySpecificDateRepo,
				workplaceEventRepo, companyEventRepo, publicHolidayRepo, specificDateItemRepo);
		DatePeriod period = new DatePeriod(param.startDate, param.endDate);
		period.datesBetween().stream().forEach(date -> {
			TargetOrgIdenInfor targetOrgIdenInfor = new TargetOrgIdenInfor(param.workplaceGroupId == null ? TargetOrganizationUnit.WORKPLACE : TargetOrganizationUnit.WORKPLACE_GROUP, param.workplaceId, param.workplaceGroupId);
			DateInformation dateInformation = null;
			dateInformation = DateInformation.create(require, date, targetOrgIdenInfor);
			listDateInfo.add(dateInformation);
		});
		
		// step2
		Optional<DisplayControlPersonalCondition> displayControlPerCond =  displayControlPerCondRepo.get(AppContexts.user().companyId());
		if (!displayControlPerCond.isPresent()) {
			// step 3
			return new DataSpecDateAndHolidayDto(listDateInfo, new ArrayList<>(), Optional.empty());
		}
		
		RequireImplDispControlPerCond requireImplDispControlPerCond = new RequireImplDispControlPerCond(
				belongScheduleTeamRepo, scheduleTeamRepo, employeeRankRepo, rankRepo, empMedicalWorkStyleHistoryRepo,
				nurseClassificationRepo);
		
		List<PersonalCondition> listPersonalCond = DisplayControlPersonalCondition.acquireInforDisplayControlPersonalCondition(requireImplDispControlPerCond, param.endDate, param.listSid);
		*/
		
		DateInformation dateInformation1 = new DateInformation(GeneralDate.ymd(2020, 7, 1), DayOfWeek.WEDNESDAY, false, false, 
				Optional.empty(), Optional.empty(), 
				new ArrayList<>(), new ArrayList<>());
		
		DateInformation dateInformation2 = new DateInformation(GeneralDate.ymd(2020, 7, 2), DayOfWeek.THURSDAY, false, false, 
				Optional.empty(), Optional.empty(), 
				new ArrayList<>(), new ArrayList<>());
		
		DateInformation dateInformation3 = new DateInformation(GeneralDate.ymd(2020, 7, 3), DayOfWeek.FRIDAY, false, false, 
				Optional.empty(), Optional.empty(), 
				new ArrayList<>(), new ArrayList<>());
		
		DateInformation dateInformation4 = new DateInformation(GeneralDate.ymd(2020, 7, 4), DayOfWeek.SATURDAY, false, false, 
				Optional.empty(), Optional.empty(), 
				new ArrayList<>(), new ArrayList<>());
		
		DateInformation dateInformation5 = new DateInformation(GeneralDate.ymd(2020, 7, 5), DayOfWeek.SUNDAY, false, false, 
				Optional.empty(), Optional.empty(), 
				new ArrayList<>(), new ArrayList<>());
		
		DateInformation dateInformation6 = new DateInformation(GeneralDate.ymd(2020, 7, 6), DayOfWeek.MONDAY, false, false, 
				Optional.empty(), Optional.empty(), 
				new ArrayList<>(), new ArrayList<>());
		
		DateInformation dateInformation7 = new DateInformation(GeneralDate.ymd(2020, 7, 7), DayOfWeek.TUESDAY, false, false, 
				Optional.empty(), Optional.empty(), 
				new ArrayList<>(), new ArrayList<>());
		
		DateInformation dateInformation8 = new DateInformation(GeneralDate.ymd(2020, 7, 8), DayOfWeek.WEDNESDAY, false, true, 
				Optional.of(new EventName("WorkplaceEventName")), Optional.of(new EventName("CompanyEventName")), 
				Arrays.asList(new SpecificName("specDayNameWorkplace1"),new SpecificName("specDayNameWorkplace2")), 
				Arrays.asList(new SpecificName("specDayNameCompany1"), new SpecificName("specDayNameCompany2")));
		
		DateInformation dateInformation9 = new DateInformation(GeneralDate.ymd(2020, 7, 9), DayOfWeek.THURSDAY, false, true, 
				Optional.of(new EventName("WorkplaceEventName")), Optional.of(new EventName("CompanyEventName")), 
				Arrays.asList(new SpecificName("specDayNameWorkplace1"),new SpecificName("specDayNameWorkplace2")), 
				Arrays.asList(new SpecificName("specDayNameCompany1"), new SpecificName("specDayNameCompany2"), 
							  new SpecificName("specDayNameCompany3"), new SpecificName("specDayNameCompany4")));
		
		DateInformation dateInformation10 = new DateInformation(GeneralDate.ymd(2020, 7, 10), DayOfWeek.FRIDAY, false, false, 
				Optional.empty(), Optional.empty(), 
				new ArrayList<>(), new ArrayList<>());
		
		DateInformation dateInformation11 = new DateInformation(GeneralDate.ymd(2020, 7, 11), DayOfWeek.SATURDAY, false, false, 
				Optional.empty(), Optional.empty(), 
				new ArrayList<>(), new ArrayList<>());
		
		DateInformation dateInformation12 = new DateInformation(GeneralDate.ymd(2020, 7, 12), DayOfWeek.SUNDAY, false, false, 
				Optional.empty(), Optional.empty(), 
				new ArrayList<>(), new ArrayList<>());
		
		DateInformation dateInformation13 = new DateInformation(GeneralDate.ymd(2020, 7, 13), DayOfWeek.MONDAY, false, false, 
				Optional.empty(), Optional.empty(), 
				new ArrayList<>(), new ArrayList<>());
		
		DateInformation dateInformation14 = new DateInformation(GeneralDate.ymd(2020, 7, 14), DayOfWeek.TUESDAY, false, false, 
				Optional.empty(), Optional.empty(), 
				new ArrayList<>(), new ArrayList<>());
		
		DateInformation dateInformation15 = new DateInformation(GeneralDate.ymd(2020, 7, 15), DayOfWeek.WEDNESDAY, false, true, 
				Optional.of(new EventName("WorkplaceEventName")), Optional.of(new EventName("CompanyEventName")), 
				Arrays.asList(new SpecificName("specDayNameWorkplace1"),new SpecificName("specDayNameWorkplace2")), 
				Arrays.asList(new SpecificName("specDayNameCompany1"), new SpecificName("specDayNameCompany2")));
		
		DateInformation dateInformation16 = new DateInformation(GeneralDate.ymd(2020, 7, 16), DayOfWeek.THURSDAY, false, true, 
				Optional.of(new EventName("WorkplaceEventName")), Optional.of(new EventName("CompanyEventName")), 
				Arrays.asList(new SpecificName("specDayNameWorkplace1"),new SpecificName("specDayNameWorkplace2")), 
				Arrays.asList(new SpecificName("specDayNameCompany1"), new SpecificName("specDayNameCompany2")));
		
		DateInformation dateInformation17 = new DateInformation(GeneralDate.ymd(2020, 7, 17), DayOfWeek.FRIDAY, false, false, 
				Optional.empty(), Optional.empty(), 
				new ArrayList<>(), new ArrayList<>());
		
		DateInformation dateInformation18 = new DateInformation(GeneralDate.ymd(2020, 7, 18), DayOfWeek.SATURDAY, false, false, 
				Optional.empty(), Optional.empty(), 
				new ArrayList<>(), new ArrayList<>());
		
		DateInformation dateInformation19 = new DateInformation(GeneralDate.ymd(2020, 7, 19), DayOfWeek.SUNDAY, false, false, 
				Optional.empty(), Optional.empty(), 
				new ArrayList<>(), new ArrayList<>());
		
		DateInformation dateInformation20 = new DateInformation(GeneralDate.ymd(2020, 7, 20), DayOfWeek.MONDAY, false, false, 
				Optional.empty(), Optional.empty(), 
				new ArrayList<>(), new ArrayList<>());
		
		DateInformation dateInformation21 = new DateInformation(GeneralDate.ymd(2020, 7, 21), DayOfWeek.TUESDAY, false, false, 
				Optional.empty(), Optional.empty(), 
				new ArrayList<>(), new ArrayList<>());
		
		DateInformation dateInformation22 = new DateInformation(GeneralDate.ymd(2020, 7, 22), DayOfWeek.WEDNESDAY, false, false, 
				Optional.empty(), Optional.empty(), 
				new ArrayList<>(), new ArrayList<>());
		
		DateInformation dateInformation23 = new DateInformation(GeneralDate.ymd(2020, 7, 23), DayOfWeek.THURSDAY, false, false, 
				Optional.empty(), Optional.empty(), 
				new ArrayList<>(), new ArrayList<>());
		
		DateInformation dateInformation24 = new DateInformation(GeneralDate.ymd(2020, 7, 24), DayOfWeek.FRIDAY, false, false, 
				Optional.empty(), Optional.empty(), 
				new ArrayList<>(), new ArrayList<>());
		
		DateInformation dateInformation25 = new DateInformation(GeneralDate.ymd(2020, 7, 25), DayOfWeek.SATURDAY, false, false, 
				Optional.empty(), Optional.empty(), 
				new ArrayList<>(), new ArrayList<>());
		
		DateInformation dateInformation26 = new DateInformation(GeneralDate.ymd(2020, 7, 26), DayOfWeek.SUNDAY, false, false, 
				Optional.empty(), Optional.empty(), 
				new ArrayList<>(), new ArrayList<>());
		
		DateInformation dateInformation27 = new DateInformation(GeneralDate.ymd(2020, 7, 27), DayOfWeek.MONDAY, false, false, 
				Optional.empty(), Optional.empty(), 
				new ArrayList<>(), new ArrayList<>());
		
		DateInformation dateInformation28 = new DateInformation(GeneralDate.ymd(2020, 7, 28), DayOfWeek.TUESDAY, false, false, 
				Optional.empty(), Optional.empty(), 
				new ArrayList<>(), new ArrayList<>());
		
		DateInformation dateInformation29 = new DateInformation(GeneralDate.ymd(2020, 7, 29), DayOfWeek.WEDNESDAY, false, false, 
				Optional.empty(), Optional.empty(), 
				new ArrayList<>(), new ArrayList<>());
		
		DateInformation dateInformation30 = new DateInformation(GeneralDate.ymd(2020, 7, 30), DayOfWeek.THURSDAY, false, false, 
				Optional.empty(), Optional.empty(), 
				new ArrayList<>(), new ArrayList<>());
		
		DateInformation dateInformation31 = new DateInformation(GeneralDate.ymd(2020, 7, 31), DayOfWeek.FRIDAY, false, false, 
				Optional.empty(), Optional.empty(), 
				new ArrayList<>(), new ArrayList<>());
		
		listDateInfo.add(dateInformation1);
		listDateInfo.add(dateInformation2);
		listDateInfo.add(dateInformation3);
		listDateInfo.add(dateInformation4);
		listDateInfo.add(dateInformation5);
		listDateInfo.add(dateInformation6);
		listDateInfo.add(dateInformation7);
		listDateInfo.add(dateInformation8);
		listDateInfo.add(dateInformation9);
		listDateInfo.add(dateInformation10);
		listDateInfo.add(dateInformation11);
		listDateInfo.add(dateInformation12);
		listDateInfo.add(dateInformation13);
		listDateInfo.add(dateInformation14);
		listDateInfo.add(dateInformation15);
		listDateInfo.add(dateInformation16);
		listDateInfo.add(dateInformation17);
		listDateInfo.add(dateInformation18);
		listDateInfo.add(dateInformation19);
		listDateInfo.add(dateInformation20);
		listDateInfo.add(dateInformation21);
		listDateInfo.add(dateInformation22);
		listDateInfo.add(dateInformation23);
		listDateInfo.add(dateInformation24);
		listDateInfo.add(dateInformation25);
		listDateInfo.add(dateInformation26);
		listDateInfo.add(dateInformation27);
		listDateInfo.add(dateInformation28);
		listDateInfo.add(dateInformation29);
		listDateInfo.add(dateInformation30);
		listDateInfo.add(dateInformation31);
		
		Optional<DisplayControlPersonalCondition> displayControlPerCond = Optional
				.of(new DisplayControlPersonalCondition(AppContexts.user().companyId(),
						Arrays.asList(new PersonInforDisplayControl(ConditionATRWorkSchedule.TEAM, NotUseAtr.USE)),
						Optional.of(new WorkscheQualifi(new PersonSymbolQualify("PersonSymbolQualify"),
								Arrays.asList(new QualificationCD("QualificationCD"))))));
		List<PersonalCondition> listPersonalCond = Arrays.asList(
				new PersonalCondition("fc4304be-8121-4bad-913f-3e48f4e2a752", Optional.of("A"), Optional.of("A"), Optional.of(LicenseClassification.NURSE)),
				new PersonalCondition("338c26ac-9b80-4bab-aa11-485f3c624186", Optional.of("B"), Optional.of("S"), Optional.of(LicenseClassification.NURSE_ASSIST)),
				new PersonalCondition("89ea1474-d7d8-4694-9e9b-416ea1d6381c", Optional.of("C"), Optional.of("D"), Optional.of(LicenseClassification.NURSE_ASSOCIATE)),
				new PersonalCondition("ae7fe82e-a7bd-4ce3-adeb-5cd403a9d570", Optional.of("D"), Optional.of("F"), Optional.of(LicenseClassification.NURSE)),
				new PersonalCondition("8f9edce4-e135-4a1e-8dca-ad96abe405d6", Optional.of("A"), Optional.of("G"), Optional.of(LicenseClassification.NURSE_ASSIST)),
				new PersonalCondition("9787c06b-3c71-4508-8e06-c70ad41f042a", Optional.of("B"), Optional.of("Y"), Optional.of(LicenseClassification.NURSE_ASSOCIATE)),
				new PersonalCondition("62785783-4213-4a05-942b-c32a5ffc1d63", Optional.of("C"), Optional.of("U"), Optional.of(LicenseClassification.NURSE)),
				new PersonalCondition("4859993b-8065-4789-90d6-735e3b65626b", Optional.of("D"), Optional.of("I"), Optional.of(LicenseClassification.NURSE_ASSIST)),
				new PersonalCondition("aeaa869d-fe62-4eb2-ac03-2dde53322cb5", Optional.of("A"), Optional.of("J"), Optional.of(LicenseClassification.NURSE_ASSOCIATE)),
				new PersonalCondition("70c48cfa-7e8d-4577-b4f6-7b715c091f24", Optional.of("B"), Optional.of("H"), Optional.of(LicenseClassification.NURSE)),
				new PersonalCondition("c141daf2-70a4-4f4b-a488-847f4686e848", Optional.of("C"), Optional.of("G"), Optional.of(LicenseClassification.NURSE_ASSIST)));
		// step 4
		return new DataSpecDateAndHolidayDto(listDateInfo, listPersonalCond, displayControlPerCond);
		
	}
	

	@AllArgsConstructor
	private static class RequireImpl implements DateInformation.Require {
		
		@Inject
		private  WorkplaceSpecificDateRepository workplaceSpecificDateRepo;
		@Inject
		private  CompanySpecificDateRepository companySpecificDateRepo;
		@Inject
		private  WorkplaceEventRepository workplaceEventRepo;
		@Inject
		private  CompanyEventRepository companyEventRepo;
		@Inject
		private  PublicHolidayRepository publicHolidayRepo;
		@Inject
		private  SpecificDateItemRepository specificDateItemRepo;
		
		@Override
		public List<WorkplaceSpecificDateItem> getWorkplaceSpecByDate(String workplaceId, GeneralDate specificDate) {
			List<WorkplaceSpecificDateItem> data = workplaceSpecificDateRepo.getWorkplaceSpecByDate(workplaceId, specificDate);
			return data;
		}

		@Override
		public List<CompanySpecificDateItem> getComSpecByDate(GeneralDate specificDate) {
			List<CompanySpecificDateItem> data = companySpecificDateRepo.getComSpecByDate(AppContexts.user().companyId(), specificDate);
			return data;
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
		public boolean getHolidaysByDate(GeneralDate date) {
			Optional<PublicHoliday> data = publicHolidayRepo.getHolidaysByDate(AppContexts.user().companyId(), date);
			return data.isPresent();
		}

		@Override
		public List<SpecificDateItem> getSpecifiDateByListCode(List<SpecificDateItemNo> lstSpecificDateItemNo) {
			if (lstSpecificDateItemNo.isEmpty()) {
				return new ArrayList<>();
			}
			
			List<Integer> _lstSpecificDateItemNo = lstSpecificDateItemNo.stream().map(mapper -> mapper.v()).collect(Collectors.toList());
			List<SpecificDateItem> data = specificDateItemRepo.getSpecifiDateByListCode(AppContexts.user().companyId(), _lstSpecificDateItemNo);
			return data;
		}
	}
	
	@AllArgsConstructor
	private static class RequireImplDispControlPerCond implements DisplayControlPersonalCondition.Require {
		@Inject
		private   BelongScheduleTeamRepository belongScheduleTeamRepo;
		@Inject
		private   ScheduleTeamRepository scheduleTeamRepo;
		@Inject
		private   EmployeeRankRepository employeeRankRepo;
		@Inject
		private   RankRepository rankRepo;
		@Inject
		private   EmpMedicalWorkStyleHistoryRepository empMedicalWorkStyleHistoryRepo;
		@Inject
		private   NurseClassificationRepository nurseClassificationRepo;
		
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
		public List<EmpMedicalWorkFormHisItem> get(List<String> listEmp, GeneralDate referenceDate) {
			List<EmpMedicalWorkFormHisItem> data = empMedicalWorkStyleHistoryRepo.get(listEmp, referenceDate);
			return data;
		}

		@Override
		public List<NurseClassification> getListCompanyNurseCategory() {
			List<NurseClassification> data = nurseClassificationRepo.getListCompanyNurseCategory(AppContexts.user().companyId());
			return data;
		}
	}
}
