package nts.uk.ctx.pr.core.app.command.itemmaster.itemattend;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddItemAttendCommand {
	private String itemCd;
	private int avePayAtr;
	private int itemAtr;
	private int errRangeLowAtr;
	private BigDecimal errRangeLow;
	private int errRangeHighAtr;
	private BigDecimal errRangeHigh;
	private int alRangeLowAtr;
	private BigDecimal alRangeLow;
	private int alRangeHighAtr;
	private BigDecimal alRangeHigh;
	private int workDaysScopeAtr;
	private String memo;
}
