package nts.uk.ctx.pr.core.app.find.itemmaster.dto.itemsalary;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.core.dom.itemmaster.AvePayAtr;
import nts.uk.ctx.pr.core.dom.itemmaster.itemsalary.FixPayAtr;
import nts.uk.ctx.pr.core.dom.itemmaster.itemsalary.InsAtr;
import nts.uk.ctx.pr.core.dom.itemmaster.itemsalary.ItemSalary;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ItemSalaryDto {
	private int taxAtr;
	private InsAtr socialInsAtr;
	private InsAtr laborInsAtr;
	private FixPayAtr fixPayAtr;
	private int applyForAllEmpFlg;
	private int applyForMonthlyPayEmp;
	private int applyForDaymonthlyPayEmp;
	private int applyForDaylyPayEmp;
	private int applyForHourlyPayEmp;
	private AvePayAtr avePayAtr;
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
	private String limitMnyRefItemCode;
	private BigDecimal limitMny;

	public static ItemSalaryDto fromDomain(ItemSalary domain) {

		return new ItemSalaryDto(domain.getTaxAtr().value, domain.getSocialInsAtr(), domain.getLaborInsAtr(),
				domain.getFixPayAtr(), domain.getApplyForAllEmpFlg().value, domain.getApplyForMonthlyPayEmp().value,
				domain.getApplyForDaymonthlyPayEmp().value, domain.getApplyForDaylyPayEmp().value,
				domain.getApplyForHourlyPayEmp().value, domain.getAvePayAtr(), domain.getErrRangeLowAtr().value,
				domain.getErrRangeLow().v(), domain.getErrRangeHighAtr().value, domain.getErrRangeHigh().v(),
				domain.getAlRangeLowAtr().value, domain.getAlRangeLow().v(), domain.getAlRangeHighAtr().value,
				domain.getAlRangeHigh().v(), domain.getMemo().v(), domain.getLimitMnyAtr().value,
				domain.getLimitMnyRefItemCode().v(), domain.getLimitMny() != null ? domain.getLimitMny().v() : null);
	}
}
