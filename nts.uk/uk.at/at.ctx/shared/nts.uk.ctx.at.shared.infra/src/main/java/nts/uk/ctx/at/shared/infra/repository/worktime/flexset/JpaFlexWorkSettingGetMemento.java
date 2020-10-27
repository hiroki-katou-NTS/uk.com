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
import nts.uk.ctx.at.shared.dom.worktime.common.StampReflectTimezone;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSet;
import nts.uk.ctx.at.shared.dom.worktime.flexset.CoreTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexCalcSetting;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexHalfDayWorkTime;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexOffdayWorkTime;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSettingGetMemento;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkRestSetting;
import nts.uk.ctx.at.shared.infra.entity.worktime.common.KshmtWtCom;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtWtFle;
import nts.uk.ctx.at.shared.infra.repository.worktime.common.JpaFlexFlowWorkRestSettingGetMemento;
import nts.uk.ctx.at.shared.infra.repository.worktime.common.JpaFlexStampReflectTZGetMemento;
import nts.uk.ctx.at.shared.infra.repository.worktime.common.JpaWorkTimezoneCommonSetGetMemento;

/**
 * The Class JpaFlexWorkSettingGetMemento.
 */
public class JpaFlexWorkSettingGetMemento implements FlexWorkSettingGetMemento{
	
	/** The entity. */
	private KshmtWtFle entity;
	
	/** The entity common. */
	private KshmtWtCom entityCommon;
	

	/**
	 * Instantiates a new jpa flex work setting get memento.
	 *
	 * @param entity the entity
	 * @param entityCommon the entity common
	 */
	public JpaFlexWorkSettingGetMemento(KshmtWtFle entity, KshmtWtCom entityCommon) {
		super();
		this.entity = entity;
		this.entityCommon = entityCommon;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSettingGetMemento#
	 * getCompanyId()
	 */
	@Override
	public String getCompanyId() {
		return this.entity.getKshmtWtFlePK().getCid();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSettingGetMemento#
	 * getWorkTimeCode()
	 */
	@Override
	public WorkTimeCode getWorkTimeCode() {
		return new WorkTimeCode(this.entity.getKshmtWtFlePK().getWorktimeCd());
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
		return new FlowWorkRestSetting(new JpaFlexFlowWorkRestSettingGetMemento(this.entity.getKshmtWtFleBrFl()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSettingGetMemento#
	 * getOffdayWorkTime()
	 */
	@Override
	public FlexOffdayWorkTime getOffdayWorkTime() {
		return new FlexOffdayWorkTime(new JpaFlexODWorkTimeGetMemento(this.entity.getKshmtWtFleBrFlHol()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSettingGetMemento#
	 * getCommonSetting()
	 */
	@Override
	public WorkTimezoneCommonSet getCommonSetting() {
		return new WorkTimezoneCommonSet(new JpaWorkTimezoneCommonSetGetMemento(this.entityCommon));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSettingGetMemento#
	 * getUseHalfDayShift()
	 */
	@Override
	public boolean getUseHalfDayShift() {
		return BooleanGetAtr.getAtrByInteger(this.entity.getUseHalfdayShift());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSettingGetMemento#
	 * getLstHalfDayWorkTimezone()
	 */
	@Override
	public List<FlexHalfDayWorkTime> getLstHalfDayWorkTimezone() {
		if (CollectionUtil.isEmpty(this.entity.getKshmtWtFleBrFlWeks())) {
			return new ArrayList<>();
		}
		return this.entity.getKshmtWtFleBrFlWeks().stream()
				.map(entity -> new FlexHalfDayWorkTime(new JpaFlexHAWorkTimeGetMemento(entity)))
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
		if (CollectionUtil.isEmpty(this.entity.getKshmtWtFleStmpRefTss())) {
			return new ArrayList<>();
		}
		return this.entity.getKshmtWtFleStmpRefTss().stream()
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
		return new FlexCalcSetting(new JpaFlexCalcSettingGetMemento(this.entity));
	}

}
