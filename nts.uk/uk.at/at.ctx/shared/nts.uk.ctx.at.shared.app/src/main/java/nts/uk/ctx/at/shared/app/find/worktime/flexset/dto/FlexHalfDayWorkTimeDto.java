/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.flexset.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.app.find.worktime.common.dto.FlowWorkRestTimezoneDto;
import nts.uk.ctx.at.shared.dom.worktime.common.AmPmAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.FixedWorkTimezoneSet;
import nts.uk.ctx.at.shared.dom.worktime.common.FlowWorkRestTimezone;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexHalfDayWorkTimeSetMemento;

/**
 * The Class FlexHalfDayWorkTimeDto.
 */
@Getter
@Setter
public class FlexHalfDayWorkTimeDto implements FlexHalfDayWorkTimeSetMemento{
	
	/** The lst rest timezone. */
	private List<FlowWorkRestTimezoneDto> lstRestTimezone;
	
	/** The work timezone. */
	private FixedWorkTimezoneSetDto workTimezone;
	
	/** The ampm atr. */
	private Integer ampmAtr;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.flexset.FlexHalfDayWorkTimeSetMemento#
	 * setLstRestTimezone(java.util.List)
	 */
	@Override
	public void setLstRestTimezone(List<FlowWorkRestTimezone> lstRestTimezone) {
		if (CollectionUtil.isEmpty(lstRestTimezone)) {
			this.lstRestTimezone = new ArrayList<>();
		} else {
			this.lstRestTimezone = lstRestTimezone.stream().map(domain -> {
				FlowWorkRestTimezoneDto dto = new FlowWorkRestTimezoneDto();
				domain.saveToMemento(dto);
				return dto;
			}).collect(Collectors.toList());
		}
	}

	/**
	 * Sets the lst work timezone.
	 *
	 * @param workTimezone the new lst work timezone
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.flexset.FlexHalfDayWorkTimeSetMemento#
	 * setLstWorkTimezone(nts.uk.ctx.at.shared.dom.worktime.fixedset.
	 * FixedWorkTimezoneSet)
	 */
	@Override
	public void setWorkTimezone(FixedWorkTimezoneSet workTimezone) {
		workTimezone.saveToMemento(this.workTimezone);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.flexset.FlexHalfDayWorkTimeSetMemento#
	 * setAmPmAtr(nts.uk.ctx.at.shared.dom.worktime.common.AmPmAtr)
	 */
	@Override
	public void setAmpmAtr(AmPmAtr ampmAtr) {
		this.ampmAtr = ampmAtr.value;
	}

	
}
