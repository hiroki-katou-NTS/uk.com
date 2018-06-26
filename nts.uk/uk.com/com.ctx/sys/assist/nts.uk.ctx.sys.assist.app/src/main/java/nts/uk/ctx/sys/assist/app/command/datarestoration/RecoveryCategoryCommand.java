package nts.uk.ctx.sys.assist.app.command.datarestoration;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class RecoveryCategoryCommand {
	public String categoryId;
	public String endOfPeriod;
	public String startOfPeriod;
}
