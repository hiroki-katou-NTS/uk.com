package nts.uk.ctx.at.record.dom.monthlyprocess.aggr;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.diagnose.stopwatch.Stopwatches;
import nts.arc.layer.app.command.AsyncCommandHandlerContext;
import nts.arc.task.data.TaskDataSetter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.output.ExecutionAttr;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.CreateDailyResultDomainServiceImpl.ProcessState;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.MonAggrCompanySettings;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.RepositoriesRequiredByMonthlyAggr;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.EmpCalAndSumExeLogRepository;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.ErrMessageInfo;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.ErrMessageInfoRepository;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.ErrMessageResource;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.ExecutionLog;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.enums.ErrorPresent;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.enums.ExecutionContent;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.enums.ExecutionStatus;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.enums.ExecutionType;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * ドメインサービス：月次集計
 * @author shuichi_ishida
 */
@Stateless
public class MonthlyAggregationServiceImpl implements MonthlyAggregationService {

	/** リポジトリ：就業計算と集計実行ログ */
	@Inject
	private EmpCalAndSumExeLogRepository empCalAndSumExeLogRepository;
	/** リポジトリ：対象者ログ */
	//@Inject
	//private TargetPersonRepository targetPersonRepository;
	
	/** 月別集計が必要とするリポジトリ */
	@Inject
	private RepositoriesRequiredByMonthlyAggr repositories;
	/** ドメインサービス：月別集計　（社員の月別実績を集計する） */
	@Inject
	private MonthlyAggregationEmployeeService monthlyAggregationEmployeeService;
	/** エラーメッセージ情報 */
	@Inject
	private ErrMessageInfoRepository errMessageInfoRepository;
	
