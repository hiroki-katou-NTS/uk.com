package nts.uk.ctx.exio.dom.exo.exoutsummaryservice;

import lombok.Getter;

@Getter
public class CtgItemDataCustom {
	String itemName;
	String conditions;
	
	public CtgItemDataCustom(String itemName, String conditions) {
		this.itemName = itemName;
		this.conditions = conditions;
	}
}
