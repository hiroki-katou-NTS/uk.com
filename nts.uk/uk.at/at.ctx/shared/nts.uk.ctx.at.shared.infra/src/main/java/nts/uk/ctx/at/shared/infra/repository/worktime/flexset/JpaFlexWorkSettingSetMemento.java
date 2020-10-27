/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.flexset;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
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
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSettingSetMemento;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkRestSetting;
import nts.uk.ctx.at.shared.infra.entity.worktime.common.KshmtWtCom;
import nts.uk.ctx.at.shared.infra.entity.worktime.common.KshmtWtComPK;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtWtFleBrFlWek;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtWtFleBrFlWekPK;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtWtFleBrFlHol;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtWtFleBrFlHolPK;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtWtFleBrFl;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtWtFleBrFlPK;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtWtFleStmpRefTs;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtWtFleStmpRefTsPK;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtWtFle;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtWtFlePK;
import nts.uk.ctx.at.shared.infra.repository.worktime.common.JpaFlexFlowWorkRestSettingSetMemento;
import nts.uk.ctx.at.shared.infra.repository.worktime.common.JpaFlexStampReflectTZSetMemento;
import nts.uk.ctx.at.shared.infra.repository.worktime.common.JpaWorkTimezoneCommonSetSetMemento;

/**
 * The Class JpaFlexWorkSettingSetMemento.
 */
public class JpaFlexWorkSettingSetMemento implements FlexWorkSettingSetMemento {
	
	/** The entity. */
	private KshmtWtFle entity;

	/** The entity common. */
	private KshmtWtCom entityCommon;
	

	/**
	 * Instantiates a new jpa flex work setting set memento.
	 *
	 * @param entity the entity
	 * @param entityCommon the entity common
	 */
	public JpaFlexWorkSettingSetMemento(KshmtWtFle entity, KshmtWtCom entityCommon) {
		super();
		if(entity.getKshmtWtFlePK() == null){
			entity.setKshmtWtFlePK(new KshmtWtFlePK());
		}
		if(entityCommon.getKshmtWtComPK() == null){
			entityCommon.setKshmtWtComPK(new KshmtWtComPK());
		}
		this.entity = entity;
		this.entityCommon = entityCommon;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSettingSetMemento#
	 * setcompanyId(java.lang.String)
	 */
	@Override
	public void setcompanyId(String companyId) {
		this.entity.getKshmtWtFlePK().setCid(companyId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSettingSetMemento#
	 * setWorkTimeCode(nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode)
	 */
	@Override
	public void setWorkTimeCode(WorkTimeCode workTimeCode) {
		this.entity.getKshmtWtFlePK().setWorktimeCd(workTimeCode.v());
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
			coreTimeSetting.saveToMemento(new JpaCoreTimeSettingSetMemento(this.entity));
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
			this.entity.setKshmtWtFleBrFl(
					new KshmtWtFleBrFl(new KshmtWtFleBrFlPK(this.entity.getKshmtWtFlePK().getCid(),
							this.entity.getKshmtWtFlePK().getWorktimeCd())));
			restSetting.saveToMemento(new JpaFlexFlowWorkRestSettingSetMemento(this.entity.getKshmtWtFleBrFl()));
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
			if (this.entity.getKshmtWtFleBrFlHol() == null) {
				this.entity.setKshmtWtFleBrFlHol(
						new KshmtWtFleBrFlHol(new KshmtWtFleBrFlHolPK(this.entity.getKshmtWtFlePK().getCid(),
								this.entity.getKshmtWtFlePK().getWorktimeCd())));
			}
			offdayWorkTime.saveToMemento(new JpaFlexODWorkTimeSetMemento(this.entity.getKshmtWtFleBrFlHol()));
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
		if(commonSetting!=null){
			commonSetting.saveToMemento(new JpaWorkTimezoneCommonSetSetMemento(this.entityCommon));
		}
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
	public void setLstHalfDayWorkTimezone(List<FlexHalfDayWorkTime> lstHalfDayWorkTimezone) {
		
		// check input empty
		if (CollectionUtil.isEmpty(lstHalfDayWorkTimezone)) {
			lstHalfDayWorkTimezone = new ArrayList<>();
		}
		
		// check list entity empty
		if (CollectionUtil.isEmpty(this.entity.getKshmtWtFleBrFlWeks())) {
			this.entity.setKshmtWtFleBrFlWeks(new ArrayList<>());
		}
		
		// convert map entity
		Map<KshmtWtFleBrFlWekPK, KshmtWtFleBrFlWek> mapEntity = this.entity.getKshmtWtFleBrFlWeks().stream()
				.collect(Collectors.toMap(item -> ((KshmtWtFleBrFlWek) item).getKshmtWtFleBrFlWekPK(), Function.identity()));
		
		String companyId = this.entity.getKshmtWtFlePK().getCid();
		String workTimeCd = this.entity.getKshmtWtFlePK().getWorktimeCd();
		
		this.entity.setKshmtWtFleBrFlWeks(lstHalfDayWorkTimezone.stream()
			.map(domain -> {
				
				// newPK
				KshmtWtFleBrFlWekPK pk = new KshmtWtFleBrFlWekPK();
				pk.setCid(companyId);
				pk.setWorktimeCd(workTimeCd);
				pk.setAmPmAtr(domain.getAmpmAtr().value);
				
				// find entity existed, if not new entity
				KshmtWtFleBrFlWek entity = mapEntity.get(pk);
				if (entity == null) {
					entity = new KshmtWtFleBrFlWek(pk);
				}
				
				// save to memento
				domain.saveToMemento(new JpaFlexHAWorkTimeSetMemento(entity));
				
				return entity;
			})
			.collect(Collectors.toList()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSettingSetMemento#
	 * setLstStampReflectTimezone(java.util.List)
	 */
	@Override
	public void setLstStampReflectTimezone(List<StampReflectTimezone> stampReflectTimezone) {
		if (CollectionUtil.isEmpty(stampReflectTimezone)) {
			this.entity.setKshmtWtFleStmpRefTss(new ArrayList<>());
		} else {
			this.entity.setKshmtWtFleStmpRefTss(stampReflectTimezone.stream().map(domain -> {
				KshmtWtFleStmpRefTs entity = new KshmtWtFleStmpRefTs(
						new KshmtWtFleStmpRefTsPK(this.entity.getKshmtWtFlePK().getCid(),
								this.entity.getKshmtWtFlePK().getWorktimeCd(), domain.getWorkNo().v(), domain.getClassification().value));
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
			calculateSetting.saveToMemento(new JpaFlexCalcSettingSetMemento(this.entity));
		}
	}

}
