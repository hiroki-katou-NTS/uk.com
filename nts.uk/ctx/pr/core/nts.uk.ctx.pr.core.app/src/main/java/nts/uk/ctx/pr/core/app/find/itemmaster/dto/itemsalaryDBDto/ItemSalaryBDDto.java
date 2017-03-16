package nts.uk.ctx.pr.core.app.find.itemmaster.dto.itemsalaryDBDto;

import java.math.BigDecimal;

import javax.persistence.Basic;
import javax.persistence.Column;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.core.dom.itemmaster.itemsalarybd.ItemSalaryBD;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ItemSalaryBDDto {
	public String itemBreakdownCd;
	public String itemBreakdownName;
	public String itemBreakdownAbName;
	public String uniteCd;
	public int zeroDispSet;
	public int itemDispAtr;
	public int errRangeLowAtr;
	public BigDecimal errRangeLow;
	public int errRangeHighAtr;
	public BigDecimal errRangeHigh;
	public int alRangeLowAtr;
	public BigDecimal alRangeLow;
	public int alRangeHighAtr;
	public BigDecimal alRangeHigh;

	public static ItemSalaryBDDto fromDomain(ItemSalaryBD domain) {

		return new ItemSalaryBDDto(domain.getItemBreakdownCd().v(), domain.getItemBreakdownName().v(),
				domain.getItemBreakdownAbName().v(), domain.getUniteCd().v(), domain.getZeroDispSet().value,
				domain.getItemDispAtr().value, domain.getErrRangeLowAtr().value, domain.getErrRangeLow().v(),
				domain.getErrRangeHighAtr().value, domain.getErrRangeHigh().v(), domain.getAlRangeLowAtr().value,
				domain.getAlRangeLow().v(), domain.getAlRangeHighAtr().value, domain.getAlRangeHigh().v());
	}
}
