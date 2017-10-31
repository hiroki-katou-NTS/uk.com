/**
 * 
 */
package nts.uk.ctx.at.record.dom.workrecord.log;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.record.dom.workrecord.log.enums.ErrorPresent;
import nts.uk.ctx.at.record.dom.workrecord.log.enums.ExecutionContent;
import nts.uk.ctx.at.record.dom.workrecord.log.enums.ExecutionStatus;
import nts.uk.ctx.at.record.dom.workrecord.log.enums.ExecutionType;

/**
 * 実行ログ
 * @author tutk
 *
 */
@Getter
@AllArgsConstructor
public class ExecutionLog {
	/**
	 * 会社ID
	 */

	private String companyID;
	
	/**
	 * ID (table 就業計算と集計実行ログ)
	 */
	private String empCalAndSumExecLogID;

	/**
	 * 運用ケース
	 */
	private String caseSpecExeContentID;
	
	/**
	 * 実行社員ID
	 */

	private String employeeID;
	
	/**
	 * 就業計算と集計実行ログID
	 */
	private String executedLogID;
	
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
	
	public static ExecutionLog createFromJavaType(String companyID,
			String empCalAndSumExecLogID,
			String caseSpecExeContentID,
			String employeeID,
			String executedLogID,int existenceError,
			int executeContenByCaseID,int executionContent,GeneralDateTime startExecutionTime,GeneralDateTime endExecutionTime,
			int processStatus,int exeType ,int exeContent,GeneralDate startObjectPeriod,GeneralDate endObjectPeriod) {
		
		return new ExecutionLog(
				companyID,
				empCalAndSumExecLogID,
				caseSpecExeContentID,
				employeeID,
				executedLogID,
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
