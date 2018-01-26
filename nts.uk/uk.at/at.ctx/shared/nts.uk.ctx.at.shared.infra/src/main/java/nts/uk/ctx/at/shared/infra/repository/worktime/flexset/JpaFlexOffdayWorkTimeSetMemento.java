///******************************************************************
// * Copyright (c) 2017 Nittsu System to present.                   *
// * All right reserved.                                            *
// *****************************************************************/
//package nts.uk.ctx.at.shared.infra.repository.worktime.flexset;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.stream.Collectors;
//
//import nts.gul.collection.CollectionUtil;
//import nts.uk.ctx.at.shared.dom.worktime.common.FlowWorkRestTimezone;
//import nts.uk.ctx.at.shared.dom.worktime.common.HDWorkTimeSheetSetting;
//import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexOffdayWorkTimeSetMemento;
//import nts.uk.ctx.at.shared.infra.entity.worktime.KshmtFlexHolSet;
//import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtFlexHaFixRest;
//import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtFlexHaRestTime;
//import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtFlexOdRestSet;
//import nts.uk.ctx.at.shared.infra.repository.worktime.common.JpaFlexOffdayFlWRestTzSetMemento;
//import nts.uk.ctx.at.shared.infra.repository.worktime.common.JpaFlexOffdayHDWTSheetSetMemento;
//
///**
// * The Class JpaFlexOffdayWorkTimeGetMemento.
// */
//public class JpaFlexOffdayWorkTimeSetMemento implements FlexOffdayWorkTimeSetMemento{
//	
//	/** The entity worktimezones. */
//	private List<KshmtFlexHolSet> entityWorktimezones;
//	
//	/** The entity. */
//	private KshmtFlexHaRestTime entity;
//	
//	/** The entity fixed rests. */
//	private List<KshmtFlexHaFixRest> entityFixedRests;
//	
//	/** The entity flow rests. */
//	private List<KshmtFlexOdRestSet> entityFlowRests;
//	
//
//	/**
//	 * Instantiates a new jpa flex offday work time set memento.
//	 *
//	 * @param entityWorktimezones the entity worktimezones
//	 * @param entity the entity
//	 * @param entityFixedRests the entity fixed rests
//	 * @param entityFlowRests the entity flow rests
//	 */
//	public JpaFlexOffdayWorkTimeSetMemento(List<KshmtFlexHolSet> entityWorktimezones, KshmtFlexHaRestTime entity,
//			List<KshmtFlexHaFixRest> entityFixedRests, List<KshmtFlexOdRestSet> entityFlowRests) {
//		super();
//		this.entityWorktimezones = entityWorktimezones;
//		this.entity = entity;
//		this.entityFixedRests = entityFixedRests;
//		this.entityFlowRests = entityFlowRests;
//	}
//
//	/* (non-Javadoc)
//	 * @see nts.uk.ctx.at.shared.dom.worktime.flexset.FlexOffdayWorkTimeSetMemento#setLstWorkTimezone(java.util.List)
//	 */
//	@Override
//	public void setLstWorkTimezone(List<HDWorkTimeSheetSetting> lstWorkTimezone) {
//		if(CollectionUtil.isEmpty(lstWorkTimezone)){
//			this.entityWorktimezones = new ArrayList<>();
//		}
//		else {
//			this.entityWorktimezones = lstWorkTimezone.stream().map(domain -> {
//				KshmtFlexHolSet entity = new KshmtFlexHolSet();
//				domain.saveToMemento(new JpaFlexOffdayHDWTSheetSetMemento(entity));
//				return entity;
//			}).collect(Collectors.toList());
//		}
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see
//	 * nts.uk.ctx.at.shared.dom.worktime.flexset.FlexOffdayWorkTimeSetMemento#
//	 * setRestTimezone(nts.uk.ctx.at.shared.dom.worktime.common.
//	 * FlowWorkRestTimezone)
//	 */
//	@Override
//	public void setRestTimezone(FlowWorkRestTimezone restTimezone) {
//		restTimezone.saveToMemento(
//				new JpaFlexOffdayFlWRestTzSetMemento(this.entity, this.entityFixedRests, this.entityFlowRests));
//	}
//
//}
