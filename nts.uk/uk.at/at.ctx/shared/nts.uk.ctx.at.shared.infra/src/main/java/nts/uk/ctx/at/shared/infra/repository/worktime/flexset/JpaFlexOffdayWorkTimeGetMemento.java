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
//import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexOffdayWorkTimeGetMemento;
//import nts.uk.ctx.at.shared.infra.entity.worktime.KshmtWtFleHolTs;
//import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtWtFleBrFiWekTs;
//import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtWtFleBrFlHolTs;
//import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtFlexOdRestTime;
//import nts.uk.ctx.at.shared.infra.repository.worktime.common.JpaFlexOffdayFlWRestTzGetMemento;
//import nts.uk.ctx.at.shared.infra.repository.worktime.common.JpaFlexOffdayHDWTSheetGetMemento;
//
///**
// * The Class JpaFlexOffdayWorkTimeGetMemento.
// */
//public class JpaFlexOffdayWorkTimeGetMemento implements FlexOffdayWorkTimeGetMemento{
//	
//	/** The entity worktimezones. */
//	private List<KshmtWtFleHolTs> entityWorktimezones;
//	
//	/** The entity. */
//	private KshmtFlexOdRestTime entity;
//	
//	/** The entity fixed rests. */
//	private List<KshmtWtFleBrFiWekTs> entityFixedRests;
//	
//	/** The entity flow rests. */
//	private List<KshmtWtFleBrFlHolTs> entityFlowRests;
//	
//
//	/**
//	 * Instantiates a new jpa flex offday work time get memento.
//	 *
//	 * @param entityWorktimezones the entity worktimezones
//	 * @param entity the entity
//	 * @param entityFixedRests the entity fixed rests
//	 * @param entityFlowRests the entity flow rests
//	 */
//	public JpaFlexOffdayWorkTimeGetMemento(List<KshmtWtFleHolTs> entityWorktimezones, KshmtFlexOdRestTime entity,
//			List<KshmtWtFleBrFiWekTs> entityFixedRests, List<KshmtWtFleBrFlHolTs> entityFlowRests) {
//		super();
//		this.entityWorktimezones = entityWorktimezones;
//		this.entity = entity;
//		this.entityFixedRests = entityFixedRests;
//		this.entityFlowRests = entityFlowRests;
//	}
//
//	/* (non-Javadoc)
//	 * @see nts.uk.ctx.at.shared.dom.worktime.flexset.FlexOffdayWorkTimeGetMemento#getLstWorkTimezone()
//	 */
//	@Override
//	public List<HDWorkTimeSheetSetting> getLstWorkTimezone() {
//		if (CollectionUtil.isEmpty(this.entityWorktimezones)) {
//			return new ArrayList<>();
//		}
//		return this.entityWorktimezones.stream()
//				.map(entity -> new HDWorkTimeSheetSetting(new JpaFlexOffdayHDWTSheetGetMemento(entity)))
//				.collect(Collectors.toList());
//	}
//
//	/* (non-Javadoc)
//	 * @see nts.uk.ctx.at.shared.dom.worktime.flexset.FlexOffdayWorkTimeGetMemento#getRestTimezone()
//	 */
//	@Override
//	public FlowWorkRestTimezone getRestTimezone() {
//		return new FlowWorkRestTimezone(new JpaFlexOffdayFlWRestTzGetMemento(this.entity, this.entityFixedRests, this.entityFlowRests));
//	}
//
//}
