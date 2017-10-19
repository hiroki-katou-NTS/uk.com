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
 * 実行ログ
 * @author tutk
 *
 */
@Getter
public class ExecutionLog {

	/**
	 * 就業計算と集計実行ログID
	 */
	private long empCalAndSumExecLogId;

	/**
	 * 設定情報
	 * 
	 */
	private CalExeSettingInfor calExeSetInfor;

	/**
	 * 実行内容
	 */
	private ExecutionContent executionContent;

	/**
	 * エラーの有無
	 */
	public ErrorPresent errorPresentation;

	/**
	 * ケース別実行実施内容ID
	 */
	private int caseExecutionContentId;

	/**
	 * 処理状況
	 */
	private ExecutionStatus processStatus;

	/**
	 * 実行日時
	 * start time - end time
	 */
	private ExecutionTime executionTime;

	/**
	 * 対象期間
	 * start date - end date
	 */
	private ObjectPeriod objectPeriod;

}
