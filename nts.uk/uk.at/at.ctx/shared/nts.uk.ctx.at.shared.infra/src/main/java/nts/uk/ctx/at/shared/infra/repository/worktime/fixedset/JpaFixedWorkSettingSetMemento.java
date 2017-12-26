/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.fixedset;

import java.util.ArrayList;
import java.util.List;

import nts.uk.ctx.at.shared.dom.worktime.common.FixedWorkRestSet;
import nts.uk.ctx.at.shared.dom.worktime.common.LegalOTSetting;
import nts.uk.ctx.at.shared.dom.worktime.common.StampReflectTimezone;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSet;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixHalfDayWorkTimezone;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixOffdayWorkTimezone;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSettingSetMemento;
import nts.uk.ctx.at.shared.infra.entity.worktime.fixedset.KshmtFixedStampReflect;
import nts.uk.ctx.at.shared.infra.entity.worktime.fixedset.KshmtFixedStampReflectPK;
import nts.uk.ctx.at.shared.infra.entity.worktime.fixedset.KshmtFixedWorkSet;
import nts.uk.ctx.at.shared.infra.entity.worktime.fixedset.KshmtFixedWorkSetPK;

/**
 * The Class JpaFixedWorkSettingSetMemento.
 */
public class JpaFixedWorkSettingSetMemento implements FixedWorkSettingSetMemento {

	/** The entity. */
	private KshmtFixedWorkSet entity;

	/**
	 * Instantiates a new jpa fixed work setting set memento.
	 *
	 * @param entity the entity
	 */
	public JpaFixedWorkSettingSetMemento(KshmtFixedWorkSet entity) {
		super();
		if (entity.getKshmtFixedWorkSetPK() == null) {
			entity.setKshmtFixedWorkSetPK(new KshmtFixedWorkSetPK());
		}
		this.entity = entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSettingSetMemento#
	 * setCompanyId(java.lang.String)
	 */
	@Override
	public void setCompanyId(String companyId) {
		this.entity.getKshmtFixedWorkSetPK().setCid(companyId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSettingSetMemento#
	 * setWorkTimeCode(nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode)
	 */
	@Override
	public void setWorkTimeCode(WorkTimeCode workTimeCode) {
		this.entity.getKshmtFixedWorkSetPK().setWorktimeCd(workTimeCode.v());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSettingSetMemento#
	 * setOffdayWorkTimezone(nts.uk.ctx.at.shared.dom.worktime.fixedset.
	 * FixOffdayWorkTimezone)
	 */
	@Override
	public void setOffdayWorkTimezone(FixOffdayWorkTimezone offdayWorkTimezone) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSettingSetMemento#
	 * setCommonSetting(nts.uk.ctx.at.shared.dom.worktime.common.
	 * WorkTimezoneCommonSet)
	 */
	@Override
	public void setCommonSetting(WorkTimezoneCommonSet commonSetting) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSettingSetMemento#
	 * setUseHalfDayShift(java.lang.Boolean)
	 */
	@Override
	public void setUseHalfDayShift(Boolean useHalfDayShift) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSettingSetMemento#
	 * setFixedWorkRestSetting(nts.uk.ctx.at.shared.dom.worktime.common.
	 * FixedWorkRestSet)
	 */
	@Override
	public void setFixedWorkRestSetting(FixedWorkRestSet fixedWorkRestSetting) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSettingSetMemento#
	 * setLstHalfDayWorkTimezone(java.util.List)
	 */
	@Override
	public void setLstHalfDayWorkTimezone(List<FixHalfDayWorkTimezone> lstHalfDayWorkTimezone) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSettingSetMemento#
	 * setLstStampReflectTimezone(java.util.List)
	 */
	@Override
	public void setLstStampReflectTimezone(List<StampReflectTimezone> lstStampReflectTimezone) {
		
		String companyId = this.entity.getKshmtFixedWorkSetPK().getCid();
		String workTimeCd = this.entity.getKshmtFixedWorkSetPK().getWorktimeCd();
		
		// get list entity
		List<KshmtFixedStampReflect> lstStampReflect = this.entity.getLstKshmtFixedStampReflect();
		
		List<KshmtFixedStampReflect> newListStampReflect = new ArrayList<>();
		
		for (StampReflectTimezone stampRelect : lstStampReflectTimezone) {
			
			// get entity existed
			KshmtFixedStampReflect entity = lstStampReflect.stream().filter(item -> {
						KshmtFixedStampReflectPK pk = item.getKshmtFixedStampReflectPK();
						return pk.getCid().compareTo(companyId) == 0 && pk.getWorktimeCd().compareTo(workTimeCd) == 0
								&& pk.getWorkNo() == stampRelect.getWorkNo().v().intValue();
					})
					.findFirst()
					.orElse(new KshmtFixedStampReflect());
			
			stampRelect.saveToMemento(new JpaFixedStampReflectTimezoneSetMemento(companyId, workTimeCd, entity));
			newListStampReflect.add(entity);
		}
		this.entity.setLstKshmtFixedStampReflect(newListStampReflect);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSettingSetMemento#
	 * setLegalOTSetting(nts.uk.ctx.at.shared.dom.worktime.common.
	 * LegalOTSetting)
	 */
	@Override
	public void setLegalOTSetting(LegalOTSetting legalOTSetting) {
		// TODO Auto-generated method stub

	}

}
