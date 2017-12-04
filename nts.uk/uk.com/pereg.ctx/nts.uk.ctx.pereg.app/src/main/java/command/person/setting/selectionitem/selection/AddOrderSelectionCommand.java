package command.person.setting.selectionitem.selection;

import lombok.Value;

/**
 * 
 * @author tuannv
 *
 */

@Value
public class AddOrderSelectionCommand {
	private String selectionID;
	private String histId;
	private int disporder;
	private int initSelection;
}
