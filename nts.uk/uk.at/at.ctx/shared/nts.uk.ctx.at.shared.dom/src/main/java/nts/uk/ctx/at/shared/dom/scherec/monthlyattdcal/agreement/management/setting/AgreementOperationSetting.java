package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.setting;

import lombok.Getter;
import lombok.val;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.Year;
import nts.arc.time.calendar.period.DatePeriod;
import nts.arc.time.calendar.period.YearMonthPeriod;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.AgreementTimeOfManagePeriod;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.AggregatePeriod;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.enums.StartingMonthType;
import nts.uk.shr.com.time.calendar.date.ClosureDate;

/**
 * ３６協定運用設定
 * @author nampt
 *
 */
@Getter
public class AgreementOperationSetting extends AggregateRoot {

	/**会社ID**/
	private String companyId;

	/** ３６協定起算月 **/
	private StartingMonthType startingMonth;

	/** 締め日 **/
	private ClosureDate closureDate;

	/** 特別条項申請を使用する **/
	private boolean specicalConditionApplicationUse;

	/** 年間の特別条項申請を使用する **/
	private boolean yearSpecicalConditionApplicationUse;

	public AgreementOperationSetting(String companyId, StartingMonthType startingMonth,
			ClosureDate closureDate, boolean specicalConditionApplicationUse,
			boolean yearSpecicalConditionApplicationUse) {
		super();
		this.companyId = companyId;
		this.startingMonth = startingMonth;
		this.closureDate = closureDate;
		this.specicalConditionApplicationUse = specicalConditionApplicationUse;
		this.yearSpecicalConditionApplicationUse = yearSpecicalConditionApplicationUse;
	}
	
	/** 期間から集計期間を取得 */
	/**
	 * 集計期間を取得
	 * @param period 月別実績集計期間
	 * @return 集計期間
	 */
	public AggregatePeriod getAggregatePeriod(DatePeriod period){

		AggregatePeriod aggrPeriod = new AggregatePeriod();
			
		/** ○集計期間を取得 */
		aggrPeriod.setPeriod(updatePeriod(period));
		
		/** 年度・年月の取得 */
		getYearAndMonth(aggrPeriod);
		
		return aggrPeriod;
	}

	/** 年度・年月の取得 */
	private void getYearAndMonth(AggregatePeriod aggrPeriod) {
		val aggrPeriodEnd = aggrPeriod.getPeriod().end();
		
		/** ○月度を求める */
		aggrPeriod.setYearMonth(YearMonth.of(aggrPeriodEnd.year(), aggrPeriodEnd.month()));
		
		/** ○年度を求める */
		int year = aggrPeriodEnd.month() < this.startingMonth.getMonth() ?
				aggrPeriodEnd.year() - 1 : aggrPeriodEnd.year();
		aggrPeriod.setYear(new Year(year));
	}

	/** 集計期間を取得 */
	private DatePeriod updatePeriod(DatePeriod period) {
		/** 末締めの期間を計算 */
		val endYMEnd = GeneralDate.ymd(period.end().year(), period.end().month() + 1, 1).addDays(-1);
		
		/** ○属性「締め日」を取得 */
		if (this.closureDate.getLastDayOfMonth()){
			/** ○集計期間の終了月の１日～末日とする */
			val endYMStart = GeneralDate.ymd(period.end().year(), period.end().month(), 1);
			
			return new DatePeriod(endYMStart, endYMEnd);
		} 
		
		/** ○期間を計算 */
		int closureDay = this.closureDate.getClosureDay().v() + 1;
		GeneralDate closingEnd = endYMEnd;
		if (closureDay < closingEnd.day()){
			closingEnd = GeneralDate.ymd(endYMEnd.year(), endYMEnd.month(), closureDay);
		}
		GeneralDate closingStart = closingEnd.addMonths(-1).addDays(1);
		
		return new DatePeriod(closingStart, closingEnd);
	}
	
	/**
	 * 年月から集計期間を取得
	 * @param yearMonth 年月
	 * @return 年月日期間
	 */
	public DatePeriod getAggregatePeriodByYearMonth(YearMonth yearMonth){

		/** 締め日＝末日 */
		if (this.closureDate.getLastDayOfMonth()) {
			return new DatePeriod(GeneralDate.ymd(yearMonth.year(), yearMonth.month(), 1), 
								  yearMonth.lastGeneralDate());
		} 
			
		YearMonth previousYM = yearMonth.addMonths(-1);
		int closureDay = this.closureDate.getClosureDay().v() + 1;
		return new DatePeriod(GeneralDate.ymd(previousYM.year(), previousYM.month(), closureDay + 1), 
				  		 	  GeneralDate.ymd(yearMonth.year(), yearMonth.month(), closureDay));
	}
	
	/**
	 * 年月を指定して、36協定期間の年月を取得する
	 * @param yearMonth 年月
	 * @return 年月
	 */
	public YearMonth getYearMonthOfAgreementPeriod(YearMonth yearMonth) {
		
		YearMonth result = yearMonth;
		
		/** 起算月を取得 */
		int startMon = this.startingMonth.getMonth();
		
		/** 「年月」の月と起算月を比較 */
		if (yearMonth.month() < startMon) {
			
			/** 年を-1する */
			return result.addYears(-1);
		}
		
		/** 年月を返す */
		return result;
	}
	
