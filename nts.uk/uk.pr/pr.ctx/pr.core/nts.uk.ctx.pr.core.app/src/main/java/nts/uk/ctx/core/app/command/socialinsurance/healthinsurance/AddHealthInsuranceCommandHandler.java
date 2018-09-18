package nts.uk.ctx.core.app.command.socialinsurance.healthinsurance;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.core.app.command.socialinsurance.healthinsurance.command.AddHealthInsuranceCommand;
import nts.uk.ctx.core.dom.socialinsurance.healthinsurance.BonusHealthInsuranceRate;
import nts.uk.ctx.core.dom.socialinsurance.healthinsurance.HealthInsuranceMonthlyFee;
import nts.uk.ctx.core.dom.socialinsurance.healthinsurance.service.HealthInsuranceService;
import nts.uk.shr.com.history.YearMonthHistoryItem;

@Stateless
@Transactional
public class AddHealthInsuranceCommandHandler extends CommandHandler<AddHealthInsuranceCommand> {

	@Inject
	private HealthInsuranceService healthInsuranceService;
	
    @Override
    protected void handle(CommandHandlerContext<AddHealthInsuranceCommand> context) {
    	String officeCode = context.getCommand().getOfficeCode();
		YearMonthHistoryItem yearMonthItem = context.getCommand().getYearMonthHistoryItem().fromCommandToDomain();
		BonusHealthInsuranceRate bonusHealthInsuranceRate = context.getCommand().getBonusHealthInsuranceRate().fromCommandToDomain();
		HealthInsuranceMonthlyFee healthInsuranceMonthlyFee = context.getCommand().getHealthInsuranceMonthlyFee().fromCommandToDomain();
		healthInsuranceService.registerHealthInsurance(officeCode, bonusHealthInsuranceRate, healthInsuranceMonthlyFee, yearMonthItem);
    }
}