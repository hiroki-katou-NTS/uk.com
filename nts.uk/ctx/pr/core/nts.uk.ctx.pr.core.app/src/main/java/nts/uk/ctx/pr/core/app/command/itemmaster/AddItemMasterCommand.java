package nts.uk.ctx.pr.core.app.command.itemmaster;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.pr.core.app.command.itemmaster.itemattend.AddItemAttendCommand;
import nts.uk.ctx.pr.core.app.command.itemmaster.itemdeduct.AddItemDeductCommand;
import nts.uk.ctx.pr.core.app.command.itemmaster.itemsalary.AddItemSalaryCommand;
import nts.uk.ctx.pr.core.app.command.itemmaster.itemsalarybd.AddItemSalaryBDCommand;
import nts.uk.ctx.pr.core.app.command.itemmaster.itemsalaryperiod.AddItemSalaryPeriodCommand;
import nts.uk.ctx.pr.core.dom.itemmaster.ItemMaster;

@Getter
@Setter
public class AddItemMasterCommand {
	private AddItemSalaryCommand itemSalary;
	private AddItemDeductCommand itemDeduct;
	private AddItemAttendCommand itemAttend;
	private AddItemSalaryPeriodCommand itemPeriod;
	private List<AddItemSalaryBDCommand> itemBDs;
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

	public ItemMaster toDomain() {
		return ItemMaster.createFromJavaType(companyCode, itemCode, itemName, itemAbName, itemAbNameE, itemAbNameO,
				categoryAtr, fixAtr, displaySet, uniteCode, zeroDisplaySet, itemDisplayAtr);
	}
	
}
