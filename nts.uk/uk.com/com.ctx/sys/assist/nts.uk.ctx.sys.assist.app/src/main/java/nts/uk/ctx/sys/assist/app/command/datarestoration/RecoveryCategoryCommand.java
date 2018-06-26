package nts.uk.ctx.sys.assist.app.command.datarestoration;

import lombok.Value;

@Value
public class RecoveryCategoryCommand {
	public String categoryId;
	public String endOfPeriod;
	public String startOfPeriod;
}
