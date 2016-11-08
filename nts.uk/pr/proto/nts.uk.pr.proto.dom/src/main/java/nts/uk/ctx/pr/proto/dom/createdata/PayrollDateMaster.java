package nts.uk.ctx.pr.proto.dom.createdata;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
 * Payroll date master;
 *
 */
public class PayrollDateMaster extends AggregateRoot {
	@Getter
	private int neededWorkDay;
}
