package nts.uk.ctx.at.record.dom.monthlyprocess.aggr;

import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthlyKey;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthlyRepository;
import nts.uk.ctx.at.record.dom.monthly.calc.AggregateTotalTimeSpentAtWork;
import nts.uk.ctx.at.record.dom.monthly.calc.AggregateTotalTimeSpentAtWorkRepository;
import nts.uk.ctx.at.record.dom.monthly.calc.MonthlyCalculation;
import nts.uk.ctx.at.record.dom.monthly.calc.actualworkingtime.RegularAndIrregularTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.calc.actualworkingtime.RegularAndIrregularTimeOfMonthlyRepository;
import nts.uk.ctx.at.record.dom.monthly.calc.flex.FlexTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.calc.flex.FlexTimeOfMonthlyRepository;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.AggregateTotalWorkingTime;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.AggregateTotalWorkingTimeRepository;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.holidayusetime.HolidayUseTimeOfMonthlyRepository;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.holidayusetime.HolidayUseTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.holidayworkandcompensatoryleave.AggregateHolidayWorkTime;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.holidayworkandcompensatoryleave.AggregateHolidayWorkTimeRepository;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.holidayworkandcompensatoryleave.HolidayWorkTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.holidayworkandcompensatoryleave.HolidayWorkTimeOfMonthlyRepository;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.overtime.AggregateOverTime;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.overtime.AggregateOverTimeRepository;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.overtime.OverTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.overtime.OverTimeOfMonthlyRepository;
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
	
	/** リポジトリ：月別実績の通常変形時間 */
	@Inject
	private RegularAndIrregularTimeOfMonthlyRepository regularAndIrregularTimeRepository;

	/** リポジトリ：月別実績のフレックス時間 */
	@Inject
	private FlexTimeOfMonthlyRepository flexTimeRepository;

	/** リポジトリ：集計総労働時間 */
	@Inject
	private AggregateTotalWorkingTimeRepository aggregateTotalWorkingTimeRepository;

	/** リポジトリ：月別実績の残業時間 */
	@Inject
	private OverTimeOfMonthlyRepository overTimeWorkRepository;

	/** リポジトリ：集計残業時間 */
	@Inject
	private AggregateOverTimeRepository aggregateOverTimeWorkRepository;

	/** リポジトリ：月別実績の休暇使用時間 */
	@Inject
	private HolidayUseTimeOfMonthlyRepository holidayUseTimeRepository;

	/** リポジトリ：月別実績の休出時間 */
	@Inject
	private HolidayWorkTimeOfMonthlyRepository holidayWorkTimeRepository; 

	/** リポジトリ：集計休出時間 */
	@Inject
	private AggregateHolidayWorkTimeRepository aggregateHolidayWorkTimeRepository;

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
	 * @param companyId 会社ID
	 * @param employeeIds 社員IDリスト
	 * @param yearMonth 年月
	 * @param datePeriod 期間
	 * @return 終了状態　（0:正常終了、1:中断）
	 */
	public int manager(String companyId, List<String> employeeIds, YearMonth yearMonth, DatePeriod datePeriod){
		
		return this.managerProcess(companyId, employeeIds, yearMonth, datePeriod);
	}
	
	/**
	 * 社員の月別実績を集計する
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param yearMonth 年月
	 * @param datePeriod 期間
	 * @return 終了状態　（0:正常終了、1:中断）
	 */
	public int aggregateByEmployee(String companyId, String employeeId, YearMonth yearMonth, DatePeriod datePeriod){
		
		return this.aggregateByEmployeeProcess(companyId, employeeId, yearMonth, datePeriod);
	}
	
	/**
	 * Manager処理
	 * @param companyId 会社ID
	 * @param employeeIds 社員IDリスト
	 * @param yearMonth 年月
	 * @param datePeriod 期間
	 * @return 終了状態　（0:正常終了、1:中断）
	 */
	private int managerProcess(String companyId, List<String> employeeIds, YearMonth yearMonth, DatePeriod datePeriod){
		
		// 月次集計を実行するかチェックする
		// ＞実行しない時、終了状態＝正常終了　として終了
		
		// ログ情報（実行ログ）を更新する
		
		// 社員の数だけループ
		for (String employeeId : employeeIds) {
		
			// 社員1人分の処理　（社員の月別実績を集計する）
			switch (this.aggregateByEmployeeProcess(companyId, employeeId, yearMonth, datePeriod)){
			case 1:		// 中断
				break;
			default:	// 正常終了
			}
			
			// ログ情報（実行内容の完了状態）を更新する
			
			// 状態を確認する
			// ＞中断の時、終了状態＝中断　として、ループ終了
			//　＞エラーの時、どうする？　→　必要に応じて、対応するExceptionをthrow
		}
		
		// 処理を完了する
		
		// 終了状態＝正常終了
		return 0;
	}
	
	/**
	 * 社員の月別実績を集計する　（処理）
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param yearMonth 年月
	 * @param datePeriod 期間
	 * @return 終了状態　（0:正常終了、1:中断）
	 */
	private int aggregateByEmployeeProcess(String companyId, String employeeId, YearMonth yearMonth, DatePeriod datePeriod){
		
		// 中断依頼が出されているかチェックする
		// ＞中断の時、親に「中断」を返す
		// return 1;		// 中断
		
		// アルゴリズム「実績ロックされているか判定する」を実行する
		// ＞ロックされていれば、親に「中断」を返す
		// return 1;		// 中断
		
		// 月別実績を集計する　（アルゴリズム）
		AggregateMonthlyRecordValue value = this.aggregateMonthlyRecord.aggregate(companyId, employeeId, yearMonth, datePeriod);
		
		// 状態を確認する
		// ＞エラーありの時、親に該当するExceotionをthrow
		
		// 登録する
		for (AttendanceTimeOfMonthly attendanceTime : value.getAttendanceTimes()){
			this.registAttendanceTime(attendanceTime);
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
				attendanceTime.getEmployeeId(),
				attendanceTime.getYearMonth(),
				attendanceTime.getClosureId(),
				attendanceTime.getClosureDate());
		
		// 更新ドメイン　確認
		MonthlyCalculation monthlyCalculation = attendanceTime.getMonthlyCalculation();
		RegularAndIrregularTimeOfMonthly regularAndIrregularTime = monthlyCalculation.getActualWorkingTime();
		FlexTimeOfMonthly flexTime = monthlyCalculation.getFlexTime();
		AggregateTotalWorkingTime aggregateTotalWorkingTime = monthlyCalculation.getTotalWorkingTime();
		OverTimeOfMonthly overTimeWork = aggregateTotalWorkingTime.getOverTime();
		List<AggregateOverTime> aggregateOverTimeWorks = overTimeWork.getAggregateOverTimes();
		HolidayUseTimeOfMonthly holidayUseTime = aggregateTotalWorkingTime.getHolidayUseTime();
		HolidayWorkTimeOfMonthly holidayWorktime = aggregateTotalWorkingTime.getHolidayWorkTime();
		List<AggregateHolidayWorkTime> aggregateHolidayWorkTimes = holidayWorktime.getAggregateHolidayWorkTimes();
		AggregateTotalTimeSpentAtWork aggregateTotalTimeSpentAtWork = monthlyCalculation.getTotalTimeSpentAtWork();
		
		if (this.attendanceTimeRepository.find(attendanceTime.getEmployeeId(),
				attendanceTime.getYearMonth(),
				attendanceTime.getClosureId(),
				attendanceTime.getClosureDate()).isPresent()) {
			
			// 更新
			this.attendanceTimeRepository.update(attendanceTime);
			this.regularAndIrregularTimeRepository.update(attendanceTimeKey, regularAndIrregularTime);
			this.flexTimeRepository.update(attendanceTimeKey, flexTime);
			this.aggregateTotalWorkingTimeRepository.update(attendanceTimeKey, aggregateTotalWorkingTime);
			this.overTimeWorkRepository.update(attendanceTimeKey, overTimeWork);

			// 集計残業時間は、枠数変動に対応するため、先に削除して、追加する
			this.aggregateOverTimeWorkRepository.removeByParentPK(attendanceTimeKey);
			for (AggregateOverTime aggregateOverTimework : aggregateOverTimeWorks){
				this.aggregateOverTimeWorkRepository.insert(attendanceTimeKey, aggregateOverTimework);
			}
			
			this.holidayUseTimeRepository.update(attendanceTimeKey, holidayUseTime);
			this.holidayWorkTimeRepository.update(attendanceTimeKey, holidayWorktime);

			// 集計休出時間は、枠数変動に対応するため、先に削除して、追加する
			this.aggregateHolidayWorkTimeRepository.removeByParentPK(attendanceTimeKey);
			for (AggregateHolidayWorkTime aggregateHolidayWorkTime : aggregateHolidayWorkTimes){
				this.aggregateHolidayWorkTimeRepository.insert(attendanceTimeKey, aggregateHolidayWorkTime);
			}
			
			this.aggregateTotalTimeSpentAtWorkRepository.update(attendanceTimeKey, aggregateTotalTimeSpentAtWork);
		}
		else {
			
			// 追加
			this.attendanceTimeRepository.insert(attendanceTime);
			this.regularAndIrregularTimeRepository.insert(attendanceTimeKey, regularAndIrregularTime);
			this.flexTimeRepository.insert(attendanceTimeKey, flexTime);
			this.aggregateTotalWorkingTimeRepository.insert(attendanceTimeKey, aggregateTotalWorkingTime);
			this.overTimeWorkRepository.insert(attendanceTimeKey, overTimeWork);
			for (AggregateOverTime aggregateOverTimework : aggregateOverTimeWorks){
				this.aggregateOverTimeWorkRepository.insert(attendanceTimeKey, aggregateOverTimework);
			}
			this.holidayUseTimeRepository.insert(attendanceTimeKey, holidayUseTime);
			this.holidayWorkTimeRepository.insert(attendanceTimeKey, holidayWorktime);
			for (AggregateHolidayWorkTime aggregateHolidayWorkTime : aggregateHolidayWorkTimes){
				this.aggregateHolidayWorkTimeRepository.insert(attendanceTimeKey, aggregateHolidayWorkTime);
			}
			this.aggregateTotalTimeSpentAtWorkRepository.insert(attendanceTimeKey, aggregateTotalTimeSpentAtWork);
		}
	}
}
