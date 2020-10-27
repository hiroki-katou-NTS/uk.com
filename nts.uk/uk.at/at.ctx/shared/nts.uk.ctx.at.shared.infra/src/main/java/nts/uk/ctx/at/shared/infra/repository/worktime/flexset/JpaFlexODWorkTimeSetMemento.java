/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.flexset;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.worktime.common.HDWorkTimeSheetSetting;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexOffdayWorkTimeSetMemento;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkRestTimezone;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtWtFleHolTs;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtWtFleHolTsPK;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtWtFleBrFlHol;
import nts.uk.ctx.at.shared.infra.repository.worktime.common.JpaFlexODFlWRestTzSetMemento;
import nts.uk.ctx.at.shared.infra.repository.worktime.common.JpaFlexOffdayHDWTSheetSetMemento;

/**
 * The Class JpaFlexODWorkTimeGetMemento.
 */
public class JpaFlexODWorkTimeSetMemento implements FlexOffdayWorkTimeSetMemento{
	
	/** The entity. */
	private KshmtWtFleBrFlHol entity;
	

	/**
	 * Instantiates a new jpa flex OD work time set memento.
	 *
	 * @param entity the entity
	 */
	public JpaFlexODWorkTimeSetMemento(KshmtWtFleBrFlHol entity) {
		super();
		this.entity = entity;
		if (CollectionUtil.isEmpty(this.entity.getKshmtWtFleHolTss())) {
			this.entity.setKshmtWtFleHolTss(new ArrayList<>());
		}
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.flexset.FlexOffdayWorkTimeSetMemento#setLstWorkTimezone(java.util.List)
	 */
	@Override
	public void setLstWorkTimezone(List<HDWorkTimeSheetSetting> lstWorkTimezone) {

		if (CollectionUtil.isEmpty(lstWorkTimezone)) {
			this.entity.setKshmtWtFleHolTss(new ArrayList<>());
		} else {
			if (CollectionUtil.isEmpty(this.entity.getKshmtWtFleHolTss())) {
				this.entity.setKshmtWtFleHolTss(new ArrayList<>());
			}
			Map<KshmtWtFleHolTsPK, KshmtWtFleHolTs> entityMap = this.entity.getKshmtWtFleHolTss().stream()
					.collect(Collectors.toMap(KshmtWtFleHolTs::getKshmtWtFleHolTsPK, Function.identity()));
			List<KshmtWtFleHolTs> lstNewEntity = lstWorkTimezone.stream().map(domain -> {

				KshmtWtFleHolTsPK newPK = new KshmtWtFleHolTsPK(this.entity.getKshmtWtFleBrFlHolPK().getCid(),
						this.entity.getKshmtWtFleBrFlHolPK().getWorktimeCd(), domain.getWorkTimeNo());

				KshmtWtFleHolTs newEntity = new KshmtWtFleHolTs(newPK);

				KshmtWtFleHolTs oldEntity = entityMap.get(newPK);
				if (oldEntity != null) {
					// update
					newEntity = oldEntity;
				}
				// insert:
				domain.saveToMemento(new JpaFlexOffdayHDWTSheetSetMemento(newEntity));
				return newEntity;
			}).collect(Collectors.toList());

			this.entity.setKshmtWtFleHolTss(lstNewEntity);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.flexset.FlexOffdayWorkTimeSetMemento#
	 * setRestTimezone(nts.uk.ctx.at.shared.dom.worktime.common.
	 * FlowWorkRestTimezone)
	 */
	@Override
	public void setRestTimezone(FlowWorkRestTimezone restTimezone) {
		if (restTimezone != null) {
			restTimezone.saveToMemento(new JpaFlexODFlWRestTzSetMemento(this.entity));
		}
	}
	
}
