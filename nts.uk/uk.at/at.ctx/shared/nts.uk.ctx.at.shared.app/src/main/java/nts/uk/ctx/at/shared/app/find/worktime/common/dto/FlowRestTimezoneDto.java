/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.common.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowRestSetting;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowRestTimezoneSetMemento;

/**
 * The Class FlowRestTimezoneDto.
 */
@Getter
@Setter
public class FlowRestTimezoneDto implements FlowRestTimezoneSetMemento {

	/** The flow rest sets. */
	private List<FlowRestSettingDto> flowRestSets;

	/** The use here after rest set. */
	private boolean useHereAfterRestSet;

	/** The here after rest set. */
	private FlowRestSettingDto hereAfterRestSet;
	
	/**
	 * Instantiates a new flow rest timezone dto.
	 */
	public FlowRestTimezoneDto() {
		this.flowRestSets = new ArrayList<>();
		this.hereAfterRestSet = new FlowRestSettingDto();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.FlowRestTimezoneSetMemento#
	 * setFlowRestSet(java.util.List)
	 */
	@Override
	public void setFlowRestSet(List<FlowRestSetting> set) {
		this.flowRestSets = set.stream().map(item -> {
			FlowRestSettingDto dto = new FlowRestSettingDto();
			item.saveToMemento(dto);
			return dto;
		}).collect(Collectors.toList());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.FlowRestTimezoneSetMemento#
	 * setUseHereAfterRestSet(boolean)
	 */
	@Override
	public void setUseHereAfterRestSet(boolean val) {
		this.useHereAfterRestSet = val;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.FlowRestTimezoneSetMemento#
	 * setHereAfterRestSet(nts.uk.ctx.at.shared.dom.worktime.common.
	 * FlowRestSetting)
	 */
	@Override
	public void setHereAfterRestSet(FlowRestSetting set) {
		if (set != null) {
			this.hereAfterRestSet = new FlowRestSettingDto();
			set.saveToMemento(this.hereAfterRestSet);
		}
	}
}
