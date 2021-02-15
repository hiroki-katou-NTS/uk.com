/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.fixedset;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.worktime.common.BooleanGetAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.FixedWorkRestSet;
import nts.uk.ctx.at.shared.dom.worktime.common.LegalOTSetting;
import nts.uk.ctx.at.shared.dom.worktime.common.StampReflectTimezone;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSet;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixHalfDayWorkTimezone;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixOffdayWorkTimezone;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkCalcSetting;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSettingSetMemento;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeDailyAtr;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeMethodSet;
import nts.uk.ctx.at.shared.infra.entity.worktime.common.KshmtWorktimeCommonSet;
import nts.uk.ctx.at.shared.infra.entity.worktime.common.KshmtWorktimeCommonSetPK;
import nts.uk.ctx.at.shared.infra.entity.worktime.fixedset.KshmtFixedStampReflect;
import nts.uk.ctx.at.shared.infra.entity.worktime.fixedset.KshmtFixedStampReflectPK;
import nts.uk.ctx.at.shared.infra.entity.worktime.fixedset.KshmtFixedWorkSet;
import nts.uk.ctx.at.shared.infra.entity.worktime.fixedset.KshmtFixedWorkSetPK;
import nts.uk.ctx.at.shared.infra.repository.worktime.common.JpaFixedWorkCalcSettingSetMemento;
import nts.uk.ctx.at.shared.infra.repository.worktime.common.JpaFixedWorkRestSetSetMemento;
import nts.uk.ctx.at.shared.infra.repository.worktime.common.JpaWorkTimezoneCommonSetSetMemento;

/**
 * The Class JpaFixedWorkSettingSetMemento.
 */
public class JpaFixedWorkSettingSetMemento implements FixedWorkSettingSetMemento {

	/** The entity. */
	private KshmtFixedWorkSet entity;

