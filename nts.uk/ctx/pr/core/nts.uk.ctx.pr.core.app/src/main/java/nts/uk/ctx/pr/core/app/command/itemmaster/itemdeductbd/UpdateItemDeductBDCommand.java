package nts.uk.ctx.pr.core.app.command.itemmaster.itemdeductbd;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.pr.core.dom.itemmaster.itemdeductbd.ItemDeductBD;

@Getter
@Setter
public class UpdateItemDeductBDCommand {
	private String itemCode;
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

	public ItemDeductBD toDomain() {
		return ItemDeductBD.createFromJavaType(this.itemCode, this.itemBreakdownCd, this.itemBreakdownName,
				this.itemBreakdownAbName, this.uniteCd, this.zeroDispSet, this.itemDispAtr, this.errRangeLowAtr,
				this.errRangeLow, this.errRangeHighAtr, this.errRangeHigh, this.alRangeLowAtr, this.alRangeLow,
				this.alRangeHighAtr, this.alRangeHigh);

	}

	public UpdateItemDeductBDCommand(String itemCode, String itemBreakdownCd, String itemBreakdownName,
			String itemBreakdownAbName, String uniteCd, int zeroDispSet, int itemDispAtr, int errRangeLowAtr,
			BigDecimal errRangeLow, int errRangeHighAtr, BigDecimal errRangeHigh, int alRangeLowAtr,
			BigDecimal alRangeLow, int alRangeHighAtr, BigDecimal alRangeHigh) {
		super();
		this.itemCode = itemCode;
		this.itemBreakdownCd = itemBreakdownCd;
		this.itemBreakdownName = itemBreakdownName;
		this.itemBreakdownAbName = itemBreakdownAbName;
		this.uniteCd = uniteCd;
		this.zeroDispSet = zeroDispSet;
		this.itemDispAtr = itemDispAtr;
		this.errRangeLowAtr = errRangeLowAtr;
		this.errRangeLow = errRangeLow;
		this.errRangeHighAtr = errRangeHighAtr;
		this.errRangeHigh = errRangeHigh;
		this.alRangeLowAtr = alRangeLowAtr;
		this.alRangeLow = alRangeLow;
		this.alRangeHighAtr = alRangeHighAtr;
		this.alRangeHigh = alRangeHigh;
	}
	
}
