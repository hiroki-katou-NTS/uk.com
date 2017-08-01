/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.command.shift.basicworkregister;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.schedule.app.command.shift.basicworkregister.dto.WorkplaceBasicWorkDto;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.WorkplaceBasicWork;


@Getter
@Setter
public class WorkplaceBWSaveCommand {
	
	/** The workplace basic work. */
	private WorkplaceBasicWorkDto workplaceBasicWork;
	
	/**
	 * To domain.
	 *
	 * @return the workplace basic work
	 */
	public WorkplaceBasicWork toDomain() {
		return this.workplaceBasicWork.toDomain();
	}
}
