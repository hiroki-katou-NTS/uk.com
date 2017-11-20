package nts.uk.ctx.at.record.dom.monthlyprocess.aggr;

import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthlyKey;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthlyRepository;
import nts.uk.ctx.at.record.dom.monthly.calc.AggregateTotalTimeSpentAtWork;
import nts.uk.ctx.at.record.dom.monthly.calc.AggregateTotalTimeSpentAtWorkRepository;
import nts.uk.ctx.at.record.dom.monthly.calc.MonthlyCalculation;
import nts.uk.ctx.at.record.dom.monthly.calc.MonthlyCalculationRepository;
import nts.uk.ctx.at.record.dom.monthly.calc.actualworkingtime.IrregularWorkingTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.calc.actualworkingtime.IrregularWorkingTimeOfMonthlyRepository;
import nts.uk.ctx.at.record.dom.monthly.calc.actualworkingtime.RegularAndIrregularTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.calc.actualworkingtime.RegularAndIrregularTimeOfMonthlyRepository;
import nts.uk.ctx.at.record.dom.monthly.calc.flex.FlexTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.calc.flex.FlexTimeOfMonthlyRepository;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.AggregateTotalWorkingTime;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.AggregateTotalWorkingTimeRepository;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.PrescribedWorkingTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.PrescribedWorkingTimeOfMonthlyRepository;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.WorkTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.WorkTimeOfMonthlyRepository;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.holidayusetime.AnnualLeaveUseTimeOfMonthlyRepository;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.holidayusetime.CompensatoryLeaveUseTimeOfMonthlyRepository;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.holidayusetime.HolidayUseTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.holidayusetime.RetentionYearlyUseTimeOfMonthlyRepository;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.holidayusetime.SpecialHolidayUseTimeOfMonthlyRepository;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.holidayworkandcompensatoryleave.AggregateHolidayWorkTime;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.holidayworkandcompensatoryleave.AggregateHolidayWorkTimeRepository;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.holidayworkandcompensatoryleave.HolidayWorkTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.holidayworkandcompensatoryleave.HolidayWorkTimeOfMonthlyRepository;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.overtimework.AggregateOverTimeWork;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.overtimework.AggregateOverTimeWorkRepository;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.overtimework.OverTimeWorkOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.overtimework.OverTimeWorkOfMonthlyRepository;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.AggregateMonthlyRecord;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.AggregateMonthlyRecordValue;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * ドメインサービス：月次集計
 * @author shuichi_ishida
 */
@Stateless
public class MonthlyAggregation {

	/** ドメインサービス：月別実績を集計する */
	private AggregateMonthlyRecord aggregateMonthlyRecord;
	
	/** リポジトリ：月別実績の勤怠時間 */
	@Inject
	private AttendanceTimeOfMonthlyRepository attendanceTimeRepository;
	
	/** リポジトリ：月別実績の月の計算 */
	@Inject
	private MonthlyCalculationRepository monthlyCalculationRepository;

	/** リポジトリ：月別実績の通常変形時間 */
	@Inject
	private RegularAndIrregularTimeOfMonthlyRepository regularAndIrregularTimeRepository;

	/** リポジトリ：月別実績の変形労働時間 */
	@Inject
	private IrregularWorkingTimeOfMonthlyRepository irregularWorkingTimeRepository;

	/** リポジトリ：月別実績のフレックス時間 */
	@Inject
	private FlexTimeOfMonthlyRepository flexTimeRepository;

	/** リポジトリ：集計総労働時間 */
	@Inject
	private AggregateTotalWorkingTimeRepository aggregateTotalWorkingTimeRepository;

	/** リポジトリ：月別実績の就業時間 */
	@Inject
	private WorkTimeOfMonthlyRepository workTimeRepository;

	/** リポジトリ：月別実績の残業時間 */
	@Inject
	private OverTimeWorkOfMonthlyRepository overTimeWorkRepository;

