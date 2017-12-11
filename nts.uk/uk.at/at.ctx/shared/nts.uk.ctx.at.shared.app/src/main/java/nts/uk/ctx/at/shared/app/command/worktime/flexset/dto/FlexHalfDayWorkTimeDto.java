/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.worktime.flexset.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.app.command.worktime.common.dto.FlowWorkRestTimezoneDto;
import nts.uk.ctx.at.shared.dom.worktime.common.AmPmAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.FixedWorkTimezoneSet;
import nts.uk.ctx.at.shared.dom.worktime.common.FlowWorkRestTimezone;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexHalfDayWorkTimeGetMemento;

/**
 * The Class FlexHalfDayWorkTimeDto.
 */
@Getter
@Setter
public class FlexHalfDayWorkTimeDto implements FlexHalfDayWorkTimeGetMemento{
	
	/** The lst rest timezone. */
	private List<FlowWorkRestTimezoneDto> lstRestTimezone;
	
	/** The work timezone. */
	private FixedWorkTimezoneSetDto workTimezone;
	
	/** The Am pm atr. */
	private Integer ampmAtr;

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.flexset.FlexHalfDayWorkTimeGetMemento#getLstRestTimezone()
	 */
	@Override
	public List<FlowWorkRestTimezone> getLstRestTimezone() {
		if(CollectionUtil.isEmpty(this.lstRestTimezone)){
			return new ArrayList<>();
		}
		return this.lstRestTimezone.stream().map(resttime -> new FlowWorkRestTimezone(resttime))
				.collect(Collectors.toList());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.flexset.FlexHalfDayWorkTimeGetMemento#getWorkTimezone()
	 */
	@Override
	public FixedWorkTimezoneSet getWorkTimezone() {
		return new FixedWorkTimezoneSet(this.workTimezone);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.flexset.FlexHalfDayWorkTimeGetMemento#getAmPmAtr()
	 */
	@Override
	public AmPmAtr getAmpmAtr() {
		return AmPmAtr.valueOf(this.ampmAtr);
	}
	

	
}
