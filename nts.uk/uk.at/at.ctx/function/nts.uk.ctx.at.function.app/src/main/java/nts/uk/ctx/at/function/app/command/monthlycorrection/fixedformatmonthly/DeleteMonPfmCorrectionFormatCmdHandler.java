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
import nts.uk.shr.com.context.AppContexts;
@Stateless
public class DeleteMonPfmCorrectionFormatCmdHandler extends CommandHandler<DeleteMonPfmCorrectionFormatCmd> {

	@Inject
	private MonPfmCorrectionFormatRepository repo;
	
	@Inject
	private InitialDisplayMonthlyRepository initialRepo;
	
	@Override
	protected void handle(CommandHandlerContext<DeleteMonPfmCorrectionFormatCmd> context) {
		String companyID = AppContexts.user().companyId();
		DeleteMonPfmCorrectionFormatCmd command = context.getCommand();
		command.setCompanyID(companyID);
		Optional<MonPfmCorrectionFormat> data = repo.getMonPfmCorrectionFormat(companyID, command.getMonthlyPfmFormatCode());
		if(data.isPresent()) {
			repo.deleteMonPfmCorrectionFormat(companyID, command.getMonthlyPfmFormatCode());
			
			Optional<InitialDisplayMonthly> initialDisplayMonthly = initialRepo.getInitialDisplayMon(companyID, command.getMonthlyPfmFormatCode());
			if(initialDisplayMonthly.isPresent()) {
				initialRepo.deleteInitialDisplayMonthly(companyID, command.getMonthlyPfmFormatCode());
			}
		}
		else {
			throw new BusinessException("Msg_3");
		}
	}

}
