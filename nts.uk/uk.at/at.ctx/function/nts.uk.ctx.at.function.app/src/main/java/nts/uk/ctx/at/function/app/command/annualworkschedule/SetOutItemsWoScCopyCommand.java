package nts.uk.ctx.at.function.app.command.annualworkschedule;

import lombok.Value;

/**
 * The Class SetOutItemsWoScCopyCommand.
 *
 * @author LienPTK
 */
@Value
public class SetOutItemsWoScCopyCommand {
	
	/** The cd copy. */
	private String cdCopy;
	
	/** The cd. */
	private String cd;
	
	/** The name. */
	private String name;
}
