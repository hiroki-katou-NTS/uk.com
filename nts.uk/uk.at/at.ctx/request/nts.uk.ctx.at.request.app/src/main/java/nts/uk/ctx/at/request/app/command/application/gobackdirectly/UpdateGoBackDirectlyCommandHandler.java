package nts.uk.ctx.at.request.app.command.application.gobackdirectly;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.request.dom.application.common.ApplicationRepository;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.GoBackDirectly;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.GoBackDirectlyRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class UpdateGoBackDirectlyCommandHandler extends CommandHandler<UpdateGoBackDirectlyCommand> {

	@Inject
	private GoBackDirectlyRepository goBackDirectRepo;
	@Inject
	private ApplicationRepository appRepo;

	@Override
	protected void handle(CommandHandlerContext<UpdateGoBackDirectlyCommand> context) {
		String companyId = AppContexts.user().companyId();
		UpdateGoBackDirectlyCommand command = context.getCommand();
		Optional<GoBackDirectly> currentGoBack = this.goBackDirectRepo.findByApplicationID(companyId,
				command.getAppID());
		if (currentGoBack.isPresent()) {
			goBackDirectRepo.update(currentGoBack.get());
		} else {
			throw new BusinessException("Msg_3");
		}
	}
}
