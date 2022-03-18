
package nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing;

//import java.util.List;
import java.util.Optional;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
//import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.enums.CalAndAggClassification;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.enums.ExeStateOfCalAndSum;


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
	
	/** 実行日 */
	private GeneralDateTime executionDate;
	
	/** 実行状況 */
	private Optional<ExeStateOfCalAndSum> executionStatus;
	
	/** 実行社員ID */
	private String employeeID;
	
	/** 締めID */
	private int closureID;
	
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
			 GeneralDateTime executionDate, ExeStateOfCalAndSum executionStatus,
			String employeeID, int closureID, CalAndAggClassification executionClassification) {
		super();
		this.empCalAndSumExecLogID = empCalAndSumExecLogID;
		this.companyID = companyID;
		this.processingMonth = processingMonth;
		this.executionDate = executionDate;
		this.executionStatus = Optional.ofNullable(executionStatus);
		this.employeeID = employeeID;
		this.closureID = closureID;
		this.executionClassification =  executionClassification;
	}
	
	public static EmpCalAndSumExeLog createFromJavaType(
			String empCalAndSumExecLogID,
			String companyID,
			YearMonth processingMonth,
			GeneralDateTime executionDate,
			Integer executionStatus,
			String employeeID,
			int closureID,
			int executionClassification) {
		return new EmpCalAndSumExeLog(
				empCalAndSumExecLogID,
				companyID,
				processingMonth,
				executionDate,
				executionStatus==null?null:EnumAdaptor.valueOf(executionStatus,ExeStateOfCalAndSum.class),
				employeeID,
				closureID,
				EnumAdaptor.valueOf(executionClassification,CalAndAggClassification.class));
	}

	public EmpCalAndSumExeLog(String empCalAndSumExecLogID, Integer executionStatus) {
		super();
		this.empCalAndSumExecLogID = empCalAndSumExecLogID;
		this.executionStatus = Optional.ofNullable(executionStatus==null?null:EnumAdaptor.valueOf(executionStatus,ExeStateOfCalAndSum.class));
	}
	

}
