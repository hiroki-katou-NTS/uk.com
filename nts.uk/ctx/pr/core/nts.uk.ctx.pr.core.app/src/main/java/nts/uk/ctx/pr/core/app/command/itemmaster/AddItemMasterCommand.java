package nts.uk.ctx.pr.core.app.command.itemmaster;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddItemMasterCommand {
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
