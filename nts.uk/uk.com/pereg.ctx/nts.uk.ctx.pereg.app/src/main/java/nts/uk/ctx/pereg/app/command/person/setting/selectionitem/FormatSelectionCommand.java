package nts.uk.ctx.pereg.app.command.person.setting.selectionitem;

import lombok.Value;

/**
 * 
 * @author tuannv
 *
 */
@Value
public class FormatSelectionCommand {
	private int selectionCode;
	private boolean selectionCodeCharacter;
	private int selectionName;
	private int selectionExternalCode;
}
