package nts.uk.ctx.core.app.command.socialinsurance.welfarepensioninsurance;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.core.app.command.socialinsurance.welfarepensioninsurance.command.WelfarePensionInsuraceRateCommand;
import nts.uk.ctx.core.dom.socialinsurance.welfarepensioninsurance.service.WelfareInsuranceService;
import nts.uk.shr.com.history.YearMonthHistoryItem;

@Stateless
@Transactional
public class EditWelfarePensionInsuranceCommnadHandler extends CommandHandler<WelfarePensionInsuraceRateCommand> {
	
	@Inject
	private WelfareInsuranceService socialInsuranceOfficeAndHistoryService;
	
	@Override
	protected void handle(CommandHandlerContext<WelfarePensionInsuraceRateCommand> context) {
		String officeCode = context.getCommand().getOfficeCode();
		YearMonthHistoryItem yearMonthItem = context.getCommand().getYearMonthHistoryItem().fromCommandToDomain();
		socialInsuranceOfficeAndHistoryService.updateHistory(officeCode, yearMonthItem);
	}

}
