package nts.uk.ctx.pr.core.app.command.itemmaster.itemsalarybd;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.pr.core.app.command.itemmaster.itemdeductbd.AddItemDeductBDCommand;

@Getter
@Setter
public class AddItemSalaryBDCommand {
	private String itemCd;
	private String itemBreakdownCd;
	private String itemBreakdownName;
	private String itemBreakdownAbName;
	private String uniteCd;
	private int zeroDispSet;
	private int itemDispAtr;
	private int errRangeLowAtr;
	private BigDecimal errRangeLow;
	private int errRangeHighAtr;
	private BigDecimal errRangeHigh;
	private int alRangeLowAtr;
	private BigDecimal alRangeLow;
	private int alRangeHighAtr;
	private BigDecimal alRangeHigh;

	public AddItemDeductBDCommand toItemDeduct() {
		return new AddItemDeductBDCommand(this.itemCd, this.itemBreakdownCd, this.itemBreakdownName,
				this.itemBreakdownAbName, this.uniteCd, this.zeroDispSet, this.itemDispAtr, this.errRangeLowAtr,
				this.errRangeLow, this.errRangeHighAtr, this.errRangeHigh, this.alRangeLowAtr, this.alRangeLow,
				this.alRangeHighAtr, this.alRangeHigh);
	}
}
