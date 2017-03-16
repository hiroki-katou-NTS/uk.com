package nts.uk.ctx.pr.core.app.find.itemmaster.dto.itemsalaryperiod;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.core.dom.itemmaster.itemsalaryperiod.ItemSalaryPeriod;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ItemSalaryPeriodDto {
	public String itemCode;
	public int periodAtr;
	public int strY;
	public int endY;
	public int cycleAtr;
	public int cycle01Atr;
	public int cycle02Atr;
	public int cycle03Atr;
	public int cycle04Atr;
	public int cycle05Atr;
	public int cycle06Atr;
	public int cycle07Atr;
	public int cycle08Atr;
	public int cycle09Atr;
	public int cycle10Atr;
	public int cycle11Atr;
	public int cycle12Atr;

	public static ItemSalaryPeriodDto fromDomain(ItemSalaryPeriod domain) {
		return new ItemSalaryPeriodDto(domain.getItemCode().v(), domain.getPeriodAtr().value, domain.getStrY().v(),
				domain.getEndY().v(), domain.getCycleAtr().value, domain.getCycle01Atr().value,
				domain.getCycle02Atr().value, domain.getCycle03Atr().value, domain.getCycle04Atr().value,
				domain.getCycle05Atr().value, domain.getCycle06Atr().value, domain.getCycle07Atr().value,
				domain.getCycle08Atr().value, domain.getCycle09Atr().value, domain.getCycle10Atr().value,
				domain.getCycle11Atr().value, domain.getCycle12Atr().value);

	}

}
