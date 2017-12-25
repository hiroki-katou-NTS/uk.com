///******************************************************************
// * Copyright (c) 2017 Nittsu System to present.                   *
// * All right reserved.                                            *
// *****************************************************************/
//package nts.uk.ctx.at.shared.infra.repository.worktime.common;
//
//import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
//import nts.uk.ctx.at.shared.dom.worktime.common.TimeZoneRoundingGetMemento;
//import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtFlexWorkTimeSet;
//import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtFlexWorkTimeSetPK;
//import nts.uk.shr.com.time.TimeWithDayAttr;
//
///**
// * The Class JpaFlexEmTZSetTZRoundingGetMemento.
// */
//public class JpaFlexEmTZSetTZRoundingGetMemento implements TimeZoneRoundingGetMemento{
//	
//	/** The entity. */
//	private KshmtFlexWorkTimeSet entity;
//	
//	/**
//	 * Instantiates a new jpa flex em TZ set TZ rounding get memento.
//	 *
//	 * @param entity the entity
//	 */
//	public JpaFlexEmTZSetTZRoundingGetMemento(KshmtFlexWorkTimeSet entity) {
//		super();
//		if(entity.getKshmtFlexWorkTimeSetPK() == null){
//			entity.setKshmtFlexWorkTimeSetPK(new KshmtFlexWorkTimeSetPK());
//		}
//		this.entity = entity;
//	}
//
//	/**
//	 * Gets the rounding.
//	 *
//	 * @return the rounding
//	 */
//	@Override
//	public TimeRoundingSetting getRounding() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * Gets the start.
//	 *
//	 * @return the start
//	 */
//	@Override
//	public TimeWithDayAttr getStart() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * Gets the end.
//	 *
//	 * @return the end
//	 */
//	@Override
//	public TimeWithDayAttr getEnd() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//	
//
//}
