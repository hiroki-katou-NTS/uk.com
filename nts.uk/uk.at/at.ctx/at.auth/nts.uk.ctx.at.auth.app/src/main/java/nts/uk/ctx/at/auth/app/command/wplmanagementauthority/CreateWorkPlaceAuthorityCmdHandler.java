package nts.uk.ctx.at.auth.app.command.wplmanagementauthority;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.auth.dom.wplmanagementauthority.WorkPlaceAuthority;
import nts.uk.ctx.at.auth.dom.wplmanagementauthority.WorkPlaceAuthorityRepository;

@Stateless
public class CreateWorkPlaceAuthorityCmdHandler extends CommandHandler<CreateWorkPlaceAuthorityCmd> {

	@Inject
	private WorkPlaceAuthorityRepository repo;
	
	@Override
	protected void handle(CommandHandlerContext<CreateWorkPlaceAuthorityCmd> context) {
		
		CreateWorkPlaceAuthorityCmd workPlaceAuthority = context.getCommand();
		WorkPlaceAuthority newWorkPlaceAuthority = workPlaceAuthority.toDomain();
		Optional<WorkPlaceAuthority> checkData = repo.getWorkPlaceAuthorityById(workPlaceAuthority.getCompanyId(),
				workPlaceAuthority.getRoleId(), workPlaceAuthority.getFunctionNo());
		if(checkData.isPresent()) {
			throw new BusinessException("Msg_3");
		}else {
			repo.addWorkPlaceAuthority(newWorkPlaceAuthority);
		}
		
	}

}
