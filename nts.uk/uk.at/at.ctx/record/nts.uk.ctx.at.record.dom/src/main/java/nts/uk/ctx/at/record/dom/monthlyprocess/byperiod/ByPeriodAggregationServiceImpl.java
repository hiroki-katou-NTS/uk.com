package nts.uk.ctx.at.record.dom.monthlyprocess.byperiod;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.command.AsyncCommandHandlerContext;
import nts.arc.task.data.TaskDataSetter;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.record.dom.byperiod.AttendanceTimeOfAnyPeriodRepository;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.CreateDailyResultDomainServiceImpl.ProcessState;
import nts.uk.ctx.at.record.dom.executionstatusmanage.optionalperiodprocess.AggrPeriodExcution;
import nts.uk.ctx.at.record.dom.executionstatusmanage.optionalperiodprocess.AggrPeriodExcutionRepository;
import nts.uk.ctx.at.record.dom.executionstatusmanage.optionalperiodprocess.AggrPeriodInfor;
import nts.uk.ctx.at.record.dom.executionstatusmanage.optionalperiodprocess.AggrPeriodInforRepository;
import nts.uk.ctx.at.record.dom.executionstatusmanage.optionalperiodprocess.AggrPeriodTarget;
import nts.uk.ctx.at.record.dom.executionstatusmanage.optionalperiodprocess.AggrPeriodTargetRepository;
import nts.uk.ctx.at.record.dom.executionstatusmanage.optionalperiodprocess.periodexcution.ExecutionStatus;
import nts.uk.ctx.at.record.dom.executionstatusmanage.optionalperiodprocess.periodinfor.ErrorMess;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.MonthlyAggregationErrorInfo;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.MonAggrCompanySettings;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.MonAggrEmployeeSettings;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.RepositoriesRequiredByMonthlyAggr;
import nts.uk.ctx.at.record.dom.resultsperiod.optionalaggregationperiod.OptionalAggrPeriod;
import nts.uk.ctx.at.record.dom.resultsperiod.optionalaggregationperiod.OptionalAggrPeriodRepository;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * 実装：任意期間集計Mgr
 * @author shuichu_ishida
 */
@Stateless
public class ByPeriodAggregationServiceImpl implements ByPeriodAggregationService {

	/** 任意期間集計実行ログ */
	@Inject
	private AggrPeriodExcutionRepository executionRepo;
	/** 任意集計期間 */
	@Inject
	private OptionalAggrPeriodRepository aggrPeriodRepo;
	/** 任意期間集計対象者 */
	@Inject
	private AggrPeriodTargetRepository targetRepo;
	/** 月別集計が必要とするリポジトリ */
	@Inject
	private RepositoriesRequiredByMonthlyAggr repositories;
	/** 任意期間集計Mgr　（アルゴリズム） */
	@Inject
	private AggregateByPeriodRecordService aggregateByPeriod;
	/** 任意期間別実績の勤怠時間 */
	@Inject
	private AttendanceTimeOfAnyPeriodRepository attendanceTimeRepo;
	/** エラーメッセージ情報 */
	@Inject
	private AggrPeriodInforRepository inforRepo;
	
	/** 任意期間集計Mgrクラス */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@Override
	public <C> void manager(String companyId, String executeId, AsyncCommandHandlerContext<C> async) {

		// 実行状態　初期設定
		val dataSetter = async.getDataSetter();
		dataSetter.setData("aggCreateCount", 0);
		dataSetter.setData("aggCreateStatus", ExecutionStatus.PROCESSING.name);
		
		// 「任意期間集計実行ログ」を取得
		Optional<AggrPeriodExcution> executionPeriodOpt = this.executionRepo.findByAggr(companyId, executeId);
		if (!executionPeriodOpt.isPresent()) return;
		val executionPeriod = executionPeriodOpt.get();

		// 「任意集計期間」を取得
		Optional<OptionalAggrPeriod> optionalPeriodOpt = this.aggrPeriodRepo.find(
				companyId, executionPeriod.getAggrFrameCode().v());
		if (!optionalPeriodOpt.isPresent()) return;
		val optionalPeriod = optionalPeriodOpt.get();

		// 期間の判断
		DatePeriod period = new DatePeriod(optionalPeriod.getStartDate(), optionalPeriod.getEndDate());

		// ログ情報（実行ログ）を更新する
		this.executionRepo.updateExe(executionPeriod, ExecutionStatus.PROCESSING.value, GeneralDateTime.now());

		// 対象社員を判断
		List<AggrPeriodTarget> targets = this.targetRepo.findAll(executeId);
		if (targets.size() == 0){
			this.executionRepo.updateExe(executionPeriod, ExecutionStatus.DONE.value, GeneralDateTime.now());
			dataSetter.updateData("aggCreateStatus", ExecutionStatus.DONE.name);
			return;
		}
		
		// 月別集計で必要な会社別設定を取得する
		MonAggrCompanySettings companySets = MonAggrCompanySettings.loadSettings(companyId, this.repositories);
		if (companySets.getErrorInfos().size() > 0){
			
			// エラー処理
			List<MonthlyAggregationErrorInfo> errorInfoList = new ArrayList<>();
			for (val errorInfo : companySets.getErrorInfos().entrySet()){
				errorInfoList.add(new MonthlyAggregationErrorInfo(errorInfo.getKey(), errorInfo.getValue()));
			}
			this.errorProc(dataSetter, targets.get(0).getEmployeeId(), executeId, period.end(), errorInfoList);
			
			// 処理を完了する
			this.executionRepo.updateExe(executionPeriod, ExecutionStatus.DONE_WITH_ERROR.value, GeneralDateTime.now());
			dataSetter.updateData("aggCreateStatus", ExecutionStatus.DONE_WITH_ERROR.name);
			return;
		}

		// 社員の数だけループ　（並列処理）
		StateHolder stateHolder = new StateHolder(targets.size());
		for (val target : targets){
			if (stateHolder.isInterrupt()) break;
		
			// 社員1人分の処理　（社員の任意期間別実績を集計する）
			ProcessState coStatus = this.aggregate(async,
					companyId, target.getEmployeeId(), period, executeId, optionalPeriod, companySets);
			stateHolder.add(coStatus);

			if (coStatus == ProcessState.SUCCESS){
				
				// ログ情報（実行内容の完了状態）を更新する
				this.targetRepo.updateExcution(target);
				dataSetter.updateData("aggCreateCount", stateHolder.count());
			}
			if (coStatus == ProcessState.INTERRUPTION){
				
				// 中断時
				this.executionRepo.updateExe(executionPeriod, ExecutionStatus.END_OF_INTERRUPTION.value, GeneralDateTime.now());
				dataSetter.updateData("aggCreateStatus", ExecutionStatus.END_OF_INTERRUPTION.name);
				//async.finishedAsCancelled();
				return;
			}
		}

		// 状態を確認する
		List<AggrPeriodInfor> infors = this.inforRepo.findAll(executeId);
		if (infors.size() == 0) {
			this.executionRepo.updateExe(executionPeriod, ExecutionStatus.DONE.value, GeneralDateTime.now());
			dataSetter.updateData("aggCreateStatus", ExecutionStatus.DONE.name);
		} else {
			this.executionRepo.updateExe(executionPeriod, ExecutionStatus.DONE_WITH_ERROR.value, GeneralDateTime.now());
			dataSetter.updateData("aggCreateStatus", ExecutionStatus.DONE_WITH_ERROR.name);
		}
	}
	
