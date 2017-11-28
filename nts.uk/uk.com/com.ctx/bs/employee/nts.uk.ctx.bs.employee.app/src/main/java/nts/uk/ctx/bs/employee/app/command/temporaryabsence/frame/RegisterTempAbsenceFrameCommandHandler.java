/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.app.command.temporaryabsence.frame;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.frame.TempAbsenceFrame;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.frame.TempAbsenceRepositoryFrame;

/**
 * The Class RegisterTempAbsenceFrameCommandHandler.
 */
@Stateless
@Transactional
public class RegisterTempAbsenceFrameCommandHandler extends CommandHandler<RegisterTempAbsenceFrameCommand>{

	/** The temp absence frame repository. */
	@Inject
	private TempAbsenceRepositoryFrame tempAbsenceFrameRepository;
	
	@Override
	protected void handle(CommandHandlerContext<RegisterTempAbsenceFrameCommand> context) {
		RegisterTempAbsenceFrameCommand command = context.getCommand();
		
		// convert to domain
		List<TempAbsenceFrame> absenceFrame = command.toDomain();
		
		// update
		for (TempAbsenceFrame tempAbsenceFrame: absenceFrame) {
			tempAbsenceFrameRepository.udpate(tempAbsenceFrame);
		}
	}

}