	/** リポジトリ：集計残業時間 */
	@Inject
	private AggregateOverTimeWorkRepository aggregateOverTimeWorkRepository;

	/** リポジトリ：月別実績の年休使用時間 */
	@Inject
	private AnnualLeaveUseTimeOfMonthlyRepository annualLeaveUseTimeRepository;

	/** リポジトリ：月別実績の代休使用時間 */
	@Inject
	private CompensatoryLeaveUseTimeOfMonthlyRepository compensatoryLeaveUseTimeRepository;

	/** リポジトリ：月別実績の積立年休使用時間 */
	@Inject
	private RetentionYearlyUseTimeOfMonthlyRepository retentionYearlyUseTimeRepository;

	/** リポジトリ：月別実績の特別休暇使用時間 */
	@Inject
	private SpecialHolidayUseTimeOfMonthlyRepository specialHolidayUseTimeRepository;

	/** リポジトリ：月別実績の休出時間 */
	@Inject
	private HolidayWorkTimeOfMonthlyRepository holidayWorkTimeRepository; 

	/** リポジトリ：集計休出時間 */
	@Inject
	private AggregateHolidayWorkTimeRepository aggregateHolidayWorkTimeRepository;

	/** リポジトリ：月別実績の所定労働時間 */
	@Inject
	private PrescribedWorkingTimeOfMonthlyRepository prescribedWorkingTimeRepository; 

	/** リポジトリ：集計総拘束時間 */
	@Inject
	private AggregateTotalTimeSpentAtWorkRepository aggregateTotalTimeSpentAtWorkRepository;
	
	/**
	 * コンストラクタ
	 */
	public MonthlyAggregation(){
		
		this.aggregateMonthlyRecord = new AggregateMonthlyRecord();
	}
	
	/**
	 * Manager
	 * @param companyCode 会社コード
	 * @param employeeIDs 社員IDリスト
	 * @param datePeriod 期間
	 * @return 終了状態　（0:正常終了、1:中断、-1:エラーあり）
	 */
	public int manager(String companyCode, List<String> employeeIDs, DatePeriod datePeriod){
		
		return this.managerProcess(companyCode, employeeIDs, datePeriod);
	}
	
	/**
	 * 社員の月別実績を集計する
	 * @param companyCode 会社コード
	 * @param employeeID 社員ID
	 * @param datePeriod 期間
	 * @return 終了状態　（0:正常終了、1:中断、-1:エラーあり）
	 */
	public int aggregateByEmployee(String companyCode, String employeeID, DatePeriod datePeriod){
		
		return this.aggregateByEmployeeProcess(companyCode, employeeID, datePeriod);
	}
	
	/**
	 * Manager処理
	 * @param companyCode 会社コード
	 * @param employeeIDs 社員IDリスト
	 * @param datePeriod 期間
	 * @return 終了状態　（0:正常終了、1:中断、-1:エラーあり）
	 */
	private int managerProcess(String companyCode, List<String> employeeIDs, DatePeriod datePeriod){
		
		// 月次集計を実行するかチェックする
		// ＞実行しない時、終了状態＝正常終了　として終了
		
		// ログ情報（実行ログ）を更新する
		
		// 社員の数だけループ
		for (String employeeID : employeeIDs) {
		
			// 社員1人分の処理　（社員の月別実績を集計する）
			switch (this.aggregateByEmployeeProcess(companyCode, employeeID, datePeriod)){
			case 1:		// エラーあり
				break;
			case -1:	// 中断
				break;
			default:	// 正常終了
			}
			
			// ログ情報（実行内容の完了状態）を更新する
			
			// 状態を確認する
			// ＞中断の時、終了状態＝中断　として、ループ終了
			//　＞エラーの時、どうする？
		}
		
		// 処理を完了する
		
		// 終了状態＝正常終了
		return 0;
	}
	
