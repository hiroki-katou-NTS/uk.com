package nts.uk.ctx.bs.employee.app.command.temporaryabsence;


import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.TemporaryAbsenceHistRepository;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.TemporaryAbsenceRepository;
import nts.uk.shr.pereg.app.command.PeregDeleteCommandHandler;

@Stateless
public class DeletelTemporaryAbsenceCommandHandler extends CommandHandler<DeleteTemporaryAbsenceCommand>
	implements PeregDeleteCommandHandler<DeleteTemporaryAbsenceCommand>{

	@Inject
	private TemporaryAbsenceRepository temporaryAbsenceRepository;
	
	@Inject
	private TemporaryAbsenceHistRepository temporaryAbsenceHistRepository;
	
	@Override
	public String targetCategoryCd() {
		return "CS00008";
	}

	@Override
	public Class<?> commandClass() {
		return DeleteTemporaryAbsenceCommand.class;
	}

	@Override
	protected void handle(CommandHandlerContext<DeleteTemporaryAbsenceCommand> context) {
		val command = context.getCommand();
		
		temporaryAbsenceHistRepository.deleteTemporaryAbsenceHist(command.getHistoyId());
		
		temporaryAbsenceRepository.deleteTemporaryAbsence(command.getHistoyId());
	}

}
