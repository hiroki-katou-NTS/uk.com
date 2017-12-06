package nts.uk.ctx.bs.employee.app.command.temporaryabsence;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.TempAbsenceHistory;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.TemporaryAbsenceRepository;
import nts.uk.shr.pereg.app.command.PeregAddCommandHandler;
import nts.uk.shr.pereg.app.command.PeregAddCommandResult;

@Stateless
public class AddTemporaryAbsenceCommandHandler extends CommandHandlerWithResult<AddTemporaryAbsenceCommand,PeregAddCommandResult>
	implements PeregAddCommandHandler<AddTemporaryAbsenceCommand>{

	@Inject
	private TemporaryAbsenceRepository temporaryAbsenceRepository;
	
	@Override
	public String targetCategoryCd() {
		return "CS00008";
	}

	@Override
	public Class<?> commandClass() {
		return AddTemporaryAbsenceCommand.class;
	}

	@Override
	protected PeregAddCommandResult handle(CommandHandlerContext<AddTemporaryAbsenceCommand> context) {
		/*val command = context.getCommand();
		
		String newTempID = IdentifierUtil.randomUniqueId();
		
		String newHistID = IdentifierUtil.randomUniqueId();
		
		TempAbsenceHistory temporaryAbsence = TempAbsenceHistory.createSimpleFromJavaType(command.getEmployeeId(), newTempID, command.getTempAbsenceType(), 
				newHistID, command.getStartDate(), command.getEndDate(), command.getTempAbsenceReason(), command.getFamilyMemberId(), command.getBirthDate(), command.getMulPregnancySegment());
		
		temporaryAbsenceRepository.addTemporaryAbsence(temporaryAbsence);
		return new PeregAddCommandResult(newTempID);*/
		return null;
		// TODO
	}

}
