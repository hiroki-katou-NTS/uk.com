package nts.uk.ctx.at.record.app.command.workrecord.log;

import lombok.Data;

@Data
public class ExecutionCommandResult {
	
	private String empCalAndSumExecLogID;
	
	/** 対象期間開始日 */
    private String periodStartDate;
    private String periodEndDate;
    
    /** 対象期間終了日 */
    private String targetEndDate;
	
    public ExecutionCommandResult(String empCalAndSumExecLogID, String periodStartDate, String periodEndDate,
			String targetEndDate) {
		super();
		this.empCalAndSumExecLogID = empCalAndSumExecLogID;
		this.periodStartDate = periodStartDate;
		this.periodEndDate = periodEndDate;
		this.targetEndDate = targetEndDate;
	}

}
