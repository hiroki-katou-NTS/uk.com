package nts.uk.ctx.at.record.dom.monthly;

import lombok.Getter;
import lombok.Setter;
import lombok.val;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.monthly.calc.MonthlyCalculation;
import nts.uk.ctx.at.record.dom.monthly.excessoutside.ExcessOutsideWorkOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.totalcount.TotalCountByPeriod;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.VerticalTotalOfMonthly;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.MonAggrCompanySettings;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.MonAggrEmployeeSettings;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.MonthlyCalculatingDailys;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.MonthlyOldDatas;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.RepositoriesRequiredByMonthlyAggr;
import nts.uk.ctx.at.shared.dom.common.days.AttendanceDaysMonth;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.shr.com.time.calendar.date.ClosureDate;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * 月別実績の勤怠時間
 * @author shuichi_ishida
 */
@Getter
public class AttendanceTimeOfMonthly extends AggregateRoot {

	/** 社員ID */
	private final String employeeId;
	/** 年月 */
	private final YearMonth yearMonth;
	/** 締めID */
	private final ClosureId closureId;
	/** 締め日付 */
	private final ClosureDate closureDate;

	/** 期間 */
	private DatePeriod datePeriod;
	/** 月の計算 */
	@Setter
	private MonthlyCalculation monthlyCalculation;
	/** 時間外超過 */
	@Setter
	private ExcessOutsideWorkOfMonthly excessOutsideWork;
	/** 縦計 */
	@Setter
	private VerticalTotalOfMonthly verticalTotal;
	/** 回数集計 */
	@Setter
	private TotalCountByPeriod totalCount;
	/** 集計日数 */
	@Setter
	private AttendanceDaysMonth aggregateDays;

	/**
	 * コンストラクタ
	 * @param employeeId 社員ID
	 * @param yearMonth 年月
	 * @param closureId 締めID
	 * @param closureDate 締め日付
	 * @param datePeriod 期間
	 */
	public AttendanceTimeOfMonthly(String employeeId, YearMonth yearMonth,
			ClosureId closureId, ClosureDate closureDate, DatePeriod datePeriod){
		
		super();
		this.employeeId = employeeId;
		this.yearMonth = yearMonth;
		this.closureId = closureId;
		this.closureDate = closureDate;
		this.datePeriod = datePeriod;
		this.monthlyCalculation = new MonthlyCalculation();
		this.excessOutsideWork = new ExcessOutsideWorkOfMonthly();
		this.verticalTotal = new VerticalTotalOfMonthly();
		this.totalCount = new TotalCountByPeriod();
		this.aggregateDays = new AttendanceDaysMonth((double)(datePeriod.start().daysTo(datePeriod.end()) + 1));
	}
	
	/**
	 * ファクトリー
	 * @param employeeId 社員ID
	 * @param yearMonth 年月
	 * @param closureId 締めID
	 * @param closureDate 締め日付
	 * @param datePeriod 期間
	 * @param monthlyCalculation 月の計算
	 * @param excessOutsideWork 時間外超過
	 * @param verticalTotal 縦計
	 * @param totalCount 回数集計
	 * @param aggregateDays 集計日数
	 * @return 月別実績の勤怠時間
	 */
	public static AttendanceTimeOfMonthly of(
			String employeeId,
			YearMonth yearMonth,
			ClosureId closureId,
			ClosureDate closureDate,
			DatePeriod datePeriod,
			MonthlyCalculation monthlyCalculation,
			ExcessOutsideWorkOfMonthly excessOutsideWork,
			VerticalTotalOfMonthly verticalTotal,
			TotalCountByPeriod totalCount,
			AttendanceDaysMonth aggregateDays){
		
		val domain = new AttendanceTimeOfMonthly(employeeId, yearMonth, closureId, closureDate, datePeriod);
		domain.monthlyCalculation = monthlyCalculation;
		domain.excessOutsideWork = excessOutsideWork;
		domain.verticalTotal = verticalTotal;
		domain.totalCount = totalCount;
		domain.aggregateDays = aggregateDays;
		return domain;
	}

	/**
	 * 集計準備
	 * @param companyId 会社ID
	 * @param datePeriod 期間
	 * @param workingConditionItem 労働制
	 * @param startWeekNo 開始週NO
	 * @param companySets 月別集計で必要な会社別設定
	 * @param employeeSets 月別集計で必要な社員別設定
	 * @param monthlyCalcDailys 月の計算中の日別実績データ
	 * @param monthlyOldDatas 集計前の月別実績データ
	 * @param repositories 月次集計が必要とするリポジトリ
	 */
	public void prepareAggregation(
			String companyId,
			DatePeriod datePeriod,
			WorkingConditionItem workingConditionItem,
			int startWeekNo,
			MonAggrCompanySettings companySets,
			MonAggrEmployeeSettings employeeSets,
			MonthlyCalculatingDailys monthlyCalcDailys,
			MonthlyOldDatas monthlyOldDatas,
			RepositoriesRequiredByMonthlyAggr repositories){
		
		this.monthlyCalculation.prepareAggregation(companyId, this.employeeId, this.yearMonth,
				this.closureId, this.closureDate, datePeriod, workingConditionItem,
				startWeekNo, companySets, employeeSets, monthlyCalcDailys, monthlyOldDatas, repositories);
	}

	/**
	 * 等しいかどうか
	 * @param target 比較対象
	 * @return true:等しい、false:等しくない
	 */
	public boolean equals(AttendanceTimeOfMonthly target) {
		
		return (this.employeeId == target.employeeId &&
				this.yearMonth.equals(target.yearMonth) &&
				this.closureId.value == target.closureId.value &&
				this.closureDate.getClosureDay().equals(target.closureDate.getClosureDay()) &&
				this.closureDate.getLastDayOfMonth() == target.closureDate.getLastDayOfMonth());
	}
	
	/**
	 * 合算する
	 * @param target 加算対象
	 */
	public void sum(AttendanceTimeOfMonthly target){

		GeneralDate startDate = this.datePeriod.start();
		GeneralDate endDate = this.datePeriod.end();
		if (startDate.after(target.datePeriod.start())) startDate = target.datePeriod.start();
		if (endDate.before(target.datePeriod.end())) endDate = target.datePeriod.end();
		this.datePeriod = new DatePeriod(startDate, endDate);
		
		this.monthlyCalculation.sum(target.monthlyCalculation);
		this.excessOutsideWork.sum(target.excessOutsideWork);
		this.verticalTotal.sum(target.verticalTotal);
		this.totalCount.sum(target.totalCount);
		
		this.aggregateDays = this.aggregateDays.addDays(target.aggregateDays.v());
	}
}
