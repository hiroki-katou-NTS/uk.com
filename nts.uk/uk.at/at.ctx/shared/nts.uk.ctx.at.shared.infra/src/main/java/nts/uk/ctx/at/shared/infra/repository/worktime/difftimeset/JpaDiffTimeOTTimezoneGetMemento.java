package nts.uk.ctx.at.shared.infra.repository.worktime.difftimeset;

import nts.uk.ctx.at.shared.dom.common.timerounding.Rounding;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
import nts.uk.ctx.at.shared.dom.common.timerounding.Unit;
import nts.uk.ctx.at.shared.dom.worktime.common.BooleanGetAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.EmTimezoneNo;
import nts.uk.ctx.at.shared.dom.worktime.common.OTFrameNo;
import nts.uk.ctx.at.shared.dom.worktime.common.SettlementOrder;
import nts.uk.ctx.at.shared.dom.worktime.common.TimeZoneRounding;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeOTTimezoneGetMemento;
import nts.uk.ctx.at.shared.infra.entity.worktime.difftimeset.KshmtWtDifOverTs;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * The Class JpaDiffTimeOTTimezoneGetMemento.
 */
public class JpaDiffTimeOTTimezoneGetMemento implements DiffTimeOTTimezoneGetMemento {

	private KshmtWtDifOverTs entity;

	public JpaDiffTimeOTTimezoneGetMemento(KshmtWtDifOverTs item) {
		this.entity = item;
	}

	@Override
	public EmTimezoneNo getWorkTimezoneNo() {
		return new EmTimezoneNo(this.entity.getKshmtWtDifOverTsPK().getWorkTimeNo());
	}

	@Override
	public boolean getRestraintTimeUse() {
		return BooleanGetAtr.getAtrByInteger(this.entity.getTreatTimeWork());
	}

	@Override
	public boolean getEarlyOTUse() {
		return BooleanGetAtr.getAtrByInteger(this.entity.getTreatEarlyOtWork());
	}

	@Override
	public TimeZoneRounding getTimezone() {
		return new TimeZoneRounding(new TimeWithDayAttr(this.entity.getTimeStr()),
				new TimeWithDayAttr(this.entity.getTimeEnd()), new TimeRoundingSetting(
						Unit.valueOf(this.entity.getUnit()), Rounding.valueOf(this.entity.getRounding())));
	}

	@Override
	public OTFrameNo getOTFrameNo() {
		return new OTFrameNo(this.entity.getOtFrameNo());
	}

	@Override
	public OTFrameNo getLegalOTframeNo() {
		return new OTFrameNo(this.entity.getLegalOtFrameNo());
	}

	@Override
	public SettlementOrder getSettlementOrder() {
		return new SettlementOrder(this.entity.getPayoffOrder());
	}

	@Override
	public boolean isIsUpdateStartTime() {
		return BooleanGetAtr.getAtrByInteger(this.entity.getUpdStartTime());
	}

}
