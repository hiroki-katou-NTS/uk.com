package nts.uk.ctx.pr.core.app.command.itemmaster;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.pr.core.app.command.itemmaster.itemattend.DeleteItemAttendCommand;
import nts.uk.ctx.pr.core.app.command.itemmaster.itemdeduct.DeleteItemDeductCommand;
import nts.uk.ctx.pr.core.app.command.itemmaster.itemdeductperiod.DeleteItemDeductPeriodCommand;
import nts.uk.ctx.pr.core.app.command.itemmaster.itemsalary.DeleteItemSalaryCommand;
import nts.uk.ctx.pr.core.app.command.itemmaster.itemsalarybd.DeleteItemSalaryBDCommand;
import nts.uk.ctx.pr.core.app.command.itemmaster.itemsalaryperiod.DeleteItemSalaryPeriodCommand;

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
	private DeleteItemSalaryPeriodCommand itemSalaryPeriod;
	private DeleteItemDeductPeriodCommand itemDeducPeriod;
	private List<DeleteItemSalaryBDCommand> itemBDs;
	private int categoryAtr;
	private String itemCode;

}
