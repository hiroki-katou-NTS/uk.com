package nts.uk.ctx.at.record.dom.monthlyprocess.aggr;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.command.AsyncCommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.CreateDailyResultDomainServiceImpl.ProcessState;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthlyRepository;
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
		for (int ixClosure = 1; ixClosure <= 1; ixClosure++){
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
				//*****（未）　画面側の仕様が不明だが、画面にエラーを表示するなら、このタイミングで、セション値として入れて返す。
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
		
		// 登録および更新
		this.attendanceTimeRepository.persistAndUpdate(attendanceTime);
		
		//*****（テスト　2018.2.5 shuichi_ishida） 子（残業時間）だけ更新してみる
		/*
		val monthlyCalculation = attendanceTime.getMonthlyCalculation();
		val aggregateTotalWorkingTime = monthlyCalculation.getTotalWorkingTime();
		val overTimeWork = aggregateTotalWorkingTime.getOverTime();
		val aggregateOverTimeList = overTimeWork.getAggregateOverTimeMap().values();
		val attendanceTimeKey = new AttendanceTimeOfMonthlyKey(
				attendanceTime.getEmployeeId(),
				attendanceTime.getYearMonth(),
				attendanceTime.getClosureId(),
				attendanceTime.getClosureDate());
		this.overTimeRepository.update(attendanceTimeKey, overTimeWork);
		this.aggregateOverTimeRepository.removeByParentPK(attendanceTimeKey);
		for (val aggregateOverTime : aggregateOverTimeList){
			this.aggregateOverTimeRepository.insert(attendanceTimeKey, aggregateOverTime);
		}
		*/
	}
}
