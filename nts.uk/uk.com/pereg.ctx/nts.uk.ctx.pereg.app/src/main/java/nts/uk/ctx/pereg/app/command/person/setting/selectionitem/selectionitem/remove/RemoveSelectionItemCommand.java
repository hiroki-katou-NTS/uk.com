package nts.uk.ctx.pereg.app.command.person.setting.selectionitem.selectionitem.remove;

import lombok.Value;

/**
 * 
 * @author tuannv
 *
 */
@Value
public class RemoveSelectionItemCommand {
	private String selectionItemId;
	private String histId;
}
