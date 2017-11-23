package nts.uk.ctx.bs.employee.app.command.temporaryabsence;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.TempAbsenceHistory;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.TemporaryAbsenceRepository;
import nts.uk.shr.pereg.app.command.PeregUpdateCommandHandler;

@Stateless
public class UpdateTemporaryAbsenceCommandHandler extends CommandHandler<UpdateTemporaryAbsenceCommand>
	implements PeregUpdateCommandHandler<UpdateTemporaryAbsenceCommand>{

	@Inject
	private TemporaryAbsenceRepository temporaryAbsenceRepository;
	
	@Override
	public String targetCategoryCd() {
		return "CS00008";
	}

	@Override
	public Class<?> commandClass() {
		return UpdateTemporaryAbsenceCommand.class;
	}

	@Override
	protected void handle(CommandHandlerContext<UpdateTemporaryAbsenceCommand> context) {
		/*val command = context.getCommand();
		
		TempAbsenceHistory temporaryAbsence = TempAbsenceHistory.createSimpleFromJavaType(command.getEmployeeId(), command.getTempAbsenceId(), command.getTempAbsenceType(), 
				command.getHistID(), command.getStartDate(), command.getEndDate(), command.getTempAbsenceReason(), command.getFamilyMemberId(), command.getBirthDate(), command.getMulPregnancySegment());
		
		temporaryAbsenceRepository.updateTemporaryAbsence(temporaryAbsence);*/
		// TODO 
	}

}
