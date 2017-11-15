package nts.uk.ctx.bs.employee.app.command.temporaryabsence;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.TemporaryAbsence;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.TemporaryAbsenceRepository;
import nts.uk.shr.pereg.app.command.PeregAddCommandHandler;

@Stateless
public class AddTemporaryAbsenceCommandHandler extends CommandHandler<AddTemporaryAbsenceCommand>
	implements PeregAddCommandHandler<AddTemporaryAbsenceCommand>{

	@Inject
	private TemporaryAbsenceRepository temporaryAbsenceRepository;
	
	@Override
	public String targetCategoryId() {
		return "CS00008";
	}

	@Override
	public Class<?> commandClass() {
		return AddTemporaryAbsenceCommand.class;
	}

	@Override
	protected void handle(CommandHandlerContext<AddTemporaryAbsenceCommand> context) {
		val command = context.getCommand();
		
		String newTempID = IdentifierUtil.randomUniqueId();
		
		String newHistID = IdentifierUtil.randomUniqueId();
		
		TemporaryAbsence temporaryAbsence = TemporaryAbsence.createSimpleFromJavaType(command.getEmployeeId(), newTempID, command.getTempAbsenceType(), 
				newHistID, command.getStartDate(), command.getEndDate(), command.getTempAbsenceReason(), command.getFamilyMemberId(), command.getBirthDate(), command.getMulPregnancySegment());
		
		temporaryAbsenceRepository.addTemporaryAbsence(temporaryAbsence);
	}

}
