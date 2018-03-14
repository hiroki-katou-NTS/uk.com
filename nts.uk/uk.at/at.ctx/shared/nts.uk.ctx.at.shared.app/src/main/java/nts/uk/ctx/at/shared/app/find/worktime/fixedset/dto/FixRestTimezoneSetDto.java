/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.fixedset.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.app.find.worktime.common.dto.DeductionTimeDto;
import nts.uk.ctx.at.shared.dom.worktime.common.DeductionTime;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixRestTimezoneSetSetMemento;

/**
 * The Class FixRestTimezoneSetDto.
 */
@Getter
@Setter
public class FixRestTimezoneSetDto implements FixRestTimezoneSetSetMemento {

	/** The lst timezone. */
	private List<DeductionTimeDto> timezones;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.fixedset.FixRestTimezoneSetSetMemento#
	 * setLstTimezone(java.util.List)
	 */
	@Override
	public void setLstTimezone(List<DeductionTime> lstTimezone) {
		if (CollectionUtil.isEmpty(lstTimezone)) {
			this.timezones = new ArrayList<>();
		} else {
			this.timezones = lstTimezone.stream().map(domain -> {
				DeductionTimeDto dto = new DeductionTimeDto();
				domain.saveToMemento(dto);
				return dto;
			}).collect(Collectors.toList());
		}
	}

}
