package nts.uk.ctx.at.record.dom.stamp;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.shr.com.primitive.PersonId;

@Getter
@Setter
public class StampCard extends AggregateRoot {
	private PersonId personId;
	private StampNumber stampNumber;

	public StampCard(PersonId personId, StampNumber stampNumber) {
		super();
		this.personId = personId;
		this.stampNumber = stampNumber;
	}
}
