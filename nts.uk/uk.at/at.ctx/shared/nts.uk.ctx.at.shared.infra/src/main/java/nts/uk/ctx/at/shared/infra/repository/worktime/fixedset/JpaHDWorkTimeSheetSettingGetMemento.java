/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.fixedset;

import nts.uk.ctx.at.shared.dom.worktime.common.BooleanGetAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.BreakFrameNo;
import nts.uk.ctx.at.shared.dom.worktime.common.HDWorkTimeSheetSettingGetMemento;
import nts.uk.ctx.at.shared.dom.worktime.common.TimeZoneRounding;
import nts.uk.ctx.at.shared.infra.entity.worktime.fixedset.KshmtFlexHolSet;
import nts.uk.ctx.at.shared.infra.entity.worktime.fixedset.KshmtFlexHolSetPK;

/**
 * The Class JpaHDWorkTimeSheetSettingGetMemento.
 */
public class JpaHDWorkTimeSheetSettingGetMemento implements HDWorkTimeSheetSettingGetMemento{
	
	/** The entity. */
	private KshmtFlexHolSet entity;
	
	/**
	 * Instantiates a new jpa HD work time sheet setting get memento.
	 *
	 * @param entity the entity
	 */
	public JpaHDWorkTimeSheetSettingGetMemento(KshmtFlexHolSet entity) {
		super();
		if(entity.getKshmtFlexHolSetPK() == null){
			entity.setKshmtFlexHolSetPK(new KshmtFlexHolSetPK());
		}
		this.entity = entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.fixedset.
	 * HDWorkTimeSheetSettingGetMemento#getWorkTimeNo()
	 */
	@Override
	public Integer getWorkTimeNo() {
		return this.entity.getKshmtFlexHolSetPK().getWorktimeNo();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.fixedset.
	 * HDWorkTimeSheetSettingGetMemento#getTimezone()
	 */
	@Override
	public TimeZoneRounding getTimezone() {
		return new TimeZoneRounding(new JpaHDWTSheetTimeZoneRoundingGetMemento(this.entity));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.fixedset.
	 * HDWorkTimeSheetSettingGetMemento#getIsLegalHolidayConstraintTime()
	 */
	@Override
	public boolean getIsLegalHolidayConstraintTime() {
		return BooleanGetAtr.getAtrByInteger(this.entity.getHolTime());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.fixedset.
	 * HDWorkTimeSheetSettingGetMemento#getInLegalBreakFrameNo()
	 */
	@Override
	public BreakFrameNo getInLegalBreakFrameNo() {
		return new BreakFrameNo(this.entity.getHolFrameNo());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.fixedset.
	 * HDWorkTimeSheetSettingGetMemento#getIsNonStatutoryDayoffConstraintTime()
	 */
	@Override
	public boolean getIsNonStatutoryDayoffConstraintTime() {
		return BooleanGetAtr.getAtrByInteger(this.entity.getOutHolTime());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.fixedset.
	 * HDWorkTimeSheetSettingGetMemento#getOutLegalBreakFrameNo()
	 */
	@Override
	public BreakFrameNo getOutLegalBreakFrameNo() {
		return new BreakFrameNo(this.entity.getOutHolFrameNo());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.fixedset.
	 * HDWorkTimeSheetSettingGetMemento#getIsNonStatutoryHolidayConstraintTime()
	 */
	@Override
	public boolean getIsNonStatutoryHolidayConstraintTime() {
		return BooleanGetAtr.getAtrByInteger(this.entity.getPubHolTime());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.fixedset.
	 * HDWorkTimeSheetSettingGetMemento#getOutLegalPubHDFrameNo()
	 */
	@Override
	public BreakFrameNo getOutLegalPubHDFrameNo() {
		return new BreakFrameNo(this.entity.getPubHolFrameNo());
	}

}
