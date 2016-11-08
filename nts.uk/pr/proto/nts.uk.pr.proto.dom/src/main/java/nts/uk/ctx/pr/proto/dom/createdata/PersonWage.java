package nts.uk.ctx.pr.proto.dom.createdata;

import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;

public class PersonWage extends AggregateRoot {
	/**
	 * Person wage value.
	 */
	@Setter
	private long wageValue;
		
}
