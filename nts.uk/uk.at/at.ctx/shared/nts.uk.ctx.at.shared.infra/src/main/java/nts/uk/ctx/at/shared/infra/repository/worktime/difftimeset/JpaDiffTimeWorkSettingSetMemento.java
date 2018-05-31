/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.difftimeset;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.worktime.common.BooleanGetAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.FixedWorkRestSet;
import nts.uk.ctx.at.shared.dom.worktime.common.LegalOTSetting;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSet;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeDayOffWorkTimezone;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeHalfDayWorkTimezone;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeWorkSettingSetMemento;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeWorkStampReflectTimezone;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.EmTimezoneChangeExtent;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkCalcSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeDailyAtr;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeMethodSet;
import nts.uk.ctx.at.shared.infra.entity.worktime.common.KshmtWorktimeCommonSet;
import nts.uk.ctx.at.shared.infra.entity.worktime.common.KshmtWorktimeCommonSetPK;
import nts.uk.ctx.at.shared.infra.entity.worktime.difftimeset.KshmtDiffTimeWorkSet;
import nts.uk.ctx.at.shared.infra.entity.worktime.difftimeset.KshmtDiffTimeWorkSetPK;
import nts.uk.ctx.at.shared.infra.repository.worktime.common.JpaFixedWorkCalcSettingSetMemento;
import nts.uk.ctx.at.shared.infra.repository.worktime.common.JpaWorkTimezoneCommonSetSetMemento;

/**
 * The Class JpaDiffTimeWorkSettingSetMemento.
 */
public class JpaDiffTimeWorkSettingSetMemento implements DiffTimeWorkSettingSetMemento {

	/** The entity. */
	private KshmtDiffTimeWorkSet entity;

