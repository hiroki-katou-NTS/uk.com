/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.flowset;

import nts.uk.ctx.at.shared.dom.worktime.common.LegalOTSetting;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSet;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkSettingGetMemento;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowHalfDayWorkTimezone;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowOffdayWorkTimezone;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowStampReflectTimezone;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkDedicateSetting;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkRestSetting;
import nts.uk.ctx.at.shared.infra.entity.worktime.common.KshmtWtCom;
import nts.uk.ctx.at.shared.infra.entity.worktime.flowset.KshmtWtFlo;
import nts.uk.ctx.at.shared.infra.repository.worktime.common.JpaWorkTimezoneCommonSetGetMemento;

/**
 * The Class JpaFlowWorkSettingGetMemento.
 */
public class JpaFlowWorkSettingGetMemento implements FlowWorkSettingGetMemento {

	/** The entity. */
	private KshmtWtFlo entity;
	
	/**
	 * Instantiates a new jpa flow work setting get memento.
	 *
	 * @param entity the entity
	 */
	public JpaFlowWorkSettingGetMemento(KshmtWtFlo entity) {
		super();
		this.entity = entity;
	}
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.flowset.FlWorkSettingGetMemento#getCompanyId()
	 */
	@Override
	public String getCompanyId() {
		return this.entity.getKshmtWtFloPK().getCid();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.flowset.FlWorkSettingGetMemento#getWorkingCode()
	 */
	@Override
	public WorkTimeCode getWorkingCode() {
		return new WorkTimeCode(this.entity.getKshmtWtFloPK().getWorktimeCd());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.flowset.FlWorkSettingGetMemento#getRestSetting()
	 */
	@Override
	public FlowWorkRestSetting getRestSetting() {
		return new FlowWorkRestSetting(new JpaFlowWorkRestSettingGetMemento(this.entity.getKshmtWtFloBrFlAll()));
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.flowset.FlWorkSettingGetMemento#getOffdayWorkTimezone()
	 */
	@Override
	public FlowOffdayWorkTimezone getOffdayWorkTimezone() {
		return new FlowOffdayWorkTimezone(new JpaFlowOffdayWorkTimezoneGetMemento(this.entity));
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.flowset.FlWorkSettingGetMemento#getCommonSetting()
	 */
	@Override
	public WorkTimezoneCommonSet getCommonSetting() {
		KshmtWtCom commonEntity = this.entity.getKshmtWtCom();
		if (commonEntity == null) {
			return null;
		}
		return new WorkTimezoneCommonSet(new JpaWorkTimezoneCommonSetGetMemento(commonEntity));
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.flowset.FlWorkSettingGetMemento#getHalfDayWorkTimezone()
	 */
	@Override
	public FlowHalfDayWorkTimezone getHalfDayWorkTimezone() {
		return new FlowHalfDayWorkTimezone(new JpaFlowHalfDayWorkTimezoneGetMemento(this.entity));
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.flowset.FlWorkSettingGetMemento#getStampReflectTimezone()
	 */
	@Override
	public FlowStampReflectTimezone getStampReflectTimezone() {
		return new FlowStampReflectTimezone(new JpaFlowStampReflectTimezoneGetMemento(this.entity.getKshmtWtFloStmpRef2Ts()));
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.flowset.FlWorkSettingGetMemento#getLegalOTSetting()
	 */
	@Override
	public LegalOTSetting getLegalOTSetting() {
		return LegalOTSetting.valueOf(this.entity.getLegalOtSet());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.flowset.FlWorkSettingGetMemento#getFlowSetting()
	 */
	@Override
	public FlowWorkDedicateSetting getFlowSetting() {
		return new FlowWorkDedicateSetting(new JpaFlowWorkDedicateSettingGetMemento(this.entity));
	}

}
