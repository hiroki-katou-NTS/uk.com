package nts.uk.ctx.at.request.app.command.application.gobackdirectly;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.GoBackDirectly;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.GoBackDirectlyRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class UpdateGoBackDirectlyCommandHandler extends CommandHandler<UpdateGoBackDirectlyCommand> {

	@Inject
	private GoBackDirectlyRepository goBackDirectRepo;
//	@Inject
//	private ApplicationRepository appRepo;

	@Override
	protected void handle(CommandHandlerContext<UpdateGoBackDirectlyCommand> context) {
		String companyId = AppContexts.user().companyId();
		UpdateGoBackDirectlyCommand command = context.getCommand();
		goBackDirectRepo.update(
			new GoBackDirectly(companyId, 
				command.getAppID(),
				command.getWorkTypeCD(),
				command.getSiftCd(),
				command.getWorkChangeAtr(),
				command.getGoWorkAtr1(),
				command.getBackHomeAtr1(), 
				command.getWorkTimeStart1(),
				command.getWorkTimeEnd1(),
				command.workLocationCD1,
				command.getGoWorkAtr2(),
				command.getBackHomeAtr2(), 
				command.getWorkTimeStart2(),
				command.getWorkTimeEnd2(),
				command.workLocationCD2));
	}
}
