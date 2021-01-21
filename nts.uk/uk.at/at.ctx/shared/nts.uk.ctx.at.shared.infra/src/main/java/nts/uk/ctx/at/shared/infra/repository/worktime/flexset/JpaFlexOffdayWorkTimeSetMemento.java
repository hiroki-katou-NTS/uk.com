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
//import nts.uk.ctx.at.shared.infra.entity.worktime.KshmtWtFleHolTs;
//import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtWtFleBrFiWekTs;
//import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtFlexHaRestTime;
//import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtWtFleBrFlHolTs;
//import nts.uk.ctx.at.shared.infra.repository.worktime.common.JpaFlexOffdayFlWRestTzSetMemento;
//import nts.uk.ctx.at.shared.infra.repository.worktime.common.JpaFlexOffdayHDWTSheetSetMemento;
//
///**
// * The Class JpaFlexOffdayWorkTimeGetMemento.
// */
//public class JpaFlexOffdayWorkTimeSetMemento implements FlexOffdayWorkTimeSetMemento{
//	
//	/** The entity worktimezones. */
//	private List<KshmtWtFleHolTs> entityWorktimezones;
//	
//	/** The entity. */
//	private KshmtFlexHaRestTime entity;
//	
//	/** The entity fixed rests. */
//	private List<KshmtWtFleBrFiWekTs> entityFixedRests;
//	
//	/** The entity flow rests. */
//	private List<KshmtWtFleBrFlHolTs> entityFlowRests;
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
//	public JpaFlexOffdayWorkTimeSetMemento(List<KshmtWtFleHolTs> entityWorktimezones, KshmtFlexHaRestTime entity,
//			List<KshmtWtFleBrFiWekTs> entityFixedRests, List<KshmtWtFleBrFlHolTs> entityFlowRests) {
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
//				KshmtWtFleHolTs entity = new KshmtWtFleHolTs();
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
