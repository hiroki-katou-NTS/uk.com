/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.worktime.common.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.worktime.common.FlowRestSetting;
import nts.uk.ctx.at.shared.dom.worktime.common.FlowRestTimezoneGetMemento;

/**
 * The Class FlowRestTimezoneDto.
 */
@Getter
@Setter
public class FlowRestTimezoneDto implements FlowRestTimezoneGetMemento {

	/** The flow rest set. */
	private List<FlowRestSettingDto> flowRestSets;

	/** The use here after rest set. */
	private boolean useHereAfterRestSet;

	/** The here after rest set. */
	private FlowRestSettingDto hereAfterRestSet;


	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.FlowRestTimezoneGetMemento#getFlowRestSet()
	 */
	@Override
	public List<FlowRestSetting> getFlowRestSet() {
		if (CollectionUtil.isEmpty(this.flowRestSets)) {
			return new ArrayList<>();
		}
		return this.flowRestSets.stream().map(dto -> new FlowRestSetting(dto)).collect(Collectors.toList());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.FlowRestTimezoneGetMemento#getUseHereAfterRestSet()
	 */
	@Override
	public boolean getUseHereAfterRestSet() {
		return this.useHereAfterRestSet;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.FlowRestTimezoneGetMemento#getHereAfterRestSet()
	 */
	@Override
	public FlowRestSetting getHereAfterRestSet() {
		return new FlowRestSetting(this.hereAfterRestSet);
	}
	
}
