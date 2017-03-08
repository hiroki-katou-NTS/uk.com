package nts.uk.ctx.basic.app.command.organization.employment;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.basic.dom.organization.employment.EmploymentRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class DeleteEmploymentCommandHandler extends CommandHandler<DeleteEmploymentCommand>{
	@Inject
	private EmploymentRepository repository;
	@Override
	protected void handle(CommandHandlerContext<DeleteEmploymentCommand> context) {
		try{
			DeleteEmploymentCommand command = context.getCommand();
			String companyCode = AppContexts.user().companyCode();
			this.repository.remove(companyCode, command.getEmploymentCode());
		}
		catch(Exception ex){
			throw ex;
		}
	}

}
