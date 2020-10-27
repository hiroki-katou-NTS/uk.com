/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.fixedset;

import java.math.BigDecimal;

import nts.uk.ctx.at.shared.dom.common.timerounding.Rounding;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
import nts.uk.ctx.at.shared.dom.common.timerounding.Unit;
import nts.uk.ctx.at.shared.dom.worktime.common.BreakFrameNo;
import nts.uk.ctx.at.shared.dom.worktime.common.HDWorkTimeSheetSettingGetMemento;
import nts.uk.ctx.at.shared.dom.worktime.common.TimeZoneRounding;
import nts.uk.ctx.at.shared.infra.entity.worktime.fixedset.KshmtWtFixHolTs;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * The Class JpaFixedHDWorkTimeSheetGetMemento.
 */
public class JpaFixedHDWorkTimeSheetGetMemento implements HDWorkTimeSheetSettingGetMemento {

	/** The entity. */
	private KshmtWtFixHolTs entity;

	/** The Constant TRUE. */
	private static final Integer TRUE = 1;

	/**
	 * Instantiates a new jpa fixed HD work time sheet get memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaFixedHDWorkTimeSheetGetMemento(KshmtWtFixHolTs entity) {
		super();
		this.entity = entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.HDWorkTimeSheetSettingGetMemento
	 * #getWorkTimeNo()
	 */
	@Override
	public Integer getWorkTimeNo() {
		return this.entity.getKshmtWtFixHolTsPK().getWorktimeNo();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.HDWorkTimeSheetSettingGetMemento
	 * #getTimezone()
	 */
	@Override
	public TimeZoneRounding getTimezone() {
		return new TimeZoneRounding(new TimeWithDayAttr(this.entity.getTimeStr()),
				new TimeWithDayAttr(this.entity.getTimeEnd()), new TimeRoundingSetting(
						Unit.valueOf(this.entity.getUnit()), Rounding.valueOf(this.entity.getRounding())));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.HDWorkTimeSheetSettingGetMemento
	 * #getIsLegalHolidayConstraintTime()
	 */
	@Override
	public boolean getIsLegalHolidayConstraintTime() {
		return this.entity.getHolTime() == TRUE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.HDWorkTimeSheetSettingGetMemento
	 * #getInLegalBreakFrameNo()
	 */
	@Override
	public BreakFrameNo getInLegalBreakFrameNo() {
		return new BreakFrameNo(new BigDecimal(this.entity.getHolFrameNo()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.HDWorkTimeSheetSettingGetMemento
	 * #getIsNonStatutoryDayoffConstraintTime()
	 */
	@Override
	public boolean getIsNonStatutoryDayoffConstraintTime() {
		return this.entity.getOutHolTime() == TRUE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.HDWorkTimeSheetSettingGetMemento
	 * #getOutLegalBreakFrameNo()
	 */
	@Override
	public BreakFrameNo getOutLegalBreakFrameNo() {
		return new BreakFrameNo(new BigDecimal(this.entity.getOutHolFrameNo()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.HDWorkTimeSheetSettingGetMemento
	 * #getIsNonStatutoryHolidayConstraintTime()
	 */
	@Override
	public boolean getIsNonStatutoryHolidayConstraintTime() {
		return this.entity.getPubHolTime() == TRUE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.HDWorkTimeSheetSettingGetMemento
	 * #getOutLegalPubHDFrameNo()
	 */
	@Override
	public BreakFrameNo getOutLegalPubHDFrameNo() {
		return new BreakFrameNo(new BigDecimal(this.entity.getPubHolFrameNo()));
	}

}
