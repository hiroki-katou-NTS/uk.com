package nts.uk.ctx.at.record.app.find.log.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDateTime;
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
	private String empCalAndSumExecLogID;

	/**
	 * 実行内容
	 */
	private int executionContent;

	/**
	 * executionContent : Japan name
	 */
	private String executionContentName;
	
	/**
	 * エラーの有無
	 */
	private int existenceError;
	
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
	 * 対象期間
	 * start date - end date
	 */
	private ObjectPeriod objectPeriod;
	
	/**
	 * 計算実行設定情報ID
	 */
	private String calExecutionSetInfoID;
	
	
	public static ExecutionLogDto fromDomain(ExecutionLog domain) {
		return new ExecutionLogDto(
				domain.getEmpCalAndSumExecLogID(), 
				domain.getExecutionContent().value,
				domain.getExecutionContent().nameId, //NAME japan
				domain.getExistenceError().value, 
				new ExecutionTime(
						domain.getExecutionTime().getStartTime(), 
						domain.getExecutionTime().getEndTime()),
				domain.getProcessStatus().value,
				new ObjectPeriod(
						domain.getObjectPeriod().getStartDate(), 
						domain.getObjectPeriod().getEndDate()),
				domain.getCalExecutionSetInfoID()
				); 
	}
	
}
