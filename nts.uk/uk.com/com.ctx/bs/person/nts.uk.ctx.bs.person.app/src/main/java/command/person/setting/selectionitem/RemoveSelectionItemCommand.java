package command.person.setting.selectionitem;

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
