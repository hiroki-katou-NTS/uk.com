package nts.uk.ctx.at.request.app.command.application.gobackdirectly;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EnumType;
import javax.transaction.Transactional;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.before.DetailBeforeProcessRegister;
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
	@Inject 
	private DetailBeforeProcessRegister beforeProcessRegister;

	@Override
	protected void handle(CommandHandlerContext<UpdateGoBackDirectlyCommand> context) {
		String companyId = AppContexts.user().companyId();
		UpdateGoBackDirectlyCommand command = context.getCommand();
		
		beforeProcessRegister.processBeforeDetailScreenRegistration(
				companyId, 
				command.employeeID,
				GeneralDate.fromString(command.appDate, "yyyy/MM/dd"),
				command.employeeRouteAtr,
				command.appID,
				EnumAdaptor.valueOf(command.prePostAtr, PrePostAtr.class));	
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
