package nts.uk.ctx.at.record.app.find.log.dto;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.workrecord.log.EmpCalAndSumExeLog;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmpCalAndSumExeLogDto {
	
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
	private int executedMenu;

	
	
	/**
	 * 実行状況
	 */
	private int executionStatus;

	/**
	 * 実行日
	 */

	private GeneralDate executionDate;

	/**
	 * 処理月
	 */
	private Integer processingMonth;
	
	/**
	 * 締めID
	 */
	private int closureID;
	
	/**
	 * 実行ログ
	 * 1->4 elements
	 */
	private List<ExecutionLogDto> executionLogs;
	
	public static EmpCalAndSumExeLogDto fromDomain(EmpCalAndSumExeLog domain) {
		return new EmpCalAndSumExeLogDto(
				domain.getCompanyID(), 
				domain.getEmpCalAndSumExecLogID(), 
				domain.getCaseSpecExeContentID(), 
				domain.getEmployeeID(), 
				domain.getExecutedMenu().value, 
				domain.getExecutionStatus().value, 
				domain.getExecutionDate(), 
				domain.getProcessingMonth().v(), 
				domain.getClosureID(), 
				domain.getExecutionLogs().stream().map(c->ExecutionLogDto.fromDomain(c)).collect(Collectors.toList())
				);
	}

}
