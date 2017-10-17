/**
 * 
 */
package nts.uk.ctx.at.record.dom.workrecord.log;

import lombok.Getter;
import nts.uk.ctx.at.record.dom.workrecord.log.objectvalue.ErrorPresent;
import nts.uk.ctx.at.record.dom.workrecord.log.objectvalue.ExecutionContent;
import nts.uk.ctx.at.record.dom.workrecord.log.objectvalue.ExecutionStatus;
import nts.uk.ctx.at.record.dom.workrecord.log.objectvalue.ExecutionTime;
import nts.uk.ctx.at.record.dom.workrecord.log.objectvalue.ObjectPeriod;

/**
 * @author danpv
 *
 */
@Getter
public class ExecutionLog {

	/**
	 * mother of id
	 */
	private long empCalAndSumExecLogId;

	/**
	 * ・日別作成 ・日別計算 ・承認結果反映 ・月別集計
	 * 
	 * 通常実行 再実行
	 */
	private CalExeSettingInfor calExeSetInfor;

	/**
	 * ・日別作成 ・日別計算 ・承認結果反映 ・月別集計
	 */
	private ExecutionContent executionContent;

	/**
	 * ・エラーあり ・エラーなし
	 */
	public ErrorPresent errorPresentation;

	/**
	 * caseExecutionContentId
	 */
	private int caseExecutionContentId;

	/**
	 * ・完了 ・処理中 ・未完了
	 */
	private ExecutionStatus processStatus;

	/**
	 * start time - end time
	 */
	private ExecutionTime executionTime;

	/**
	 * start date - end date
	 */
	private ObjectPeriod objectPeriod;

}
