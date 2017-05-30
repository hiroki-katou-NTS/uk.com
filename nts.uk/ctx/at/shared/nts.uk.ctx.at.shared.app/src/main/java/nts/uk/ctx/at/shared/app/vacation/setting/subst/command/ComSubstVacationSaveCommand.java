/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.vacation.setting.subst.command;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.app.vacation.setting.subst.command.dto.SubstVacationDto;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.ComSubstVacation;

/**
 * The Class ComSubstVacationSaveCommand.
 */
@Getter
@Setter
public class ComSubstVacationSaveCommand {

	/** The subst vacation dto. */
	private SubstVacationDto substVacationDto;

	/**
	 * To domain.
	 *
	 * @param companyCode
	 *            the company code
	 * @return the com subst vacation
	 */
	public ComSubstVacation toDomain(String companyCode) {
		return this.substVacationDto.toDomain(companyCode);
	}
}
