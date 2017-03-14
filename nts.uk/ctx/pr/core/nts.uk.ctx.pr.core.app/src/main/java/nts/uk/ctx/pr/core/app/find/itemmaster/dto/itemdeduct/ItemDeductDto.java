package nts.uk.ctx.pr.core.app.find.itemmaster.dto.itemdeduct;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.core.dom.itemmaster.itemdeduct.ItemDeduct;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ItemDeductDto {
	int deductAtr;
	int errRangeLowAtr;
	BigDecimal errRangeLow;
	int errRangeHighAtr;
	BigDecimal errRangeHigh;
	int alRangeLowAtr;
	BigDecimal alRangeLow;
	int alRangeHighAtr;
	BigDecimal alRangeHigh;
	String memo;

	public static ItemDeductDto fromDomain(ItemDeduct domain) {
		return new ItemDeductDto(domain.getDeductAtr().value, domain.getErrRangeLowAtr().value,
				domain.getErrRangeLow().v(), domain.getErrRangeHighAtr().value, domain.getErrRangeHigh().v(),
				domain.getAlRangeLowAtr().value, domain.getAlRangeLow().v(), domain.getAlRangeHighAtr().value,
				domain.getAlRangeHigh().v(), domain.getMemo().v());
	}
}
