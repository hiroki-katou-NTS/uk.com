package nts.uk.ctx.pereg.app.command.person.setting.selectionitem;

import lombok.Value;

/**
 * 
 * @author tuannv
 *
 */
@Value
public class AddSelectionItemCommand {
	private String selectionItemId;
	private String selectionItemName;
	private String memo;
	private boolean selectionItemClassification;
	private String contractCode;
	private String integrationCode;
	private FormatSelectionCommand formatSelection;
}
