/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.difftimeset.dto;

import java.util.List;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeDeductTimezone;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeRestTimezoneSetMemento;

/**
 * The Class DiffTimeRestTimezone.
 */
@Getter
public class DiffTimeRestTimezoneDto implements DiffTimeRestTimezoneSetMemento {
	
	/** The rest timezone. */
	private List<DiffTimeDeductTimezoneDto> restTimezones;

	@Override
	public void setRestTimezones(List<DiffTimeDeductTimezone> restTimezones) {
		// TODO Auto-generated method stub
		
	}
}
