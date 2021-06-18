/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.val;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.adapter.holidaymanagement.CompanyDto;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.common.PublicHolidayMonthSetting;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.export.query.publicholiday.AggregatePublicHolidayWork;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.AbsenceReruitmentMngInPeriodQuery.RequireM7;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveRemainingDayNumber;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureStartEndOutput;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;
import nts.uk.shr.com.time.calendar.date.ClosureDate;

/**
 * 
 * @author quytb
 * 公休設定
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared.休日管理.公休管理.設定.公休設定
 * 
 */
@Getter
@Setter
@AllArgsConstructor
public class PublicHolidaySetting extends AggregateRoot {	
	/** 会社ID*/
	private String companyID;
	
	/** 公休を管理する */	
	private int isManagePublicHoliday;
	
	/** 公休管理期間  */
	private PublicHolidayPeriod publicHolidayPeriod;
	
	/** 公休繰越期限 */
	private PublicHolidayCarryOverDeadline publicHolidayCarryOverDeadline;
	
	/** 公休日数がマイナス時に繰越する */
	private int carryOverNumberOfPublicHolidayIsNegative;
	
	
	/**
	 * 集計期間WORKを作成
	 * 
	 * @param employeeId
	 * @param yearMonth
	 * @param criteriaDate
	 * @param cacheCarrier
	 * @param require
	 * @return
	 */
	public List<AggregatePublicHolidayWork> createAggregatePeriodWork(
			String employeeId,List<YearMonth> yearMonth,GeneralDate criteriaDate, 
			CacheCarrier cacheCarrier, RequireM1 require){
		
		//集計期間を作成する
		List<PeriodList> periodList = createPeriod(employeeId, yearMonth, criteriaDate, cacheCarrier, require);
		
		//集計期間List毎の日数設定を取得
		List<AggregatePublicHolidayWork> aggregatePublicHolidayWork = 
				AggregationPeriodDailyNumberSetting(employeeId, periodList, 
						criteriaDate, cacheCarrier,require);
		
		return aggregatePublicHolidayWork;
	}
	
	
	/**
	 * 集計期間を作成する
	 * @param employeeId
	 * @param yearMonths
	 * @param criteriaDate
	 * @param cacheCarrier
	 * @param require
	 * @return
	 */
	public List<PeriodList> createPeriod(String employeeId,
			List<YearMonth> yearMonths,
			GeneralDate criteriaDate, 
			CacheCarrier cacheCarrier,
			RequireM1 require){
		
		List<PeriodList> periodList = new ArrayList<>();
		//INPUT．Require締め日を取得
		Closure closure = ClosureService.getClosureDataByEmployee(require, cacheCarrier, this.companyID, criteriaDate);
		//公休管理期間設定を確認
		if(this.publicHolidayPeriod == PublicHolidayPeriod.CLOSURE_PERIOD){
			//管理期間 = 締め期間
			//締め開始日と締め日を取得する
			Optional<ClosureStartEndOutput> startDayAndClosureDay = closure.getClosureStartDayAndClosureDay();
			
			if(!startDayAndClosureDay.isPresent()){
				return new ArrayList<>();
			}
			
			//年月の件数ループ
			for(val yearMonth : yearMonths){
				
				//年月＋締め開始日、年月＋締め日の期間を作成
				DatePeriod period = new DatePeriod(
						GeneralDate.ymd(yearMonth,startDayAndClosureDay.get().getStart().v().intValue()),
						GeneralDate.ymd(yearMonth,startDayAndClosureDay.get().getClosure().v().intValue()));
				
				//期間（List）に追加
				periodList.add(new PeriodList(yearMonth, period));
				
			}
		}else{
			//管理期間 = 1日から末日
			Optional<ClosureDate> closureDate = closure.getClosureDateOfCurrentMonth();
			if(closureDate.isPresent()){
			//締め日が末日か
				if(closureDate.get().getLastDayOfMonth() == true){
					List<YearMonth> lastMonth = new ArrayList<>();
					//年月の月を1か月前にする
					for(val yearMonth : yearMonths){
						lastMonth.add(yearMonth.previousMonth());
					}
					yearMonths.clear();
					yearMonths.addAll(lastMonth);
				}
			}
			//年月の件数ループ
			for(val yearMonth : yearMonths){
				//年月＋1日、年月＋年月の末日で期間を作成
				DatePeriod period = DatePeriod.daysFirstToLastIn(yearMonth);
				
				//期間（List）に追加
				periodList.add(new PeriodList(yearMonth, period));
			}
		}
		
		return periodList;
	}
	
