package command.person.setting.selection;

import lombok.Value;

@Value
public class UpdateSelectionItemCommand {
	private String selectionItemId;
	private String selectionItemName;
	private String memo;
	private boolean selectionItemClassification;
	private String contractCode;
	private String integrationCode;
	private FormatSelectionCommand formatSelection;
}
