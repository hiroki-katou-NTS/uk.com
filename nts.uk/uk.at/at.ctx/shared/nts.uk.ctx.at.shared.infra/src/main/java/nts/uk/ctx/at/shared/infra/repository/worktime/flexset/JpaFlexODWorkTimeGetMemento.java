/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.flexset;

import java.util.List;

import nts.uk.ctx.at.shared.dom.worktime.common.FlowWorkRestTimezone;
import nts.uk.ctx.at.shared.dom.worktime.common.HDWorkTimeSheetSetting;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexOffdayWorkTimeGetMemento;
import nts.uk.ctx.at.shared.infra.entity.worktime.KshmtFlexOdRtSet;

/**
 * The Class JpaFlexODWorkTimeGetMemento.
 */
public class JpaFlexODWorkTimeGetMemento implements FlexOffdayWorkTimeGetMemento{
	
	/** The entity. */
	private KshmtFlexOdRtSet entity;
	
	/**
	 * Instantiates a new jpa flex OD work time get memento.
	 *
	 * @param entity the entity
	 */
	public JpaFlexODWorkTimeGetMemento(KshmtFlexOdRtSet entity) {
		super();
		this.entity = entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.flexset.FlexOffdayWorkTimeGetMemento#
	 * getLstWorkTimezone()
	 */
	@Override
	public List<HDWorkTimeSheetSetting> getLstWorkTimezone() {
		return null;
//		if (CollectionUtil.isEmpty(this.entityArrayGroup.getEntityWorktimezones())) {
//			return new ArrayList<>();
//		}
//		return this.entityArrayGroup.getEntityWorktimezones().stream()
//				.map(entity -> new HDWorkTimeSheetSetting(new JpaFlexODHDWTSheetGetMemento(entity)))
//				.collect(Collectors.toList());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.flexset.FlexOffdayWorkTimeGetMemento#getRestTimezone()
	 */
	@Override
	public FlowWorkRestTimezone getRestTimezone() {
		return null;
//		return new FlowWorkRestTimezone(new JpaFlexODFlWRestTzGetMemento(this.entityArrayGroup, this.entitySetGroup));
	}

}
