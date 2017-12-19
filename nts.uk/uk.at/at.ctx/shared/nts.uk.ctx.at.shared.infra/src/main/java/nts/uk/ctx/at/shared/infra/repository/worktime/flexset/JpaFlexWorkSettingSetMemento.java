/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.flexset;

import java.util.List;

import nts.uk.ctx.at.shared.dom.worktime.common.BooleanGetAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.FlowWorkRestSetting;
import nts.uk.ctx.at.shared.dom.worktime.common.StampReflectTimezone;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSet;
import nts.uk.ctx.at.shared.dom.worktime.flexset.CoreTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexCalcSetting;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexHalfDayWorkTime;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexOffdayWorkTime;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSettingSetMemento;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtFlexWorkSet;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtFlexWorkSetPK;

/**
 * The Class JpaFlexWorkSettingSetMemento.
 */
public class JpaFlexWorkSettingSetMemento implements FlexWorkSettingSetMemento{
	
	/** The entity. */
	private KshmtFlexWorkSet entity;
	
	/** The entity settings. */
	private List<KshmtFlexSettingGroup> entitySettings;
	
	
	/** The entity OD grounp. */
	private KshmtFlexOdGroup entityODGrounp;
	

	/**
	 * Instantiates a new jpa flex work setting set memento.
	 *
	 * @param entity the entity
	 * @param entitySettings the entity settings
	 * @param entityODGrounp the entity OD grounp
	 */
	public JpaFlexWorkSettingSetMemento(KshmtFlexWorkSet entity, List<KshmtFlexSettingGroup> entitySettings,
			KshmtFlexOdGroup entityODGrounp) {
		super();
		if(entity.getKshmtFlexWorkSetPK() == null){
			entity.setKshmtFlexWorkSetPK(new KshmtFlexWorkSetPK());
		}
		this.entity = entity;
		this.entitySettings = entitySettings;
		this.entityODGrounp = entityODGrounp;
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSettingSetMemento#
	 * setcompanyId(java.lang.String)
	 */
	@Override
	public void setcompanyId(String companyId) {
		this.entity.getKshmtFlexWorkSetPK().setCid(companyId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSettingSetMemento#
	 * setWorkTimeCode(nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode)
	 */
	@Override
	public void setWorkTimeCode(WorkTimeCode workTimeCode) {
		this.entity.getKshmtFlexWorkSetPK().setWorktimeCd(workTimeCode.v());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSettingSetMemento#
	 * setCoreTimeSetting(nts.uk.ctx.at.shared.dom.worktime.flexset.
	 * CoreTimeSetting)
	 */
	@Override
	public void setCoreTimeSetting(CoreTimeSetting coreTimeSetting) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSettingSetMemento#
	 * setRestSetting(nts.uk.ctx.at.shared.dom.worktime.common.
	 * FlowWorkRestSetting)
	 */
	@Override
	public void setRestSetting(FlowWorkRestSetting restSetting) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSettingSetMemento#
	 * setOffdayWorkTime(nts.uk.ctx.at.shared.dom.worktime.flexset.
	 * FlexOffdayWorkTime)
	 */
	@Override
	public void setOffdayWorkTime(FlexOffdayWorkTime offdayWorkTime) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSettingSetMemento#
	 * setCommonSetting(nts.uk.ctx.at.shared.dom.worktime.common.
	 * WorkTimezoneCommonSet)
	 */
	@Override
	public void setCommonSetting(WorkTimezoneCommonSet commonSetting) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSettingSetMemento#
	 * setUseHalfDayShift(boolean)
	 */
	@Override
	public void setUseHalfDayShift(boolean useHalfDayShift) {
		this.entity.setUseHalfdayShift(BooleanGetAtr.getAtrByBoolean(useHalfDayShift));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSettingSetMemento#
	 * setLstHalfDayWorkTimezone(java.util.List)
	 */
	@Override
	public void setLstHalfDayWorkTimezone(List<FlexHalfDayWorkTime> halfDayWorkTimezone) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSettingSetMemento#
	 * setLstStampReflectTimezone(java.util.List)
	 */
	@Override
	public void setLstStampReflectTimezone(List<StampReflectTimezone> stampReflectTimezone) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSettingSetMemento#
	 * setCalculateSetting(nts.uk.ctx.at.shared.dom.worktime.flexset.
	 * FlexCalcSetting)
	 */
	@Override
	public void setCalculateSetting(FlexCalcSetting calculateSetting) {
		calculateSetting.saveToMemento(new JpaFlexCalcSettingSetMemento(this.entity));

	}

}
