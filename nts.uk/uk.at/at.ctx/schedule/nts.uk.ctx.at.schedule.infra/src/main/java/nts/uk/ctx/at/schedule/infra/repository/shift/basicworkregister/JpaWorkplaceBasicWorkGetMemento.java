/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.shift.basicworkregister;

import java.util.List;
import java.util.stream.Collectors;

import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.BasicWorkSetting;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.WorkplaceBasicWorkGetMemento;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.WorkplaceId;
import nts.uk.ctx.at.schedule.infra.entity.shift.basicworkregister.KwbmtWorkplaceWorkSet;

/**
 * The Class JpaWorkplaceBasicWorkGetMemento.
 */
public class JpaWorkplaceBasicWorkGetMemento implements WorkplaceBasicWorkGetMemento {

	/** The type value. */
	private List<KwbmtWorkplaceWorkSet> typeValue;

	/**
	 * Instantiates a new jpa workplace basic work get memento.
	 *
	 * @param typeValue
	 *            the type value
	 */
	public JpaWorkplaceBasicWorkGetMemento(List<KwbmtWorkplaceWorkSet> typeValue) {
		super();
		this.typeValue = typeValue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.basicworkregister.
	 * WorkplaceBasicWorkGetMemento#getWorkPlaceId()
	 */
	@Override
	public WorkplaceId getWorkPlaceId() {
		return new WorkplaceId(this.typeValue.get(0).getKwbmtWorkplaceWorkSetPK().getWorkplaceId());
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
