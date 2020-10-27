/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.flexset;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.worktime.common.HDWorkTimeSheetSetting;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexOffdayWorkTimeGetMemento;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkRestTimezone;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtWtFleBrFlHol;
import nts.uk.ctx.at.shared.infra.repository.worktime.common.JpaFlexODFlWRestTzGetMemento;
import nts.uk.ctx.at.shared.infra.repository.worktime.common.JpaFlexODHDWTSheetGetMemento;

/**
 * The Class JpaFlexODWorkTimeGetMemento.
 */
public class JpaFlexODWorkTimeGetMemento implements FlexOffdayWorkTimeGetMemento{
	
	/** The entity. */
	private KshmtWtFleBrFlHol entity;
	
	/**
	 * Instantiates a new jpa flex OD work time get memento.
	 *
	 * @param entity the entity
	 */
	public JpaFlexODWorkTimeGetMemento(KshmtWtFleBrFlHol entity) {
		super();
		this.entity = entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.flexset.FlexOffdayWorkTimeGetMemento#
	 * getLstWorkTimezone()
	 */
	@Override
	public List<HDWorkTimeSheetSetting> getLstWorkTimezone() {
		if (CollectionUtil.isEmpty(this.entity.getKshmtWtFleHolTss())) {
			return new ArrayList<>();
		}
		return this.entity.getKshmtWtFleHolTss().stream()
				.map(entity -> new HDWorkTimeSheetSetting(new JpaFlexODHDWTSheetGetMemento(entity)))
				.collect(Collectors.toList());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.flexset.FlexOffdayWorkTimeGetMemento#getRestTimezone()
	 */
	@Override
	public FlowWorkRestTimezone getRestTimezone() {
		return new FlowWorkRestTimezone(new JpaFlexODFlWRestTzGetMemento(this.entity));
	}

}
