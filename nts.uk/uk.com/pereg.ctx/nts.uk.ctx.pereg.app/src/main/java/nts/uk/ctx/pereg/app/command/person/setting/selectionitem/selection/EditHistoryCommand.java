package nts.uk.ctx.pereg.app.command.person.setting.selectionitem.selection;

import lombok.Value;
import nts.arc.time.GeneralDate;

/**
 * 
 * @author tuannv
 *
 */
@Value
public class EditHistoryCommand {
	
	private String selectionItemId;
	
	private String selectingHistId;
	
	private GeneralDate newStartDate;
	
}
