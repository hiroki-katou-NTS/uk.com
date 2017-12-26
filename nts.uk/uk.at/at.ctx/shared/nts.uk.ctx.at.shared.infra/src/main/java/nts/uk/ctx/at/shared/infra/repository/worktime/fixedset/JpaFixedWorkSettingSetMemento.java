/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.fixedset;

import java.util.List;

import nts.uk.ctx.at.shared.dom.worktime.common.BooleanGetAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.FixedWorkRestSet;
import nts.uk.ctx.at.shared.dom.worktime.common.LegalOTSetting;
import nts.uk.ctx.at.shared.dom.worktime.common.StampReflectTimezone;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSet;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixHalfDayWorkTimezone;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixOffdayWorkTimezone;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSettingSetMemento;
import nts.uk.ctx.at.shared.infra.entity.worktime.fixedset.KshmtFixedWorkSet;

/**
 * The Class JpaCoreTimeSettingGetMemento.
 */
public class JpaFixedWorkSettingSetMemento implements FixedWorkSettingSetMemento {

	private KshmtFixedWorkSet entity;

	public JpaFixedWorkSettingSetMemento(KshmtFixedWorkSet entity) {
		super();
		this.entity = entity;
	}
	
	@Override
	public void setCompanyId(String companyId) {
		this.entity.getKshmtFixedWorkSetPK().setCid(companyId);
	}

	@Override
	public void setWorkTimeCode(WorkTimeCode workTimeCode) {
		this.entity.getKshmtFixedWorkSetPK().setWorktimeCd(workTimeCode.v());
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
		this.entity.setUseHalfDay(BooleanGetAtr.getAtrByBoolean(useHalfDayShift));
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
		this.entity.setLegalOtSet(legalOTSetting.value);
	}


}
