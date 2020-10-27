package nts.uk.ctx.at.shared.infra.repository.worktime.difftimeset;

import nts.uk.ctx.at.shared.dom.worktime.common.BooleanGetAtr;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeDeductTimezoneSetMemento;
import nts.uk.ctx.at.shared.infra.entity.worktime.difftimeset.KshmtWtDifBrHolTs;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * The Class JpaDiffTimeDeductTimezoneGetMemento.
 */
public class JpaDiffTimeDeductTimezoneGetMemento implements DiffTimeDeductTimezoneSetMemento {

	/** The entity. */
	private KshmtWtDifBrHolTs entity;

	public JpaDiffTimeDeductTimezoneGetMemento(KshmtWtDifBrHolTs entity) {
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
