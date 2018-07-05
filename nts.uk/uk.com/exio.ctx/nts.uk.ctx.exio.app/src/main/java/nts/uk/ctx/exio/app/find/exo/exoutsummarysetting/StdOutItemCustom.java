package nts.uk.ctx.exio.app.find.exo.exoutsummarysetting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.exio.dom.exo.outitem.StdOutItem;

@AllArgsConstructor
@Getter
public class StdOutItemCustom {
	private String outItemCd;
	private String outItemName;
	
	public StdOutItemCustom(StdOutItem stdOutItem) {
		this.outItemCd = stdOutItem.getOutItemCd().v();
		this.outItemName = stdOutItem.getOutItemName().v();
	}
}
