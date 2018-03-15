package nts.uk.ctx.at.record.dom.divergence.time.history;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

@Getter
@AllArgsConstructor
public class DivergenceReferenceTimeUsageUnit extends AggregateRoot {

	/* Company Id */
	private String cId;

	/* Worktype usage */
	private BigDecimal workTypeUseSet;

	/**
	 * Instantiates a new divergence reference time usage unit.
	 *
	 * @param memento the memento
	 */
	public DivergenceReferenceTimeUsageUnit(DivergenceReferenceTimeUsageUnitGetMemento memento) {
		this.cId = memento.getCompanyId();
		this.workTypeUseSet = memento.getWorkTypeUseSet();
	}

	public void saveToMemento(DivergenceReferenceTimeUsageUnitSetMemento memento) {
		memento.setCompanyId(this.cId);
		memento.setWorkTypeUseSet(this.workTypeUseSet);
	}

	public DivergenceReferenceTimeUsageUnit() {
		// TODO Auto-generated constructor stub
	}

}
