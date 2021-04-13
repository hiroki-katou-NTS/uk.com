package nts.uk.ctx.at.function.app.command.annualworkschedule;

import lombok.Value;

/**
 * The Class SetOutItemsWoScCopyCommand.
 *
 * @author LienPTK
 */
@Value
public class SetOutItemsWoScCopyCommand {
	
	/** The code. */
	public String code;
	
	/** The name. */
	public String name;
	
	/** The selected type. */
	public int selectedType;
	
	/** The duplicate code. */
	public String duplicateCode;
	
	/** The duplicate name. */
	public String duplicateName;
	
	/** The layout id. */
	public String layoutId;
	
	/** The print format. */
	public int printFormat;
}
