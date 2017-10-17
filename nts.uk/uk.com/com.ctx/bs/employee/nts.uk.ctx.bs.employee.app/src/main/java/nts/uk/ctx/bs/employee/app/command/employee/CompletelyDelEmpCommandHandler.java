package nts.uk.ctx.bs.employee.app.command.employee;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.bs.employee.dom.deleteEmpManagement.DeleteEmpManagement;
import nts.uk.ctx.bs.employee.dom.deleteEmpManagement.DeleteEmpRepository;

@Stateless
@Transactional
public class CompletelyDelEmpCommandHandler extends CommandHandler<String>{
	
	/** The repository. */
	@Inject
	private DeleteEmpRepository deleteEmpRepo;

	@Override
	protected void handle(CommandHandlerContext<String> context) {
		
		String sid = context.getCommand();
		// get domain
		DeleteEmpManagement domain = deleteEmpRepo.getBySid(sid).get();
		// update deleted = true;
		domain.setDeleted(1);
		//update entity
		deleteEmpRepo.update(domain);
		
	}

}
