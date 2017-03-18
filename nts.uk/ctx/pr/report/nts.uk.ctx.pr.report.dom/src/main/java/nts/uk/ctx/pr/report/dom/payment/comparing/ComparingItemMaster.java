package nts.uk.ctx.pr.report.dom.payment.comparing;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;

@AllArgsConstructor
@Getter
@Setter
public class ComparingItemMaster extends AggregateRoot{
	private ItemCode itemCode;
	private ItemName itemName;
}
