package nts.uk.ctx.core.app.command.socialinsurance.healthinsurance;
import javax.ejb.Stateless;
import javax.inject.Inject;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.core.app.command.socialinsurance.healthinsurance.command.HealthInsuranceCommand;
import nts.uk.ctx.core.dom.socialinsurance.AutoCalculationExecutionCls;
import nts.uk.ctx.core.dom.socialinsurance.healthinsurance.BonusHealthInsuranceRate;
import nts.uk.ctx.core.dom.socialinsurance.healthinsurance.HealthInsuranceMonthlyFee;
import nts.uk.ctx.core.dom.socialinsurance.healthinsurance.service.HealthInsuranceService;
import nts.uk.shr.com.history.YearMonthHistoryItem;

@Stateless
public class CheckHealthInsuranceGradeFeeChangeCommandHandler extends CommandHandlerWithResult<HealthInsuranceCommand, Boolean> {

	@Inject
	private HealthInsuranceService healthInsuranceService;
	
    @Override
    protected Boolean handle(CommandHandlerContext<HealthInsuranceCommand> context) {
    	String officeCode = context.getCommand().getOfficeCode();
		YearMonthHistoryItem yearMonthItem = context.getCommand().getYearMonthHistoryItem().fromCommandToDomain();
		BonusHealthInsuranceRate bonusHealthInsuranceRate = context.getCommand().getBonusHealthInsuranceRate().fromCommandToDomain();
		HealthInsuranceMonthlyFee healthInsuranceMonthlyFee = context.getCommand().getHealthInsuranceMonthlyFee().fromCommandToDomain();
		if (healthInsuranceMonthlyFee.getAutoCalculationCls() == AutoCalculationExecutionCls.NOT_AUTO) return false;
		return healthInsuranceService.checkHealthInsuranceGradeFeeChange(officeCode, bonusHealthInsuranceRate, healthInsuranceMonthlyFee, yearMonthItem);
    }
}
