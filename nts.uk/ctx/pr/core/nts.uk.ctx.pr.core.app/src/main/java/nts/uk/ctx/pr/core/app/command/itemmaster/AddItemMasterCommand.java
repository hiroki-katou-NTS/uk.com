package nts.uk.ctx.pr.core.app.command.itemmaster;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.pr.core.app.command.itemmaster.itemattend.AddItemAttendCommand;
import nts.uk.ctx.pr.core.app.command.itemmaster.itemdeduct.AddItemDeductCommand;
import nts.uk.ctx.pr.core.app.command.itemmaster.itemsalary.AddItemSalaryCommand;

@Getter
@Setter
public class AddItemMasterCommand {
	private AddItemSalaryCommand itemSalary;
	private AddItemDeductCommand itemDeduct;
	private AddItemAttendCommand itemAttend;
	private String companyCode;
	private String itemCode;
	private String itemName;
	private String itemAbName;
	private String itemAbNameE;
	private String itemAbNameO;
	private int categoryAtr;
	private int fixAtr;
	private int displaySet;
	private String uniteCode;
	private int zeroDisplaySet;
	private int itemDisplayAtr;
}
