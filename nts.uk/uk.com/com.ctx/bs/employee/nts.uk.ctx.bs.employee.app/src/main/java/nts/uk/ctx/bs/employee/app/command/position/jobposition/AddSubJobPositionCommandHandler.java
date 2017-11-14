package nts.uk.ctx.bs.employee.app.command.position.jobposition;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.bs.employee.dom.position.jobposition.SubJobPosRepository;
import nts.uk.ctx.bs.employee.dom.position.jobposition.SubJobPosition;
import nts.uk.shr.pereg.app.command.PeregAddCommandHandler;

@Stateless
public class AddSubJobPositionCommandHandler extends CommandHandler<AddSubJobPositionCommand>
	implements PeregAddCommandHandler<AddSubJobPositionCommand>{

	@Inject
	private SubJobPosRepository subJobPosRepository;
	@Override
	public String targetCategoryId() {
		return "CS00013";
	}

	@Override
	public Class<?> commandClass() {
		return AddSubJobPositionCommand.class;
	}

	@Override
	protected void handle(CommandHandlerContext<AddSubJobPositionCommand> context) {
		val command = context.getCommand();
		
		String newId = IdentifierUtil.randomUniqueId();
		
		SubJobPosition domain = SubJobPosition.createFromJavaType(newId, command.getAffiDeptId(), command.getJobTitleId(), command.getStartDate(), command.getEndDate());
		subJobPosRepository.addSubJobPosition(domain);
		
		
	}

}
