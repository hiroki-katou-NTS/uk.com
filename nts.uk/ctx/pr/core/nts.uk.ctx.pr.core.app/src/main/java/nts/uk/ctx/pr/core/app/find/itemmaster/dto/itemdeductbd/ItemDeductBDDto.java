package nts.uk.ctx.pr.core.app.find.itemmaster.dto.itemdeductbd;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.core.dom.itemmaster.itemdeductbd.ItemDeductBD;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ItemDeductBDDto {
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

	public static ItemDeductBDDto fromDomain(ItemDeductBD domain) {

		return new ItemDeductBDDto(domain.getItemBreakdownCd().v(), domain.getItemBreakdownName().v(),
				domain.getItemBreakdownAbName().v(), domain.getUniteCd().v(), domain.getZeroDispSet().value,
				domain.getItemDispAtr().value, domain.getErrRangeLowAtr().value, domain.getErrRangeLow().v(),
				domain.getErrRangeHighAtr().value, domain.getErrRangeHigh().v(), domain.getAlRangeLowAtr().value,
				domain.getAlRangeLow().v(), domain.getAlRangeHighAtr().value, domain.getAlRangeHigh().v());
	}

}
