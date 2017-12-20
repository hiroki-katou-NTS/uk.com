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
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSettingGetMemento;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.group.KshmtFlexArrayGroup;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.group.KshmtFlexSetGroup;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.group.KshmtFlexSettingGroup;
import nts.uk.ctx.at.shared.infra.repository.worktime.common.JpaFlexFlowWorkRestSettingGetMemento;
import nts.uk.ctx.at.shared.infra.repository.worktime.common.JpaFlexStampReflectTZGetMemento;

/**
 * The Class JpaFlexWorkSettingGetMemento.
 */
public class JpaFlexWorkSettingGetMemento implements FlexWorkSettingGetMemento{
	
	/** The entity setting. */
	private KshmtFlexSetGroup entitySetting;
	
	/** The entity settings. */
	private List<KshmtFlexSettingGroup> entitySettings;
	
	/** The entity array group. */
	private KshmtFlexArrayGroup entityArrayGroup;
	

	/**
	 * Instantiates a new jpa flex work setting get memento.
	 *
	 * @param entitySetting the entity setting
	 * @param entitySettings the entity settings
	 * @param entityArrayGroup the entity array group
	 */
	public JpaFlexWorkSettingGetMemento(KshmtFlexSetGroup entitySetting, List<KshmtFlexSettingGroup> entitySettings,
			KshmtFlexArrayGroup entityArrayGroup) {
		super();
		this.entitySetting = entitySetting;
		this.entitySettings = entitySettings;
		this.entityArrayGroup = entityArrayGroup;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSettingGetMemento#
	 * getCompanyId()
	 */
	@Override
	public String getCompanyId() {
		return this.entitySetting.getEntitySetting().getKshmtFlexWorkSetPK().getCid();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSettingGetMemento#
	 * getWorkTimeCode()
	 */
	@Override
	public WorkTimeCode getWorkTimeCode() {
		return new WorkTimeCode(this.entitySetting.getEntitySetting().getKshmtFlexWorkSetPK().getWorktimeCd());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSettingGetMemento#
	 * getCoreTimeSetting()
	 */
	@Override
	public CoreTimeSetting getCoreTimeSetting() {
		return new CoreTimeSetting(new JpaCoreTimeSettingGetMemento(this.entitySetting.getEntitySetting()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSettingGetMemento#
	 * getRestSetting()
	 */
	@Override
	public FlowWorkRestSetting getRestSetting() {
		return new FlowWorkRestSetting(new JpaFlexFlowWorkRestSettingGetMemento(this.entitySetting.getEntityRest()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSettingGetMemento#
	 * getOffdayWorkTime()
	 */
	@Override
	public FlexOffdayWorkTime getOffdayWorkTime() {
		return new FlexOffdayWorkTime(new JpaFlexODWorkTimeGetMemento(this.entityArrayGroup, this.entitySetting));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSettingGetMemento#
	 * getCommonSetting()
	 */
	@Override
	public WorkTimezoneCommonSet getCommonSetting() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSettingGetMemento#
	 * getUseHalfDayShift()
	 */
	@Override
	public boolean getUseHalfDayShift() {
		return BooleanGetAtr.getAtrByInteger(this.entitySetting.getEntitySetting().getUseHalfdayShift());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSettingGetMemento#
	 * getLstHalfDayWorkTimezone()
	 */
	@Override
	public List<FlexHalfDayWorkTime> getLstHalfDayWorkTimezone() {
		if(CollectionUtil.isEmpty(this.entitySettings)){
			return new ArrayList<>();
		}
		return this.entitySettings.stream()
				.map(entitySetting -> new FlexHalfDayWorkTime(new JpaFlexHAWorkTimeGetMemento(entitySetting)))
				.collect(Collectors.toList());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSettingGetMemento#
	 * getLstStampReflectTimezone()
	 */
	@Override
	public List<StampReflectTimezone> getLstStampReflectTimezone() {
		if(CollectionUtil.isEmpty(this.entityArrayGroup.getEntityStamps())){
			return new ArrayList<>();
		}
		return this.entityArrayGroup.getEntityStamps().stream()
				.map(entity -> new StampReflectTimezone(new JpaFlexStampReflectTZGetMemento(entity)))
				.collect(Collectors.toList());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSettingGetMemento#
	 * getCalculateSetting()
	 */
	@Override
	public FlexCalcSetting getCalculateSetting() {
		return new FlexCalcSetting(new JpaFlexCalcSettingGetMemento(this.entitySetting.getEntitySetting()));
	}

}