	/**
	 * 社員の月別実績を集計する　（処理）
	 * @param companyCode 会社コード
	 * @param employeeID 社員ID
	 * @param datePeriod 期間
	 * @return 終了状態　（0:正常終了、1:中断、-1:エラーあり）
	 */
	private int aggregateByEmployeeProcess(String companyCode, String employeeID, DatePeriod datePeriod){
		
		// 中断依頼が出されているかチェックする
		// ＞中断の時、親に「中断」を返す
		// return 1;		// 中断
		
		// アルゴリズム「実績ロックされているか判定する」を実行する
		// ＞ロックされていれば、親に「中断」を返す
		// return 1;		// 中断
		
		try {
			// 月別実績を集計する　（アルゴリズム）
			AggregateMonthlyRecordValue value = this.aggregateMonthlyRecord.aggregate(companyCode, employeeID, datePeriod);
			
			// 状態を確認する
			// ＞エラーありの時、親に「エラー」を返す
			// return -1;		// エラーあり
			
			// 登録する
			for (AttendanceTimeOfMonthly attendanceTime : value.getAttendanceTimes()){
				this.registAttendanceTime(attendanceTime);
			}
		}
		catch (Exception e) {
			return -1;		// エラーあり
		}
		
		return 0;		// 正常終了
	}
	
