/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.fixedset;

import nts.uk.ctx.at.shared.dom.worktime.common.BooleanGetAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.BreakFrameNo;
import nts.uk.ctx.at.shared.dom.worktime.common.HDWorkTimeSheetSettingSetMemento;
import nts.uk.ctx.at.shared.dom.worktime.common.TimeZoneRounding;
import nts.uk.ctx.at.shared.infra.entity.worktime.fixedset.KshmtWtFixHolTs;
import nts.uk.ctx.at.shared.infra.entity.worktime.fixedset.KshmtWtFixHolTsPK;

/**
 * The Class JpaFixedHDWorkTimeSheetSetMemento.
 */
public class JpaFixedHDWorkTimeSheetSetMemento implements HDWorkTimeSheetSettingSetMemento {

	/** The entity. */
	private KshmtWtFixHolTs entity;

	/**
	 * Instantiates a new jpa fixed HD work time sheet set memento.
	 *
	 * @param companyId
	 *            the company id
	 * @param workTimeCd
	 *            the work time cd
	 * @param entity
	 *            the entity
	 */
	public JpaFixedHDWorkTimeSheetSetMemento(String companyId, String workTimeCd, KshmtWtFixHolTs entity) {
		if (entity.getKshmtWtFixHolTsPK() == null) {
			entity.setKshmtWtFixHolTsPK(new KshmtWtFixHolTsPK());
		}
		this.entity = entity;
		this.entity.getKshmtWtFixHolTsPK().setCid(companyId);
		this.entity.getKshmtWtFixHolTsPK().setWorktimeCd(workTimeCd);
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
		this.entity.getKshmtWtFixHolTsPK().setWorktimeNo(workTimeNo);
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
		this.entity.setTimeStr(timezone.getStart().v());
		this.entity.setTimeEnd(timezone.getEnd().v());
		this.entity.setUnit(timezone.getRounding().getRoundingTime().value);
		this.entity.setRounding(timezone.getRounding().getRounding().value);
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
		this.entity.setHolFrameNo(inLegalBreakFrameNo.v().intValue());
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
		this.entity.setOutHolFrameNo(outLegalBreakFrameNo.v().intValue());
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
		this.entity.setPubHolFrameNo(outLegalPubHDFrameNo.v().intValue());
	}

}
