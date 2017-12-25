///******************************************************************
// * Copyright (c) 2017 Nittsu System to present.                   *
// * All right reserved.                                            *
// *****************************************************************/
//package nts.uk.ctx.at.shared.infra.repository.worktime.common;
//
//import nts.uk.ctx.at.shared.dom.worktime.common.BooleanGetAtr;
//import nts.uk.ctx.at.shared.dom.worktime.common.FlowRestTimezone;
//import nts.uk.ctx.at.shared.dom.worktime.common.FlowWorkRestTimezoneGetMemento;
//import nts.uk.ctx.at.shared.dom.worktime.common.TimezoneOfFixedRestTimeSet;
//import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtFlexArrayGroup;
//import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtFlexSetGroup;
//
///**
// * The Class JpaFlexODFlWRestTzGetMemento.
// */
//public class JpaFlexODFlWRestTzGetMemento implements FlowWorkRestTimezoneGetMemento{
//	
//	/** The entity array group. */
//	private KshmtFlexArrayGroup entityArrayGroup;
//	
//	/** The entity set group. */
//	private KshmtFlexSetGroup entitySetGroup;
//	
//	
//
//	/**
//	 * Instantiates a new jpa flex OD fl W rest tz get memento.
//	 *
//	 * @param entityArrayGroup the entity array group
//	 * @param entitySetGroup the entity set group
//	 */
//	public JpaFlexODFlWRestTzGetMemento(KshmtFlexArrayGroup entityArrayGroup, KshmtFlexSetGroup entitySetGroup) {
//		super();
//		this.entityArrayGroup = entityArrayGroup;
//		this.entitySetGroup = entitySetGroup;
//	}
//
//	/* (non-Javadoc)
//	 * @see nts.uk.ctx.at.shared.dom.worktime.common.FlowWorkRestTimezoneGetMemento#getFixRestTime()
//	 */
//	@Override
//	public boolean getFixRestTime() {
//		return BooleanGetAtr.getAtrByInteger(this.entitySetGroup.getEntityOffday().getFixRestTime());
//	}
//
//	/* (non-Javadoc)
//	 * @see nts.uk.ctx.at.shared.dom.worktime.common.FlowWorkRestTimezoneGetMemento#getFixedRestTimezone()
//	 */
//	@Override
//	public TimezoneOfFixedRestTimeSet getFixedRestTimezone() {
//		return new TimezoneOfFixedRestTimeSet(new JpaFlexODTzOFRTimeSetGetMemento(this.entityArrayGroup.getEntityFixedRests()));
//	}
//
//	/* (non-Javadoc)
//	 * @see nts.uk.ctx.at.shared.dom.worktime.common.FlowWorkRestTimezoneGetMemento#getFlowRestTimezone()
//	 */
//	@Override
//	public FlowRestTimezone getFlowRestTimezone() {
//		return new FlowRestTimezone(new JpaFlexODFlowRestTzGetMemento(this.entitySetGroup.getEntityOffday(),
//				this.entityArrayGroup.getEntityFlowRests()));
//	}
//
//}
