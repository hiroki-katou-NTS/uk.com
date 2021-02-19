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
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.app.command.worktime.common.dto.RoundingTimeDto;
import nts.uk.ctx.at.shared.dom.worktime.common.PrioritySetting;
import nts.uk.ctx.at.shared.dom.worktime.common.RoundingSet;
import nts.uk.ctx.at.shared.dom.worktime.common.RoundingTime;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneStampSetSetMemento;

/**
 * The Class WorkTimezoneStampSetDto.
 */
@Getter
@Setter
public class WorkTimezoneStampSetDto implements WorkTimezoneStampSetSetMemento{
	
	/** The rounding Time. */
	private RoundingTimeDto roundingTime;
	
	/** The priority set. */
	private List<PrioritySettingDto> prioritySets;

	public WorkTimezoneStampSetDto() {
		this.roundingTime = new RoundingTimeDto();
		this.prioritySets = new ArrayList<>();
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneStampSetSetMemento#
	 * setRoundingSet(java.util.List)
	 */

	//@Override
//	public void setRoundingSet(List<RoundingSet> rdSet) {
//		if (CollectionUtil.isEmpty(rdSet)) {
//			return;
//		}
//		this.roundingSets = rdSet.stream().map(domain->{
//			RoundingSetDto dto = new RoundingSetDto();
//			domain.saveToMemento(dto);
//			return dto;
//		}).collect(Collectors.toList());
//	}
	
	
	@Override
	public void setRoundingTime(RoundingTime rdSet) {
		if (rdSet != null) {
			this.roundingTime = new RoundingTimeDto();
			rdSet.saveToMemento(this.roundingTime);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneStampSetSetMemento#
	 * setPrioritySet(java.util.List)
	 */
	@Override
	public void setPrioritySet(List<PrioritySetting> prSet) {
		if (CollectionUtil.isEmpty(prSet)) {
			return;
		}
		this.prioritySets = prSet.stream().map(domain->{
			PrioritySettingDto dto = new PrioritySettingDto();
			domain.saveToMemento(dto);
			return dto;
		}).collect(Collectors.toList());
	}	

}
