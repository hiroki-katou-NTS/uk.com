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
import nts.uk.ctx.at.shared.dom.worktime.common.DeductionTime;
import nts.uk.ctx.at.shared.dom.worktime.common.TimezoneOfFixedRestTimeSetGetMemento;

/**
 * The Class TimezoneOfFixedRestTimeSetDto.
 */
@Getter
@Setter
public class TimezoneOfFixedRestTimeSetDto implements TimezoneOfFixedRestTimeSetGetMemento {
	
	/** The timezones. */
	private List<DeductionTimeDto> timezones;

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.TimezoneOfFixedRestTimeSetGetMemento#getTimezones()
	 */
	@Override
	public List<DeductionTime> getTimezones() {
		if(CollectionUtil.isEmpty(this.timezones)){
			return new ArrayList<>();
		}
		return this.timezones.stream().map(timezone -> new DeductionTime(timezone)).collect(Collectors.toList());
	}

	

}
