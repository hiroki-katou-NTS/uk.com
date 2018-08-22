package nts.uk.ctx.at.function.app.command.monthlycorrection.fixedformatmonthly;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.function.dom.monthlycorrection.fixedformatmonthly.InitialDisplayMonthly;
import nts.uk.ctx.at.function.dom.monthlycorrection.fixedformatmonthly.InitialDisplayMonthlyRepository;
import nts.uk.ctx.at.function.dom.monthlycorrection.fixedformatmonthly.MonPfmCorrectionFormat;
import nts.uk.ctx.at.function.dom.monthlycorrection.fixedformatmonthly.MonPfmCorrectionFormatRepository;
import nts.uk.ctx.at.function.dom.monthlycorrection.fixedformatmonthly.MonthlyPerformanceFormatCode;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class AddMonPfmCorrectionFormatCmdHandler extends CommandHandler<MonPfmCorrectionFormatCmd>  {

	@Inject
	private MonPfmCorrectionFormatRepository repo;
	
	@Inject
	private InitialDisplayMonthlyRepository initialRepo;
	
	@Override
	protected void handle(CommandHandlerContext<MonPfmCorrectionFormatCmd> context) {
		String companyID = AppContexts.user().companyId();
		MonPfmCorrectionFormatCmd command = context.getCommand();
		command.setCompanyID(companyID);
		Optional<MonPfmCorrectionFormat> data = repo.getMonPfmCorrectionFormat(companyID, command.getMonthlyPfmFormatCode());
		if(!data.isPresent()){
			command.getDisplayItem().getListSheetCorrectedMonthly().get(0).setSheetNo(1);
			repo.addMonPfmCorrectionFormat(MonPfmCorrectionFormatCmd.fromCommand(command));
			//if A9_1 = true
			if(command.isSetFormatToDefault()) {
				initialRepo.deleteByCid(companyID);
				initialRepo.addInitialDisplayMonthly(new InitialDisplayMonthly(companyID,new MonthlyPerformanceFormatCode( command.getMonthlyPfmFormatCode())));
			}
		}	
		else {
			throw new BusinessException("Msg_3");
		}
			
	}
	

}
