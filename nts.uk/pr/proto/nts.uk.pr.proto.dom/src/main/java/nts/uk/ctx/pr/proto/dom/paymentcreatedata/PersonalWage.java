package nts.uk.ctx.pr.proto.dom.paymentcreatedata;

import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;

public class PersonalWage extends AggregateRoot {
	/**
	 * Person wage value.
	 */
	@Setter
	private long wageValue;
		
}
