package nts.uk.ctx.pereg.app.command.person.setting.selectionitem.selection;

import lombok.Value;
import nts.arc.time.GeneralDate;

/**
 * 
 * @author tuannv
 *
 */
@Value
public class AddSelectionHistoryCommand {
	private String companyId;
	private GeneralDate startDate;
	private String histId;
	private String selectionItemId;
	private String selectionCD;
	private String selectionName;
	private String externalCD;
	private String memoSelection;
	private int disporder;
	private int initSelection;

}
