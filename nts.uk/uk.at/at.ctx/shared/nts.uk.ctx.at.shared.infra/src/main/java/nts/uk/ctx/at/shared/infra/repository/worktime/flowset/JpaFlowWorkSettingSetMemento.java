/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.flowset;

import nts.uk.ctx.at.shared.dom.worktime.common.LegalOTSetting;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSet;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowHalfDayWorkTimezone;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowOffdayWorkTimezone;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowStampReflectTimezone;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkDedicateSetting;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkRestSetting;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkSettingSetMemento;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeDailyAtr;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeMethodSet;
import nts.uk.ctx.at.shared.infra.entity.worktime.common.KshmtWtCom;
import nts.uk.ctx.at.shared.infra.entity.worktime.common.KshmtWtComPK;
import nts.uk.ctx.at.shared.infra.entity.worktime.flowset.KshmtWtFloBrFlAll;
import nts.uk.ctx.at.shared.infra.entity.worktime.flowset.KshmtWtFloBrFlAllPK;
import nts.uk.ctx.at.shared.infra.entity.worktime.flowset.KshmtWtFlo;
import nts.uk.ctx.at.shared.infra.entity.worktime.flowset.KshmtWtFloPK;
import nts.uk.ctx.at.shared.infra.entity.worktime.flowset.KshmtWtFloStmpRef2Ts;
import nts.uk.ctx.at.shared.infra.entity.worktime.flowset.KshmtWtFloStmpRef2TsPK;
import nts.uk.ctx.at.shared.infra.repository.worktime.common.JpaWorkTimezoneCommonSetSetMemento;

/**
 * The Class JpaFlowWorkSettingSetMemento.
 */
public class JpaFlowWorkSettingSetMemento implements FlowWorkSettingSetMemento {

	/** The entity. */
	private KshmtWtFlo entity;

	/**
	 * Instantiates a new jpa flow work setting set memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaFlowWorkSettingSetMemento(KshmtWtFlo entity) {
		super();
		this.entity = entity;
		if (this.entity.getKshmtWtFloPK() == null) {
			this.entity.setKshmtWtFloPK(new KshmtWtFloPK());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flowset.FlWorkSettingSetMemento#
	 * setCompanyId(java.lang.String)
	 */
	@Override
	public void setCompanyId(String cid) {
		this.entity.getKshmtWtFloPK().setCid(cid);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flowset.FlWorkSettingSetMemento#
	 * setWorkingCode(nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode)
	 */
	@Override
	public void setWorkingCode(WorkTimeCode wtCode) {
		this.entity.getKshmtWtFloPK().setWorktimeCd(wtCode.v());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flowset.FlWorkSettingSetMemento#
	 * setRestSetting(nts.uk.ctx.at.shared.dom.worktime.common.
	 * FlowWorkRestSetting)
	 */
	@Override
	public void setRestSetting(FlowWorkRestSetting restSet) {
		KshmtWtFloBrFlAll restEntity = this.entity.getKshmtWtFloBrFlAll();
		if (restEntity == null) {
			restEntity = new KshmtWtFloBrFlAll();
			
			// new pk
			KshmtWtFloBrFlAllPK pk = new KshmtWtFloBrFlAllPK();
			pk.setCid(this.entity.getKshmtWtFloPK().getCid());
			pk.setWorktimeCd(this.entity.getKshmtWtFloPK().getWorktimeCd());
			
			// set pk
			restEntity.setKshmtWtFloBrFlAllPK(pk);			
			this.entity.setKshmtWtFloBrFlAll(restEntity);
		}
		restSet.saveToMemento(new JpaFlowWorkRestSettingSetMemento(restEntity));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flowset.FlWorkSettingSetMemento#
	 * setOffdayWorkTimezone(nts.uk.ctx.at.shared.dom.worktime.flowset.
	 * FlowOffdayWorkTimezone)
	 */
	@Override
	public void setOffdayWorkTimezone(FlowOffdayWorkTimezone offDayWtz) {
		offDayWtz.saveToMemento(new JpaFlowOffdayWorkTimezoneSetMemento(this.entity));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flowset.FlWorkSettingSetMemento#
	 * setCommonSetting(nts.uk.ctx.at.shared.dom.worktime.common.
	 * WorkTimezoneCommonSet)
	 */
	@Override
	public void setCommonSetting(WorkTimezoneCommonSet cmnSet) {
		KshmtWtCom commonEntity = this.entity.getKshmtWtCom();
		if (commonEntity == null) {
			commonEntity = new KshmtWtCom();
			
			// new pk
			KshmtWtComPK pk = new KshmtWtComPK();
			pk.setCid(this.entity.getKshmtWtFloPK().getCid());
			pk.setWorktimeCd(this.entity.getKshmtWtFloPK().getWorktimeCd());
			pk.setWorkFormAtr(WorkTimeDailyAtr.REGULAR_WORK.value);
			pk.setWorktimeSetMethod(WorkTimeMethodSet.FLOW_WORK.value);
			
			// set pk
			commonEntity.setKshmtWtComPK(pk);
			
			// add entity when empty list common.
			this.entity.getLstKshmtWtCom().add(commonEntity);
		}
		cmnSet.saveToMemento(new JpaWorkTimezoneCommonSetSetMemento(commonEntity));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flowset.FlWorkSettingSetMemento#
	 * setHalfDayWorkTimezone(nts.uk.ctx.at.shared.dom.worktime.flowset.
	 * FlowHalfDayWorkTimezone)
	 */
	@Override
	public void setHalfDayWorkTimezone(FlowHalfDayWorkTimezone halfDayWtz) {
		halfDayWtz.saveToMemento(new JpaFlowHalfDayWorkTimezoneSetMemento(this.entity));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flowset.FlWorkSettingSetMemento#
	 * setStampReflectTimezone(nts.uk.ctx.at.shared.dom.worktime.flowset.
	 * FlowStampReflectTimezone)
	 */
	@Override
	public void setStampReflectTimezone(FlowStampReflectTimezone stampRefTz) {
		KshmtWtFloStmpRef2Ts stampEntity = this.entity.getKshmtWtFloStmpRef2Ts();
		if (stampEntity == null) {
			KshmtWtFloStmpRef2TsPK pk = new KshmtWtFloStmpRef2TsPK();
			pk.setCid(this.entity.getKshmtWtFloPK().getCid());
			pk.setWorktimeCd(this.entity.getKshmtWtFloPK().getWorktimeCd());
			
			stampEntity = new KshmtWtFloStmpRef2Ts();
			stampEntity.setKshmtWtFloStmpRef2TsPK(pk);
			this.entity.setKshmtWtFloStmpRef2Ts(stampEntity);
		}
		stampRefTz.saveToMemento(new JpaFlowStampReflectTimezoneSetMemento(this.entity.getKshmtWtFloStmpRef2Ts()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flowset.FlWorkSettingSetMemento#
	 * setLegalOTSetting(nts.uk.ctx.at.shared.dom.worktime.common.
	 * LegalOTSetting)
	 */
	@Override
	public void setLegalOTSetting(LegalOTSetting legalOtSet) {
		this.entity.setLegalOtSet(legalOtSet.value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flowset.FlWorkSettingSetMemento#
	 * setFlowSetting(nts.uk.ctx.at.shared.dom.worktime.flowset.
	 * FlowWorkDedicateSetting)
	 */
	@Override
	public void setFlowSetting(FlowWorkDedicateSetting flowSet) {
		flowSet.saveToMemento(new JpaFlowWorkDedicateSettingSetMemento(this.entity));
	}

}
