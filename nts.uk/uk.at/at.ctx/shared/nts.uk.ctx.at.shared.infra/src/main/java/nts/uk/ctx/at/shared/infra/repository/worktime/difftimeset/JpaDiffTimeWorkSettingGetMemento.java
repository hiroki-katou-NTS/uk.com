/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.difftimeset;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.shared.dom.worktime.common.AmPmAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.BooleanGetAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.FixedWorkRestSet;
import nts.uk.ctx.at.shared.dom.worktime.common.LegalOTSetting;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSet;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeDayOffWorkTimezone;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeHalfDayWorkTimezone;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeWorkSettingGetMemento;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeWorkStampReflectTimezone;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.EmTimezoneChangeExtent;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkCalcSetting;
import nts.uk.ctx.at.shared.infra.entity.worktime.common.KshmtWtCom;
import nts.uk.ctx.at.shared.infra.entity.worktime.difftimeset.KshmtWtDif;
import nts.uk.ctx.at.shared.infra.repository.worktime.common.JpaFixedWorkCalcSettingGetMemento;
import nts.uk.ctx.at.shared.infra.repository.worktime.common.JpaFixedWorkRestSetGetMemento;
import nts.uk.ctx.at.shared.infra.repository.worktime.common.JpaWorkTimezoneCommonSetGetMemento;

/**
 * The Class JpaDiffTimeWorkSettingGetMemento.
 */
public class JpaDiffTimeWorkSettingGetMemento implements DiffTimeWorkSettingGetMemento {

	/** The entity. */
	private KshmtWtDif entity;

	/**
	 * Instantiates a new jpa diff time work setting get memento.
	 *
	 * @param kshmtWtDif
	 *            the kshmt diff time work set
	 */
	public JpaDiffTimeWorkSettingGetMemento(KshmtWtDif kshmtWtDif) {
		this.entity = kshmtWtDif;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.difftimeset.
	 * DiffTimeWorkSettingGetMemento#getCompanyId()
	 */
	@Override
	public String getCompanyId() {
		return this.entity.getKshmtWtDifPK().getCid();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.difftimeset.
	 * DiffTimeWorkSettingGetMemento#getWorkTimeCode()
	 */
	@Override
	public WorkTimeCode getWorkTimeCode() {
		return new WorkTimeCode(this.entity.getKshmtWtDifPK().getWorktimeCd());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.difftimeset.
	 * DiffTimeWorkSettingGetMemento#getRestSet()
	 */
	@Override
	public FixedWorkRestSet getRestSet() {
		return new FixedWorkRestSet(new JpaFixedWorkRestSetGetMemento<KshmtWtDif>(this.entity));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.difftimeset.
	 * DiffTimeWorkSettingGetMemento#getDayoffWorkTimezone()
	 */
	@Override
	public DiffTimeDayOffWorkTimezone getDayoffWorkTimezone() {
		return new DiffTimeDayOffWorkTimezone(new DiffTimeDayOffWorkTimezoneGetMemento(this.entity));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.difftimeset.
	 * DiffTimeWorkSettingGetMemento#getCommonSet()
	 */
	@Override
	public WorkTimezoneCommonSet getCommonSet() {
		KshmtWtCom diffTimeCommonSet = this.entity.getKshmtWtCom();
		if (diffTimeCommonSet == null) {
			return null;
		}
		return new WorkTimezoneCommonSet(new JpaWorkTimezoneCommonSetGetMemento(diffTimeCommonSet));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.difftimeset.
	 * DiffTimeWorkSettingGetMemento#isIsUseHalfDayShift()
	 */
	@Override
	public boolean isIsUseHalfDayShift() {
		return BooleanGetAtr.getAtrByInteger(this.entity.getUseHalfDay());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.difftimeset.
	 * DiffTimeWorkSettingGetMemento#getChangeExtent()
	 */
	@Override
	public EmTimezoneChangeExtent getChangeExtent() {
		return new EmTimezoneChangeExtent(new JpaEmTimezoneChangeExtentGetMemento(this.entity));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.difftimeset.
	 * DiffTimeWorkSettingGetMemento#getHalfDayWorkTimezones()
	 */
	@Override
	public List<DiffTimeHalfDayWorkTimezone> getHalfDayWorkTimezones() {
		List<DiffTimeHalfDayWorkTimezone> lstReturn = new ArrayList<>();

		for (AmPmAtr type : AmPmAtr.values()) {
			lstReturn.add(new DiffTimeHalfDayWorkTimezone(new JpaDiffTimeHalfDayGetMemento(this.entity, type)));
		}
		return lstReturn;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.difftimeset.
	 * DiffTimeWorkSettingGetMemento#getStampReflectTimezone()
	 */
	@Override
	public DiffTimeWorkStampReflectTimezone getStampReflectTimezone() {
		return new DiffTimeWorkStampReflectTimezone(new JpaDiffTimeStampReflectGetMemento(this.entity));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.difftimeset.
	 * DiffTimeWorkSettingGetMemento#getOvertimeSetting()
	 */
	@Override
	public LegalOTSetting getOvertimeSetting() {
		return LegalOTSetting.valueOf(this.entity.getOtSet());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.difftimeset.
	 * DiffTimeWorkSettingGetMemento#getCalculationSetting()
	 */
	@Override
	public Optional<FixedWorkCalcSetting> getCalculationSetting() {
		return Optional.ofNullable(new FixedWorkCalcSetting(new JpaFixedWorkCalcSettingGetMemento<KshmtWtDif>(this.entity)));
	}

}
