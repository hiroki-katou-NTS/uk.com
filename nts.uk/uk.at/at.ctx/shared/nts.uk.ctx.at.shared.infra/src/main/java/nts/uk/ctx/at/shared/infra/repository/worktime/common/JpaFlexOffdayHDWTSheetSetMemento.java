/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.common;

import nts.uk.ctx.at.shared.dom.worktime.common.BooleanGetAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.BreakFrameNo;
import nts.uk.ctx.at.shared.dom.worktime.common.HDWorkTimeSheetSettingSetMemento;
import nts.uk.ctx.at.shared.dom.worktime.common.TimeZoneRounding;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtFlexHolSet;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtFlexHolSetPK;

/**
 * The Class JpaFlexOffdayHDWTSheetSetMemento.
 */
public class JpaFlexOffdayHDWTSheetSetMemento implements HDWorkTimeSheetSettingSetMemento{
	
	/** The entity timezone. */
	private KshmtFlexHolSet entity;

	/**
	 * Instantiates a new jpa flex offday HDWT sheet set memento.
	 *
	 * @param entity the entity
	 */
	public JpaFlexOffdayHDWTSheetSetMemento(KshmtFlexHolSet entity) {
		super();
		if(entity.getKshmtFlexHolSetPK() == null){
			entity.setKshmtFlexHolSetPK(new KshmtFlexHolSetPK());
		}
		this.entity = entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.HDWorkTimeSheetSettingSetMemento
	 * #setWorkTimeNo(java.lang.Integer)
	 */
	@Override
	public void setWorkTimeNo(Integer workTimeNo) {
		this.entity.getKshmtFlexHolSetPK().setWorktimeNo(workTimeNo);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.HDWorkTimeSheetSettingSetMemento
	 * #setTimezone(nts.uk.ctx.at.shared.dom.worktime.common.TimeZoneRounding)
	 */
	@Override
	public void setTimezone(TimeZoneRounding timezone) {
		timezone.saveToMemento(new JpaFlexOffdayTZRoundSetMemento(this.entity));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.HDWorkTimeSheetSettingSetMemento
	 * #setIsLegalHolidayConstraintTime(boolean)
	 */
	@Override
	public void setIsLegalHolidayConstraintTime(boolean isLegalHolidayConstraintTime) {
		this.entity.setHolTime(BooleanGetAtr.getAtrByBoolean(isLegalHolidayConstraintTime));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.HDWorkTimeSheetSettingSetMemento
	 * #setInLegalBreakFrameNo(nts.uk.ctx.at.shared.dom.worktime.common.
	 * BreakFrameNo)
	 */
	@Override
	public void setInLegalBreakFrameNo(BreakFrameNo inLegalBreakFrameNo) {
		this.entity.setHolFrameNo(inLegalBreakFrameNo.v());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.HDWorkTimeSheetSettingSetMemento
	 * #setIsNonStatutoryDayoffConstraintTime(boolean)
	 */
	@Override
	public void setIsNonStatutoryDayoffConstraintTime(boolean isNonStatutoryDayoffConstraintTime) {
		this.entity.setOutHolTime(BooleanGetAtr.getAtrByBoolean(isNonStatutoryDayoffConstraintTime));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.HDWorkTimeSheetSettingSetMemento
	 * #setOutLegalBreakFrameNo(nts.uk.ctx.at.shared.dom.worktime.common.
	 * BreakFrameNo)
	 */
	@Override
	public void setOutLegalBreakFrameNo(BreakFrameNo outLegalBreakFrameNo) {
		this.entity.setOutHolFrameNo(outLegalBreakFrameNo.v());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.HDWorkTimeSheetSettingSetMemento
	 * #setIsNonStatutoryHolidayConstraintTime(boolean)
	 */
	@Override
	public void setIsNonStatutoryHolidayConstraintTime(boolean isNonStatutoryHolidayConstraintTime) {
		this.entity.setPubHolTime(BooleanGetAtr.getAtrByBoolean(isNonStatutoryHolidayConstraintTime));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.HDWorkTimeSheetSettingSetMemento
	 * #setOutLegalPubHDFrameNo(nts.uk.ctx.at.shared.dom.worktime.common.
	 * BreakFrameNo)
	 */
	@Override
	public void setOutLegalPubHDFrameNo(BreakFrameNo outLegalPubHDFrameNo) {
		this.entity.setPubHolFrameNo(outLegalPubHDFrameNo.v());
	}

}
