package nts.uk.ctx.at.shared.infra.repository.worktime.difftimeset;

import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.worktime.common.FontRearSection;
import nts.uk.ctx.at.shared.dom.worktime.common.InstantRounding;
import nts.uk.ctx.at.shared.dom.worktime.common.RoundingTimeUnit;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.EmTimezoneChangeExtentGetMemento;
import nts.uk.ctx.at.shared.infra.entity.worktime.difftimeset.KshmtWtDif;

/**
 * The Class JpaEmTimezoneChangeExtentGetMemento.
 */
public class JpaEmTimezoneChangeExtentGetMemento implements EmTimezoneChangeExtentGetMemento {

	private KshmtWtDif entity;

	public JpaEmTimezoneChangeExtentGetMemento(KshmtWtDif entity) {
		this.entity = entity;
	}

	@Override
	public AttendanceTime getAheadChange() {
		return new AttendanceTime(this.entity.getChangeAhead());
	}

	@Override
	public InstantRounding getUnit() {
		return new InstantRounding(FontRearSection.valueOf(this.entity.getFrontRearAtr()),
				RoundingTimeUnit.valueOf(this.entity.getTimeRoundingUnit()));
	}

	@Override
	public AttendanceTime getBehindChange() {
		return new AttendanceTime(this.entity.getChangeBehind());
	}

}
