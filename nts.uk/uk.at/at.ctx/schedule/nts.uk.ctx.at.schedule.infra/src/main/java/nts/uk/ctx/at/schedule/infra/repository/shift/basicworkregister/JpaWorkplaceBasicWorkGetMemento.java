/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.shift.basicworkregister;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.BasicWorkSetting;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.WorkplaceBasicWorkGetMemento;
import nts.uk.ctx.at.schedule.infra.entity.shift.basicworkregister.KscmtBasicWorkWkp;

/**
 * The Class JpaWorkplaceBasicWorkGetMemento.
 */
public class JpaWorkplaceBasicWorkGetMemento implements WorkplaceBasicWorkGetMemento {

	/** The type value. */
	private List<KscmtBasicWorkWkp> typeValue;

	/**
	 * Instantiates a new jpa workplace basic work get memento.
	 *
	 * @param typeValue
	 *            the type value
	 */
	public JpaWorkplaceBasicWorkGetMemento(List<KscmtBasicWorkWkp> typeValue) {
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
	 * WorkplaceBasicWorkGetMemento#getWorkPlaceId()
	 */
	@Override
	public String getWorkPlaceId() {
		return this.typeValue.get(0).getKscmtBasicWorkWkpPK().getWorkplaceId();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.basicworkregister.
	 * WorkplaceBasicWorkGetMemento#getBasicWorkSetting()
	 */
	@Override
	public List<BasicWorkSetting> getBasicWorkSetting() {
		return this.typeValue.stream().map(entity -> {
			return new BasicWorkSetting(new JpaBWSettingWorkplaceGetMemento(entity));
		}).collect(Collectors.toList());
	}

}
