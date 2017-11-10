package command.person.setting.selectionitem.selection;

import lombok.Value;
import nts.arc.time.GeneralDate;

/**
 * 
 * @author tuannv
 *
 */
@Value
public class EditHistoryCommand {
	private String companyCode;
	private GeneralDate startDate;
	private String histId;
	private String selectionItemId;
}
