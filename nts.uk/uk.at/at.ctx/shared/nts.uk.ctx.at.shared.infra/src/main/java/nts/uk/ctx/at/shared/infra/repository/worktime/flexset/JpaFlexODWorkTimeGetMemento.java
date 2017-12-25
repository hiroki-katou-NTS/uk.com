/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.flexset;

import java.util.List;

import nts.uk.ctx.at.shared.dom.worktime.common.FlowWorkRestTimezone;
import nts.uk.ctx.at.shared.dom.worktime.common.HDWorkTimeSheetSetting;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexOffdayWorkTimeGetMemento;

/**
 * The Class JpaFlexODWorkTimeGetMemento.
 */
public class JpaFlexODWorkTimeGetMemento implements FlexOffdayWorkTimeGetMemento{
	
//	/** The entity array group. */
//	private KshmtFlexArrayGroup entityArrayGroup;
//	
//	/** The entity set group. */
//	private KshmtFlexSetGroup entitySetGroup;
//	
//	/**
//	 * Instantiates a new jpa flex OD work time get memento.
//	 *
//	 * @param entityArrayGroup the entity array group
//	 * @param entitySetGroup the entity set group
//	 */
//	public JpaFlexODWorkTimeGetMemento(KshmtFlexArrayGroup entityArrayGroup, KshmtFlexSetGroup entitySetGroup) {
//		super();
//		this.entityArrayGroup = entityArrayGroup;
//		this.entitySetGroup = entitySetGroup;
//	}

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
