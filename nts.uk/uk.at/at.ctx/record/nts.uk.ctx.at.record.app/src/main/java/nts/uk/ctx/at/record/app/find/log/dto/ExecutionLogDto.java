package nts.uk.ctx.at.record.app.find.log.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.record.dom.workrecord.log.ExecutionLog;
import nts.uk.ctx.at.record.dom.workrecord.log.ExecutionTime;
import nts.uk.ctx.at.record.dom.workrecord.log.ObjectPeriod;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExecutionLogDto {
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
	private int existenceError;
	
	/**
	 * ケース別実行実施内容ID
	 */
	private int executeContenByCaseID;
	
	/**
	 * 実行内容
	 */
	private int executionContent;

	/**
	 * 実行日時
	 * start time - end time
	 */
	private ExecutionTime executionTime;
	
	/**
	 * 処理状況
	 */
	private int processStatus;
	
	/**
	 * 設定情報
	 * ExecutionContent - ExecutionType
	 */
	private CalExeSettingInforDto calExeSetInfor;


	/**
	 * 対象期間
	 * start date - end date
	 */
	private ObjectPeriod objectPeriod;
	
	public static ExecutionLogDto fromDomain(ExecutionLog domain) {
		return new ExecutionLogDto(
				domain.getExecutedLogID(), 
				domain.getEmpCalAndSumExecLogID(), 
				domain.getExistenceError().value, 
				domain.getExecuteContenByCaseID(),
				domain.getExecutionContent().value, 
				new ExecutionTime(
						domain.getExecutionTime().getStartTime(), 
						domain.getExecutionTime().getEndTime()),
				domain.getProcessStatus().value,
				new CalExeSettingInforDto(
						domain.getCalExeSetInfor().getExecutionType().value, 
						domain.getCalExeSetInfor().getExecutionContent().value),
				new ObjectPeriod(
						domain.getObjectPeriod().getStartDate(), 
						domain.getObjectPeriod().getEndDate())); 
	}
}
