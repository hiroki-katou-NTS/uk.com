/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.flexset;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.worktime.common.FlowWorkRestTimezone;
import nts.uk.ctx.at.shared.dom.worktime.common.HDWorkTimeSheetSetting;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexOffdayWorkTimeGetMemento;
import nts.uk.ctx.at.shared.infra.repository.worktime.common.JpaFlexODFlWRestTzGetMemento;
import nts.uk.ctx.at.shared.infra.repository.worktime.common.JpaFlexODHDWTSheetGetMemento;

/**
 * The Class JpaFlexODWorkTimeGetMemento.
 */
public class JpaFlexODWorkTimeGetMemento implements FlexOffdayWorkTimeGetMemento{
	
	/** The entity groub. */
	private KshmtFlexOdGroup entityGroub;
	

	/**
	 * Instantiates a new jpa flex OD work time get memento.
	 *
	 * @param entityGroub the entity groub
	 */
	public JpaFlexODWorkTimeGetMemento(KshmtFlexOdGroup entityGroub) {
		super();
		this.entityGroub = entityGroub;
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
		if (CollectionUtil.isEmpty(this.entityGroub.getEntityWorktimezones())) {
			return new ArrayList<>();
		}
		return this.entityGroub.getEntityWorktimezones().stream()
				.map(entity -> new HDWorkTimeSheetSetting(new JpaFlexODHDWTSheetGetMemento(entity)))
				.collect(Collectors.toList());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.flexset.FlexOffdayWorkTimeGetMemento#getRestTimezone()
	 */
	@Override
	public FlowWorkRestTimezone getRestTimezone() {
		return new FlowWorkRestTimezone(new JpaFlexODFlWRestTzGetMemento(this.entityGroub.getEntityOffday(),
				this.entityGroub.getEntityFixedRests(), this.entityGroub.getEntityFlowRests()));
	}

}
