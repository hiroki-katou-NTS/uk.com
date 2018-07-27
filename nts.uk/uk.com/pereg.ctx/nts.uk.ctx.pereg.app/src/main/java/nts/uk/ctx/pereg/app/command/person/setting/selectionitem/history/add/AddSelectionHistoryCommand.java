package nts.uk.ctx.pereg.app.command.person.setting.selectionitem.history.add;

import lombok.Value;
import nts.arc.time.GeneralDate;

/**
 * 
 * @author tuannv
 *
 */
@Value
public class AddSelectionHistoryCommand {
	
	private String selectionItemId;
	
	private String selectingHistId;
	
	private GeneralDate startDate;
	
}
