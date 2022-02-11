package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.val;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.param.PeriodAfterDivision;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmpEmployeeAdapter;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmployeeImport;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.daynumber.AnnualLeaveUsedDayNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.interim.TempAnnualLeaveMngs;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.annualleave.AnnLeaRemNumEachMonth;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.algorithm.monthly.GetPeriodExcluseEntryRetireTime;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;
import nts.uk.shr.com.context.AppContexts;

import javax.inject.Inject;

public class FindAnnLeaUsedDaysFromPreviousToNextGrantDate {

	/**
	 * [RQ717]前回付与から次回付与までの年休使用数を求める（時間使用を含まない）
	 * @param employeeId
	 * @param criteriaDate
	 * @param require
	 * @param cacheCarrier
	 * @return
	 */
	// 2022.02.07 - 3S - chinh.hm - issues #122665- 変更 START
	//public static AnnualLeaveUsedDayNumber findUsedDays(String employeeId, GeneralDate criteriaDate,
	//		Require require, CacheCarrier cacheCarrier){
	public static AnnualLeaveUsedDayNumber findUsedDays(String employeeId, GeneralDate criteriaDate,
				Require require, CacheCarrier cacheCarrier, EmployeeImport employee){
	// 2022.02.07 - 3S - chinh.hm - issues #122665- 変更 END
		String companyId = AppContexts.user().companyId();
		//指定した年月日を基準に、前回付与日から次回付与日までの期間を取得
		Optional<GrantPeriodDto> GrantPeriod = require.getPeriodYMDGrant(companyId,employeeId,criteriaDate, null, Optional.empty());
		
		if(!GrantPeriod.isPresent()){
			return new AnnualLeaveUsedDayNumber(0.0);
		}
		// 2022.02.07 - 3S - chinh.hm  - issues #122665- 追加 START
		//入社前、退職後を期間から除く
		DatePeriod  datePeriod = GrantPeriod.get().getPeriod();
		DatePeriod exclusedPeriod = GetPeriodExcluseEntryRetireTime
				.getPeriodExcluseEntryRetireTime(new GetPeriodExcluseEntryRetireTime.Require() {
					@Override
					public EmployeeImport employeeInfo(CacheCarrier cacheCarrier, String empId) {
						return employee;
					}
				}, cacheCarrier , employeeId, datePeriod).orElse(null);
		if(exclusedPeriod == null){
			return new AnnualLeaveUsedDayNumber(0.0);
		}
		// 2022.02.07 - 3S - chinh.hm  - issues #122665- 追加 END
		//期間を当月以前と以降に分ける。
		// 2022.02.07 - 3S - chinh.hm - issues #122665- 変更 START
		//PeriodAfterDivision periodAfterDivision = divisionThePeriodTheCurrentMonth(employeeId,criteriaDate,
		//		GrantPeriod.get().getPeriod(),require, cacheCarrier);
		PeriodAfterDivision periodAfterDivision = divisionThePeriodTheCurrentMonth(employeeId,criteriaDate,
				exclusedPeriod,require, cacheCarrier);
		// 2022.02.07 - 3S - chinh.hm - issues #122665- 変更 END
		//当月以前の使用日数を計算
		// 2022.02.07 - 3S - chinh.hm - issues #122665- 変更 START
		//AnnualLeaveUsedDayNumber beforeUsedDays = calcUsedDaysBeforeTheCurrentMonth(employeeId, criteriaDate,
		//		GrantPeriod.get().getPeriod(),  GrantPeriod.get().getPeriod(), require, cacheCarrier);
		AnnualLeaveUsedDayNumber beforeUsedDays = calcUsedDaysBeforeTheCurrentMonth(employeeId, criteriaDate,
				exclusedPeriod,  GrantPeriod.get().getPeriod(), require, cacheCarrier);
		// 2022.02.07 - 3S - chinh.hm - issues #122665- 変更 END

		AnnualLeaveUsedDayNumber afterUsedDays = calcUsedDaysAfterTheCurrentMonth(employeeId, 
				periodAfterDivision.getPeriodAfterTheMonth(), require, cacheCarrier);

		return new AnnualLeaveUsedDayNumber(beforeUsedDays.v() + afterUsedDays.v());
	}
	
