package command.person.contact;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.bs.person.dom.person.contact.PersonContactRepository;
import nts.uk.shr.pereg.app.command.PeregDeleteCommandHandler;

@Stateless
public class DeletePerContactCommandHandler extends CommandHandler<DeletePerContactCommand>
 	implements PeregDeleteCommandHandler<DeletePerContactCommand>{

	@Inject
	private PersonContactRepository personContactRepository;
	
	@Override
	public String targetCategoryCd() {
		return "CS00022";
	}

	@Override
	public Class<?> commandClass() {
		return DeletePerContactCommand.class;
	}

	@Override
	protected void handle(CommandHandlerContext<DeletePerContactCommand> context) {
		
		val command = context.getCommand();
		
		personContactRepository.delete(command.getPersonId());
	}

}
