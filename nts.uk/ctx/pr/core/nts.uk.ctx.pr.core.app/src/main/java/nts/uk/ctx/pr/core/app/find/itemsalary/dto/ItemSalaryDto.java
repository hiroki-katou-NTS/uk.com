package nts.uk.ctx.pr.core.app.find.itemsalary.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.core.dom.itemSalary.ItemSalary;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ItemSalaryDto {
	public int taxAtr;
	public int socialInsAtr;
	public int laborInsAtr;
	public int fixPayAtr;
	public int applyForAllEmpFlg;
	public int applyForMonthlyPayEmp;
	public int applyForDaymonthlyPayEmp;
	public int applyForDaylyPayEmp;
	public int applyForHourlyPayEmp;
	public int avePayAtr;
	public int errRangeLowAtr;
	public BigDecimal errRangeLow;
	public int errRangeHighAtr;
	public BigDecimal errRangeHigh;
	public int alRangeLowAtr;
	public BigDecimal alRangeLow;
	public int alRangeHighAtr;
	public BigDecimal alRangeHigh;
	public String memo;
	public int limitMnyAtr;
	public String limitMnyRefItemCd;
	public Long limitMny;

	public static ItemSalaryDto fromDomain(ItemSalary domain) {

		return new ItemSalaryDto(
				domain.getTaxAtr().value,
				domain.getSocialInsAtr().value,
				domain.getLaborInsAtr().value, 
				domain.getFixPayAtr().value, 
				domain.getApplyForAllEmpFlg().value,
				domain.getApplyForMonthlyPayEmp().value,
				domain.getApplyForDaymonthlyPayEmp().value,
				domain.getApplyForDaylyPayEmp().value,
				domain.getApplyForHourlyPayEmp().value, 
				domain.getAvePayAtr().value, 
				domain.getErrRangeLowAtr().value,
				domain.getErrRangeLow().v(),
				domain.getErrRangeHighAtr().value, 
				domain.getErrRangeHigh().v(), 
				domain.getAlRangeLowAtr().value,
				domain.getAlRangeLow().v(),
				domain.getAlRangeHighAtr().value,
				domain.getAlRangeHigh().v(), 
				domain.getMemo().v(),
				domain.getLimitMnyAtr().value,
				domain.getLimitMnyRefItemCd().v(), 
				domain.getLimitMny().v());
	}
}
