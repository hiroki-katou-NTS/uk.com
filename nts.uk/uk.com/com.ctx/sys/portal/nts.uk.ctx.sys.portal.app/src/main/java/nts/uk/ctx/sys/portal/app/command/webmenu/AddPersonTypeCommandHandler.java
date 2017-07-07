package nts.uk.ctx.sys.portal.app.command.webmenu;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.sys.portal.dom.webmenu.WebMenuCode;
import nts.uk.ctx.sys.portal.dom.webmenu.WebMenuRepository;
import nts.uk.ctx.sys.portal.dom.webmenu.personaltying.PersonalTying;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class AddPersonTypeCommandHandler extends CommandHandler<AddPersonTypeCommand>{
	
	@Inject
	private WebMenuRepository respository;
	
	@Override
	protected void handle(CommandHandlerContext<AddPersonTypeCommand> context) {
		AddPersonTypeCommand addPersonTypeCommand = context.getCommand();
		String companyId = AppContexts.user().companyId();
		String employeeId = addPersonTypeCommand.getEmployeeId();
		
		PersonalTying personalTying = new PersonalTying(companyId, new WebMenuCode(addPersonTypeCommand.getWebMenuCode()), employeeId);
		respository.add(personalTying);
	}

}
