package nts.uk.ctx.at.record.dom.monthlyprocess.aggr;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.command.AsyncCommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.CreateDailyResultDomainServiceImpl.ProcessState;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthlyKey;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthlyRepository;
import nts.uk.ctx.at.record.dom.monthly.calc.AggregateTotalTimeSpentAtWorkRepository;
import nts.uk.ctx.at.record.dom.monthly.calc.actualworkingtime.RegularAndIrregularTimeOfMonthlyRepository;
import nts.uk.ctx.at.record.dom.monthly.calc.flex.FlexTimeOfMonthlyRepository;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.AggregateTotalWorkingTimeRepository;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.hdwkandcompleave.AggregateHolidayWorkTimeRepository;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.hdwkandcompleave.HolidayWorkTimeOfMonthlyRepository;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.overtime.AggregateOverTimeRepository;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.overtime.OverTimeOfMonthlyRepository;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.vacationusetime.VacationUseTimeOfMonthlyRepository;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.AggregateMonthlyRecordService;
import nts.uk.ctx.at.record.dom.workrecord.log.enums.ExecutionType;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureDate;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * ドメインサービス：月別集計　（社員の月別実績を集計する）
 * @author shuichu_ishida
 */
@Stateless
public class MonthlyAggregationEmployeeServiceImpl implements MonthlyAggregationEmployeeService {

	/** ドメインサービス：月別実績を集計する */
	@Inject
	private AggregateMonthlyRecordService aggregateMonthlyRecordService;
	
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
	private AggregateOverTimeRepository aggregateOverTimeRepository;
	/** リポジトリ：月別実績の休暇使用時間 */
	@Inject
	private VacationUseTimeOfMonthlyRepository vacationUseTimeRepository;
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
	 * 社員の月別実績を集計する
	 * @param asyncContext 同期コマンドコンテキスト
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param criteriaDate 基準日
	 * @param empCalAndSumExecLogID 就業計算と集計実行ログID
	 * @param executionType 実行種別　（通常、再実行）
	 */
	@Override
	public ProcessState aggregate(AsyncCommandHandlerContext asyncContext, String companyId, String employeeId,
			GeneralDate criteriaDate, String empCalAndSumExecLogID, ExecutionType executionType) {
		
		ProcessState status = ProcessState.SUCCESS;
		val dataSetter = asyncContext.getDataSetter();
		
		// 集計期間の判断
		//*****（未）　実締め毎集計期間クラスの作成が必要。仮に変数で設定。実締め毎集計期間は、ループで複数件の処理要。
		for (int ixClosure = 1; ixClosure <= 2; ixClosure++){
			YearMonth yearMonth = YearMonth.of(2017, 11);
			val closureId = ClosureId.RegularEmployee;
			ClosureDate closureDate = new ClosureDate(0, true);
			DatePeriod datePeriod = new DatePeriod(GeneralDate.ymd(2017, 11, 1), GeneralDate.ymd(2017, 11, 30));
			if (ixClosure == 2) {
				yearMonth = YearMonth.of(2017, 12);
				closureDate = new ClosureDate(20, false);
				datePeriod = new DatePeriod(GeneralDate.ymd(2017, 12, 1), GeneralDate.ymd(2017, 12, 20));
			}
			
			// 中断依頼が出されているかチェックする
			if (asyncContext.hasBeenRequestedToCancel()) {
				asyncContext.finishedAsCancelled();
				return ProcessState.INTERRUPTION;
			}
			
			// アルゴリズム「実績ロックされているか判定する」を実行する
			// ＞ロックされていれば、親に「中断」を返す
			//*****（未）　共通処理として作る必要がある。現時点では、日別作成の中にprivateで作られているため、共有できない。
			
			// 月別実績を集計する　（アルゴリズム）
			val value = this.aggregateMonthlyRecordService.aggregate(companyId, employeeId,
					yearMonth, closureId, closureDate, datePeriod);
			if (value.isError()) {
				//*****（未）　返却値に、「エラー」が必要？画面側の仕様が不明だが、とりあえず、このプロパティに書くのか？
				dataSetter.updateData("monthlyAggregateHasError", "エラーあり");
				asyncContext.finishedAsCancelled();
				return ProcessState.INTERRUPTION;
			}
			
			// 登録する
			for (val attendanceTime : value.getAttendanceTimes()){
				this.registAttendanceTime(attendanceTime);
			}
		}
		return status;
	}
	
	/**
	 * 登録する
	 * @param attendanceTime 月別実績の勤怠時間
	 */
	private void registAttendanceTime(AttendanceTimeOfMonthly attendanceTime){
		
		// キー値　確認
		val attendanceTimeKey = new AttendanceTimeOfMonthlyKey(
				attendanceTime.getEmployeeId(),
				attendanceTime.getYearMonth(),
				attendanceTime.getClosureId(),
				attendanceTime.getClosureDate());
		
		// 更新ドメイン　確認
		val monthlyCalculation = attendanceTime.getMonthlyCalculation();
		val regularAndIrregularTime = monthlyCalculation.getActualWorkingTime();
		val flexTime = monthlyCalculation.getFlexTime();
		val aggregateTotalWorkingTime = monthlyCalculation.getTotalWorkingTime();
		val overTimeWork = aggregateTotalWorkingTime.getOverTime();
		val aggregateOverTimeList = overTimeWork.getAggregateOverTimeMap().values();
		val vacationUseTime = aggregateTotalWorkingTime.getVacationUseTime();
		val holidayWorktime = aggregateTotalWorkingTime.getHolidayWorkTime();
		val aggregateHolidayWorkTimeList = holidayWorktime.getAggregateHolidayWorkTimeMap().values();
		val aggregateTotalTimeSpentAtWork = monthlyCalculation.getTotalTimeSpentAtWork();
		
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
			this.aggregateOverTimeRepository.removeByParentPK(attendanceTimeKey);
			for (val aggregateOverTime : aggregateOverTimeList){
				this.aggregateOverTimeRepository.insert(attendanceTimeKey, aggregateOverTime);
			}
			
			this.vacationUseTimeRepository.update(attendanceTimeKey, vacationUseTime);
			this.holidayWorkTimeRepository.update(attendanceTimeKey, holidayWorktime);

			// 集計休出時間は、枠数変動に対応するため、先に削除して、追加する
			this.aggregateHolidayWorkTimeRepository.removeByParentPK(attendanceTimeKey);
			for (val aggregateHolidayWorkTime : aggregateHolidayWorkTimeList){
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
			for (val aggregateOverTime : aggregateOverTimeList){
				this.aggregateOverTimeRepository.insert(attendanceTimeKey, aggregateOverTime);
			}
			this.vacationUseTimeRepository.insert(attendanceTimeKey, vacationUseTime);
			this.holidayWorkTimeRepository.insert(attendanceTimeKey, holidayWorktime);
			for (val aggregateHolidayWorkTime : aggregateHolidayWorkTimeList){
				this.aggregateHolidayWorkTimeRepository.insert(attendanceTimeKey, aggregateHolidayWorkTime);
			}
			this.aggregateTotalTimeSpentAtWorkRepository.insert(attendanceTimeKey, aggregateTotalTimeSpentAtWork);
		}
	}
}
