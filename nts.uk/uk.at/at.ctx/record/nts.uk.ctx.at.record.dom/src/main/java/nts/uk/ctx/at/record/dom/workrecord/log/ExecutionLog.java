/**
 * 
 */
package nts.uk.ctx.at.record.dom.workrecord.log;

import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.record.dom.workrecord.log.enums.ErrorPresent;
import nts.uk.ctx.at.record.dom.workrecord.log.enums.ExecutionContent;
import nts.uk.ctx.at.record.dom.workrecord.log.enums.ExecutionStatus;

/**
 * 実行ログ
 * @author tutk
 *
 */
@Getter
public class ExecutionLog {
	
	/**
	 * 就業計算と集計実行ログID
	 */
	private String empCalAndSumExecLogID;
	
	/**
	 * 実行内容
	 */
	private ExecutionContent executionContent;
	
	/**
	 * エラーの有無
	 */
	private ErrorPresent existenceError;
	
	/**
	 * 実行日時
	 * start time - end time
	 */
	private ExecutionTime executionTime;
	
	/**
	 * 処理状況
	 */
	private ExecutionStatus processStatus;

	/**
	 * 対象期間
	 * start date - end date
	 */
	private ObjectPeriod objectPeriod;
	
	/**
	 * 計算実行設定情報ID
	 */
	private String calExecutionSetInfoID;
	
	/**
	 * 承認結果反映の設定情報
	 */
	@Setter
	private Optional<SetInforReflAprResult> reflectApprovalSetInfo;
	
	/**
	 * 日別作成の設定情報
	 */
	@Setter
	private Optional<SettingInforForDailyCreation> dailyCreationSetInfo;
	/**
	 * 日別計算の設定情報
	 */
	@Setter
	private Optional<CalExeSettingInfor> dailyCalSetInfo;
	/**
	 * 月別集計の設定情報
	 */
	@Setter
	private Optional<CalExeSettingInfor>  monlyAggregationSetInfo;
	
	
	
	public ExecutionLog(String empCalAndSumExecLogID, ExecutionContent executionContent, ErrorPresent existenceError,
			ExecutionTime executionTime, ExecutionStatus processStatus, ObjectPeriod objectPeriod,
			String calExecutionSetInfoID) {
		super();
		this.empCalAndSumExecLogID = empCalAndSumExecLogID;
		this.executionContent = executionContent;
		this.existenceError = existenceError;
		this.executionTime = executionTime;
		this.processStatus = processStatus;
		this.objectPeriod = objectPeriod;
		this.calExecutionSetInfoID = calExecutionSetInfoID;
		this.reflectApprovalSetInfo = Optional.empty();
		this.dailyCreationSetInfo =  Optional.empty();
		this.dailyCalSetInfo =  Optional.empty();
		this.monlyAggregationSetInfo =  Optional.empty();
	}
	
	public static ExecutionLog createFromJavaType(
			String empCalAndSumExecLogID,
			int executionContent,
			int existenceError,
			GeneralDateTime startExecutionTime,
			GeneralDateTime endExecutionTime,
			int processStatus,
			GeneralDate startObjectPeriod,
			GeneralDate endObjectPeriod,
			String calExecutionSetInfoID
			) {
		
		return new ExecutionLog(
				empCalAndSumExecLogID,
				EnumAdaptor.valueOf(executionContent,ExecutionContent.class),
				EnumAdaptor.valueOf(existenceError,ErrorPresent.class),
				new ExecutionTime(startExecutionTime,endExecutionTime),
				EnumAdaptor.valueOf(processStatus,ExecutionStatus.class),
				new ObjectPeriod(startObjectPeriod,endObjectPeriod),
				calExecutionSetInfoID);
	}
	
}
