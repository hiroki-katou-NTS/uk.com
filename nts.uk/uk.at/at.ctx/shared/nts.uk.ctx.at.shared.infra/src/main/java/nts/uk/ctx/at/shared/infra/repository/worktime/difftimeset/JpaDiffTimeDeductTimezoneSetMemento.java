package nts.uk.ctx.at.shared.infra.repository.worktime.difftimeset;

import nts.uk.ctx.at.shared.dom.worktime.common.BooleanGetAtr;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeDeductTimezoneSetMemento;
import nts.uk.ctx.at.shared.infra.entity.worktime.difftimeset.KshmtWtDifBrWekTs;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * The Class JpaDiffTimeDeductTimezoneSetMemento.
 */
public class JpaDiffTimeDeductTimezoneSetMemento implements DiffTimeDeductTimezoneSetMemento {

	/** The entity. */
	private KshmtWtDifBrWekTs entity;

	/**
	 * Instantiates a new jpa diff time deduct timezone set memento.
	 *
	 * @param entity the entity
	 */
	public JpaDiffTimeDeductTimezoneSetMemento(KshmtWtDifBrWekTs entity) {
		this.entity = entity;
	}

	@Override
	public void setStart(TimeWithDayAttr start) {
		this.entity.setStartTime(start.v());
	}

	@Override
	public void setEnd(TimeWithDayAttr end) {
		this.entity.setEndTime(end.v());
	}

	@Override
	public void setIsUpdateStartTime(boolean isUpdateStartTime) {
		this.entity.setUpdStartTime(BooleanGetAtr.getAtrByBoolean(isUpdateStartTime));
	}

}
