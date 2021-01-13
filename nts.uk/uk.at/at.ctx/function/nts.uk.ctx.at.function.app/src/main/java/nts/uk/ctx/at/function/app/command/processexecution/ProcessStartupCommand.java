package nts.uk.ctx.at.function.app.command.processexecution;

import lombok.Value;

@Value
public class ProcessStartupCommand {
	
	/**
	 * 実行ID
	 */
	private String executionId;
	
	/**
	 * 実施内容	
	 */
	private boolean isDaily;
}
