
package nts.uk.ctx.at.record.dom.workrecord.log.aggregateroot;

import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.workrecord.log.ExeErMesInfor;
import nts.uk.ctx.at.record.dom.workrecord.log.ExecutionLog;
import nts.uk.ctx.at.record.dom.workrecord.log.objectvalue.ExeStateOfCalAndSum;
import nts.uk.ctx.at.record.dom.workrecord.log.objectvalue.ExecutedMenu;


/**
 * 就業計算と集計実行ログ
 * @author tutk
 *
 */
@Getter 
@AllArgsConstructor
public class EmpCalAndSumExeLog extends AggregateRoot {

	/**
	 * ID
	 */
	private long empCalAndSumExecLogId;

	/**
	 * 会社ID
	 */

	private String companyId;

	/**
	 * 実行社員ID
	 */

	private String employeeId;

	/**
	 * 締めID
	 */
	private int closureId;

	/**
	 * 実行日
	 */

	private GeneralDate executionDate;
	
	/**
	 * 実行したメニュー
	 */

	private YearMonth processingMonth;

	/**
	 * ・選択して実行 ・ケース別実行
	 */
	private ExecutedMenu executedMenu;

	/**
	 * 実行ログ
	 * 1->4 elements
	 */
	private List<ExecutionLog> executionLogs;

	/**
	 * 実行状況
	 */
	private ExeStateOfCalAndSum executionStatus;

	/**
	 * 運用ケース
	 */
	private long caseSpecExeContentID;
	/**
	 * 実行エラーメッセージ情報	
	 */
	private ExeErMesInfor exeErmesInfor;
	
	
	public static EmpCalAndSumExeLog createFromJavaType(
			long empCalAndSumExecLogId,
			String companyId,
			String employeeId,
			int closureId,
			GeneralDate executionDate,
			Integer processingMonth,
			int executedMenu,
			List<ExecutionLog> executionLogs,
			int executionStatus,
			long caseSpecExeContentID,
			ExeErMesInfor exeErmesInfor
			) {
		
		return new  EmpCalAndSumExeLog(
				empCalAndSumExecLogId,
				companyId,
				employeeId,
				closureId,
				executionDate,
				new YearMonth(processingMonth),
				EnumAdaptor.valueOf(executedMenu,ExecutedMenu.class),
				executionLogs,
				EnumAdaptor.valueOf(executionStatus,ExeStateOfCalAndSum.class),
				caseSpecExeContentID,
				exeErmesInfor
				);
	}
	
}