	/**
	 * Instantiates a new jpa fixed work setting set memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaFixedWorkSettingSetMemento(KshmtFixedWorkSet entity) {
		super();
		this.entity = entity;
		if (this.entity.getKshmtFixedWorkSetPK() == null) {
			this.entity.setKshmtFixedWorkSetPK(new KshmtFixedWorkSetPK());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSettingSetMemento#
	 * setCompanyId(java.lang.String)
	 */
	@Override
	public void setCompanyId(String companyId) {
		this.entity.getKshmtFixedWorkSetPK().setCid(companyId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSettingSetMemento#
	 * setWorkTimeCode(nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode)
	 */
	@Override
	public void setWorkTimeCode(WorkTimeCode workTimeCode) {
		this.entity.getKshmtFixedWorkSetPK().setWorktimeCd(workTimeCode.v());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSettingSetMemento#
	 * setOffdayWorkTimezone(nts.uk.ctx.at.shared.dom.worktime.fixedset.
	 * FixOffdayWorkTimezone)
	 */
	@Override
	public void setOffdayWorkTimezone(FixOffdayWorkTimezone offdayWorkTimezone) {
		offdayWorkTimezone.saveToMemento(new JpaFixOffdayWorkTimezoneSetMemento(this.entity));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSettingSetMemento#
	 * setCommonSetting(nts.uk.ctx.at.shared.dom.worktime.common.
	 * WorkTimezoneCommonSet)
	 */
	@Override
	public void setCommonSetting(WorkTimezoneCommonSet commonSetting) {
		KshmtWorktimeCommonSet commonEntity = this.entity.getKshmtWorktimeCommonSet();
		if (commonEntity == null) {
			commonEntity = new KshmtWorktimeCommonSet();

			// new pk
			KshmtWorktimeCommonSetPK pk = new KshmtWorktimeCommonSetPK();
			pk.setCid(this.entity.getKshmtFixedWorkSetPK().getCid());
			pk.setWorktimeCd(this.entity.getKshmtFixedWorkSetPK().getWorktimeCd());
			pk.setWorkFormAtr(WorkTimeDailyAtr.REGULAR_WORK.value);
			pk.setWorktimeSetMethod(WorkTimeMethodSet.FIXED_WORK.value);

			// set pk
			commonEntity.setKshmtWorktimeCommonSetPK(pk);

			// add entity when empty list common.
			this.entity.getLstKshmtWorktimeCommonSet().add(commonEntity);
		}
		commonSetting.saveToMemento(new JpaWorkTimezoneCommonSetSetMemento(commonEntity));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSettingSetMemento#
	 * setUseHalfDayShift(java.lang.Boolean)
	 */
	@Override
	public void setUseHalfDayShift(Boolean useHalfDayShift) {
		this.entity.setUseHalfDay(BooleanGetAtr.getAtrByBoolean(useHalfDayShift));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSettingSetMemento#
	 * setFixedWorkRestSetting(nts.uk.ctx.at.shared.dom.worktime.common.
	 * FixedWorkRestSet)
	 */
	@Override
	public void setFixedWorkRestSetting(FixedWorkRestSet fixedWorkRestSetting) {
		fixedWorkRestSetting.saveToMemento(new JpaFixedWorkRestSetSetMemento<KshmtFixedWorkSet>(this.entity));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSettingSetMemento#
	 * setLstHalfDayWorkTimezone(java.util.List)
	 */
	@Override
	public void setLstHalfDayWorkTimezone(List<FixHalfDayWorkTimezone> lstHalfDayWorkTimezone) {
		if (CollectionUtil.isEmpty(lstHalfDayWorkTimezone)) {
			lstHalfDayWorkTimezone = new ArrayList<>();
		}
		lstHalfDayWorkTimezone.forEach(domain -> domain
				.saveToMemento(new JpaFixHalfDayWorkTimezoneSetMemento(this.entity, domain.getDayAtr())));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSettingSetMemento#
	 * setLstStampReflectTimezone(java.util.List)
	 */
	@Override
	public void setLstStampReflectTimezone(List<StampReflectTimezone> lstStampReflectTimezone) {
		if (CollectionUtil.isEmpty(lstStampReflectTimezone)) {
			lstStampReflectTimezone = new ArrayList<>();
		}

		String companyId = this.entity.getKshmtFixedWorkSetPK().getCid();
		String workTimeCd = this.entity.getKshmtFixedWorkSetPK().getWorktimeCd();

		// get list entity
		if (CollectionUtil.isEmpty(this.entity.getLstKshmtFixedStampReflect())) {
			this.entity.setLstKshmtFixedStampReflect(new ArrayList<>());
		}
		List<KshmtFixedStampReflect> lstStampReflect = this.entity.getLstKshmtFixedStampReflect();

		List<KshmtFixedStampReflect> newListStampReflect = new ArrayList<>();

		for (StampReflectTimezone stampRelect : lstStampReflectTimezone) {

			// get entity existed
			KshmtFixedStampReflect entity = lstStampReflect.stream().filter(item -> {
				KshmtFixedStampReflectPK pk = item.getKshmtFixedStampReflectPK();
				return pk.getCid().compareTo(companyId) == 0 && pk.getWorktimeCd().compareTo(workTimeCd) == 0
						&& pk.getWorkNo() == stampRelect.getWorkNo().v().intValue() && pk.getAtr() == stampRelect.getClassification().value;
			}).findFirst().orElse(new KshmtFixedStampReflect());

			stampRelect.saveToMemento(new JpaFixedStampReflectTimezoneSetMemento(companyId, workTimeCd, stampRelect.getWorkNo(), stampRelect.getClassification(), entity));
			newListStampReflect.add(entity);
		}
		this.entity.setLstKshmtFixedStampReflect(newListStampReflect);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSettingSetMemento#
	 * setLegalOTSetting(nts.uk.ctx.at.shared.dom.worktime.common.
	 * LegalOTSetting)
	 */
	@Override
	public void setLegalOTSetting(LegalOTSetting legalOTSetting) {
		this.entity.setLegalOtSet(legalOTSetting.value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSettingSetMemento#
	 * setFixedWorkCalcSetting(nts.uk.ctx.at.shared.dom.worktime.fixedset.
	 * FixedWorkCalcSetting)
	 */
	@Override
	public void setCalculationSetting(Optional<FixedWorkCalcSetting> fixedWorkCalcSetting) {
		if (fixedWorkCalcSetting.isPresent()) {
			fixedWorkCalcSetting.get().saveToMemento(new JpaFixedWorkCalcSettingSetMemento<KshmtFixedWorkSet>(this.entity));
		}		
	}
}
