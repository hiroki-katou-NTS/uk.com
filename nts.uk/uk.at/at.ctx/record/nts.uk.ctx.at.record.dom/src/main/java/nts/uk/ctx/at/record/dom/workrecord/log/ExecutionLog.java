/**
 * 
 */
package nts.uk.ctx.at.record.dom.workrecord.log;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.workrecord.log.objectvalue.ErrorPresent;
import nts.uk.ctx.at.record.dom.workrecord.log.objectvalue.ExecutionContent;
import nts.uk.ctx.at.record.dom.workrecord.log.objectvalue.ExecutionStatus;
import nts.uk.ctx.at.record.dom.workrecord.log.objectvalue.ExecutionTime;
import nts.uk.ctx.at.record.dom.workrecord.log.objectvalue.ExecutionType;
import nts.uk.ctx.at.record.dom.workrecord.log.objectvalue.ObjectPeriod;
import nts.uk.ctx.at.record.dom.workrecord.log.usecase.CalExeSettingInfor;

/**
 * 実行ログ
 * @author tutk
 *
 */
@Getter
@AllArgsConstructor
public class ExecutionLog {

	/**
	 * 就業計算と集計実行ログID
	 */
	private String executedLogID;
	/**
	 * ID (table 就業計算と集計実行ログ)
	 */
	private long empCalAndSumExecLogID;
	
	/**
	 * エラーの有無
	 */
	private ErrorPresent existenceError;
	
	/**
	 * ケース別実行実施内容ID
	 */
	private int executeContenByCaseID;
	
	/**
	 * 実行内容
	 */
	private ExecutionContent executionContent;

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
	 * 設定情報
	 * ExecutionContent - ExecutionType
	 */
	private CalExeSettingInfor calExeSetInfor;


	/**
	 * 対象期間
	 * start date - end date
	 */
	private ObjectPeriod objectPeriod;
	
	public static ExecutionLog createFromJavaType(String executedLogID,long empCalAndSumExecLogID,int existenceError,
			int executeContenByCaseID,int executionContent,GeneralDate startExecutionTime,GeneralDate endExecutionTime,
			int processStatus,int exeType ,int exeContent,GeneralDate startObjectPeriod,GeneralDate endObjectPeriod) {
		
		return new ExecutionLog(
				executedLogID,empCalAndSumExecLogID,
				EnumAdaptor.valueOf(existenceError,ErrorPresent.class),
				executeContenByCaseID,
				EnumAdaptor.valueOf(executionContent,ExecutionContent.class),
				new ExecutionTime(startExecutionTime,endExecutionTime),
				EnumAdaptor.valueOf(processStatus,ExecutionStatus.class),
				new CalExeSettingInfor(
						EnumAdaptor.valueOf(exeContent,ExecutionContent.class),
						EnumAdaptor.valueOf(exeType,ExecutionType.class)),
				new ObjectPeriod(startObjectPeriod,endObjectPeriod)
				);
		
	}

}
