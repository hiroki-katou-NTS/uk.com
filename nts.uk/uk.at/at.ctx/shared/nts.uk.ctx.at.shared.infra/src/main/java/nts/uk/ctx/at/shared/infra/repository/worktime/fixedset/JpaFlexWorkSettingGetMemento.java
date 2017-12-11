/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.fixedset;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.worktime.common.FlowWorkRestSetting;
import nts.uk.ctx.at.shared.dom.worktime.common.StampReflectTimezone;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSet;
import nts.uk.ctx.at.shared.dom.worktime.flexset.CoreTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexCalcSetting;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexHalfDayWorkTime;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexOffdayWorkTime;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSettingGetMemento;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtFlexWorkSet;

/**
 * The Class JpaFlexWorkSettingGetMemento.
 */
public class JpaFlexWorkSettingGetMemento implements FlexWorkSettingGetMemento{
	
	/** The entity. */
	private KshmtFlexWorkSet entity;
	
	/** The entity settings. */
	private List<KshmtFlexSettingGroup> entitySettings;
	
	
	/** The entity OD grounp. */
	private KshmtFlexOdGroup entityODGrounp;
	

	/**
	 * Instantiates a new jpa flex work setting get memento.
	 *
	 * @param entity the entity
	 * @param entitySettings the entity settings
	 * @param entityODGrounp the entity OD grounp
	 */
	public JpaFlexWorkSettingGetMemento(KshmtFlexWorkSet entity, List<KshmtFlexSettingGroup> entitySettings,
			KshmtFlexOdGroup entityODGrounp) {
		super();
		this.entity = entity;
		this.entitySettings = entitySettings;
		this.entityODGrounp = entityODGrounp;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSettingGetMemento#
	 * getCompanyId()
	 */
	@Override
	public String getCompanyId() {
		return this.entity.getKshmtFlexWorkSetPK().getCid();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSettingGetMemento#
	 * getWorkTimeCode()
	 */
	@Override
	public WorkTimeCode getWorkTimeCode() {
		return new WorkTimeCode(this.entity.getKshmtFlexWorkSetPK().getWorktimeCd());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSettingGetMemento#
	 * getCoreTimeSetting()
	 */
	@Override
	public CoreTimeSetting getCoreTimeSetting() {
		return new CoreTimeSetting(new JpaCoreTimeSettingGetMemento(this.entity));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSettingGetMemento#
	 * getRestSetting()
	 */
	@Override
	public FlowWorkRestSetting getRestSetting() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSettingGetMemento#
	 * getOffdayWorkTime()
	 */
	@Override
	public FlexOffdayWorkTime getOffdayWorkTime() {
		return new FlexOffdayWorkTime(new JpaFlexODWorkTimeGetMemento(this.entityODGrounp));
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
		// TODO Auto-generated method stub
		return false;
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
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSettingGetMemento#
	 * getCalculateSetting()
	 */
	@Override
	public FlexCalcSetting getCalculateSetting() {
		// TODO Auto-generated method stub
		return null;
	}

}
