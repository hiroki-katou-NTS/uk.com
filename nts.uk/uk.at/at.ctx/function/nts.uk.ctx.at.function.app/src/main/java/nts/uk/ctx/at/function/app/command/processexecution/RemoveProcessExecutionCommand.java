package nts.uk.ctx.at.function.app.command.processexecution;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RemoveProcessExecutionCommand {
	private String execItemCd;
}
