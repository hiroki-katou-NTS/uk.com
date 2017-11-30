/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.app.command.temporaryabsence.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.bs.employee.app.command.temporaryabsence.frame.RegisterTempAbsenceFrameCommand;

/**
 * The Class RegisterTempAbsenceFrameDto.
 */
@Getter
@Setter
public class RegisterTempAbsenceFrameDto {
	
	/** The lst temp absence frame command. */
	private List<RegisterTempAbsenceFrameCommand> lstTempAbsenceFrameCommand;
}
