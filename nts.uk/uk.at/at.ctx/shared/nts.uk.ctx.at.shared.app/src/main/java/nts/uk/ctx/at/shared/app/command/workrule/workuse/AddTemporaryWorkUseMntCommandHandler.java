package nts.uk.ctx.at.shared.app.command.workrule.workuse;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.workrule.workuse.TemporaryWorkUseManage;
import nts.uk.ctx.at.shared.dom.workrule.workuse.TemporaryWorkUseManageRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class AddTemporaryWorkUseMntCommandHandler.
 *
 * @author HoangNDH
 */
@Stateless
public class AddTemporaryWorkUseMntCommandHandler extends CommandHandler<AddTemporaryWorkUseMntCommand> {
	
	/** The repository. */
	@Inject
	TemporaryWorkUseManageRepository repository;
	
	/* (non-Javadoc)
	 * @see nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command.CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<AddTemporaryWorkUseMntCommand> context) {
		AddTemporaryWorkUseMntCommand command = context.getCommand();
		String companyId = AppContexts.user().companyId();
		
		Optional<TemporaryWorkUseManage> optTempWorkUseManage = repository.findByCid(companyId);
		
		TemporaryWorkUseManage setting = TemporaryWorkUseManage.createFromJavaType(companyId, command.getUseClassification());
		if (optTempWorkUseManage.isPresent()) {
			repository.update(setting);
		}
		else {
			repository.insert(setting);
		}
		
	}

}