	/**
	 * 期間を当月以前と以降に分ける。
	 * @param employeeId 社員ID
	 * @param criteriaDate　基準日
	 * @param GrantPeriod　付与期間
	 * @param require
	 * @param cacheCarrier
	 * @return　分割後の期間
	 */
	private static PeriodAfterDivision divisionThePeriodTheCurrentMonth(String employeeId, GeneralDate criteriaDate,
			DatePeriod GrantPeriod, Require require, CacheCarrier cacheCarrier){
		
		//社員に対応する締め期間を取得する
		DatePeriod closurePeriod = ClosureService.findClosurePeriod(require, cacheCarrier, employeeId, criteriaDate);
		
		return new PeriodAfterDivision(
				new DatePeriod(GrantPeriod.start(), closurePeriod.start().addDays(-1)),
				new DatePeriod(closurePeriod.start(),GrantPeriod.end())
				);
		
	}
	
	/**
	 * 当月以前の使用日数を計算
	 * @param employeeId　社員ID
	 * @param criteriaDate　基準日
	 * @param period　期間
	 * @param GrantPeriod　付与期間
	 * @param require
	 * @return
	 */
	private static AnnualLeaveUsedDayNumber calcUsedDaysBeforeTheCurrentMonth(String employeeId, GeneralDate criteriaDate,
			DatePeriod period, DatePeriod GrantPeriod, Require require, CacheCarrier cacheCarrier){
		
		//年月日期間から年月締め期間を求める
		List<YearMonth> yearMonth = require.GetYearMonthClosurePeriod(require, cacheCarrier, 
				employeeId, criteriaDate, period);
		
		//年休月別残数データを取得
		List<String> employeeIds = new ArrayList<>();
		employeeIds.add(employeeId);
		List<AnnLeaRemNumEachMonth> annLeaRemNumEachMonthList = require.findBySidsAndYearMonths(employeeIds, yearMonth);
		
		double total = annLeaRemNumEachMonthList.stream().mapToDouble(c -> {
			//付与後の使用数を加算する
			if (c.getClosurePeriod().start().after(GrantPeriod.start()) && c.getClosurePeriod().end().beforeOrEquals(GrantPeriod.start())) {
				return c.getAnnualLeave().getUsedNumberInfo().getUsedNumberAfterGrantOpt()
						.flatMap(x -> x.getUsedDays()).map(x -> x.v()).orElse(0d);
			}
			//付与前の使用数を加算する
			return c.getAnnualLeave().getUsedNumberInfo().getUsedNumberBeforeGrant()
					.getUsedDays().map(x -> x.v()).orElse(0d);

		}).sum();
		
		return new AnnualLeaveUsedDayNumber(total);
	}
	
	
	/**
	 * 当月以降の使用数を計算
	 * @param employeeId 社員ID
	 * @param period　期間
	 * @param require
	 * @param cacheCarrier
	 * @return
	 */
	private static AnnualLeaveUsedDayNumber calcUsedDaysAfterTheCurrentMonth(String employeeId,
			DatePeriod period, Require require, CacheCarrier cacheCarrier){
		
		List<TempAnnualLeaveMngs> interimRemains = require.tmpAnnualHolidayMng(employeeId, period);
		
		double total = interimRemains.stream().mapToDouble(c ->{
			return c.getUsedNumber().getUsedDayNumberOrZero().v();
		}).sum();
		
		return new AnnualLeaveUsedDayNumber(total);
	}
	
	public static interface Require extends ClosureService.RequireM3{
		
		Optional<GrantPeriodDto> getPeriodYMDGrant(String cid, String sid, GeneralDate ymd, 
				Integer periodOutput, Optional<DatePeriod> fromTo);
		
		DatePeriod findClosurePeriod(ClosureService.RequireM3 require,CacheCarrier cacheCarrier, 
				String employeeId, GeneralDate criteriaDate);
		
		List<YearMonth> GetYearMonthClosurePeriod(ClosureService.RequireM3 require, CacheCarrier cacheCarrier, 
				String employeeId, GeneralDate criteriaDate, DatePeriod period);
		
		List<AnnLeaRemNumEachMonth> findBySidsAndYearMonths(List<String> employeeIds, List<YearMonth> yearMonths);

		List<TempAnnualLeaveMngs> tmpAnnualHolidayMng(String sid, DatePeriod dateData);
		
	}
	
}
