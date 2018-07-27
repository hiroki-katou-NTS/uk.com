package nts.uk.ctx.pereg.app.command.person.setting.selectionitem.selectionorder.add;

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
