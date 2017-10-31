
package nts.uk.ctx.at.record.dom.workrecord.log;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.workrecord.log.enums.ExeStateOfCalAndSum;
import nts.uk.ctx.at.record.dom.workrecord.log.enums.ExecutedMenu;


/**
 * 就業計算と集計実行ログ
 * @author tutk
 *
 */
@Getter 
@AllArgsConstructor
public class EmpCalAndSumExeLog extends AggregateRoot {

	/**
	 * 会社ID
	 */

	private String companyID;
	
	/**
	 * ID
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
	 * 実行したメニュー
	 * ・選択して実行 ・ケース別実行
	 */
	private ExecutedMenu executedMenu;

	
	
	/**
	 * 実行状況
	 */
	private ExeStateOfCalAndSum executionStatus;

	/**
	 * 実行日
	 */

	private GeneralDate executionDate;

	/**
	 * 処理月
	 */
	private YearMonth processingMonth;
	
	/**
	 * 締めID
	 */
	private int closureID;
	
	/**
	 * 実行ログ
	 * 1->4 elements
	 */
	private List<ExecutionLog> executionLogs;
	
	
	public static EmpCalAndSumExeLog createFromJavaType(
			String companyID,
			String empCalAndSumExecLogID,
			String caseSpecExeContentID,
			String employeeID,
			int executedMenu,
			int executionStatus,
			GeneralDate executionDate,
			Integer processingMonth,
			int closureID,
			List<ExecutionLog> executionLogs) {
		return new EmpCalAndSumExeLog(
				companyID,
				empCalAndSumExecLogID,
				caseSpecExeContentID,
				employeeID,
				EnumAdaptor.valueOf(executedMenu,ExecutedMenu.class),
				EnumAdaptor.valueOf(executionStatus,ExeStateOfCalAndSum.class),
				executionDate,
				new YearMonth(processingMonth),
				closureID,
				executionLogs);
	}
	
}
