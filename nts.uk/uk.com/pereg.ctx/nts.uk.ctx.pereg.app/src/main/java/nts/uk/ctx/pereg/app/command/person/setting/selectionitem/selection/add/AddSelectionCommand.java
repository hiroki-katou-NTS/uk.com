package nts.uk.ctx.pereg.app.command.person.setting.selectionitem.selection.add;

import lombok.Value;

/**
 * 
 * @author tuannv
 *
 */
@Value
public class AddSelectionCommand {
	private String selectionID;
	private String histId;
	private String selectionCD;
	private String selectionName;
	private String externalCD;
	private String memoSelection;
}
