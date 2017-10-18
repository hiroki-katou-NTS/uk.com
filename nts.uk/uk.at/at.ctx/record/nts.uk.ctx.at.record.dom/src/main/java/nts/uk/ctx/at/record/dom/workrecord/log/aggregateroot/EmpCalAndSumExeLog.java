/**
 * 
 */
package nts.uk.ctx.at.record.dom.workrecord.log.aggregateroot;

import java.util.List;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.workrecord.log.CaseExecutionContentId;
import nts.uk.ctx.at.record.dom.workrecord.log.ExeErMesInfor;
import nts.uk.ctx.at.record.dom.workrecord.log.ExecutionLog;
import nts.uk.ctx.at.record.dom.workrecord.log.objectvalue.ExeStateOfCalAndSum;
import nts.uk.ctx.at.record.dom.workrecord.log.objectvalue.ExecutedMenu;

/**
 * @author danpv
 *
 */
@Getter
public class EmpCalAndSumExeLog extends AggregateRoot {

	private long empCalAndSumExecLogId;

	private String companyId;

	private String employeeId;

	/**
	 * 締めID
	 */
	private int closureId;

	private GeneralDate executionDate;

	private YearMonth processingMonth;

	/**
	 * ・選択して実行 ・ケース別実行
	 */
	private ExecutedMenu executedMenu;

	/**
	 * List log
	 * 1->4 elements
	 */
	private List<ExecutionLog> executionLogs;

	/**
	 * ・完了 　・完了（エラーあり）　 ・処理中 　・実行中止 　・中断開始 　・中断終了
	 */
	private ExeStateOfCalAndSum executionStatus;

	private CaseExecutionContentId operationCase;
	
	private ExeErMesInfor exeErmesInfor;

}
