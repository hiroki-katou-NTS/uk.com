
package nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing;

import java.util.List;
import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.enums.CalAndAggClassification;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.enums.ExeStateOfCalAndSum;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.enums.ExecutedMenu;


/**
 * 就業計算と集計実行ログ
 * @author tutk
 *
 */
@Getter 
public class EmpCalAndSumExeLog extends AggregateRoot {

	/**就業計算と集計実行ログID */
	private String empCalAndSumExecLogID;
	
	/** 会社ID */
	private String companyID;
	
	/** 処理月 */
	private YearMonth processingMonth;
	
	/**
	 * 実行したメニュー
	 * ・選択して実行 ・ケース別実行
	 */
	@Setter
	private ExecutedMenu executedMenu;
	
	/** 実行日 */
	private GeneralDateTime executionDate;
	
	/** 実行状況 */
	private Optional<ExeStateOfCalAndSum> executionStatus;
	
	/** 実行社員ID */
	private String employeeID;
	
	/** 締めID */
	private int closureID;

	/** 運用ケース */
	private String caseSpecExeContentID;
	
	/** 実行区分 */
	private CalAndAggClassification executionClassification;
	
	
//	/**
//	 * 実行ログ
//	 * 1->4 elements
//	 */
//	@Setter
//	private List<ExecutionLog> executionLogs;
//	public void addExecutionLog(ExecutionLog executionLog) {
//		this.executionLogs.add(executionLog);
//	}
	
	public EmpCalAndSumExeLog(String empCalAndSumExecLogID, String companyID, YearMonth processingMonth,
			ExecutedMenu executedMenu, GeneralDateTime executionDate, ExeStateOfCalAndSum executionStatus,
			String employeeID, int closureID, String caseSpecExeContentID,CalAndAggClassification executionClassification) {
		super();
		this.empCalAndSumExecLogID = empCalAndSumExecLogID;
		this.companyID = companyID;
		this.processingMonth = processingMonth;
		this.executedMenu = executedMenu;
		this.executionDate = executionDate;
		this.executionStatus = Optional.ofNullable(executionStatus);
		this.employeeID = employeeID;
		this.closureID = closureID;
		this.caseSpecExeContentID = caseSpecExeContentID;
		this.executionClassification =  executionClassification;
	}
	
	public static EmpCalAndSumExeLog createFromJavaType(
			String empCalAndSumExecLogID,
			String companyID,
			YearMonth processingMonth,
			int executedMenu,
			GeneralDateTime executionDate,
			Integer executionStatus,
			String employeeID,
			int closureID,
			String caseSpecExeContentID, int executionClassification) {
		return new EmpCalAndSumExeLog(
				empCalAndSumExecLogID,
				companyID,
				processingMonth,
				EnumAdaptor.valueOf(executedMenu,ExecutedMenu.class),
				executionDate,
				executionStatus==null?null:EnumAdaptor.valueOf(executionStatus,ExeStateOfCalAndSum.class),
				employeeID,
				closureID,
				caseSpecExeContentID, EnumAdaptor.valueOf(executionClassification,CalAndAggClassification.class));
	}

}