	/** 社員の任意期間別実績を集計する */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	@Override
	public ProcessState aggregate(
			AsyncCommandHandlerContext async, String companyId, String employeeId, DatePeriod period,
			String executeId, OptionalAggrPeriod optionalPeriod, MonAggrCompanySettings companySets) {
		
		val dataSetter = async.getDataSetter();
		
		// 中断依頼が出されているかチェックする
		if (async.hasBeenRequestedToCancel()) {
			//async.finishedAsCancelled();
			return ProcessState.INTERRUPTION;
		}
		
		// 月別集計で必要な社員別設定を取得
		val employeeSets = MonAggrEmployeeSettings.loadSettings(companyId, employeeId, period, this.repositories);
		if (employeeSets.getErrorInfos().size() > 0){
			
			// エラー処理
			List<MonthlyAggregationErrorInfo> errorInfoList = new ArrayList<>();
			for (val errorInfo : employeeSets.getErrorInfos().entrySet()){
				errorInfoList.add(new MonthlyAggregationErrorInfo(errorInfo.getKey(), errorInfo.getValue()));
			}
			this.errorProc(dataSetter, employeeId, executeId, period.end(), errorInfoList);
			return ProcessState.SUCCESS;
		}
		
		// 集計処理を実行
		val value = this.aggregateByPeriod.algorithm(
				companyId, employeeId, period, optionalPeriod, companySets, employeeSets);
		if (value.getErrorInfos().size() > 0) {

			// エラー処理
			List<MonthlyAggregationErrorInfo> errorInfoList = new ArrayList<>();
			errorInfoList.addAll(value.getErrorInfos().values());
			this.errorProc(dataSetter, employeeId, executeId, period.end(), errorInfoList);
			
			// 中断するエラーがある時、中断処理をする
			if (value.isInterruption()){
				async.finishedAsCancelled();
				return ProcessState.INTERRUPTION;
			}
		}
		
		// 登録する
		if (value.getAttendanceTime().isPresent()){
			this.attendanceTimeRepo.persistAndUpdate(value.getAttendanceTime().get());
		}
		
		return ProcessState.SUCCESS;
	}
	
	/**
	 * 状態保存
	 * @author shuichu_ishida
	 */
	private class StateHolder {
		private BlockingQueue<ProcessState> status;
		
		public StateHolder(int max) {
			this.status = new ArrayBlockingQueue<>(max);
		}
		
		public void add(ProcessState status) { this.status.add(status); }
		
		public int count() { return this.status.size(); }
		
		public boolean isInterrupt() {
			return this.status.stream().filter(c -> c == ProcessState.INTERRUPTION).findFirst().isPresent();
		}
	}
	
	/**
	 * エラー処理
	 * @param dataSetter データセッター
	 * @param employeeId 社員ID
	 * @param empCalAndSumExecLogID 就業計算と集計実行ログID
	 * @param outYmd 出力年月日
	 * @param errorInfoList エラー情報リスト
	 */
	private void errorProc(
			TaskDataSetter dataSetter,
			String employeeId,
			String empCalAndSumExecLogID,
			GeneralDate outYmd,
			List<MonthlyAggregationErrorInfo> errorInfoList){
		
		// 「エラーあり」に更新
		dataSetter.updateData("aggCreateStatus", ExecutionStatus.DONE_WITH_ERROR.name);
		
		// エラー出力
		errorInfoList.sort((a, b) -> a.getResourceId().compareTo(b.getResourceId()));
		for (val errorInfo : errorInfoList){
			this.inforRepo.addPeriodInfor(new AggrPeriodInfor(
					employeeId,
					empCalAndSumExecLogID,
					errorInfo.getResourceId(),
					outYmd,
					new ErrorMess(errorInfo.getMessage().v())));
		}
	}
}
