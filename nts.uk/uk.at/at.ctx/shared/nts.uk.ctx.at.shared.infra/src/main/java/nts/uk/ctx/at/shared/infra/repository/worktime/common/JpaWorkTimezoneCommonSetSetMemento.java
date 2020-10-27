/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.primitives.BonusPaySettingCode;
import nts.uk.ctx.at.shared.dom.worktime.common.BooleanGetAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.HolidayCalculation;
import nts.uk.ctx.at.shared.dom.worktime.common.IntervalTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSetSetMemento;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneExtraordTimeSet;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneGoOutSet;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneLateEarlySet;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneLateNightTimeSet;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneMedicalSet;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneOtherSubHolTimeSet;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneShortTimeWorkSet;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneStampSet;
import nts.uk.ctx.at.shared.infra.entity.worktime.common.KshmtWtComMedical;
import nts.uk.ctx.at.shared.infra.entity.worktime.common.KshmtWtComMedicalPK;
import nts.uk.ctx.at.shared.infra.entity.worktime.common.KshmtWtComHdcom;
import nts.uk.ctx.at.shared.infra.entity.worktime.common.KshmtWtComHdcomPK;
import nts.uk.ctx.at.shared.infra.entity.worktime.common.KshmtWtCom;
import nts.uk.ctx.at.shared.infra.entity.worktime.common.KshmtWtComPK;

/**
 * The Class JpaWorkTimezoneCommonSetSetMemento.
 */
public class JpaWorkTimezoneCommonSetSetMemento implements WorkTimezoneCommonSetSetMemento {

	/** The entity. */
	private KshmtWtCom entity;

