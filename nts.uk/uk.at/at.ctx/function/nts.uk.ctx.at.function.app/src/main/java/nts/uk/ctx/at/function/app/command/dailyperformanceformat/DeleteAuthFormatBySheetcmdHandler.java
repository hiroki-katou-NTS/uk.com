package nts.uk.ctx.at.function.app.command.dailyperformanceformat;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.primitivevalue.DailyPerformanceFormatCode;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.repository.AuthorityFormatSheetRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class DeleteAuthFormatBySheetcmdHandler extends CommandHandler<DeleteAuthFormatBySheetcmd> {

	@Inject
	private AuthorityFormatSheetRepository repo;

	
	@Override
	protected void handle(CommandHandlerContext<DeleteAuthFormatBySheetcmd> context) {
		String companyID = AppContexts.user().companyId();
		DeleteAuthFormatBySheetcmd command = context.getCommand();
		if(repo.find(companyID, new DailyPerformanceFormatCode(command.getDailyPerformanceFormatCode()), command.getSheetNo()).isPresent()) {
			repo.deleteBySheetNo(companyID, command.getDailyPerformanceFormatCode(), command.getSheetNo());
		}
	}

}
