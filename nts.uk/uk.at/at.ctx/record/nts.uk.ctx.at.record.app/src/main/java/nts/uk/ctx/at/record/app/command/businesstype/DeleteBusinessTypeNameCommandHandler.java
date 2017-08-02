package nts.uk.ctx.at.record.app.command.businesstype;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.repository.BusinessTypesRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class DeleteBusinessTypeNameCommandHandler extends CommandHandler<DeleteBusinessTypeNameCommand>{
	@Inject
	private  BusinessTypesRepository businessRepo;
	@Override
	protected void handle (CommandHandlerContext<DeleteBusinessTypeNameCommand> context){
		String companyId = AppContexts.user().companyId();
		businessRepo.deleteBusinessType(companyId, context.getCommand().getWorkTypeCode());
	}
}
