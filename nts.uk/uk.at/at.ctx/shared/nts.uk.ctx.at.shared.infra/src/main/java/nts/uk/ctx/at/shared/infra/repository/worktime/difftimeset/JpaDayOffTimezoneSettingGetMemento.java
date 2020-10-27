package nts.uk.ctx.at.shared.infra.repository.worktime.difftimeset;

import java.math.BigDecimal;

import nts.uk.ctx.at.shared.dom.common.timerounding.Rounding;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
import nts.uk.ctx.at.shared.dom.common.timerounding.Unit;
import nts.uk.ctx.at.shared.dom.worktime.common.BooleanGetAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.BreakFrameNo;
import nts.uk.ctx.at.shared.dom.worktime.common.HDWorkTimeSheetSettingGetMemento;
import nts.uk.ctx.at.shared.dom.worktime.common.TimeZoneRounding;
import nts.uk.ctx.at.shared.infra.entity.worktime.difftimeset.KshmtWtDifHolTs;
import nts.uk.shr.com.time.TimeWithDayAttr;

public class JpaDayOffTimezoneSettingGetMemento implements HDWorkTimeSheetSettingGetMemento {

	private KshmtWtDifHolTs entity;

	public JpaDayOffTimezoneSettingGetMemento(KshmtWtDifHolTs item) {
		this.entity = item;
	}

	@Override
	public Integer getWorkTimeNo() {
		return this.entity.getKshmtWtDifHolTsPK().getWorkTimeNo();
	}

	@Override
	public TimeZoneRounding getTimezone() {
		return new TimeZoneRounding(new TimeWithDayAttr(this.entity.getTimeStr()),
				new TimeWithDayAttr(this.entity.getTimeEnd()), new TimeRoundingSetting(
						Unit.valueOf(this.entity.getUnit()), Rounding.valueOf(this.entity.getRounding())));
	}

	@Override
	public boolean getIsLegalHolidayConstraintTime() {
		return BooleanGetAtr.getAtrByInteger(this.entity.getHolTime());
	}

	@Override
	public BreakFrameNo getInLegalBreakFrameNo() {
		return new BreakFrameNo(new BigDecimal(this.entity.getHolFrameNo()));
	}

	@Override
	public boolean getIsNonStatutoryDayoffConstraintTime() {
		return BooleanGetAtr.getAtrByInteger(this.entity.getOutHolTime());
	}

	@Override
	public BreakFrameNo getOutLegalBreakFrameNo() {
		return new BreakFrameNo(new BigDecimal(this.entity.getOutHolFrameNo()));
	}

	@Override
	public boolean getIsNonStatutoryHolidayConstraintTime() {
		return BooleanGetAtr.getAtrByInteger(this.entity.getPubHolTime());
	}

	@Override
	public BreakFrameNo getOutLegalPubHDFrameNo() {
		return new BreakFrameNo(new BigDecimal(this.entity.getPubHolFrameNo()));
	}
}
