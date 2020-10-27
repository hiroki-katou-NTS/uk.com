package nts.uk.ctx.at.shared.infra.repository.worktime.difftimeset;

import nts.uk.ctx.at.shared.dom.worktime.common.BooleanGetAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.EmTimezoneNo;
import nts.uk.ctx.at.shared.dom.worktime.common.OTFrameNo;
import nts.uk.ctx.at.shared.dom.worktime.common.SettlementOrder;
import nts.uk.ctx.at.shared.dom.worktime.common.TimeZoneRounding;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeOTTimezoneSetMemento;
import nts.uk.ctx.at.shared.infra.entity.worktime.difftimeset.KshmtWtDifOverTs;

/**
 * The Class JpaDiffTimeOTTimezoneSetMemento.
 */
public class JpaDiffTimeOTTimezoneSetMemento implements DiffTimeOTTimezoneSetMemento {

	/** The entity. */
	private KshmtWtDifOverTs entity;

	/**
	 * Instantiates a new jpa diff time OT timezone set memento.
	 *
	 * @param entity the entity
	 */
	public JpaDiffTimeOTTimezoneSetMemento(KshmtWtDifOverTs entity) {
		this.entity = entity;
	}

	@Override
	public void setWorkTimezoneNo(EmTimezoneNo workTimezoneNo) {
		this.entity.setOtFrameNo(workTimezoneNo.v());
	}

	@Override
	public void setRestraintTimeUse(boolean restraintTimeUse) {
		this.entity.setTreatTimeWork(BooleanGetAtr.getAtrByBoolean(restraintTimeUse));
	}

	@Override
	public void setEarlyOTUse(boolean earlyOTUse) {
		this.entity.setTreatEarlyOtWork(BooleanGetAtr.getAtrByBoolean(earlyOTUse));
	}

	@Override
	public void setTimezone(TimeZoneRounding timezone) {
		this.entity.setTimeStr(timezone.getStart().v());
		this.entity.setTimeEnd(timezone.getEnd().v());
		this.entity.setUnit(timezone.getRounding().getRoundingTime().value);
		this.entity.setRounding(timezone.getRounding().getRounding().value);
	}

	@Override
	public void setOTFrameNo(OTFrameNo OTFrameNo) {
		this.entity.setOtFrameNo(OTFrameNo.v());
	}

	@Override
	public void setLegalOTframeNo(OTFrameNo legalOTframeNo) {
		this.entity.setLegalOtFrameNo(legalOTframeNo == null ? null : legalOTframeNo.v());
	}

	@Override
	public void setSettlementOrder(SettlementOrder settlementOrder) {
		this.entity.setPayoffOrder(settlementOrder == null ? null : settlementOrder.v());
	}

	@Override
	public void setIsUpdateStartTime(boolean isUpdateStartTime) {
		this.entity.setUpdStartTime(BooleanGetAtr.getAtrByBoolean(isUpdateStartTime));
	}

}
