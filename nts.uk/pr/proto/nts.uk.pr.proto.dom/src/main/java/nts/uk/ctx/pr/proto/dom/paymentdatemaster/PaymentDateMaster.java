package nts.uk.ctx.pr.proto.dom.paymentdatemaster;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
 * Payroll date master;
 *
 */
public class PaymentDateMaster extends AggregateRoot {
	@Getter
	private int neededWorkDay;
}
