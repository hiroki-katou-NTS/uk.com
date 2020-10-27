package nts.uk.ctx.at.shared.infra.repository.worktime.difftimeset;

import nts.uk.ctx.at.shared.dom.worktime.common.BooleanGetAtr;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeDeductTimezoneGetMemento;
import nts.uk.ctx.at.shared.infra.entity.worktime.difftimeset.KshmtWtDifBrWekTs;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * The Class JpaHalfDiffTimeDeductTimezoneGetMemento.
 */
public class JpaHalfDiffTimeDeductTimezoneGetMemento implements DiffTimeDeductTimezoneGetMemento {

	/** The entity. */
	private KshmtWtDifBrWekTs entity;

	public JpaHalfDiffTimeDeductTimezoneGetMemento(KshmtWtDifBrWekTs item) {
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
