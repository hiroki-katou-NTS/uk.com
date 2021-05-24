package nts.uk.ctx.at.record.app.command.workrecord.log;

import lombok.Value;

@Value
public class CheckProcessCommand {

	private String empCalAndSumExecLogID;
    private String periodStartDate;
    private String periodEndDate;

}
