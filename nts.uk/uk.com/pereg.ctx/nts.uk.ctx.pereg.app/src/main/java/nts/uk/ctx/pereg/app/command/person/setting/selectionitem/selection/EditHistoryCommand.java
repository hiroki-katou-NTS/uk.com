package nts.uk.ctx.pereg.app.command.person.setting.selectionitem.selection;

import lombok.Value;

/**
 * 
 * @author tuannv
 *
 */
@Value
public class EditHistoryCommand {
	private String startDateNew;
	private String startDate;
	private String endDate;
	private String histId;
	private String selectionItemId;
}
