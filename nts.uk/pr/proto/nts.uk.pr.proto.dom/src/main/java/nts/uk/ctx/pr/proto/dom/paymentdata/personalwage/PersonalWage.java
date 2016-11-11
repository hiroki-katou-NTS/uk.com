package nts.uk.ctx.pr.proto.dom.paymentdata.personalwage;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.shr.com.primitive.PersonId;

public class PersonalWage extends AggregateRoot {
	/**
	 * Person wage value.
	 */
	@Setter
	private long wageValue;

	/**
	 * Personal Id
	 */
	@Getter
	private PersonId personId;

	public PersonalWage(long wageValue, PersonId personId) {
		super();
		this.wageValue = wageValue;
		this.personId = personId;
	}
	
}
