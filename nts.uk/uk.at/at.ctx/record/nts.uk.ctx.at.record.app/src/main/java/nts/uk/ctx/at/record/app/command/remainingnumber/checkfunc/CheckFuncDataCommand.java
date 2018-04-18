package nts.uk.ctx.at.record.app.command.remainingnumber.checkfunc;

import java.util.List;

import lombok.Data;

@Data
public class CheckFuncDataCommand {
	private int total;
	private int error;
	private int pass;
	private List<OutputErrorInfoCommand> outputErrorList;
}
