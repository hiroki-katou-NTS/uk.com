/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.command.shift.basicworkregister;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.schedule.app.command.shift.basicworkregister.dto.CompanyBasicWorkDto;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.CompanyBasicWork;


@Getter
@Setter
public class CompanyBWSaveCommand {

	/** The company basic work. */
	private CompanyBasicWorkDto companyBasicWork;

	/**
	 * To domain.
	 *
	 * @return the company basic work
	 */
	public CompanyBasicWork toDomain() {
		return this.companyBasicWork.toDomain();
	}
}
