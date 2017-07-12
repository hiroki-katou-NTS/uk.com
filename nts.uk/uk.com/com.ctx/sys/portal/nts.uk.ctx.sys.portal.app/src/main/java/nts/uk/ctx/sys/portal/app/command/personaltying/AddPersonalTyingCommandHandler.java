package nts.uk.ctx.sys.portal.app.command.personaltying;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.sys.portal.dom.webmenu.personaltying.PersonalTying;
import nts.uk.ctx.sys.portal.dom.webmenu.personaltying.PersonalTyingRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class AddPersonalTyingCommandHandler extends CommandHandler<List<PersonTypingCommand>>{
	
	@Inject
	private PersonalTyingRepository respository;
	
	@Override
	protected void handle(CommandHandlerContext<List<PersonTypingCommand>> context) {
		
		List<PersonTypingCommand> persons = context.getCommand();
		String companyId = AppContexts.user().companyId();
		respository.delete(companyId);
		
		persons.forEach(person-> {
			PersonalTying personalTying = new PersonalTying(companyId, person.getWebMenuCode(), person.getEmployeeId());
			personalTying.validate();
			
			respository.add(personalTying);
		});
	}

}
