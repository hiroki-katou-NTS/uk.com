/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.shift.basicworkregister;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.BasicWorkSetting;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.CompanyBasicWorkGetMemento;
import nts.uk.ctx.at.schedule.infra.entity.shift.basicworkregister.KscmtCompanyWorkSet;

/**
 * The Class JpaCompanyBasicWorkGetMemento.
 */
public class JpaCompanyBasicWorkGetMemento implements CompanyBasicWorkGetMemento {

	/** The type value. */
	private List<KscmtCompanyWorkSet> typeValue;

	/**
	 * Instantiates a new jpa company basic work get memento.
	 *
	 * @param typeValue
	 *            the type value
	 */
	public JpaCompanyBasicWorkGetMemento(List<KscmtCompanyWorkSet> typeValue) {
		super();
		this.typeValue = typeValue;
		if (this.typeValue == null) {
			this.typeValue = new ArrayList<>();
		}	
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.basicworkregister.
	 * CompanyBasicWorkGetMemento#getCompanyId()
	 */
	@Override
	public String getCompanyId() {
		return this.typeValue.get(0).getKscmtCompanyWorkSetPK().getCid();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.basicworkregister.
	 * CompanyBasicWorkGetMemento#getBasicWorkSetting()
	 */
	@Override
	public List<BasicWorkSetting> getBasicWorkSetting() {
		return this.typeValue.stream().map(entity -> {
			return new BasicWorkSetting(new JpaBWSettingComGetMemento(entity));
		}).collect(Collectors.toList());
	}

}
