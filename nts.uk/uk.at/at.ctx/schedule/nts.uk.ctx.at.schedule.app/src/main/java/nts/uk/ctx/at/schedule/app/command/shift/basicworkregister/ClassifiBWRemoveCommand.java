/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.command.shift.basicworkregister;

import lombok.Getter;
import lombok.Setter;


/**
 * The Class ClassifiBWRemoveCommand.
 */
@Getter
@Setter
public class ClassifiBWRemoveCommand {

	/** The company id. */
	private String companyId;

	/** The classification code. */
	private String classificationCode;

	/** The work type code. */
	private String workTypeCode;
}
