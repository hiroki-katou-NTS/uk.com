/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.shift.basicworkregister;

import java.util.ArrayList;
import java.util.List;

import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.BasicWorkSetting;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.WorkplaceBasicWorkSetMemento;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.WorkplaceId;
import nts.uk.ctx.at.schedule.infra.entity.shift.basicworkregister.KwbmtWorkplaceWorkSet;

/**
 * The Class JpaWorkplaceBasicWorkSetMemento.
 */
public class JpaWorkplaceBasicWorkSetMemento implements WorkplaceBasicWorkSetMemento {

	/** The type value. */
	private List<KwbmtWorkplaceWorkSet> typeValue;

	/**
	 * Instantiates a new jpa workplace basic work set memento.
	 *
	 * @param typeValue
	 *            the type value
	 */
	public JpaWorkplaceBasicWorkSetMemento(List<KwbmtWorkplaceWorkSet> typeValue) {
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
	 * WorkplaceBasicWorkSetMemento#setWorkPlaceId(nts.uk.ctx.at.schedule.dom.
	 * shift.basicworkregister.WorkplaceId)
	 */
	@Override
	public void setWorkPlaceId(WorkplaceId workplaceId) {
		this.typeValue.stream().forEach(item -> {
			item.getKwbmtWorkplaceWorkSetPK().setWorkplaceId(workplaceId.v());
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.basicworkregister.
	 * WorkplaceBasicWorkSetMemento#setBasicWorkSetting(java.util.List)
	 */
	@Override
	public void setBasicWorkSetting(List<BasicWorkSetting> basicWorkSetting) {
		basicWorkSetting.stream().forEach(item -> {
			KwbmtWorkplaceWorkSet entity = new KwbmtWorkplaceWorkSet();
			entity.getKwbmtWorkplaceWorkSetPK().setWorkdayDivision(item.getWorkdayDivision().value);
			entity.setWorktypeCode(item.getWorktypeCode().v());
			entity.setWorkingCode(item.getWorkingCode().v());
			this.typeValue.add(entity);
		});
	}

}
