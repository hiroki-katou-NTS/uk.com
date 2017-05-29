/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.vacation.setting.subst.command;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.app.vacation.setting.subst.command.dto.EmpSubstVacationDto;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.EmpSubstVacation;

/**
 * The Class EmpSubstVacationSaveCommand.
 */
@Getter
@Setter
public class EmpSubstVacationSaveCommand {

	/** The subst vacation dto. */
	private EmpSubstVacationDto substVacationDto;

	/**
	 * To domain.
	 *
	 * @param companyId
	 *            the company id
	 * @return the emp subst vacation
	 */
	public EmpSubstVacation toDomain(String companyId) {
		return this.substVacationDto.toEmpSubstDomain(companyId);
	}
}
