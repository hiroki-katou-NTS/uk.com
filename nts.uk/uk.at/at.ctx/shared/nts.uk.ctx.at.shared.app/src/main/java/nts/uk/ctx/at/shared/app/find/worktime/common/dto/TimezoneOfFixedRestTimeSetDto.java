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
import nts.uk.ctx.at.shared.dom.worktime.common.DeductionTime;
import nts.uk.ctx.at.shared.dom.worktime.common.TimezoneOfFixedRestTimeSetSetMemento;

/**
 * The Class TimezoneOfFixedRestTimeSetDto.
 */
@Getter
@Setter
public class TimezoneOfFixedRestTimeSetDto implements TimezoneOfFixedRestTimeSetSetMemento {
	
	/** The timezones. */
	private List<DeductionTimeDto> timezones;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.
	 * TimezoneOfFixedRestTimeSetSetMemento#setTimezones(java.util.List)
	 */
	@Override
	public void setTimezones(List<DeductionTime> timzones) {
		if(CollectionUtil.isEmpty(timzones)){
			this.timezones = new ArrayList<>();
		}
		else {
		this.timezones = timzones.stream().map(domain->{
			DeductionTimeDto dto = new DeductionTimeDto();
			domain.saveToMemento(dto);
			return dto;
		}).collect(Collectors.toList());
		}
	}

}
