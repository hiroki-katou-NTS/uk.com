package command.person.setting.selectionitem.selection;

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
	private String companyCode;
	private GeneralDate startDate;
	private GeneralDate endDate;

}
