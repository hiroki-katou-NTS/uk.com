package nts.uk.ctx.at.function.app.command.monthlycorrection.fixedformatmonthly;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.function.dom.monthlycorrection.fixedformatmonthly.MonPfmCorrectionFormat;
import nts.uk.ctx.at.function.dom.monthlycorrection.fixedformatmonthly.MonPfmCorrectionFormatRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class UpdateMonPfmCorrectionFormatCmdHandler extends CommandHandler<MonPfmCorrectionFormatCmd> {

	@Inject
	private MonPfmCorrectionFormatRepository repo;
	
	@Override
	protected void handle(CommandHandlerContext<MonPfmCorrectionFormatCmd> context) {
		String companyID = AppContexts.user().companyId();
		MonPfmCorrectionFormatCmd command = context.getCommand();
		command.setCompanyID(companyID);
		Optional<MonPfmCorrectionFormat> data = repo.getMonPfmCorrectionFormat(companyID, command.getMonthlyPfmFormatCode());
		if(data.isPresent())
			repo.updateMonPfmCorrectionFormat(MonPfmCorrectionFormatCmd.fromCommand(command));
		else
			throw new BusinessException("Msg_3");
		
	}

}
