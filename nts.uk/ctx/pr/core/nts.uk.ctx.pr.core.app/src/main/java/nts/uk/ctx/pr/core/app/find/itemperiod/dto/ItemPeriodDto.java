package nts.uk.ctx.pr.core.app.find.itemperiod.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.core.dom.itemperiod.ItemPeriod;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ItemPeriodDto {

	int itemClass;
	String itemCode;
	int StartYear;
	int ExpYear;
	int cycleAtr;
	int cycle1Atr;
	int cycle2Atr;
	int cycle3Atr;
	int cycle4Atr;
	int cycle5Atr;
	int cycle6Atr;
	int cycle7Atr;
	int cycle8Atr;
	int cycle9Atr;
	int cycle10Atr;
	int cycle11Atr;
	int cycle12Atr;

	public static ItemPeriodDto fromDomain(ItemPeriod domain) {
		// TODO Auto-generated method stub
		return new ItemPeriodDto(
				domain.getItemclass().v(),
				domain.getItemCode().v(),
				domain.getStartYear().v(),
				domain.getEndYear().v(),
				domain.getCycleUsageClassification().value,
				domain.getJanuaryUsageClassification().value, 
				domain.getFebruaryUsageClassification().value,
				domain.getMarchUsageClassification().value, 
				domain.getAprilUsageClassification().value,
				domain.getMayUsageClassification().value, 
				domain.getJuneUsageClassification().value,
				domain.getJulyUsageClassification().value, 
				domain.getAugustUsageClassification().value,
				domain.getSeptemberUsageClassification().value, 
				domain.getOctoberUsageClassification().value,
				domain.getNovemberUsageClassification().value,
				domain.getDecemberUsageClassification().value);
	}

}
