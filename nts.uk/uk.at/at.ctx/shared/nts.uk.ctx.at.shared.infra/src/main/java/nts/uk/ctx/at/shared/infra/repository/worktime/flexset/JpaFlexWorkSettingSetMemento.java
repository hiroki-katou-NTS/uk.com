/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.flexset;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import nts.gul.collection.CollectionUtil;
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
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtFlexStampReflect;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.group.KshmtFlexArrayGroup;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.group.KshmtFlexSetGroup;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.group.KshmtFlexSettingGroup;
import nts.uk.ctx.at.shared.infra.repository.worktime.common.JpaFlexFlowWorkRestSettingSetMemento;
import nts.uk.ctx.at.shared.infra.repository.worktime.common.JpaFlexStampReflectTZSetMemento;

/**
 * The Class JpaFlexWorkSettingSetMemento.
 */
public class JpaFlexWorkSettingSetMemento implements FlexWorkSettingSetMemento {

	/** The entity setting. */
	private KshmtFlexSetGroup entitySetting;

	/** The entity settings. */
	private List<KshmtFlexSettingGroup> entitySettings;

	/** The entity OD grounp. */
	private KshmtFlexArrayGroup entityArrayGroup;

	/**
	 * Instantiates a new jpa flex work setting set memento.
	 *
	 * @param entitySetting
	 *            the entity setting
	 * @param entitySettings
	 *            the entity settings
	 * @param entityArrayGroup
	 *            the entity array group
	 */
	public JpaFlexWorkSettingSetMemento(KshmtFlexSetGroup entitySetting, List<KshmtFlexSettingGroup> entitySettings,
			KshmtFlexArrayGroup entityArrayGroup) {
		super();
		this.entitySetting = entitySetting;
		this.entitySettings = entitySettings;
		this.entityArrayGroup = entityArrayGroup;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSettingSetMemento#
	 * setcompanyId(java.lang.String)
	 */
	@Override
	public void setcompanyId(String companyId) {
		this.entitySetting.setCompanyId(companyId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSettingSetMemento#
	 * setWorkTimeCode(nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode)
	 */
	@Override
	public void setWorkTimeCode(WorkTimeCode workTimeCode) {
		this.entitySetting.setWorktimeCode(workTimeCode.v());
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
		if (coreTimeSetting != null) {
			coreTimeSetting.saveToMemento(new JpaCoreTimeSettingSetMemento(this.entitySetting.getEntitySetting()));
		}
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
		if (restSetting != null) {
			restSetting.saveToMemento(new JpaFlexFlowWorkRestSettingSetMemento(this.entitySetting.getEntityRest()));
		}
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
		if (offdayWorkTime != null) {
			offdayWorkTime.saveToMemento(new JpaFlexODWorkTimeSetMemento(this.entityArrayGroup, this.entitySetting));
		}
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
		this.entitySetting.getEntitySetting().setUseHalfdayShift(BooleanGetAtr.getAtrByBoolean(useHalfDayShift));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSettingSetMemento#
	 * setLstHalfDayWorkTimezone(java.util.List)
	 */
	@Override
	public void setLstHalfDayWorkTimezone(List<FlexHalfDayWorkTime> halfDayWorkTimezone) {
		if(CollectionUtil.isEmpty(halfDayWorkTimezone)){
			this.entitySettings = new ArrayList<>();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSettingSetMemento#
	 * setLstStampReflectTimezone(java.util.List)
	 */
	@Override
	public void setLstStampReflectTimezone(List<StampReflectTimezone> stampReflectTimezone) {
		if(CollectionUtil.isEmpty(stampReflectTimezone)){
			this.entityArrayGroup.setEntityStamps(new ArrayList<>());
		}
		else {
			this.entityArrayGroup.setEntityStamps(stampReflectTimezone.stream().map(domain -> {
				KshmtFlexStampReflect entity = new KshmtFlexStampReflect();
				domain.saveToMemento(new JpaFlexStampReflectTZSetMemento(entity));
				return entity;
			}).collect(Collectors.toList()));
		}
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
		if (calculateSetting != null) {
			calculateSetting.saveToMemento(new JpaFlexCalcSettingSetMemento(this.entitySetting.getEntitySetting()));
		}
	}

}
