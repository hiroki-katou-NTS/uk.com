package nts.uk.ctx.at.shared.infra.repository.worktime.difftimeset;

import nts.uk.ctx.at.shared.dom.worktime.common.EmTimeFrameNo;
import nts.uk.ctx.at.shared.dom.worktime.common.EmTimeZoneSetSetMemento;
import nts.uk.ctx.at.shared.dom.worktime.common.TimeZoneRounding;
import nts.uk.ctx.at.shared.infra.entity.worktime.difftimeset.KshmtWtDifWorkTs;

/**
 * The Class JpaEmTimeZoneSetSetMemento.
 */
public class JpaEmTimeZoneSetSetMemento implements EmTimeZoneSetSetMemento {

	/** The entity. */
	private KshmtWtDifWorkTs entity;

	public JpaEmTimeZoneSetSetMemento(KshmtWtDifWorkTs entity) {
		this.entity = entity;
	}

	@Override
	public void setEmploymentTimeFrameNo(EmTimeFrameNo no) {
		this.entity.getKshmtWtDifWorkTsPK().setTimeFrameNo(no.v());
	}

	@Override
	public void setTimezone(TimeZoneRounding rounding) {
		this.entity.setTimeStr(rounding.getStart().v());
		this.entity.setTimeEnd(rounding.getEnd().v());
		this.entity.setRounding(rounding.getRounding().getRounding().value);
		this.entity.setUnit(rounding.getRounding().getRoundingTime().value);
	}

}
