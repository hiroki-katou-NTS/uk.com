package nts.uk.ctx.at.function.app.export.holidaysremaining;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.arc.task.parallel.ManagedParallelWithContext;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.function.app.find.holidaysremaining.HdRemainManageFinder;
import nts.uk.ctx.at.function.dom.adapter.RegulationInfoEmployeeAdapter;
import nts.uk.ctx.at.function.dom.adapter.annualleave.GetNextAnnLeaGrantDateAdapter;
import nts.uk.ctx.at.function.dom.adapter.annualworkschedule.EmployeeInformationAdapter;
import nts.uk.ctx.at.function.dom.adapter.annualworkschedule.EmployeeInformationImport;
import nts.uk.ctx.at.function.dom.adapter.annualworkschedule.EmployeeInformationQueryDtoImport;
import nts.uk.ctx.at.function.dom.adapter.holidaysremaining.AbsenceReruitmentManaAdapter;
import nts.uk.ctx.at.function.dom.adapter.holidaysremaining.AnnLeaGrantNumberImported;
import nts.uk.ctx.at.function.dom.adapter.holidaysremaining.AnnLeaveOfThisMonthImported;
import nts.uk.ctx.at.function.dom.adapter.holidaysremaining.AnnLeaveRemainingAdapter;
import nts.uk.ctx.at.function.dom.adapter.holidaysremaining.AnnLeaveUsageStatusOfThisMonthImported;
import nts.uk.ctx.at.function.dom.adapter.holidaysremaining.AnnualLeaveUsageImported;
import nts.uk.ctx.at.function.dom.adapter.holidaysremaining.ChildNursingLeaveCurrentSituationImported;
import nts.uk.ctx.at.function.dom.adapter.holidaysremaining.ChildNursingLeaveRemainingAdapter;
import nts.uk.ctx.at.function.dom.adapter.holidaysremaining.CurrentHolidayRemainImported;
import nts.uk.ctx.at.function.dom.adapter.holidaysremaining.HdRemainDetailMerEx;
import nts.uk.ctx.at.function.dom.adapter.holidaysremaining.HolidayRemainMerEx;
import nts.uk.ctx.at.function.dom.adapter.holidaysremaining.HolidayRemainMergeAdapter;
import nts.uk.ctx.at.function.dom.adapter.holidaysremaining.NursingLeaveCurrentSituationImported;
import nts.uk.ctx.at.function.dom.adapter.holidaysremaining.NursingLeaveRemainingAdapter;
import nts.uk.ctx.at.function.dom.adapter.holidaysremaining.StatusOfHolidayImported;
import nts.uk.ctx.at.function.dom.adapter.periodofspecialleave.ComplileInPeriodOfSpecialLeaveAdapter;
import nts.uk.ctx.at.function.dom.adapter.periodofspecialleave.SpecialHolidayImported;
import nts.uk.ctx.at.function.dom.adapter.periodofspecialleave.SpecialVacationImported;
import nts.uk.ctx.at.function.dom.adapter.reserveleave.GetReserveLeaveNumbersAdpter;
import nts.uk.ctx.at.function.dom.adapter.reserveleave.ReserveHolidayImported;
import nts.uk.ctx.at.function.dom.adapter.reserveleave.ReservedYearHolidayImported;
import nts.uk.ctx.at.function.dom.adapter.reserveleave.RsvLeaUsedCurrentMonImported;
import nts.uk.ctx.at.function.dom.adapter.vacation.CurrentHolidayImported;
import nts.uk.ctx.at.function.dom.adapter.vacation.MonthlyDayoffRemainAdapter;
import nts.uk.ctx.at.function.dom.adapter.vacation.StatusHolidayImported;
import nts.uk.ctx.at.function.dom.holidaysremaining.VariousVacationControl;
import nts.uk.ctx.at.function.dom.holidaysremaining.VariousVacationControlService;
import nts.uk.ctx.at.function.dom.holidaysremaining.report.HolidayRemainingDataSource;
import nts.uk.ctx.at.function.dom.holidaysremaining.report.HolidayRemainingInfor;
import nts.uk.ctx.at.function.dom.holidaysremaining.report.HolidaysRemainingEmployee;
import nts.uk.ctx.at.function.dom.holidaysremaining.report.HolidaysRemainingReportGenerator;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureInfo;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;
import nts.uk.shr.com.company.CompanyAdapter;
import nts.uk.shr.com.company.CompanyInfor;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.com.time.calendar.period.DatePeriod;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;

