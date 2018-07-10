package nts.uk.ctx.exio.app.find.exo.exoutsummarysetting;

import lombok.Getter;
import nts.uk.ctx.exio.dom.exo.outputitem.StandardOutputItem;

@Getter
public class StdOutItemCustom {
	private String outItemCd;
	private String outItemName;
	
	public StdOutItemCustom(StandardOutputItem stdOutItem) {
		this.outItemCd = stdOutItem.getOutputItemCode().v();
		this.outItemName = stdOutItem.getOutputItemName().v();
	}
}
