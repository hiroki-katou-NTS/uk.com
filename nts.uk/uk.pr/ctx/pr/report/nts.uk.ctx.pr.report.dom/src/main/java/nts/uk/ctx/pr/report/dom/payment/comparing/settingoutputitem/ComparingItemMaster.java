package nts.uk.ctx.pr.report.dom.payment.comparing.settingoutputitem;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

@AllArgsConstructor
@Getter
public class ComparingItemMaster extends AggregateRoot{
	private ItemCode itemCode;
	private ItemName itemName;
}
