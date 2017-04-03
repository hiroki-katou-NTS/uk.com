package nts.uk.ctx.pr.core.app.command.itemmaster.itemdeductperiod;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.pr.core.dom.itemmaster.itemdeductperiod.ItemDeductPeriod;

@Getter
@Setter
public class AddItemDeductPeriodCommand {
	private String itemCode;
	private int periodAtr;
	private int strY;
	private int endY;
	private int cycleAtr;
	private int cycle01Atr;
	private int cycle02Atr;
	private int cycle03Atr;
	private int cycle04Atr;
	private int cycle05Atr;
	private int cycle06Atr;
	private int cycle07Atr;
	private int cycle08Atr;
	private int cycle09Atr;
	private int cycle10Atr;
	private int cycle11Atr;
	private int cycle12Atr;

	public ItemDeductPeriod toDomain() {
		return ItemDeductPeriod.createFromJavaType(itemCode, periodAtr, strY, endY, cycleAtr, cycle01Atr, cycle02Atr,
				cycle03Atr, cycle04Atr, cycle05Atr, cycle06Atr, cycle07Atr, cycle08Atr, cycle09Atr, cycle10Atr,
				cycle11Atr, cycle12Atr);
	}

	public AddItemDeductPeriodCommand(String itemCode, int periodAtr, int strY, int endY, int cycleAtr, int cycle01Atr,
			int cycle02Atr, int cycle03Atr, int cycle04Atr, int cycle05Atr, int cycle06Atr, int cycle07Atr,
			int cycle08Atr, int cycle09Atr, int cycle10Atr, int cycle11Atr, int cycle12Atr) {
		super();
		this.itemCode = itemCode;
		this.periodAtr = periodAtr;
		this.strY = strY;
		this.endY = endY;
		this.cycleAtr = cycleAtr;
		this.cycle01Atr = cycle01Atr;
		this.cycle02Atr = cycle02Atr;
		this.cycle03Atr = cycle03Atr;
		this.cycle04Atr = cycle04Atr;
		this.cycle05Atr = cycle05Atr;
		this.cycle06Atr = cycle06Atr;
		this.cycle07Atr = cycle07Atr;
		this.cycle08Atr = cycle08Atr;
		this.cycle09Atr = cycle09Atr;
		this.cycle10Atr = cycle10Atr;
		this.cycle11Atr = cycle11Atr;
		this.cycle12Atr = cycle12Atr;
	}
	
}
