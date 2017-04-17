package nts.uk.ctx.pr.core.app.command.itemmaster;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.pr.core.app.command.itemmaster.itemattend.DeleteItemAttendCommand;
import nts.uk.ctx.pr.core.app.command.itemmaster.itemdeduct.DeleteItemDeductCommand;
import nts.uk.ctx.pr.core.app.command.itemmaster.itemsalary.DeleteItemSalaryCommand;

/**
 * @author sonnlb
 *
 */
@Getter
@Setter
public class DeleteItemMasterCommand {

	private DeleteItemSalaryCommand itemSalary;
	private DeleteItemDeductCommand itemDeduct;
	private DeleteItemAttendCommand itemAttend;
	private int categoryAtr;
	private String itemCode;

}
