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
import nts.uk.ctx.at.shared.infra.entity.worktime.common.KshmtWtCom;
import nts.uk.ctx.at.shared.infra.entity.worktime.common.KshmtWtComPK;
import nts.uk.ctx.at.shared.infra.entity.worktime.fixedset.KshmtWtFixStmpRefTs;
import nts.uk.ctx.at.shared.infra.entity.worktime.fixedset.KshmtWtFixStmpRefTsPK;
import nts.uk.ctx.at.shared.infra.entity.worktime.fixedset.KshmtWtFix;
import nts.uk.ctx.at.shared.infra.entity.worktime.fixedset.KshmtWtFixPK;
import nts.uk.ctx.at.shared.infra.repository.worktime.common.JpaFixedWorkCalcSettingSetMemento;
import nts.uk.ctx.at.shared.infra.repository.worktime.common.JpaFixedWorkRestSetSetMemento;
import nts.uk.ctx.at.shared.infra.repository.worktime.common.JpaWorkTimezoneCommonSetSetMemento;

/**
 * The Class JpaFixedWorkSettingSetMemento.
 */
public class JpaFixedWorkSettingSetMemento implements FixedWorkSettingSetMemento {

	/** The entity. */
	private KshmtWtFix entity;

	/**
	 * Instantiates a new jpa fixed work setting set memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaFixedWorkSettingSetMemento(KshmtWtFix entity) {
		super();
		this.entity = entity;
		if (this.entity.getKshmtWtFixPK() == null) {
			this.entity.setKshmtWtFixPK(new KshmtWtFixPK());
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
		this.entity.getKshmtWtFixPK().setCid(companyId);
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
		this.entity.getKshmtWtFixPK().setWorktimeCd(workTimeCode.v());
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
		KshmtWtCom commonEntity = this.entity.getKshmtWtCom();
		if (commonEntity == null) {
			commonEntity = new KshmtWtCom();

			// new pk
			KshmtWtComPK pk = new KshmtWtComPK();
			pk.setCid(this.entity.getKshmtWtFixPK().getCid());
			pk.setWorktimeCd(this.entity.getKshmtWtFixPK().getWorktimeCd());
			pk.setWorkFormAtr(WorkTimeDailyAtr.REGULAR_WORK.value);
			pk.setWorktimeSetMethod(WorkTimeMethodSet.FIXED_WORK.value);

			// set pk
			commonEntity.setKshmtWtComPK(pk);

			// add entity when empty list common.
			this.entity.getLstKshmtWtCom().add(commonEntity);
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
		fixedWorkRestSetting.saveToMemento(new JpaFixedWorkRestSetSetMemento<KshmtWtFix>(this.entity));
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

		String companyId = this.entity.getKshmtWtFixPK().getCid();
		String workTimeCd = this.entity.getKshmtWtFixPK().getWorktimeCd();

		// get list entity
		if (CollectionUtil.isEmpty(this.entity.getLstKshmtWtFixStmpRefTs())) {
			this.entity.setLstKshmtWtFixStmpRefTs(new ArrayList<>());
		}
		List<KshmtWtFixStmpRefTs> lstStampReflect = this.entity.getLstKshmtWtFixStmpRefTs();

		List<KshmtWtFixStmpRefTs> newListStampReflect = new ArrayList<>();

		for (StampReflectTimezone stampRelect : lstStampReflectTimezone) {

			// get entity existed
			KshmtWtFixStmpRefTs entity = lstStampReflect.stream().filter(item -> {
				KshmtWtFixStmpRefTsPK pk = item.getKshmtWtFixStmpRefTsPK();
				return pk.getCid().compareTo(companyId) == 0 && pk.getWorktimeCd().compareTo(workTimeCd) == 0
						&& pk.getWorkNo() == stampRelect.getWorkNo().v().intValue() && pk.getAtr() == stampRelect.getClassification().value;
			}).findFirst().orElse(new KshmtWtFixStmpRefTs());

			stampRelect.saveToMemento(new JpaFixedStampReflectTimezoneSetMemento(companyId, workTimeCd, stampRelect.getWorkNo(), stampRelect.getClassification(), entity));
			newListStampReflect.add(entity);
		}
		this.entity.setLstKshmtWtFixStmpRefTs(newListStampReflect);
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
			fixedWorkCalcSetting.get().saveToMemento(new JpaFixedWorkCalcSettingSetMemento<KshmtWtFix>(this.entity));
		}		
	}
}
