package nts.uk.ctx.at.shared.dom.scherec.statutory.worktime;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.val;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.algorithm.monthly.MonthlyStatutoryWorkingHours;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.algorithm.monthly.MonthlyStatutoryWorkingHours.RequireM1;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.algorithm.monthly.MonthlyStatutoryWorkingHours.RequireM4;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.flex.GetFlexPredWorkTime;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.flex.ReferencePredTimeOfFlex;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingSystem;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.employeeinfor.employmenthistory.imported.EmploymentPeriodImported;
import nts.uk.shr.com.context.AppContexts;

/**
 * 社員の法定労働時間を取得する
 * @author lan_lt
 *
 */
public class GetLegalWorkTimeOfEmployeeService {

	/**
	 * 取得する
	 * @param require
	 * @param sid　社員ID
	 * @param period　期間
	 * @return
	 */
	public static Optional<LegalWorkTimeOfEmployee> get(Require require, String sid, DatePeriod period) {
		
		val employeementHists = require.getEmploymentHistories(Arrays.asList(sid), period);
		if(!employeementHists.isEmpty())
			return Optional.empty();
		
		val employeementHistSorted = employeementHists.stream()
				.sorted((a, b) -> a.getDatePeriod().end().compareTo(b.getDatePeriod().end()))
				.collect(Collectors.toList());
		val employementCd = employeementHistSorted.get(employeementHistSorted.size() - 1).getEmploymentCd();
		val wkCondItemOfEmp = require.getHistoryItemBySidAndBaseDate(sid, period.start());
		
		if(!wkCondItemOfEmp.isPresent()) 
			return Optional.empty();
		
		return getLegalWorkTimeOfEmployee(require, sid, period.start(), wkCondItemOfEmp.get().getLaborSystem(), employementCd);
	}
	
	/**
	 * 社員の法定労働時間を取得する
	 * @param require
	 * @param sid　	社員ID
	 * @param baseDate　基準日
	 * @param workingSystem　労働制
	 * @param employmentCd　雇用コード
	 * @return
	 */
	private static Optional<LegalWorkTimeOfEmployee> getLegalWorkTimeOfEmployee(Require require, String sid, GeneralDate baseDate, WorkingSystem workingSystem, String employmentCd){
		val cacheCarrier = new CacheCarrier();
		val yearMonth = baseDate.yearMonth();
		val yearMonths = require.yearMonthFromCalender(yearMonth);
		
		 if(workingSystem == WorkingSystem.EXCLUDED_WORKING_CALCULATE)
			return Optional.empty();
		 
		 if(workingSystem == WorkingSystem.FLEX_TIME_WORK) {
			 return getLegalWorkTimeFlex(require, cacheCarrier, sid, baseDate, yearMonths, workingSystem, employmentCd);
		 }
		 
		 val workTime = MonthlyStatutoryWorkingHours.monAndWeekStatutoryTime(require, cacheCarrier, AppContexts.user().companyId(), employmentCd
				 , sid, baseDate, yearMonths, workingSystem);
		if(workTime.isPresent()) 
			return Optional.empty();
		
		return Optional.of(LegalWorkTimeOfEmployee.create(sid, workTime.get().getWeeklyEstimateTime(),
				workTime.get().getMonthlyEstimateTime()));
	}
	
	/**
	 * フレックスの法定時間を取得する
	 * @param require
	 * @param sid　社員ID
	 * @param baseDate　基準日
	 * @param yearMonth　年月度
	 * @param workingSystem　労働制
	 * @param employmentCd　雇用コード
	 * @return
	 */
	private static Optional<LegalWorkTimeOfEmployee> getLegalWorkTimeFlex(Require require, CacheCarrier cacheCarrier, String sid, GeneralDate baseDate
			, YearMonth yearMonth , WorkingSystem workingSystem, String employmentCd){
		val flexUse = require.getFlexStatutoryTime();
		
		if(!flexUse.isPresent()) 
			return Optional.empty();
		
		val flexMonAndWeek = MonthlyStatutoryWorkingHours.flexMonAndWeekStatutoryTime(require, cacheCarrier, AppContexts.user().companyId(), employmentCd, sid, baseDate, yearMonth);
		val workTime = flexUse.get().getReference() == ReferencePredTimeOfFlex.FROM_RECORD?
				flexMonAndWeek.getStatutorySetting(): flexMonAndWeek.getSpecifiedSetting();
				
		return Optional.of(LegalWorkTimeOfEmployee.createFlexWorkTime(sid, workTime));
	}
	
	public static interface Require extends RequireM1, RequireM4{
		/**
		 * 社員を指定して年月日時点の履歴項目を取得する
		 * @param sid
		 * @param baseDate
		 * @return
		 */
		Optional<WorkingConditionItem> getHistoryItemBySidAndBaseDate(String sid, GeneralDate baseDate);

		/**
		 * 雇用履歴を取得する	
		 * @param sids
		 * @param datePeriod
		 */
		List<EmploymentPeriodImported> getEmploymentHistories(List<String> sids, DatePeriod datePeriod);

		/**
		 * 暦上の年月を渡して、年度に沿った年月を取得する
		 * @param companyId
		 * @param targetOrg
		 */
		YearMonth yearMonthFromCalender(YearMonth yearMonth);
//		
//		/**
//		 * フレックスの法定労働時間を取得する 
//		 * @param require
//		 * @param cacheCarrier
//		 * @param employmentCd
//		 * @param employeeId
//		 * @param baseDate
//		 * @param ym
//		 * @return
//		 */
//		MonthlyFlexStatutoryLaborTime flexMonAndWeekStatutoryTime(RequireM1 require, CacheCarrier cacheCarrier, String employmentCd, String employeeId, GeneralDate baseDate, YearMonth ym);
//	
//		/**
//		 * 通常、変形の法定労働時間を取得する
//		 * @param require
//		 * @param cacheCarrier
//		 * @param employmentCd
//		 * @param employeeId
//		 * @param baseDate
//		 * @param ym
//		 * @param workingSystem
//		 * @return
//		 */
//		Optional<MonAndWeekStatutoryTime> monAndWeekStatutoryTime(RequireM4 require, CacheCarrier cacheCarrier, String employmentCd, String employeeId,  GeneralDate baseDate, YearMonth ym, WorkingSystem workingSystem);
//	
		/**
		 * フレックス勤務所定労働時間取得を取得する
		 * @return
		 */
		Optional<GetFlexPredWorkTime> getFlexStatutoryTime();
	}
}
