/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.ot.autocalsetting.unit;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.usecls.ApplyAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.use.UseUnitAutoCalSettingSetMemento;

/**
 * The Class UseUnitAutoCalSettingDto.
 */
@Setter
@Getter
public class UseUnitAutoCalSettingDto implements UseUnitAutoCalSettingSetMemento {

	/** The use job set. */
	// 職位の自動計算設定をする
	private boolean useJobSet;

	/** The use wkp set. */
	// 職場の自動計算設定をする
	private boolean useWkpSet;

	/** The use jobwkp set. */
	// 職場・職位の自動計算設定を行う
	private boolean useJobwkpSet;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.autocalsetting.
	 * UseUnitAutoCalSettingSetMemento#setCompanyId(nts.uk.ctx.at.shared.dom.
	 * common.CompanyId)
	 */
	@Override
	public void setCompanyId(CompanyId companyId) {
		// Do nothing
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.autocalsetting.
	 * UseUnitAutoCalSettingSetMemento#setUseJobSet(nts.uk.ctx.at.schedule.dom.
	 * shift.autocalsetting.UseClassification)
	 */
	@Override
	public void setUseJobSet(ApplyAtr useJobSet) {
		this.useJobSet = ApplyAtr.USE.equals(useJobSet);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.autocalsetting.
	 * UseUnitAutoCalSettingSetMemento#setUseWkpSet(nts.uk.ctx.at.schedule.dom.
	 * shift.autocalsetting.UseClassification)
	 */
	@Override
	public void setUseWkpSet(ApplyAtr useWkpSet) {
		this.useWkpSet = ApplyAtr.USE.equals(useWkpSet);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.autocalsetting.
	 * UseUnitAutoCalSettingSetMemento#setUseJobwkpSet(nts.uk.ctx.at.schedule.
	 * dom.shift.autocalsetting.UseClassification)
	 */
	@Override
	public void setUseJobwkpSet(ApplyAtr useJobwkpSet) {
		this.useJobwkpSet = ApplyAtr.USE.equals(useJobwkpSet);
	}

}
