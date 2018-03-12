/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.difftimeset.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeDeductTimezone;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeRestTimezoneSetMemento;

/**
 * The Class DiffTimeRestTimezone.
 */
@Getter
@Setter
public class DiffTimeRestTimezoneDto implements DiffTimeRestTimezoneSetMemento {

	/** The rest timezone. */
	private List<DiffTimeDeductTimezoneDto> restTimezones;

	@Override
	public void setRestTimezones(List<DiffTimeDeductTimezone> restTimezones) {
		this.restTimezones = new ArrayList<>();
		this.restTimezones.addAll(restTimezones.stream().map(item -> {
			DiffTimeDeductTimezoneDto dto = new DiffTimeDeductTimezoneDto();
			item.saveToMemento(dto);
			return dto;
		}).collect(Collectors.toList()));
	}
}