	/**
	 * Instantiates a new jpa work timezone common set set memento.
	 *
	 * @param entity the entity
	 */
	public JpaWorkTimezoneCommonSetSetMemento(KshmtWtCom entity) {
		super();
		if (entity.getKshmtWtComPK() == null) {
			entity.setKshmtWtComPK(new KshmtWtComPK());
		}
		this.entity = entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSetSetMemento#
	 * setZeroHStraddCalculateSet(boolean)
	 */
	@Override
	public void setZeroHStraddCalculateSet(boolean calcSet) {
		this.entity.setOverDayCalcSet(BooleanGetAtr.getAtrByBoolean(calcSet));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSetSetMemento#
	 * setIntervalSet(nts.uk.ctx.at.shared.dom.worktime.common.
	 * IntervalTimeSetting)
	 */
	@Override
	public void setIntervalSet(IntervalTimeSetting itvset) {
		itvset.saveToMemento(new JpaIntervalTimeSettingSetMemento(this.entity));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSetSetMemento#
	 * setSubHolTimeSet(java.util.List)
	 */
	@Override
	public void setSubHolTimeSet(List<WorkTimezoneOtherSubHolTimeSet> shtSet) {
		if (CollectionUtil.isEmpty(shtSet)) {
			return;
		}
		if (CollectionUtil.isEmpty(this.entity.getKshmtWtComHdcoms())) {
			this.entity.setKshmtWtComHdcoms(new ArrayList<>());
		}

		Map<KshmtWtComHdcomPK, KshmtWtComHdcom> existShtSet = this.entity.getKshmtWtComHdcoms().stream()
				.collect(Collectors.toMap(KshmtWtComHdcom::getKshmtWtComHdcomPK, Function.identity()));

		shtSet.forEach(domain -> {
			KshmtWtComHdcomPK pk = new KshmtWtComHdcomPK(this.entity.getKshmtWtComPK().getCid(),
					this.entity.getKshmtWtComPK().getWorktimeCd(),
					this.entity.getKshmtWtComPK().getWorkFormAtr(),
					this.entity.getKshmtWtComPK().getWorktimeSetMethod(), domain.getOriginAtr().value);
			KshmtWtComHdcom entity = existShtSet.get(pk);
			if (entity == null) {
				entity = new KshmtWtComHdcom();
			}

			domain.saveToMemento(new JpaWorkTimezoneOtherSubHolTimeSetSetMemento(entity));
			entity.setKshmtWtComHdcomPK(pk);
			existShtSet.put(entity.getKshmtWtComHdcomPK(), entity);
		});
		this.entity.setKshmtWtComHdcoms(existShtSet.values().stream().collect(Collectors.toList()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSetSetMemento#
	 * setMedicalSet(java.util.List)
	 */
	@Override
	public void setMedicalSet(List<WorkTimezoneMedicalSet> list) {
		if (CollectionUtil.isEmpty(list)) {
			return;
		}
		if (CollectionUtil.isEmpty(this.entity.getKshmtWtComMedicals())) {
			this.entity.setKshmtWtComMedicals(new ArrayList<>());
		}

		Map<KshmtWtComMedicalPK, KshmtWtComMedical> existShtSet = this.entity.getKshmtWtComMedicals().stream()
				.collect(Collectors.toMap(KshmtWtComMedical::getKshmtWtComMedicalPK, Function.identity()));

		list.forEach(domain -> {
			KshmtWtComMedicalPK pk = new KshmtWtComMedicalPK(this.entity.getKshmtWtComPK().getCid(),
					this.entity.getKshmtWtComPK().getWorktimeCd(),
					this.entity.getKshmtWtComPK().getWorkFormAtr(),
					this.entity.getKshmtWtComPK().getWorktimeSetMethod(), domain.getWorkSystemAtr().value);
			KshmtWtComMedical entity = existShtSet.get(pk);

			if (entity == null) {
				entity = new KshmtWtComMedical();
			}

			domain.saveToMemento(new JpaWorkTimezoneMedicalSetSetMemento(entity));
			entity.setKshmtWtComMedicalPK(pk);
			existShtSet.put(entity.getKshmtWtComMedicalPK(), entity);
		});
		this.entity.setKshmtWtComMedicals(existShtSet.values().stream().collect(Collectors.toList()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSetSetMemento#
	 * setGoOutSet(nts.uk.ctx.at.shared.dom.worktime.common.
	 * WorkTimezoneGoOutSet)
	 */
	@Override
	public void setGoOutSet(WorkTimezoneGoOutSet set) {
		set.saveToMemento(new JpaWorkTimezoneGoOutSetSetMemento(this.entity));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSetSetMemento#
	 * setStampSet(nts.uk.ctx.at.shared.dom.worktime.common.
	 * WorkTimezoneStampSet)
	 */
	@Override
	public void setStampSet(WorkTimezoneStampSet set) {
		set.saveToMemento(new JpaWorkTimezoneStampSetSetMemento(this.entity));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSetSetMemento#
	 * setLateNightTimeSet(nts.uk.ctx.at.shared.dom.worktime.common.
	 * WorkTimezoneLateNightTimeSet)
	 */
	@Override
	public void setLateNightTimeSet(WorkTimezoneLateNightTimeSet set) {
		this.entity.setLateNightUnit(set.getRoundingSetting().getRoundingTime().value);
		this.entity.setLateNightRounding(set.getRoundingSetting().getRounding().value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSetSetMemento#
	 * setShortTimeWorkSet(nts.uk.ctx.at.shared.dom.worktime.common.
	 * WorkTimezoneShortTimeWorkSet)
	 */
	@Override
	public void setShortTimeWorkSet(WorkTimezoneShortTimeWorkSet set) {
		this.entity.setNurTimezoneWorkUse(BooleanGetAtr.getAtrByBoolean(set.isNursTimezoneWorkUse()));
		this.entity.setEmpTimeDeduct(BooleanGetAtr.getAtrByBoolean(set.isEmploymentTimeDeduct()));
		this.entity.setChildCareWorkUse(BooleanGetAtr.getAtrByBoolean(set.isChildCareWorkUse()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSetSetMemento#
	 * setExtraordTimeSet(nts.uk.ctx.at.shared.dom.worktime.common.
	 * WorkTimezoneExtraordTimeSet)
	 */
	@Override
	public void setExtraordTimeSet(WorkTimezoneExtraordTimeSet set) {
		set.saveToMemento(new JpaWorkTimezoneExtraordTimeSetSetMemento(this.entity));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSetSetMemento#
	 * setLateEarlySet(nts.uk.ctx.at.shared.dom.worktime.common.
	 * WorkTimezoneLateEarlySet)
	 */
	@Override
	public void setLateEarlySet(WorkTimezoneLateEarlySet set) {
		set.saveToMemento(new JpaWorkTimezoneLateEarlySetSetMemento(this.entity));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSetSetMemento#
	 * setHolidayCalculation(nts.uk.ctx.at.shared.dom.worktime.common.
	 * HolidayCalculation)
	 */
	@Override
	public void setHolidayCalculation(HolidayCalculation holidayCalculation) {
		holidayCalculation.saveToMememto(new JpaHolidayCalculationSetMemento(this.entity));
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSetSetMemento#setRaisingSalarySet(java.util.Optional)
	 */
	@Override
	public void setRaisingSalarySet(Optional<BonusPaySettingCode> set) {
		this.entity.setRaisingSalarySet(set.isPresent() ? set.get().v() : null);
	}

}
