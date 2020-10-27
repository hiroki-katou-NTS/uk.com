/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.fixedset;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import nts.uk.ctx.at.shared.dom.worktime.common.AmPmAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.BooleanGetAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.FixedWorkRestSet;
import nts.uk.ctx.at.shared.dom.worktime.common.LegalOTSetting;
import nts.uk.ctx.at.shared.dom.worktime.common.StampReflectTimezone;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSet;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixHalfDayWorkTimezone;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixOffdayWorkTimezone;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkCalcSetting;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSettingGetMemento;
import nts.uk.ctx.at.shared.infra.entity.worktime.common.KshmtWtCom;
import nts.uk.ctx.at.shared.infra.entity.worktime.fixedset.KshmtWtFix;
import nts.uk.ctx.at.shared.infra.repository.worktime.common.JpaFixedWorkCalcSettingGetMemento;
import nts.uk.ctx.at.shared.infra.repository.worktime.common.JpaFixedWorkRestSetGetMemento;
import nts.uk.ctx.at.shared.infra.repository.worktime.common.JpaWorkTimezoneCommonSetGetMemento;

/**
 * The Class JpaFixedWorkSettingGetMemento.
 */
public class JpaFixedWorkSettingGetMemento implements FixedWorkSettingGetMemento {

	/** The entity. */
	private KshmtWtFix entity;

	/**
	 * Instantiates a new jpa fixed work setting get memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaFixedWorkSettingGetMemento(KshmtWtFix entity) {
		super();
		this.entity = entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSettingGetMemento#
	 * getCompanyId()
	 */
	@Override
	public String getCompanyId() {
		return this.entity.getKshmtWtFixPK().getCid();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSettingGetMemento#
	 * getWorkTimeCode()
	 */
	@Override
	public WorkTimeCode getWorkTimeCode() {
		return new WorkTimeCode(this.entity.getKshmtWtFixPK().getWorktimeCd());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSettingGetMemento#
	 * getOffdayWorkTimezone()
	 */
	@Override
	public FixOffdayWorkTimezone getOffdayWorkTimezone() {
		return new FixOffdayWorkTimezone(new JpaFixOffdayWorkTimezoneGetMemento(this.entity));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSettingGetMemento#
	 * getCommonSetting()
	 */
	@Override
	public WorkTimezoneCommonSet getCommonSetting() {
		KshmtWtCom commonEntity = this.entity.getKshmtWtCom();
		if (commonEntity == null) {
			return null;
		}
		return new WorkTimezoneCommonSet(new JpaWorkTimezoneCommonSetGetMemento(commonEntity));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSettingGetMemento#
	 * getUseHalfDayShift()
	 */
	@Override
	public Boolean getUseHalfDayShift() {
		return BooleanGetAtr.getAtrByInteger(this.entity.getUseHalfDay());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSettingGetMemento#
	 * getFixedWorkRestSetting()
	 */
	@Override
	public FixedWorkRestSet getFixedWorkRestSetting() {
		return new FixedWorkRestSet(new JpaFixedWorkRestSetGetMemento<KshmtWtFix>(this.entity));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSettingGetMemento#
	 * getLstHalfDayWorkTimezone()
	 */
	@Override
	public List<FixHalfDayWorkTimezone> getLstHalfDayWorkTimezone() {

		// Build a list with item for each enum AmPmAtr case
		List<FixHalfDayWorkTimezone> result = new ArrayList<>();
		for (AmPmAtr type : AmPmAtr.values()) {
			result.add(new FixHalfDayWorkTimezone(new JpaFixHalfDayWorkTimezoneGetMemento(entity, type)));
		}

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSettingGetMemento#
	 * getLstStampReflectTimezone()
	 */
	@Override
	public List<StampReflectTimezone> getLstStampReflectTimezone() {
		return this.entity.getLstKshmtWtFixStmpRefTs().stream()
				.map(entity -> new StampReflectTimezone(new JpaFixedStampReflectTimezoneGetMemento(entity)))
				.collect(Collectors.toList());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSettingGetMemento#
	 * getLegalOTSetting()
	 */
	@Override
	public LegalOTSetting getLegalOTSetting() {
		return LegalOTSetting.valueOf(this.entity.getLegalOtSet());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSettingGetMemento#
	 * getFixedWorkCalcSetting()
	 */
	@Override
	public Optional<FixedWorkCalcSetting> getCalculationSetting() {
		return Optional.ofNullable(
				new FixedWorkCalcSetting(new JpaFixedWorkCalcSettingGetMemento<KshmtWtFix>(this.entity)));
	}

}
