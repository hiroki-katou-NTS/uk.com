///******************************************************************
// * Copyright (c) 2017 Nittsu System to present.                   *
// * All right reserved.                                            *
// *****************************************************************/
//package nts.uk.ctx.at.shared.infra.repository.worktime.common;
//
//import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
//import nts.uk.ctx.at.shared.dom.worktime.common.FlowRestSettingGetMemento;
//import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtFlexOdRestSet;
//
///**
// * The Class JpaFlexOffdayFlowRestGetMemento.
// */
//public class JpaFlexHAFlowRestGetMemento implements FlowRestSettingGetMemento{
//
//	/** The entity. */
//	private KshmtFlexOdRestSet entity;
//	
//	/**
//	 * Instantiates a new jpa flex offday flow rest get memento.
//	 *
//	 * @param entity the entity
//	 */
//	public JpaFlexHAFlowRestGetMemento(KshmtFlexOdRestSet entity) {
//		super();
//		this.entity = entity;
//	}
//	
//
//	/**
//	 * Gets the flow rest time.
//	 *
//	 * @return the flow rest time
//	 */
//	@Override
//	public AttendanceTime getFlowRestTime() {
//		return new AttendanceTime(this.entity.getFlowRestTime());
//	}
//
//	/**
//	 * Gets the flow passage time.
//	 *
//	 * @return the flow passage time
//	 */
//	@Override
//	public AttendanceTime getFlowPassageTime() {
//		return new AttendanceTime(this.entity.getFlowPassageTime());
//	}
//	
//
//}