	/**
	 * 集計期間List毎の日数設定を取得
	 * @param employeeId
	 * @param periodList
	 * @param yearMonths
	 * @param criteriaDate
	 * @param cacheCarrier
	 * @param require
	 * @return 公休集計期間WORK
	 */
	public List<AggregatePublicHolidayWork> AggregationPeriodDailyNumberSetting(String employeeId, 
			List<PeriodList> periodList, GeneralDate criteriaDate, CacheCarrier cacheCarrier,RequireM2 require){
		
		//会社の期首月を取得する
		CompanyDto compInfor = require.firstMonth(cacheCarrier, this.companyID);
		
		//公休日数取得
		PublicHolidayManagementUsageUnit publicHolidayManagementUsageUnit =
				require.publicHolidayManagementUsageUnit(this.companyID);
		
		List<PublicHolidayMonthSetting> publicHolidayMonthSettings =
				publicHolidayManagementUsageUnit.GetNumberofPublicHoliday(require, this.companyID, employeeId,periodList , criteriaDate);
		
		
		List<AggregatePublicHolidayWork> aggregatePublicHolidayWork =  new ArrayList<>();
		//期限日作成
		for(PeriodList period : periodList){
			GeneralDate ymd =createDeadline(compInfor.getStartMonth(),period.getPeriod().end());
			
			aggregatePublicHolidayWork.add(new AggregatePublicHolidayWork(
					period.getYearMonth(),
					period.getPeriod(),
					publicHolidayMonthSettings.stream().filter(
							x -> x.getPublicHdManagementYear().v() == period.getYearMonth().year()&&
							x.getMonth() == period.getYearMonth().month()).findFirst().get(),
					ymd,
					new LeaveRemainingDayNumber(0.0)
					));
			
		}
		
		
		return aggregatePublicHolidayWork;
	}
	
	
	/**
	 * 繰越期限を計算する
	 * @param int 期首月
	 * @param endDay 当月の最終日
	 * @return GeneralDate 期限日
	 */
	public GeneralDate createDeadline(int compInfor, GeneralDate endDay){
		GeneralDate deadline = endDay;
		
		//繰越期限を確認
		switch(this.publicHolidayCarryOverDeadline){
			//当月
			case CURRENT_MONTH:
				deadline = endDay;
				break;
			//無期限
			case INDEFINITE:
				deadline = GeneralDate.ymd(9999, 12, 31);
				break;
			//年度末
			case YEAR_END:
				
				int year = (endDay.month() < compInfor) ?
							(compInfor == 1 ? endDay.year()-1 :endDay.year())
							:compInfor == 1 ? endDay.year() :endDay.year()+1;
				YearMonth yearMonth =  endDay.yearMonth();
				
				int day = GeneralDate.ymd(yearMonth.year(), yearMonth.month(), 1).addDays(-1).day();
				
				deadline = GeneralDate.ymd(
							year
							,yearMonth.previousMonth().v(),
							day);
				break;
		
		}
		return deadline;
	}
	
	
	public boolean iscarryOverNumberOfPublicHoliday(){
		return this.carryOverNumberOfPublicHolidayIsNegative == 1;
	}
	
	//締め
	public static interface RequireM1 extends ClosureService.RequireM3, RequireM2{
	}

	public static interface RequireM2 extends ClosureService.RequireM3, RequireM7, PublicHolidayManagementUsageUnit.RequireM1 {
		//会社の期首月を取得する
		CompanyDto firstMonth(CacheCarrier cacheCarrier,String companyId);
		
		//公休利用単位設定
		PublicHolidayManagementUsageUnit publicHolidayManagementUsageUnit(String companyId);
	}
}
