package nts.uk.ctx.pr.core.app.command.itemmaster.itemsalary;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddItemSalaryCommand {
	private String itemCd;
	private int taxAtr;
	private int socialInsAtr;
	private int laborInsAtr;
	private int fixPayAtr;
	private int applyForAllEmpFlg;
	private int applyForMonthlyPayEmp;
	private int applyForDaymonthlyPayEmp;
	private int applyForDaylyPayEmp;
	private int applyForHourlyPayEmp;
	private int avePayAtr;
	private int errRangeLowAtr;
	private BigDecimal errRangeLow;
	private int errRangeHighAtr;
	private BigDecimal errRangeHigh;
	private int alRangeLowAtr;
	private BigDecimal alRangeLow;
	private int alRangeHighAtr;
	private BigDecimal alRangeHigh;
	private String memo;
	private int limitMnyAtr;
	private String limitMnyRefItemCd;
	private Long limitMny;
}
