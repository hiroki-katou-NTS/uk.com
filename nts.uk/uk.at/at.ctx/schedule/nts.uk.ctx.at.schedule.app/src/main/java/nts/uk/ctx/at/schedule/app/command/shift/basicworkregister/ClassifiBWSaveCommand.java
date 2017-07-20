/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.command.shift.basicworkregister;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.schedule.app.command.shift.basicworkregister.dto.ClassificationBasicWorkDto;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.ClassificationBasicWork;


@Getter
@Setter
public class ClassifiBWSaveCommand {

	/** The classifi basic work. */
	private ClassificationBasicWorkDto classifiBasicWork;

	/**
	 * To domain.
	 *
	 * @return the classification basic work
	 */
	public ClassificationBasicWork toDomain() {
		return this.classifiBasicWork.toDomain();
	}
}