	/**
	 * Instantiates a new jpa diff time work setting set memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaDiffTimeWorkSettingSetMemento(KshmtDiffTimeWorkSet entity) {
		// case add new
		if (entity.getKshmtDiffTimeWorkSetPK() == null) {
			entity.setKshmtDiffTimeWorkSetPK(new KshmtDiffTimeWorkSetPK());
		}
		this.entity = entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.difftimeset.
	 * DiffTimeWorkSettingSetMemento#setCompanyId(java.lang.String)
	 */
	@Override
	public void setCompanyId(String companyId) {
		this.entity.getKshmtDiffTimeWorkSetPK().setCid(companyId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.difftimeset.
	 * DiffTimeWorkSettingSetMemento#setWorkTimeCode(nts.uk.ctx.at.shared.dom.
	 * worktime.common.WorkTimeCode)
	 */
	@Override
	public void setWorkTimeCode(WorkTimeCode employmentTimezoneCode) {
		this.entity.getKshmtDiffTimeWorkSetPK().setWorktimeCd(employmentTimezoneCode.v());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.difftimeset.
	 * DiffTimeWorkSettingSetMemento#setRestSet(nts.uk.ctx.at.shared.dom.
	 * worktime.common.FixedWorkRestSet)
	 */
	@Override
	public void setRestSet(FixedWorkRestSet restSet) {
		this.entity.setDtCommonRestSet(restSet.getCommonRestSet().getCalculateMethod().value);
		this.entity.setDtCalcMethod(restSet.getCalculateMethod().value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.difftimeset.
	 * DiffTimeWorkSettingSetMemento#setDayoffWorkTimezone(nts.uk.ctx.at.shared.
	 * dom.worktime.difftimeset.DiffTimeDayOffWorkTimezone)
	 */
	@Override
	public void setDayoffWorkTimezone(DiffTimeDayOffWorkTimezone dayoffWorkTimezone) {
		dayoffWorkTimezone.saveToMemento(new JpaDiffTimeDayOffWorkTimezoneSetMemento(this.entity));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.difftimeset.
	 * DiffTimeWorkSettingSetMemento#setCommonSet(nts.uk.ctx.at.shared.dom.
	 * worktime.common.WorkTimezoneCommonSet)
	 */
	@Override
	public void setCommonSet(WorkTimezoneCommonSet commonSet) {
		KshmtWorktimeCommonSet commonEntity = this.entity.getKshmtWorktimeCommonSet();
		if (commonEntity == null) {
			commonEntity = new KshmtWorktimeCommonSet();

			// new pk
			KshmtWorktimeCommonSetPK pk = new KshmtWorktimeCommonSetPK();
			pk.setCid(this.entity.getKshmtDiffTimeWorkSetPK().getCid());
			pk.setWorktimeCd(this.entity.getKshmtDiffTimeWorkSetPK().getWorktimeCd());
			pk.setWorkFormAtr(WorkTimeDailyAtr.REGULAR_WORK.value);
			pk.setWorktimeSetMethod(WorkTimeMethodSet.DIFFTIME_WORK.value);

			// set pk
			commonEntity.setKshmtWorktimeCommonSetPK(pk);

			// add entity when empty list common.
			this.entity.getLstKshmtWorktimeCommonSet().add(commonEntity);
		}
		commonSet.saveToMemento(new JpaWorkTimezoneCommonSetSetMemento(commonEntity));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.difftimeset.
	 * DiffTimeWorkSettingSetMemento#setIsUseHalfDayShift(boolean)
	 */
	@Override
	public void setIsUseHalfDayShift(boolean isUseHalfDayShift) {
		this.entity.setUseHalfDay(BooleanGetAtr.getAtrByBoolean(isUseHalfDayShift));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.difftimeset.
	 * DiffTimeWorkSettingSetMemento#setChangeExtent(nts.uk.ctx.at.shared.dom.
	 * worktime.difftimeset.EmTimezoneChangeExtent)
	 */
	@Override
	public void setChangeExtent(EmTimezoneChangeExtent changeExtent) {
		this.entity.setChangeAhead(changeExtent.getAheadChange() == null ? 0 : changeExtent.getAheadChange().v());
		this.entity.setChangeBehind(changeExtent.getBehindChange() == null ? 0 : changeExtent.getBehindChange().v());
		this.entity.setFrontRearAtr(
				changeExtent.getUnit() == null ? 0 : changeExtent.getUnit().getFontRearSection().value);
		this.entity.setTimeRoundingUnit(
				changeExtent.getUnit() == null ? 0 : changeExtent.getUnit().getRoundingTimeUnit().value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.difftimeset.
	 * DiffTimeWorkSettingSetMemento#setHalfDayWorkTimezones(java.util.List)
	 */
	@Override
	public void setHalfDayWorkTimezones(List<DiffTimeHalfDayWorkTimezone> halfDayWorkTimezone) {
		if (CollectionUtil.isEmpty(halfDayWorkTimezone)) {
			halfDayWorkTimezone = new ArrayList<>();
		}
		halfDayWorkTimezone.forEach(domain -> domain
				.saveToMemento(new JpaDiffTimeHalfDayWorkTimezoneSetMemento(this.entity, domain.getAmPmAtr().value)));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.difftimeset.
	 * DiffTimeWorkSettingSetMemento#setStampReflectTimezone(nts.uk.ctx.at.
	 * shared.dom.worktime.difftimeset.DiffTimeWorkStampReflectTimezone)
	 */
	@Override
	public void setStampReflectTimezone(DiffTimeWorkStampReflectTimezone stampReflectTimezone) {
		stampReflectTimezone.saveToMemento(new JpaDiffTimeStampReflectSetMemento(this.entity));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.difftimeset.
	 * DiffTimeWorkSettingSetMemento#setOvertimeSetting(nts.uk.ctx.at.shared.dom
	 * .worktime.common.LegalOTSetting)
	 */
	@Override
	public void setOvertimeSetting(LegalOTSetting overtimeSetting) {
		this.entity.setOtSet(overtimeSetting.value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.difftimeset.
	 * DiffTimeWorkSettingSetMemento#setCalculationSetting(nts.uk.ctx.at.shared.
	 * dom.worktime.fixedset.FixedWorkCalcSetting)
	 */
	@Override
	public void setCalculationSetting(Optional<FixedWorkCalcSetting> fixedWorkCalcSetting) {
		if (fixedWorkCalcSetting.isPresent()) {
			fixedWorkCalcSetting.get().saveToMemento(new JpaFixedWorkCalcSettingSetMemento<KshmtDiffTimeWorkSet>(this.entity));
		}	
	}

}
