/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.fixedset;

import nts.uk.ctx.at.shared.dom.worktime.common.BreakFrameNo;
import nts.uk.ctx.at.shared.dom.worktime.common.HDWorkTimeSheetSettingSetMemento;
import nts.uk.ctx.at.shared.dom.worktime.common.TimeZoneRounding;
import nts.uk.ctx.at.shared.infra.entity.worktime.fixedset.KshmtFixedHolTimeSet;
import nts.uk.ctx.at.shared.infra.entity.worktime.fixedset.KshmtFixedHolTimeSetPK;

/**
 * The Class JpaFixedHDWorkTimeSheetSetMemento.
 */
public class JpaFixedHDWorkTimeSheetSetMemento implements HDWorkTimeSheetSettingSetMemento {

	/** The company id. */
	private String companyId;

	/** The work time cd. */
	private String workTimeCd;

	/** The entity. */
	private KshmtFixedHolTimeSet entity;

	/** The Constant VALUE_TRUE. */
	private static final Integer VALUE_TRUE = 1;

	/** The Constant VALUE_FALSE. */
	private static final Integer VALUE_FALSE = 0;

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
	public JpaFixedHDWorkTimeSheetSetMemento(String companyId, String workTimeCd, KshmtFixedHolTimeSet entity) {
		this.companyId = companyId;
		this.workTimeCd = workTimeCd;
		if (entity.getKshmtFixedHolTimeSetPK() == null) {
			entity.setKshmtFixedHolTimeSetPK(new KshmtFixedHolTimeSetPK());
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
		this.entity.getKshmtFixedHolTimeSetPK().setCid(companyId);
		this.entity.getKshmtFixedHolTimeSetPK().setWorktimeCd(this.workTimeCd);
		this.entity.getKshmtFixedHolTimeSetPK().setWorktimeNo(workTimeNo);
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
		this.entity.setTimeStr(timezone.getEnd().v());
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
		this.entity.setHolTime(isLegalHolidayConstraintTime == Boolean.TRUE ? VALUE_TRUE : VALUE_FALSE);
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
		this.entity.setOutHolTime(isNonStatutoryDayoffConstraintTime == Boolean.TRUE ? VALUE_TRUE : VALUE_FALSE);
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
		this.entity.setPubHolTime(isNonStatutoryHolidayConstraintTime == Boolean.TRUE ? VALUE_TRUE : VALUE_FALSE);
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
