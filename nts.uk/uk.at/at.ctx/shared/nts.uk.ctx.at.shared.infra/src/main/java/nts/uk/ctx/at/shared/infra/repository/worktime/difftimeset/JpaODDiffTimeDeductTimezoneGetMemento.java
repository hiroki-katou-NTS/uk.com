package nts.uk.ctx.at.shared.infra.repository.worktime.difftimeset;

import nts.uk.ctx.at.shared.dom.worktime.common.BooleanGetAtr;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeDeductTimezoneGetMemento;
import nts.uk.ctx.at.shared.infra.entity.worktime.difftimeset.KshmtWtDifBrHolTs;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * The Class JpaDiffTimeDeductTimezoneGetMemento.
 */
public class JpaODDiffTimeDeductTimezoneGetMemento implements DiffTimeDeductTimezoneGetMemento {

	/** The entity. */
	private KshmtWtDifBrHolTs entity;

	public JpaODDiffTimeDeductTimezoneGetMemento(KshmtWtDifBrHolTs item) {
		this.entity = item;
	}

	@Override
	public TimeWithDayAttr getStart() {
		return new TimeWithDayAttr(this.entity.getStartTime());
	}

	@Override
	public TimeWithDayAttr getEnd() {
		return new TimeWithDayAttr(this.entity.getEndTime());
	}

	@Override
	public boolean isIsUpdateStartTime() {
		return BooleanGetAtr.getAtrByInteger(this.entity.getUpdStartTime());
	}

}
