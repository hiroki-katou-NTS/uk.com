package nts.uk.ctx.at.record.app.find.log.dto;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
//import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.EmpCalAndSumExeLog;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.ExecutionLog;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmpCalAndSumExeLogDto {
	
	
	/**
	 * 就業計算と集計実行ログID
	 */
	private String empCalAndSumExecLogID;
	
	/**
	 * 会社ID
	 */

	private String companyID;
	
	/**
	 * 選択した締め
	 */
	private String closureName;
	/**
	 * 処理月
	 */
	private Integer processingMonth;

	/**
	 * 実行日
	 */

	private GeneralDateTime executionDate;
	
	/**
	 * 実行状況
	 */
	private Integer executionStatus;
	
	/**
	 * 実行状況 Name Japan
	 */
	private String executionStatusName;
	
	/**
	 * 実行社員ID
	 */

	private String employeeID;
	
	/**
	 * 締めID
	 */
	private int closureID;
	
	/**
	 * 実行ログ
	 * 1->4 elements
	 */
	private List<ExecutionLogDto> executionLogs;
	
	public static EmpCalAndSumExeLogDto fromDomain(EmpCalAndSumExeLog domain, List<ExecutionLog> lstExecutionLog) {
		return new EmpCalAndSumExeLogDto(
				domain.getEmpCalAndSumExecLogID(), 
				domain.getCompanyID(),
				null,
				domain.getProcessingMonth().v(), 
				domain.getExecutionDate(), 
				(domain.getExecutionStatus()!=null && domain.getExecutionStatus().isPresent())?	domain.getExecutionStatus().get().value:null,
				((domain.getExecutionStatus()!=null && domain.getExecutionStatus().isPresent()))?domain.getExecutionStatus().get().nameId: " ",
				domain.getEmployeeID(),
				domain.getClosureID(),
				lstExecutionLog.stream().map(c->ExecutionLogDto.fromDomain(c)).collect(Collectors.toList())
				);
	}

}
