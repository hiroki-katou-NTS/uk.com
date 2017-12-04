package nts.uk.ctx.at.auth.app.command.wplmanagementauthority;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.auth.dom.wplmanagementauthority.WorkPlaceAuthority;
import nts.uk.ctx.at.auth.dom.wplmanagementauthority.WorkPlaceAuthorityRepository;

@Stateless
public class DeleteWorkPlaceAuthorityCmdHandler extends CommandHandler<DeleteWorkPlaceAuthorityCmd>{

	@Inject
	private WorkPlaceAuthorityRepository repo;
	
	@Override
	protected void handle(CommandHandlerContext<DeleteWorkPlaceAuthorityCmd> context) {
		DeleteWorkPlaceAuthorityCmd input = context.getCommand();
		Optional<WorkPlaceAuthority> checkData = repo.getWorkPlaceAuthorityById(
				input.getCompanyId(), input.getRoleId(), input.getFunctionNo());
		if(checkData.isPresent()) {
			repo.deleteWorkPlaceAuthority(input.getCompanyId(), input.getRoleId(), input.getFunctionNo());
		}
		
		
		
	}
	
}
