package nts.uk.ctx.at.record.app.command.dailyperformanceformat;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.primitivevalue.BusinessTypeCode;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.repository.BusinessFormatSheetRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class DeleteBusiFormatBySheetCmdHandler extends CommandHandler<DeleteBusiFormatBySheetCmd> {

	@Inject
	private BusinessFormatSheetRepository repo;
	
	@Override
	protected void handle(CommandHandlerContext<DeleteBusiFormatBySheetCmd> context) {
		String companyId = AppContexts.user().companyId();
		DeleteBusiFormatBySheetCmd command = context.getCommand();
		if(repo.getSheetInformation(companyId,new BusinessTypeCode( command.getBusinessTypeCode()), command.getSheetNo()).isPresent()) {
			repo.deleteBusBySheetNo(companyId, command.getBusinessTypeCode(),  command.getSheetNo());
		}
	}

}