	/**
	 * Managerクラス
	 * @param asyncContext 同期コマンドコンテキスト
	 * @param companyId 会社ID
	 * @param employeeIds 社員IDリスト
	 * @param datePeriod 期間
	 * @param executionAttr 実行区分　（手動、自動）
	 * @param empCalAndSumExecLogID 就業計算と集計実行ログID
	 * @param executionLog 実行ログ
	 */
	@Override
	public ProcessState manager(AsyncCommandHandlerContext asyncContext, String companyId, List<String> employeeIds,
			DatePeriod datePeriod, ExecutionAttr executionAttr, String empCalAndSumExecLogID,
			Optional<ExecutionLog> executionLog) {
		
		ProcessState status = ProcessState.SUCCESS;
		
		// 実行状態　初期設定
		val dataSetter = asyncContext.getDataSetter();
		dataSetter.setData("monthlyAggregateCount", 0);
		dataSetter.setData("monthlyAggregateStatus", ExecutionStatus.PROCESSING.nameId);
		dataSetter.setData("monthlyAggregateHasError", ErrorPresent.NO_ERROR.nameId);

		// 月次集計を実行するかチェックする
		// ※　実行しない時、終了状態＝正常終了
		if (!executionLog.isPresent()) return status;
		if (executionLog.get().getExecutionContent() != ExecutionContent.MONTHLY_AGGREGATION) return status;
		if (!executionLog.get().getMonlyAggregationSetInfo().isPresent()) return status;
		val executionContent = executionLog.get().getExecutionContent();
		
		// 実行種別　取得　（通常、再実行）
		ExecutionType reAggrAtr = executionLog.get().getMonlyAggregationSetInfo().get().getExecutionType();
		
		// ログ情報（実行ログ）を更新する
		this.empCalAndSumExeLogRepository.updateLogInfo(empCalAndSumExecLogID, executionContent.value,
				ExecutionStatus.PROCESSING.value);
		
		// システム日付を取得する
		val criteriaDate = GeneralDate.today();
		
		// 対象社員を判断
		List<String> procEmployeeIds = new ArrayList<>();
		if (executionAttr == ExecutionAttr.AUTO){
			
			// 自動実行の時
			//*****（未）　社員IDリストを得るアルゴリズムの引数設計に不整合があるので、確認待ち。以下、仮対応。
			procEmployeeIds = employeeIds;
		}
		else {
			
			// 手動実行の時
			procEmployeeIds = employeeIds;
		}
		
		Stopwatches.reset("08000:会社別データ読み込み");
		Stopwatches.start("08000:会社別データ読み込み");
		
		MonAggrCompanySettings companySets = null;
		if (procEmployeeIds.size() > 0){
			
			// 月別集計で必要な会社別設定を取得する
			companySets = MonAggrCompanySettings.loadSettings(companyId, this.repositories);
			if (companySets.getErrorInfos().size() > 0){
				
				// エラー処理
				List<MonthlyAggregationErrorInfo> errorInfoList = new ArrayList<>();
				for (val errorInfo : companySets.getErrorInfos().entrySet()){
					errorInfoList.add(new MonthlyAggregationErrorInfo(errorInfo.getKey(), errorInfo.getValue()));
				}
				this.errorProc(dataSetter, procEmployeeIds.get(0), empCalAndSumExecLogID, criteriaDate, errorInfoList);
				
				// 処理を完了する
				dataSetter.updateData("monthlyAggregateStatus", ExecutionStatus.DONE.nameId);
				this.empCalAndSumExeLogRepository.updateLogInfo(
						empCalAndSumExecLogID, executionContent.value, ExecutionStatus.DONE.value);
				return status;
			}
		}
		
		Stopwatches.stop("08000:会社別データ読み込み");
		
		// 社員の数だけループ
		int aggregatedCount = 0;
		for (val employeeId : procEmployeeIds) {
		
			Stopwatches.reset("10000:社員ごと：" + employeeId);
			Stopwatches.start("10000:社員ごと：" + employeeId);
			
			// 社員1人分の処理　（社員の月別実績を集計する）
			status = this.monthlyAggregationEmployeeService.aggregate(asyncContext,
					companyId, employeeId, criteriaDate, empCalAndSumExecLogID, reAggrAtr, companySets);

			Stopwatches.stop("10000:社員ごと：" + employeeId);
			Stopwatches.printAll();

			if (status == ProcessState.SUCCESS){
				
				// 成功時、ログ情報（実行内容の完了状態(（社員別））を更新する
				//*****（未）　処理が不完全で、日別作成以外で使えない状態になっている。
				//*****（未）　ステータスは、何を設定するかが規定されていない。未処理は、1っぽい。
				//this.targetPersonRepository.update(employeeId, empCalAndSumExecLogID, 0);
				
				aggregatedCount++;
				dataSetter.updateData("monthlyAggregateCount", aggregatedCount);
			}
			if (status == ProcessState.INTERRUPTION){
				
				// 中断時
				dataSetter.updateData("monthlyAggregateHasError", ErrorPresent.NO_ERROR.nameId);
				dataSetter.updateData("monthlyAggregateStatus", ExecutionStatus.INCOMPLETE.nameId);
				break;
			}
		}
		if (status == ProcessState.INTERRUPTION) return status;
		
		// 処理を完了する
		dataSetter.updateData("monthlyAggregateHasError", ErrorPresent.NO_ERROR.nameId);
		dataSetter.updateData("monthlyAggregateStatus", ExecutionStatus.DONE.nameId);
		this.empCalAndSumExeLogRepository.updateLogInfo(
				empCalAndSumExecLogID, executionContent.value, ExecutionStatus.DONE.value);
		return status;
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
		dataSetter.updateData("monthlyAggregateHasError", ErrorPresent.HAS_ERROR.nameId);
		
		// エラー出力
		errorInfoList.sort((a, b) -> a.getResourceId().compareTo(b.getResourceId()));
		for (val errorInfo : errorInfoList){
			this.errMessageInfoRepository.add(new ErrMessageInfo(
					employeeId,
					empCalAndSumExecLogID,
					new ErrMessageResource(errorInfo.getResourceId()),
					ExecutionContent.MONTHLY_AGGREGATION,
					outYmd,
					errorInfo.getMessage()));
		}
	}
}
