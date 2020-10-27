package nts.uk.ctx.at.shared.infra.repository.worktime.difftimeset;

import nts.uk.ctx.at.shared.dom.worktime.common.BooleanGetAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.BreakFrameNo;
import nts.uk.ctx.at.shared.dom.worktime.common.HDWorkTimeSheetSettingSetMemento;
import nts.uk.ctx.at.shared.dom.worktime.common.TimeZoneRounding;
import nts.uk.ctx.at.shared.infra.entity.worktime.difftimeset.KshmtWtDifHolTs;

/**
 * The Class JpaDayOffTimezoneSettingSetMemento.
 */
public class JpaDayOffTimezoneSettingSetMemento implements HDWorkTimeSheetSettingSetMemento {

	/** The entity. */
	private KshmtWtDifHolTs entity;

	public JpaDayOffTimezoneSettingSetMemento(KshmtWtDifHolTs entity) {
		this.entity = entity;
	}

	@Override
	public void setWorkTimeNo(Integer workTimeNo) {
		this.entity.getKshmtWtDifHolTsPK().setWorkTimeNo(workTimeNo);
	}

	@Override
	public void setTimezone(TimeZoneRounding timezone) {
		this.entity.setTimeStr(timezone.getStart().v());
		this.entity.setTimeEnd(timezone.getEnd().v());
		this.entity.setRounding(timezone.getRounding().getRounding().value);
		this.entity.setUnit(timezone.getRounding().getRoundingTime().value);
	}

	@Override
	public void setIsLegalHolidayConstraintTime(boolean isLegalHolidayConstraintTime) {
		this.entity.setHolTime(BooleanGetAtr.getAtrByBoolean(isLegalHolidayConstraintTime));
	}

	@Override
	public void setInLegalBreakFrameNo(BreakFrameNo inLegalBreakFrameNo) {
		this.entity.setHolFrameNo(inLegalBreakFrameNo.v().intValue());
	}

	@Override
	public void setIsNonStatutoryDayoffConstraintTime(boolean isNonStatutoryDayoffConstraintTime) {
		this.entity.setOutHolTime(BooleanGetAtr.getAtrByBoolean(isNonStatutoryDayoffConstraintTime));
	}

	@Override
	public void setOutLegalBreakFrameNo(BreakFrameNo outLegalBreakFrameNo) {
		this.entity.setOutHolFrameNo(outLegalBreakFrameNo.v().intValue());
	}

	@Override
	public void setIsNonStatutoryHolidayConstraintTime(boolean isNonStatutoryHolidayConstraintTime) {
		this.entity.setPubHolTime(BooleanGetAtr.getAtrByBoolean(isNonStatutoryHolidayConstraintTime));
	}

	@Override
	public void setOutLegalPubHDFrameNo(BreakFrameNo outLegalPubHDFrameNo) {
		this.entity.setPubHolFrameNo(outLegalPubHDFrameNo.v().intValue());
	}
}
