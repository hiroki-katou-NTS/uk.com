package nts.uk.ctx.at.record.app.command.workrecord.log;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

//@Value
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CheckProcessCommand {

	private String empCalAndSumExecLogID;
    private String periodStartDate;
    private String periodEndDate;

}
