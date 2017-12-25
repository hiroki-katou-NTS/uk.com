/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.fixedset;

import java.util.List;

import nts.uk.ctx.at.shared.dom.worktime.common.FixedWorkRestSet;
import nts.uk.ctx.at.shared.dom.worktime.common.LegalOTSetting;
import nts.uk.ctx.at.shared.dom.worktime.common.StampReflectTimezone;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSet;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixHalfDayWorkTimezone;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixOffdayWorkTimezone;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSettingSetMemento;
import nts.uk.ctx.at.shared.infra.entity.worktime.KshmtFlexWorkSet;
import nts.uk.ctx.at.shared.infra.entity.worktime.KshmtFlexWorkSetPK;

/**
 * The Class JpaCoreTimeSettingGetMemento.
 */
public class JpaFixedWorkSettingSetMemento implements FixedWorkSettingSetMemento {
	// /** The entity. */
	// private KshmtFlexWorkSet entity;
	//
	// /**
	// * Instantiates a new jpa core time setting get memento.
	// *
	// * @param entity the entity
	// */
	// public JpaCoreTimeSettingGetMemento(KshmtFlexWorkSet entity) {
	// super();
	// if(entity.getKshmtFlexWorkSetPK() == null){
	// entity.setKshmtFlexWorkSetPK(new KshmtFlexWorkSetPK());
	// }
	// this.entity = entity;
	// }

	@Override
	public void setCompanyId(String companyId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setWorkTimeCode(WorkTimeCode workTimeCode) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setOffdayWorkTimezone(FixOffdayWorkTimezone offdayWorkTimezone) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setCommonSetting(WorkTimezoneCommonSet commonSetting) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setUseHalfDayShift(Boolean useHalfDayShift) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setFixedWorkRestSetting(FixedWorkRestSet fixedWorkRestSetting) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setLstHalfDayWorkTimezone(List<FixHalfDayWorkTimezone> lstHalfDayWorkTimezone) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setLstStampReflectTimezone(List<StampReflectTimezone> lstStampReflectTimezone) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setLegalOTSetting(LegalOTSetting legalOTSetting) {
		// TODO Auto-generated method stub
		
	}


}
