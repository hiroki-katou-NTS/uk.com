package nts.uk.ctx.at.function.app.command.processexecution;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TerminateProcessExecutionCommand {
	/* 会社ID */
	private String companyId;
	
	/* コード */
	private String execItemCd;
    
    private String execId;
    private int execType;
    private String taskTerminate;
}