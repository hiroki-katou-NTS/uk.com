package nts.uk.ctx.at.record.dom.monthly;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.record.dom.monthly.calc.MonthlyCalculation;
import nts.uk.ctx.at.shared.dom.employment.statutory.worktime.employment.WorkingSystem;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * 月別実績の勤怠時間
 * @author shuichi_ishida
 */
@Getter
public class AttendanceTimeOfMonthly extends AggregateRoot {

	/** 社員ID */
	private final String employeeID;
	/** 期間 */
	private final DatePeriod datePeriod;

	/** 月の計算 */
	private MonthlyCalculation monthlyCalculation;
	/** 集計日数 */
	private AttendanceDaysMonthly aggregateDays;
	/** 回数集計 */
	//aggregateTimes
	/** 休暇 */
	//holiday
	/** 時間外超過 */
	//excessOutsideWork
	/** 縦計 */
	//verticalAggregate
	/** 任意項目 */
	//anyItem

	/**
	 * コンストラクタ
	 * @param employeeID 社員ID
	 * @param datePeriod 期間
	 */
	public AttendanceTimeOfMonthly(String employeeID, DatePeriod datePeriod){
		
		super();
		this.employeeID = employeeID;
		this.datePeriod = datePeriod;
		this.monthlyCalculation = new MonthlyCalculation();
	}
	
	/**
	 * ファクトリー
	 * @param employeeID 社員ID
	 * @param datePeriod 期間
	 * @param monthlyCalculation 月の計算
	 * @param aggregateDays 集計日数
	 * @return 月別実績の勤怠時間
	 */
	public static AttendanceTimeOfMonthly of(
			String employeeID,
			DatePeriod datePeriod,
			MonthlyCalculation monthlyCalculation,
			AttendanceDaysMonthly aggregateDays){
		
		AttendanceTimeOfMonthly domain = new AttendanceTimeOfMonthly(employeeID, datePeriod);
		domain.monthlyCalculation = monthlyCalculation;
		domain.aggregateDays = aggregateDays;
		return domain;
	}
	
	/**
	 * 履歴ごとに月別実績を集計する
	 * @param companyCode 会社コード
	 * @param workingSystem 労働制
	 */
	public void aggregate(String companyCode, WorkingSystem workingSystem){
		
		this.monthlyCalculation.aggregate(companyCode, workingSystem);
	}
}