	/**
	 * 年月期間から36協定期間を取得する
	 * @param yearMonthPeriod 年月期間
	 * @return 期間
	 */
	public DatePeriod getAgreementPeriodByYMPeriod(YearMonthPeriod yearMonthPeriod) {
		
		/** 年月から集計期間を取得　（開始年月） */
		val startAggrPeriodOpt = this.getAggregatePeriodByYearMonth(yearMonthPeriod.start());
		
		/** 年月から集計期間を取得　（終了年月） */
		val endAggrPeriodOpt = this.getAggregatePeriodByYearMonth(yearMonthPeriod.end());
		
		/** 期間を返す */
		return new DatePeriod(startAggrPeriodOpt.start(), endAggrPeriodOpt.end());
	}
	
	/**
	 * 年度から36協定の年月期間を取得
	 * @param year 年度
	 * @param getAgreementPeriodFromYear 年度から集計期間を取得
	 * @return 年月期間
	 */
	public YearMonthPeriod getYearMonthPeriod(Year year){
		
		/** 年度から集計期間を取得 */
		DatePeriod yearPeriod = getPeriodFromYear(year);
		
		/** 取得した期間の年と月を移送　→　年月期間を返す */
		return new YearMonthPeriod(yearPeriod.start().yearMonth(), 
									yearPeriod.end().yearMonth());
	}
	
	/** 年度から集計期間を取得
	 * @param Year year
	 * */
	public DatePeriod getPeriodFromYear(Year year) {
	
		/** ○属性「起算月」を取得 */
		int month = this.startingMonth.getMonth();
		
		/** 締め日＝末日 */
		if (this.closureDate.getLastDayOfMonth()) {
			val ym = YearMonth.of(year.v(), month);
			val start = ym.lastGeneralDate();
			return new DatePeriod(start, start.addMonths(11));
		}
		
		val start = GeneralDate.ymd(year.v(), month, this.closureDate.getClosureDay().v());
		return new DatePeriod(start, start.addMonths(11));
	}
	
	/**
	 * 指定日を含む年期間を取得
	 * @param criteria 指定年月日
	 * @return 年月期間
	 */
	public YearMonthPeriod getPeriodYear(GeneralDate criteria){
		
		/** ○年度を計算する */
		int year = criteria.year();
		if (criteria.month() < this.startingMonth.getMonth()) year--;
		
		/** ○年度から36協定の年月期間を取得 */
		YearMonthPeriod currentYmPeriod = this.getYearMonthPeriod(new Year(year));
		
		/** ○年月期間から36協定期間の期間を取得する */
		DatePeriod currentPeriod = this.getAgreementPeriodByYMPeriod(currentYmPeriod);
		
		/** 期間．開始日 <= INPUT．指定年月日 <= 期間．終了日 */
		if (currentPeriod.contains(criteria)) return currentYmPeriod;
		
		/** 期間．開始日 > INPUT．指定年月日 */
		if (criteria.before(currentPeriod.start())) {
			return new YearMonthPeriod(currentYmPeriod.start().addYears(-1), 
										currentYmPeriod.end().addYears(-1));
		}
		
		/** 期間．終了日 < INPUT．指定年月日 */
		return new YearMonthPeriod(currentYmPeriod.start().addYears(1), 
									currentYmPeriod.end().addYears(1));
	}
	
	 /**
	 * 日から36協定の集計年月を取得
	 * @param 基準日
	 * @return 年月
	 */
	public YearMonth getAgreementYMBytargetDay(GeneralDate targetTime) {
		/** 締日が末日 */
		if(this.closureDate.getLastDayOfMonth()) {
			return targetTime.yearMonth();
		}
		
		/** INPUT．指定年月日.日 > 締め日 */
		if(targetTime.day() > (this.closureDate.getClosureDay().v()) ) {
			return targetTime.yearMonth().addMonths(1);		
		}
		
		/** INPUT．指定年月日 .日＜＝ 締め日 */
		return targetTime.yearMonth();
	}
	
	/** 管理期間の36協定時間の年度の設定 */
	public AgreementTimeOfManagePeriod setYear(AgreementTimeOfManagePeriod agreementTime) {
		
		/** ○月度から年を取得する */
		Year year = getYear(agreementTime.getYm());
		
		return AgreementTimeOfManagePeriod.of(agreementTime.getSid(), 
												YearMonth.of(year.v(), agreementTime.getYm().month()), 
												agreementTime.getAgreementTime(), 
												agreementTime.getLegalMaxTime(),
												agreementTime.getBreakdown(),
												agreementTime.getStatus());
	}
	
	/** 年月を指定して、36協定期間の年度を取得する */
	public Year getYear(YearMonth ym) {
		/** ○月度から年を取得する */
		int year = ym.year();
		
		/** ○月度の月と起算月を比較する */
		if (ym.month() < (this.startingMonth.getMonth())) {
			year--;
		}
		
		return new Year(year);
	}
}