	/**
	 * 登録する
	 * @param attendanceTime 月別実績の勤怠時間
	 */
	private void registAttendanceTime(AttendanceTimeOfMonthly attendanceTime){
		
		// キー値　確認
		AttendanceTimeOfMonthlyKey attendanceTimeKey = new AttendanceTimeOfMonthlyKey(
				attendanceTime.getEmployeeID(),
				attendanceTime.getDatePeriod());
		
		// 更新ドメイン　確認
		MonthlyCalculation monthlyCalculation = attendanceTime.getMonthlyCalculation();
		RegularAndIrregularTimeOfMonthly regularAndIrregularTime = monthlyCalculation.getActualWorkingTime();
		IrregularWorkingTimeOfMonthly irregularWorkingTime = regularAndIrregularTime.getIrregularWorkingTime();
		FlexTimeOfMonthly flexTime = monthlyCalculation.getFlexTime();
		AggregateTotalWorkingTime aggregateTotalWorkingTime = monthlyCalculation.getTotalWorkingTime();
		WorkTimeOfMonthly workTime = aggregateTotalWorkingTime.getWorkTime();
		OverTimeWorkOfMonthly overTimeWork = aggregateTotalWorkingTime.getOverTimeWork();
		List<AggregateOverTimeWork> aggregateOverTimeWorks = overTimeWork.getAggregateOverTimeWorks();
		HolidayUseTimeOfMonthly holidayUseTime = aggregateTotalWorkingTime.getHolidayUseTime();
		HolidayWorkTimeOfMonthly holidayWorktime = aggregateTotalWorkingTime.getHolidayWorkTime();
		List<AggregateHolidayWorkTime> aggregateHolidayWorkTimes = holidayWorktime.getAggregateHolidayWorkTimes();
		PrescribedWorkingTimeOfMonthly prescribedWorkingTime = aggregateTotalWorkingTime.getPrescribedWorkingTime();
		AggregateTotalTimeSpentAtWork aggregateTotalTimeSpentAtWork = monthlyCalculation.getTotalTimeSpentAtWork();
		
		if (this.attendanceTimeRepository.findByPK(
				attendanceTime.getEmployeeID(), attendanceTime.getDatePeriod()).isPresent()) {
			
			// 更新
			this.attendanceTimeRepository.update(attendanceTime);
			this.monthlyCalculationRepository.update(attendanceTimeKey, monthlyCalculation);
			this.regularAndIrregularTimeRepository.update(attendanceTimeKey, regularAndIrregularTime);
			this.irregularWorkingTimeRepository.update(attendanceTimeKey, irregularWorkingTime);
			this.flexTimeRepository.update(attendanceTimeKey, flexTime);
			this.aggregateTotalWorkingTimeRepository.update(attendanceTimeKey, aggregateTotalWorkingTime);
			this.workTimeRepository.update(attendanceTimeKey, workTime);
			this.overTimeWorkRepository.update(attendanceTimeKey, overTimeWork);

			// 集計残業時間は、枠数変動に対応するため、先に削除して、追加する
			this.aggregateOverTimeWorkRepository.removeByParentPK(attendanceTimeKey);
			for (AggregateOverTimeWork aggregateOverTimework : aggregateOverTimeWorks){
				this.aggregateOverTimeWorkRepository.insert(attendanceTimeKey, aggregateOverTimework);
			}
			
			this.annualLeaveUseTimeRepository.update(attendanceTimeKey, holidayUseTime.getAnnualLeave());
			this.compensatoryLeaveUseTimeRepository.update(attendanceTimeKey, holidayUseTime.getCompensatoryLeave());
			this.retentionYearlyUseTimeRepository.update(attendanceTimeKey, holidayUseTime.getRetentionYearly());
			this.specialHolidayUseTimeRepository.update(attendanceTimeKey, holidayUseTime.getSpecialHoliday());
			this.holidayWorkTimeRepository.update(attendanceTimeKey, holidayWorktime);

			// 集計休出時間は、枠数変動に対応するため、先に削除して、追加する
			this.aggregateHolidayWorkTimeRepository.removeByParentPK(attendanceTimeKey);
			for (AggregateHolidayWorkTime aggregateHolidayWorkTime : aggregateHolidayWorkTimes){
				this.aggregateHolidayWorkTimeRepository.insert(attendanceTimeKey, aggregateHolidayWorkTime);
			}
			
			this.prescribedWorkingTimeRepository.update(attendanceTimeKey, prescribedWorkingTime);
			this.aggregateTotalTimeSpentAtWorkRepository.update(attendanceTimeKey, aggregateTotalTimeSpentAtWork);
		}
		else {
			
			// 追加
			this.attendanceTimeRepository.insert(attendanceTime);
			this.monthlyCalculationRepository.insert(attendanceTimeKey, monthlyCalculation);
			this.regularAndIrregularTimeRepository.insert(attendanceTimeKey, regularAndIrregularTime);
			this.irregularWorkingTimeRepository.insert(attendanceTimeKey, irregularWorkingTime);
			this.flexTimeRepository.insert(attendanceTimeKey, flexTime);
			this.aggregateTotalWorkingTimeRepository.insert(attendanceTimeKey, aggregateTotalWorkingTime);
			this.workTimeRepository.insert(attendanceTimeKey, workTime);
			this.overTimeWorkRepository.insert(attendanceTimeKey, overTimeWork);
			for (AggregateOverTimeWork aggregateOverTimework : aggregateOverTimeWorks){
				this.aggregateOverTimeWorkRepository.insert(attendanceTimeKey, aggregateOverTimework);
			}
			this.annualLeaveUseTimeRepository.insert(attendanceTimeKey, holidayUseTime.getAnnualLeave());
			this.compensatoryLeaveUseTimeRepository.insert(attendanceTimeKey, holidayUseTime.getCompensatoryLeave());
			this.retentionYearlyUseTimeRepository.insert(attendanceTimeKey, holidayUseTime.getRetentionYearly());
			this.specialHolidayUseTimeRepository.insert(attendanceTimeKey, holidayUseTime.getSpecialHoliday());
			this.holidayWorkTimeRepository.insert(attendanceTimeKey, holidayWorktime);
			for (AggregateHolidayWorkTime aggregateHolidayWorkTime : aggregateHolidayWorkTimes){
				this.aggregateHolidayWorkTimeRepository.insert(attendanceTimeKey, aggregateHolidayWorkTime);
			}
			this.prescribedWorkingTimeRepository.insert(attendanceTimeKey, prescribedWorkingTime);
			this.aggregateTotalTimeSpentAtWorkRepository.insert(attendanceTimeKey, aggregateTotalTimeSpentAtWork);
		}
	}
}
