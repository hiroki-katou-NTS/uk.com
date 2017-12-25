/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.fixedset;

import java.util.List;

import nts.uk.ctx.at.shared.dom.worktime.common.FixedWorkRestSet;
import nts.uk.ctx.at.shared.dom.worktime.common.HDWorkTimeSheetSetting;
import nts.uk.ctx.at.shared.dom.worktime.common.LegalOTSetting;
import nts.uk.ctx.at.shared.dom.worktime.common.StampReflectTimezone;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSet;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixHalfDayWorkTimezone;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixOffdayWorkTimezone;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixOffdayWorkTimezoneGetMemento;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixRestTimezoneSet;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSettingGetMemento;
import nts.uk.ctx.at.shared.infra.entity.worktime.fixedset.KshmtFixedWorkSet;
import nts.uk.ctx.at.shared.infra.entity.worktime.fixedset.KshmtFixedWorkSetPK;

/**
 * The Class JpaCoreTimeSettingGetMemento.
 */
public class JpaFixedWorkSettingGetMemento implements FixedWorkSettingGetMemento {

	/** The entity. */
	private KshmtFixedWorkSet entity;

	/**
	 * Instantiates a new jpa fixed work setting get memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaFixedWorkSettingGetMemento(KshmtFixedWorkSet entity) {
		super();
		this.entity = entity;
	}

	@Override
	public String getCompanyId() {
		return this.entity.getKshmtFixedWorkSetPK().getCid();
	}

	@Override
	public WorkTimeCode getWorkTimeCode() {
		return new WorkTimeCode(this.entity.getKshmtFixedWorkSetPK().getWorktimeCd());
	}

	@Override
	public FixOffdayWorkTimezone getOffdayWorkTimezone() {
		return null;
//		return new FixOffdayWorkTimezone(new JpaFixOffdayWorkTimezoneGetMemento())
	}

	@Override
	public WorkTimezoneCommonSet getCommonSetting() {
		return null;
	}

	@Override
	public Boolean getUseHalfDayShift() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FixedWorkRestSet getFixedWorkRestSetting() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<FixHalfDayWorkTimezone> getLstHalfDayWorkTimezone() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<StampReflectTimezone> getLstStampReflectTimezone() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LegalOTSetting getLegalOTSetting() {
		// TODO Auto-generated method stub
		return null;
	}

}
