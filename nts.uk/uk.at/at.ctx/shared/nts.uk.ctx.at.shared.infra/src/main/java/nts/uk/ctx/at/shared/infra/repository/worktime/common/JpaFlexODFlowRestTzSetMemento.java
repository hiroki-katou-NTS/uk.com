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
//import nts.uk.ctx.at.shared.dom.worktime.common.BooleanGetAtr;
//import nts.uk.ctx.at.shared.dom.worktime.common.FlowRestSetting;
//import nts.uk.ctx.at.shared.dom.worktime.common.FlowRestTimezoneSetMemento;
//import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtFlexOdRestSet;
//import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtFlexOdRestTime;
//
///**
// * The Class JpaFlexODFlowRestTzSetMemento.
// */
//public class JpaFlexODFlowRestTzSetMemento implements FlowRestTimezoneSetMemento{
//	
//	/** The entity. */
//	private KshmtFlexOdRestTime entity;
//	
//	/** The entity flow rests. */
//	private List<KshmtFlexOdRestSet> entityFlowRests;
//
//	/**
//	 * Instantiates a new jpa flex OD flow rest tz set memento.
//	 *
//	 * @param entity the entity
//	 * @param entityFlowRests the entity flow rests
//	 */
//	public JpaFlexODFlowRestTzSetMemento(KshmtFlexOdRestTime entity, List<KshmtFlexOdRestSet> entityFlowRests) {
//		super();
//		this.entity = entity;
//		this.entityFlowRests = entityFlowRests;
//	}
//
//
//
//	/* (non-Javadoc)
//	 * @see nts.uk.ctx.at.shared.dom.worktime.common.FlowRestTimezoneSetMemento#setFlowRestSet(java.util.List)
//	 */
//	@Override
//	public void setFlowRestSet(List<FlowRestSetting> set) {
//		if (CollectionUtil.isEmpty(set)) {
//			this.entityFlowRests = new ArrayList<>();
//		} else {
//			this.entityFlowRests = set.stream().map(domain -> {
//				KshmtFlexOdRestSet entity = new KshmtFlexOdRestSet();
//				domain.saveToMemento(new JpaFlexHAFlowRestSetMemento(entity));
//				return entity;
//			}).collect(Collectors.toList());
//		}
//	}
//
//	/* (non-Javadoc)
//	 * @see nts.uk.ctx.at.shared.dom.worktime.common.FlowRestTimezoneSetMemento#setUseHereAfterRestSet(boolean)
//	 */
//	@Override
//	public void setUseHereAfterRestSet(boolean val) {
//		this.entity.setUseRestAfterSet(BooleanGetAtr.getAtrByBoolean(val));
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see nts.uk.ctx.at.shared.dom.worktime.common.FlowRestTimezoneSetMemento#
//	 * setHereAfterRestSet(nts.uk.ctx.at.shared.dom.worktime.common.
//	 * FlowRestSetting)
//	 */
//	@Override
//	public void setHereAfterRestSet(FlowRestSetting set) {
//		if (set != null) {
//			set.saveToMemento(new JpaFlexODFlowRestSettingSetMemento(this.entity));
//		}
//
//	}
//	
//
//}
