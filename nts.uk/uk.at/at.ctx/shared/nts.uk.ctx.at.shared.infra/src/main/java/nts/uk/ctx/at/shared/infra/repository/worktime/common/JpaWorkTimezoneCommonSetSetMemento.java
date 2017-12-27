/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import nts.uk.ctx.at.shared.dom.bonuspay.primitives.BonusPaySettingCode;
import nts.uk.ctx.at.shared.dom.worktime.common.BooleanGetAtr;
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
import nts.uk.ctx.at.shared.infra.entity.worktime.common.KshmtMedicalTimeSet;
import nts.uk.ctx.at.shared.infra.entity.worktime.common.KshmtMedicalTimeSetPK;
import nts.uk.ctx.at.shared.infra.entity.worktime.common.KshmtSubstitutionSet;
import nts.uk.ctx.at.shared.infra.entity.worktime.common.KshmtSubstitutionSetPK;
import nts.uk.ctx.at.shared.infra.entity.worktime.common.KshmtWorktimeCommonSet;

/**
 * The Class JpaWorkTimezoneCommonSetSetMemento.
 */
public class JpaWorkTimezoneCommonSetSetMemento implements WorkTimezoneCommonSetSetMemento {

	/** The entity. */
	private KshmtWorktimeCommonSet entity;

	/**
	 * Instantiates a new jpa work timezone common set set memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaWorkTimezoneCommonSetSetMemento(KshmtWorktimeCommonSet entity) {
		super();
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
		List<KshmtSubstitutionSet> newListEntity = new ArrayList<>();
		
		Map<KshmtSubstitutionSetPK, KshmtSubstitutionSet> existShtSet = this.entity.getKshmtSubstitutionSets().stream()
				.collect(Collectors.toMap(KshmtSubstitutionSet::getKshmtSubstitutionSetPK, Function.identity()));
		
		shtSet.forEach(domain -> {							
			KshmtSubstitutionSet entity = existShtSet.get(new KshmtSubstitutionSetPK(
					this.entity.getKshmtWorktimeCommonSetPK().getCid(),
					this.entity.getKshmtWorktimeCommonSetPK().getWorktimeCd(),
					this.entity.getKshmtWorktimeCommonSetPK().getWorkFormAtr(), 
					this.entity.getKshmtWorktimeCommonSetPK().getWorktimeSetMethod(), 
					domain.getOriginAtr().value));
			
			if (entity == null) {
				KshmtSubstitutionSetPK pk = new KshmtSubstitutionSetPK(
						this.entity.getKshmtWorktimeCommonSetPK().getCid(),
						this.entity.getKshmtWorktimeCommonSetPK().getWorktimeCd(),
						this.entity.getKshmtWorktimeCommonSetPK().getWorkFormAtr(), 
						this.entity.getKshmtWorktimeCommonSetPK().getWorktimeSetMethod(), 
						domain.getOriginAtr().value);
				entity = new KshmtSubstitutionSet();
				entity.setKshmtSubstitutionSetPK(pk);
			}
			
			domain.saveToMemento(new JpaWorkTimezoneOtherSubHolTimeSetSetMemento(entity));
			newListEntity.add(entity);
		});
		this.entity.setKshmtSubstitutionSets(newListEntity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSetSetMemento#
	 * setRaisingSalarySet(nts.uk.ctx.at.shared.dom.bonuspay.primitives.
	 * BonusPaySettingCode)
	 */
	@Override
	public void setRaisingSalarySet(BonusPaySettingCode set) {
		this.entity.setRaisingSalarySet(set.v());
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
		List<KshmtMedicalTimeSet> newListEntity = new ArrayList<>();
		
		Map<KshmtMedicalTimeSetPK, KshmtMedicalTimeSet> existShtSet = this.entity.getKshmtMedicalTimeSets().stream()
				.collect(Collectors.toMap(KshmtMedicalTimeSet::getKshmtMedicalTimeSetPK, Function.identity()));
		
		list.forEach(domain -> {							
			KshmtMedicalTimeSet entity = existShtSet.get(new KshmtMedicalTimeSetPK(
					this.entity.getKshmtWorktimeCommonSetPK().getCid(),
					this.entity.getKshmtWorktimeCommonSetPK().getWorktimeCd(),
					this.entity.getKshmtWorktimeCommonSetPK().getWorkFormAtr(), 
					this.entity.getKshmtWorktimeCommonSetPK().getWorktimeSetMethod(), 
					domain.getWorkSystemAtr().value));
			
			if (entity == null) {
				KshmtMedicalTimeSetPK pk = new KshmtMedicalTimeSetPK(
						this.entity.getKshmtWorktimeCommonSetPK().getCid(),
						this.entity.getKshmtWorktimeCommonSetPK().getWorktimeCd(),
						this.entity.getKshmtWorktimeCommonSetPK().getWorkFormAtr(), 
						this.entity.getKshmtWorktimeCommonSetPK().getWorktimeSetMethod(), 
						domain.getWorkSystemAtr().value);
				entity = new KshmtMedicalTimeSet();
				entity.setKshmtMedicalTimeSetPK(pk);
			}
			
			domain.saveToMemento(new JpaWorkTimezoneMedicalSetSetMemento(entity));
			newListEntity.add(entity);
		});
		this.entity.setKshmtMedicalTimeSets(newListEntity);
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
		// TODO Auto-generated method stub

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

}
