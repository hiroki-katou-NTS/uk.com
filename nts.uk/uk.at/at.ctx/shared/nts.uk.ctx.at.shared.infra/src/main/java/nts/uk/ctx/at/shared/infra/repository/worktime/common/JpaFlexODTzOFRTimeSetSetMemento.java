///******************************************************************
// * Copyright (c) 2017 Nittsu System to present.                   *
// * All right reserved.                                            *
// *****************************************************************/
//package nts.uk.ctx.at.shared.infra.repository.worktime.common;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.stream.Collectors;
//
//import nts.gul.collection.CollectionUtil;
//import nts.uk.ctx.at.shared.dom.worktime.common.DeductionTime;
//import nts.uk.ctx.at.shared.dom.worktime.common.TimezoneOfFixedRestTimeSetSetMemento;
//import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtFlexOdFixRest;
//
///**
// * The Class JpaFlexOffdayTzOFRTimeSetGetMemento.
// */
//public class JpaFlexODTzOFRTimeSetSetMemento implements TimezoneOfFixedRestTimeSetSetMemento{
//	
//	/** The entitys. */
//	private List<KshmtFlexOdFixRest> entitys;
//
//
//	/**
//	 * Instantiates a new jpa flex OD tz OFR time set get memento.
//	 *
//	 * @param entitys the entitys
//	 */
//	public JpaFlexODTzOFRTimeSetSetMemento(List<KshmtFlexOdFixRest> entitys) {
//		super();
//		this.entitys = entitys;
//	}
//
//
//	/* (non-Javadoc)
//	 * @see nts.uk.ctx.at.shared.dom.worktime.common.TimezoneOfFixedRestTimeSetSetMemento#setTimezones(java.util.List)
//	 */
//	@Override
//	public void setTimezones(List<DeductionTime> timzones) {
//		if (CollectionUtil.isEmpty(timzones)) {
//			this.entitys = new ArrayList<>();
//		} else {
//			this.entitys = timzones.stream().map(domain -> {
//				KshmtFlexOdFixRest entity = new KshmtFlexOdFixRest();
//				domain.saveToMemento(new JpaFlexODDeductionTimeSetMemento(entity));
//				return entity;
//			}).collect(Collectors.toList());
//		}
//
//	}
//
//}