@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class HolidaysRemainingReportHandler extends ExportService<HolidaysRemainingReportQuery> {

	@Inject
	private HolidaysRemainingReportGenerator reportGenerator;
	@Inject
	private HdRemainManageFinder hdFinder;
	@Inject
	private RegulationInfoEmployeeAdapter regulationInfoEmployeeAdapter;
	@Inject
	private EmployeeInformationAdapter employeeInformationAdapter;
	@Inject
	private HdRemainManageFinder hdRemainManageFinder;
	@Inject
	private ClosureRepository closureRepository;
	@Inject
	private ClosureService closureService;
	@Inject
	private GetNextAnnLeaGrantDateAdapter getNextAnnLeaGrantDateAdapter;
	@Inject
	private AnnLeaveRemainingAdapter annLeaveAdapter;
	@Inject
	private GetReserveLeaveNumbersAdpter reserveLeaveAdpter;
	@Inject
	private MonthlyDayoffRemainAdapter monthlyDayoffAdapter;
	@Inject
	private AbsenceReruitmentManaAdapter absenceReruitmentAdapter;
	@Inject
	private ComplileInPeriodOfSpecialLeaveAdapter specialLeaveAdapter;
	@Inject
	private ChildNursingLeaveRemainingAdapter childNursingAdapter;
	@Inject
	private NursingLeaveRemainingAdapter nursingLeaveAdapter;
	@Inject
	private VariousVacationControlService varVacaCtrSv;
	@Inject
	private CompanyAdapter companyRepo;
	@Inject
	private ManagedParallelWithContext parallel;
	@Inject
	private HolidayRemainMergeAdapter hdRemainAdapter;

	@Override
	protected void handle(ExportServiceContext<HolidaysRemainingReportQuery> context) {
		val query = context.getQuery();
		val hdRemainCond = query.getHolidayRemainingOutputCondition();
		String cId = AppContexts.user().companyId();
		val baseDate = GeneralDate.fromString(hdRemainCond.getBaseDate(), "yyyy/MM/dd");
		val startDate = GeneralDate.fromString(hdRemainCond.getStartMonth(), "yyyy/MM/dd");
		val endDate = GeneralDate.fromString(hdRemainCond.getEndMonth(), "yyyy/MM/dd");
		// ドメインモデル「休暇残数管理表の出力項目設定」を取得する
		val hdManagement = hdFinder.findByCode(hdRemainCond.getOutputItemSettingCode());
		if (!hdManagement.isPresent()) {
			return;
		}
		int closureId = hdRemainCond.getClosureId();
		// ※該当の締めIDが「0：全締め」のときは、「1締め（締めID＝1）」とする
		if (closureId == 0) {
			closureId = 1;
		}
		// アルゴリズム「指定した年月の期間をすべて取得する」を実行する
		Optional<Closure> closureOpt = closureRepository.findById(cId, closureId);
		if (!closureOpt.isPresent()) {
			return;
		}
		// アルゴリズム「指定した年月の期間をすべて取得する」を実行する
		List<DatePeriod> periodByYearMonths = closureOpt.get().getPeriodByYearMonth(endDate.yearMonth());
		// 処理基準日を決定する
		Optional<DatePeriod> criteriaDatePeriodOpt = periodByYearMonths.stream()
				.max(Comparator.comparing(DatePeriod::end));
		if (!criteriaDatePeriodOpt.isPresent()) {
			return;
		}
		GeneralDate criteriaDate = criteriaDatePeriodOpt.get().end();
		// 画面項目「A2_2：社員リスト」で選択されている社員の社員ID
		List<String> employeeIds = query.getLstEmpIds().stream().map(EmployeeQuery::getEmployeeId)
				.collect(Collectors.toList());
		// <<Public>> 社員を並べ替える
		employeeIds = this.regulationInfoEmployeeAdapter.sortEmployee(cId, employeeIds,
				AppContexts.system().getInstallationType().value, null, null,
				GeneralDateTime.legacyDateTime(criteriaDate.date()));

		Map<String, EmployeeQuery> empMap = query.getLstEmpIds().stream()
				.collect(Collectors.toMap(EmployeeQuery::getEmployeeId, Function.identity()));

		Map<String, HolidaysRemainingEmployee> employees = new HashMap<>();

		// <<Public>> 社員の情報を取得する
		List<EmployeeInformationImport> listEmployeeInformationImport = employeeInformationAdapter
				.getEmployeeInfo(new EmployeeInformationQueryDtoImport(employeeIds, criteriaDate, true, false, true,
						true, false, false));
		// 出力するデータ件数をチェックする
		if (listEmployeeInformationImport.isEmpty()) {
			throw new BusinessException("Msg_885");
		}

		val varVacaCtr = varVacaCtrSv.getVariousVacationControl();
		val closureInforOpt = this.getClosureInfor(closureId);

		boolean isSameCurrentMonth = true;
		boolean isFirstEmployee = true;
		Optional<YearMonth> currentMonthOfFirstEmp = Optional.empty();
		//hoatt
		List<String> lstSID = listEmployeeInformationImport.stream().map(c -> c.getEmployeeId()).collect(Collectors.toList());
		Map<String, YearMonth> mapCurMon = hdRemainManageFinder.getCurrentMonthVer2(cId, lstSID, baseDate);
		for (EmployeeInformationImport emp : listEmployeeInformationImport) {
			String wpCode = emp.getWorkplace() != null ? emp.getWorkplace().getWorkplaceCode() : "";
			String wpName = emp.getWorkplace() != null ? emp.getWorkplace().getWorkplaceName()
					: TextResource.localize("KDR001_55");
			String empmentName = emp.getEmployment() != null ? emp.getEmployment().getEmploymentName() : "";
			String positionName = emp.getPosition() != null ? emp.getPosition().getPositionName() : "";

			// List内の締め情報．当月をチェックする
			Optional<YearMonth> currentMonth = Optional.empty();
			if(mapCurMon.containsKey(emp.getEmployeeId())){
				currentMonth = Optional.of(mapCurMon.get(emp.getEmployeeId()));
			}
			if (isFirstEmployee) {
				isFirstEmployee = false;
				currentMonthOfFirstEmp = currentMonth;
			} else {
				if (isSameCurrentMonth && !currentMonth.equals(currentMonthOfFirstEmp)) {
					isSameCurrentMonth = false;
				}
			}
			HolidayRemainingInfor holidayRemainingInfor = this.getHolidayRemainingInfor(varVacaCtr, closureInforOpt,
					emp.getEmployeeId(), baseDate, startDate, endDate,currentMonth);

			employees.put(emp.getEmployeeId(), new HolidaysRemainingEmployee(emp.getEmployeeId(), emp.getEmployeeCode(),
					empMap.get(emp.getEmployeeId()).getEmployeeName(), empMap.get(emp.getEmployeeId()).getWorkplaceId(),
					wpCode, wpName, empmentName, positionName, currentMonth, holidayRemainingInfor));
		}
		
		Optional<CompanyInfor> companyCurrent = this.companyRepo.getCurrentCompany();
		HolidayRemainingDataSource dataSource = new HolidayRemainingDataSource(hdRemainCond.getStartMonth(),
				hdRemainCond.getEndMonth(), varVacaCtr, hdRemainCond.getPageBreak(),
				hdRemainCond.getBaseDate(), hdManagement.get(), isSameCurrentMonth, employeeIds, employees, companyCurrent.isPresent() == true? companyCurrent.get().getCompanyName():"");

		this.reportGenerator.generate(context.getGeneratorContext(), dataSource);

	}

	private HolidayRemainingInfor getHolidayRemainingInfor(VariousVacationControl variousVacationControl,
			Optional<ClosureInfo> closureInforOpt, String employeeId, GeneralDate baseDate, GeneralDate startDate,
			GeneralDate endDate, Optional<YearMonth> currMonth) {

		// RequestList369
		Optional<GeneralDate> grantDate = Optional.empty();
		// RequestList281
		List<AnnLeaGrantNumberImported> listAnnLeaGrantNumber = null;
		// RequestList265
		AnnLeaveOfThisMonthImported annLeaveOfThisMonth = null;
		// RequestList255
		List<AnnualLeaveUsageImported> listAnnualLeaveUsage = null;
		// RequestList363
		List<AnnLeaveUsageStatusOfThisMonthImported> listAnnLeaveUsageStatusOfThisMonth = null;
		// RequestList268
		ReserveHolidayImported reserveHoliday = null;
		// RequestList258
		List<ReservedYearHolidayImported> listReservedYearHoliday = null;
		// RequestList364
		List<RsvLeaUsedCurrentMonImported> listRsvLeaUsedCurrentMon = null;
		// RequestList269
		List<CurrentHolidayImported> listCurrentHoliday = null;
		// RequestList259
		List<StatusHolidayImported> listStatusHoliday = null;
		// RequestList270
		List<CurrentHolidayRemainImported> listCurrentHolidayRemain = null;
		// RequestList260
		List<StatusOfHolidayImported> listStatusOfHoliday = null;

		// RequestList206
		ChildNursingLeaveCurrentSituationImported childNursingLeave = null;
		// RequestList207
		NursingLeaveCurrentSituationImported nursingLeave = null;

		if (!closureInforOpt.isPresent()) {
			return null;
		}
		
		YearMonth currentMonth = closureInforOpt.get().getCurrentMonth();
		val cId = AppContexts.user().companyId();
		val datePeriod = new DatePeriod(startDate, endDate);
		//hoatt
		YearMonthPeriod period = new YearMonthPeriod(startDate.yearMonth(),currentMonth.previousMonth());
		//Mer RQ255,258,259,260,263
		HolidayRemainMerEx hdRemainMer = hdRemainAdapter.getRemainMer(employeeId, period);
		//Mer RQ265,268,269,363,364,369
		HdRemainDetailMerEx remainDel = hdRemainAdapter.getRemainDetailMer(employeeId, currentMonth, baseDate, new DatePeriod(startDate, endDate));
		if (variousVacationControl.isAnnualHolidaySetting()) {
			// Call RequestList369
			grantDate = remainDel.getResult369();
			// Call RequestList281
			listAnnLeaGrantNumber = annLeaveAdapter.algorithm(employeeId);
			listAnnLeaGrantNumber = listAnnLeaGrantNumber.stream()
					.sorted(Comparator.comparing(AnnLeaGrantNumberImported::getGrantDate)).collect(Collectors.toList());
			// Call RequestList265
			annLeaveOfThisMonth = remainDel.getResult265();
			// Call RequestList255 ver2 - hoatt
			if (currentMonth.compareTo(startDate.yearMonth()) > 0) {
				listAnnualLeaveUsage = hdRemainMer.getResult255();
			}
			// Call RequestList363
			if (currentMonth.compareTo(endDate.yearMonth()) <= 0) {
				listAnnLeaveUsageStatusOfThisMonth = remainDel.getResult363();
			}
		}

		if (variousVacationControl.isYearlyReservedSetting()) {
			// Call RequestList268
			reserveHoliday = remainDel.getResult268();
			// Call RequestList258 ver2 - hoatt
			if (currentMonth.compareTo(startDate.yearMonth()) > 0) {
				listReservedYearHoliday = hdRemainMer.getResult258();
			}
			// Call RequestList364
			if (currentMonth.compareTo(endDate.yearMonth()) <= 0) {
				listRsvLeaUsedCurrentMon = remainDel.getResult364();
			}
		}

		if (variousVacationControl.isSubstituteHolidaySetting()) {
			// Call RequestList269
			listCurrentHoliday = remainDel.getResult269();
			// Call RequestList259 ver2 - hoatt
			if (currentMonth.compareTo(startDate.yearMonth()) > 0) {
				listStatusHoliday = hdRemainMer.getResult259();
			}
		}

		if (variousVacationControl.isPauseItemHolidaySetting()) {
			// Call RequestList270
			listCurrentHolidayRemain = absenceReruitmentAdapter.getAbsRecRemainAggregate(employeeId, baseDate,
					startDate.yearMonth(), endDate.yearMonth());
			// Call RequestList260 ver2 - hoatt
			if (currentMonth.compareTo(startDate.yearMonth()) > 0) {
				listStatusOfHoliday = hdRemainMer.getResult260();
			}
		}
		//hoatt
		Map<Integer, SpecialVacationImported> tmpCurrMon = Collections.synchronizedMap(new HashMap<Integer, SpecialVacationImported>());
		Map<Integer, SpecialVacationImported> tmpSpeVaca = Collections.synchronizedMap(new HashMap<Integer, SpecialVacationImported>());
		Map<Integer, List<SpecialHolidayImported>> tmpSpeHd = Collections.synchronizedMap(new HashMap<Integer, List<SpecialHolidayImported>>());
		parallel.forEach(variousVacationControl.getListSpecialHoliday(), specialHolidayDto ->{
			int sphdCode = specialHolidayDto.getSpecialHolidayCode().v();
			// Call RequestList273
			SpecialVacationImported specialVacationImported = specialLeaveAdapter.complileInPeriodOfSpecialLeave(cId,
					employeeId, closureInforOpt.get().getPeriod(), false, baseDate, sphdCode, false);
			tmpSpeVaca.put(sphdCode, specialVacationImported);

			// Call RequestList263 ver2 - hoatt
			if (currentMonth.compareTo(startDate.yearMonth()) > 0) {
				List<SpecialHolidayImported> specialHolidayList = hdRemainMer.getResult263();
				tmpSpeHd.put(sphdCode, specialHolidayList);
			} else {
				tmpSpeHd.put(sphdCode, new ArrayList<SpecialHolidayImported>());
			}

            // Call RequestList273
			if (currMonth.isPresent() && currMonth.get().lessThanOrEqualTo(endDate.yearMonth())){
	            SpecialVacationImported spVaImported = specialLeaveAdapter.complileInPeriodOfSpecialLeave(cId,
	                    employeeId, closureInforOpt.get().getPeriod(), false, baseDate, sphdCode, false);
	            tmpCurrMon.put(sphdCode, spVaImported);
			}
		});
		//convert
		// RequestList273
		Map<Integer, SpecialVacationImported> mapSpecVaca = new HashMap<>();
		mapSpecVaca.putAll(tmpSpeVaca);
        Map<Integer, SpecialVacationImported> mapSPVaCurrMon = new HashMap<>();
		mapSPVaCurrMon.putAll(tmpCurrMon);
		// RequestList263
		Map<Integer, List<SpecialHolidayImported>> mapSpeHd = new HashMap<>();
		mapSpeHd.putAll(tmpSpeHd);
		if (variousVacationControl.isChildNursingSetting()) {
			// Call RequestList206
			childNursingLeave = childNursingAdapter.getChildNursingLeaveCurrentSituation(cId, employeeId, datePeriod);
		}

		if (variousVacationControl.isNursingCareSetting()) {
			// Call RequestList207
			nursingLeave = nursingLeaveAdapter.getNursingLeaveCurrentSituation(cId, employeeId, datePeriod);
		}

		return new HolidayRemainingInfor(grantDate, listAnnLeaGrantNumber, annLeaveOfThisMonth, listAnnualLeaveUsage,
				listAnnLeaveUsageStatusOfThisMonth, reserveHoliday, listReservedYearHoliday, listRsvLeaUsedCurrentMon,
				listCurrentHoliday, listStatusHoliday, listCurrentHolidayRemain, listStatusOfHoliday,
				mapSpecVaca, mapSPVaCurrMon, mapSpeHd, childNursingLeave, nursingLeave);
	}

	private Optional<ClosureInfo> getClosureInfor(int closureId) {
		val listClosureInfo = closureService.getAllClosureInfo();
		return listClosureInfo.stream().filter(i -> i.getClosureId().value == closureId).findFirst();
	}
}