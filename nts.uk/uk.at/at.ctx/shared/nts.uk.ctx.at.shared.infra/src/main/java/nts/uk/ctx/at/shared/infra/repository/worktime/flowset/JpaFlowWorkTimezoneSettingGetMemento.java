/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.flowset;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlWtzSettingGetMemento;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowOTTimezone;
import nts.uk.ctx.at.shared.infra.entity.worktime.flowset.KshmtWtFloWorkTs;
import nts.uk.ctx.at.shared.infra.entity.worktime.flowset.KshmtWtFlo;

/**
 * The Class JpaFlowWorkTimezoneSettingGetMemento.
 */
public class JpaFlowWorkTimezoneSettingGetMemento implements FlWtzSettingGetMemento {
	
	/** The entity. */
	private KshmtWtFlo entity;
	
	/**
	 * Instantiates a new jpa flow work timezone setting get memento.
	 *
	 * @param entity the entity
	 */
	public JpaFlowWorkTimezoneSettingGetMemento(KshmtWtFlo entity) {
		super();
		this.entity = entity;	
		if (CollectionUtil.isEmpty(this.entity.getLstKshmtWtFloOverTs())) {
			this.entity.setLstKshmtWtFloOverTs(new ArrayList<>());
		}
	}
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.flowset.FlWtzSettingGetMemento#getWorkTimeRounding()
	 */
	@Override
	public TimeRoundingSetting getWorkTimeRounding() {
		KshmtWtFloWorkTs kshmtWtFloWorkTs = this.entity.getKshmtWtFloWorkTs();
		return new TimeRoundingSetting(kshmtWtFloWorkTs.getUnit(), kshmtWtFloWorkTs.getRounding());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.flowset.FlWtzSettingGetMemento#getLstOTTimezone()
	 */
	@Override
	public List<FlowOTTimezone> getLstOTTimezone() {
		return this.entity.getLstKshmtWtFloOverTs().stream()
				.map(entity -> new FlowOTTimezone(new JpaFlowOTTimezoneGetMemento(entity)))
				.sorted((item1, item2) -> item1.getWorktimeNo().compareTo(item2.getWorktimeNo()))
				.collect(Collectors.toList());
	}

}
