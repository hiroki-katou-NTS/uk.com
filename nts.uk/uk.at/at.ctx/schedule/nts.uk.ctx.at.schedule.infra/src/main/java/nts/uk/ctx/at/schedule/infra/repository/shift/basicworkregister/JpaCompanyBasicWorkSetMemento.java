/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.shift.basicworkregister;

import java.util.ArrayList;
import java.util.List;

import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.BasicWorkSetting;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.CompanyBasicWorkSetMemento;
import nts.uk.ctx.at.schedule.infra.entity.shift.basicworkregister.KscmtBasicWorkCom;

/**
 * The Class JpaCompanyBasicWorkSetMemento.
 */
public class JpaCompanyBasicWorkSetMemento implements CompanyBasicWorkSetMemento {

	/** The type value. */
	private List<KscmtBasicWorkCom> typeValue;

	/**
	 * Instantiates a new jpa company basic work set memento.
	 *
	 * @param typeValue
	 *            the type value
	 */
	public JpaCompanyBasicWorkSetMemento(List<KscmtBasicWorkCom> typeValue) {
		super();
		if (this.typeValue == null) {
			this.typeValue = new ArrayList<>();
		}
		this.typeValue = typeValue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.basicworkregister.
	 * CompanyBasicWorkSetMemento#setCompanyId(nts.uk.ctx.at.schedule.dom.shift.
	 * basicworkregister.CompanyId)
	 */
	@Override
	public void setCompanyId(String companyId) {
		this.typeValue.stream().forEach(item -> {
			item.getKscmtBasicWorkComPK().setCid(companyId);
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.basicworkregister.
	 * CompanyBasicWorkSetMemento#setBasicWorkSetting(java.util.List)
	 */
	@Override
	public void setBasicWorkSetting(List<BasicWorkSetting> basicWorkSetting) {
		basicWorkSetting.stream().forEach(item -> {
			KscmtBasicWorkCom entity = new KscmtBasicWorkCom();
			entity.getKscmtBasicWorkComPK().setWorkdayDivision(item.getWorkdayDivision().value);
			entity.setWorktypeCode(item.getWorktypeCode().v());
			entity.setWorkingCode(item.getWorkingCode().v());
			this.typeValue.add(entity);
		});
	}

}
