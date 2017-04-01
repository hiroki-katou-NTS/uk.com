package nts.uk.ctx.pr.core.app.command.itemmaster.itemdeductbd;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddItemDeductBDCommand {
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
}
