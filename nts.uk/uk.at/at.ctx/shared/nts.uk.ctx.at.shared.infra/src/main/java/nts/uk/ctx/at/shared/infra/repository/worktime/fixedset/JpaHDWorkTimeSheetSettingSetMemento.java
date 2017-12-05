/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.fixedset;

import nts.uk.ctx.at.shared.dom.worktime.common.BooleanGetAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.BreakFrameNo;
import nts.uk.ctx.at.shared.dom.worktime.common.HDWorkTimeSheetSettingSetMemento;
import nts.uk.ctx.at.shared.dom.worktime.common.TimeZoneRounding;
import nts.uk.ctx.at.shared.infra.entity.worktime.fixedset.KshmtFlexHolSet;
import nts.uk.ctx.at.shared.infra.entity.worktime.fixedset.KshmtFlexHolSetPK;

/**
 * The Class JpaHDWorkTimeSheetSettingSetMemento.
 */
public class JpaHDWorkTimeSheetSettingSetMemento implements HDWorkTimeSheetSettingSetMemento {
	
	/** The entity. */
	private KshmtFlexHolSet entity;

	/**
	 * Instantiates a new jpa HD work time sheet setting set memento.
	 *
	 * @param entity the entity
	 */
	public JpaHDWorkTimeSheetSettingSetMemento(KshmtFlexHolSet entity) {
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
	 * HDWorkTimeSheetSettingSetMemento#setWorkTimeNo(java.lang.Integer)
	 */
	@Override
	public void setWorkTimeNo(Integer workTimeNo) {
		this.entity.getKshmtFlexHolSetPK().setWorktimeNo(workTimeNo);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.fixedset.
	 * HDWorkTimeSheetSettingSetMemento#setTimezone(nts.uk.ctx.at.shared.dom.
	 * worktime.fixedset.TimeZoneRounding)
	 */
	@Override
	public void setTimezone(TimeZoneRounding timezone) {
		timezone.saveToMemento(new JpaHDWTSheetTimeZoneRoundingSetMemento(this.entity));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.fixedset.
	 * HDWorkTimeSheetSettingSetMemento#setIsLegalHolidayConstraintTime(boolean)
	 */
	@Override
	public void setIsLegalHolidayConstraintTime(boolean isLegalHolidayConstraintTime) {
		this.entity.setHolTime(BooleanGetAtr.getAtrByBoolean(isLegalHolidayConstraintTime));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.fixedset.
	 * HDWorkTimeSheetSettingSetMemento#setInLegalBreakFrameNo(nts.uk.ctx.at.
	 * shared.dom.worktime.fixedset.BreakFrameNo)
	 */
	@Override
	public void setInLegalBreakFrameNo(BreakFrameNo inLegalBreakFrameNo) {
		this.entity.setHolFrameNo(inLegalBreakFrameNo.v());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.fixedset.
	 * HDWorkTimeSheetSettingSetMemento#setIsNonStatutoryDayoffConstraintTime(
	 * boolean)
	 */
	@Override
	public void setIsNonStatutoryDayoffConstraintTime(boolean isNonStatutoryDayoffConstraintTime) {
		this.entity.setOutHolTime(BooleanGetAtr.getAtrByBoolean(isNonStatutoryDayoffConstraintTime));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.fixedset.
	 * HDWorkTimeSheetSettingSetMemento#setOutLegalBreakFrameNo(nts.uk.ctx.at.
	 * shared.dom.worktime.fixedset.BreakFrameNo)
	 */
	@Override
	public void setOutLegalBreakFrameNo(BreakFrameNo outLegalBreakFrameNo) {
		this.entity.setOutHolFrameNo(outLegalBreakFrameNo.v());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.fixedset.
	 * HDWorkTimeSheetSettingSetMemento#setIsNonStatutoryHolidayConstraintTime(
	 * boolean)
	 */
	@Override
	public void setIsNonStatutoryHolidayConstraintTime(boolean isNonStatutoryHolidayConstraintTime) {
		this.entity.setOutHolTime(BooleanGetAtr.getAtrByBoolean(isNonStatutoryHolidayConstraintTime));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.fixedset.
	 * HDWorkTimeSheetSettingSetMemento#setOutLegalPubHDFrameNo(nts.uk.ctx.at.
	 * shared.dom.worktime.fixedset.BreakFrameNo)
	 */
	@Override
	public void setOutLegalPubHDFrameNo(BreakFrameNo outLegalPubHDFrameNo) {
		this.entity.setPubHolFrameNo(outLegalPubHDFrameNo.v());
	}

}
