package nts.uk.ctx.pereg.app.command.person.setting.selectionitem.history.remove;

import lombok.Value;

/**
 * 
 * @author tuannv
 *
 */
@Value
public class RemoveHistoryCommand {
	
	private String selectionItemId;
	
	private String histId;
}
