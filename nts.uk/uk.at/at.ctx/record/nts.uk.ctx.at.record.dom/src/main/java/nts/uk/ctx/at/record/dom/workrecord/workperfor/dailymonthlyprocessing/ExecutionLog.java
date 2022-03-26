/**
 * 
 */
package nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing;

import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.enums.ErrorPresent;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.enums.ExecutionStatus;
import nts.uk.ctx.at.shared.dom.workrecord.workperfor.dailymonthlyprocessing.enums.ExecutionContent;
import nts.uk.ctx.at.shared.dom.workrecord.workperfor.dailymonthlyprocessing.enums.ExecutionType;

/**
 * 実行ログ
 * @author tutk
 *
 */
@Getter
public class ExecutionLog extends AggregateRoot {
	
	/** 就業計算と集計実行ログID */
	private String empCalAndSumExecLogID;
	
	/** 実行内容 */
	private ExecutionContent executionContent;
	
	/** エラーの有無 */
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
	private Optional<ObjectPeriod> objectPeriod;
	
    private Optional<Boolean> isCalWhenLock;
    
    /**
     * 実行種別
     */
    @Setter
    private ExecutionType executionType;
	
	public ExecutionLog(String empCalAndSumExecLogID, ExecutionContent executionContent, ErrorPresent existenceError,
			ExecutionTime executionTime, ExecutionStatus processStatus, ObjectPeriod objectPeriod, Optional<Boolean> isCalWhenLock,
			ExecutionType executionType) {
		super();
		this.empCalAndSumExecLogID = empCalAndSumExecLogID;
		this.executionContent = executionContent;
		this.existenceError = existenceError;
		this.executionTime = executionTime;
		this.processStatus = processStatus;
		this.objectPeriod = Optional.ofNullable(objectPeriod);
		this.isCalWhenLock = isCalWhenLock;
		this.executionType = executionType;
	}
	
	public static ExecutionLog createFromJavaType (
			String empCalAndSumExecLogID,
			int executionContent,
			int existenceError,
			GeneralDateTime startExecutionTime,
			GeneralDateTime endExecutionTime,
			int processStatus,
			GeneralDate startObjectPeriod,
			GeneralDate endObjectPeriod,
			Optional<Boolean> isCalWhenLock,
			Integer executionType) {
		return new ExecutionLog(
				empCalAndSumExecLogID,
				EnumAdaptor.valueOf(executionContent,ExecutionContent.class),
				EnumAdaptor.valueOf(existenceError,ErrorPresent.class),
				new ExecutionTime(startExecutionTime,endExecutionTime),
				EnumAdaptor.valueOf(processStatus,ExecutionStatus.class),
				new ObjectPeriod(startObjectPeriod,endObjectPeriod),
				isCalWhenLock,
				executionType != null ? EnumAdaptor.valueOf(executionType, ExecutionType.class) : null);
	}
	
	public boolean isComplete() {
		return this.processStatus == ExecutionStatus.DONE;
	}
	
}
