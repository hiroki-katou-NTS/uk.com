package nts.uk.ctx.at.record.dom.divergence.time.history;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;


/**
 * The Class DivergenceReferenceTimeUsageUnit.
 */
//乖離基準時間利用単位
@Getter
@Setter
@NoArgsConstructor
public class DivergenceReferenceTimeUsageUnit extends AggregateRoot {

	/** The c id. */
	/* 会社ID */
	private String cId;

	/** The work type use set. */
	/* 勤務種別ごとに乖離基準時間を設定する */
	private boolean workTypeUseSet;

	/**
	 * Instantiates a new divergence reference time usage unit.
	 *
	 * @param memento the memento
	 */
	public DivergenceReferenceTimeUsageUnit(DivergenceReferenceTimeUsageUnitGetMemento memento) {
		this.cId = memento.getCompanyId();
		this.workTypeUseSet = memento.getWorkTypeUseSet();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(DivergenceReferenceTimeUsageUnitSetMemento memento) {
		memento.setCompanyId(this.cId);
		memento.setWorkTypeUseSet(this.workTypeUseSet);
	}

}
