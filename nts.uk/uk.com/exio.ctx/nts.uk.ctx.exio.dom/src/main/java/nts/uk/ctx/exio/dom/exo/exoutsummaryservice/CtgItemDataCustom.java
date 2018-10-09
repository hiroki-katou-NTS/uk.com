package nts.uk.ctx.exio.dom.exo.exoutsummaryservice;

import lombok.Getter;

@Getter
public class CtgItemDataCustom {
	int seriNum;
	String itemName;
	String conditions;
	
	public CtgItemDataCustom(int seriNum, String itemName, String conditions) {
		this.seriNum = seriNum;
		this.itemName = itemName;
		this.conditions = conditions;
	}
}
