/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.shift.basicworkregister;

import java.util.ArrayList;
import java.util.List;

import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.BasicWorkSetting;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.WorkplaceBasicWorkSetMemento;
import nts.uk.ctx.at.schedule.infra.entity.shift.basicworkregister.KscmtWorkplaceWorkSet;

/**
 * The Class JpaWorkplaceBasicWorkSetMemento.
 */
public class JpaWorkplaceBasicWorkSetMemento implements WorkplaceBasicWorkSetMemento {

	/** The type value. */
	private List<KscmtWorkplaceWorkSet> typeValue;

	/**
	 * Instantiates a new jpa workplace basic work set memento.
	 *
	 * @param typeValue
	 *            the type value
	 */
	public JpaWorkplaceBasicWorkSetMemento(List<KscmtWorkplaceWorkSet> typeValue) {
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
	public void setWorkPlaceId(String workplaceId) {
		this.typeValue.stream().forEach(item -> {
			item.getKscmtWorkplaceWorkSetPK().setWorkplaceId(workplaceId);
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
			KscmtWorkplaceWorkSet entity = new KscmtWorkplaceWorkSet();
			entity.getKscmtWorkplaceWorkSetPK().setWorkdayDivision(item.getWorkdayDivision().value);
			entity.setWorktypeCode(item.getWorktypeCode().v());
			entity.setWorkingCode(item.getWorkingCode().v());
			this.typeValue.add(entity);
		});
	}

}
