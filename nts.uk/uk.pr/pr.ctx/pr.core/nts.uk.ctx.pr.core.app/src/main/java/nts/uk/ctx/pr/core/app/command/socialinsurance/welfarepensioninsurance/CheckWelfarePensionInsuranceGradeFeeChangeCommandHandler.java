package nts.uk.ctx.pr.core.app.command.socialinsurance.welfarepensioninsurance;

import javax.ejb.Stateless;
import javax.inject.Inject;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.pr.core.app.command.socialinsurance.welfarepensioninsurance.command.WelfarePensionInsuraceRateCommand;
import nts.uk.ctx.pr.core.dom.socialinsurance.AutoCalculationExecutionCls;
import nts.uk.ctx.pr.core.dom.socialinsurance.welfarepensioninsurance.BonusEmployeePensionInsuranceRate;
import nts.uk.ctx.pr.core.dom.socialinsurance.welfarepensioninsurance.EmployeesPensionMonthlyInsuranceFee;
import nts.uk.ctx.pr.core.dom.socialinsurance.welfarepensioninsurance.WelfarePensionInsuranceClassification;
import nts.uk.ctx.pr.core.dom.socialinsurance.welfarepensioninsurance.service.WelfareInsuranceService;
import nts.uk.shr.com.history.YearMonthHistoryItem;

@Stateless
public class CheckWelfarePensionInsuranceGradeFeeChangeCommandHandler extends CommandHandlerWithResult<WelfarePensionInsuraceRateCommand, Boolean> {
	
	@Inject
	private WelfareInsuranceService socialInsuranceOfficeAndHistoryService;
	
	@Override
	protected Boolean handle(CommandHandlerContext<WelfarePensionInsuraceRateCommand> context) {
		String officeCode = context.getCommand().getOfficeCode();
		YearMonthHistoryItem yearMonthItem = context.getCommand().getYearMonthHistoryItem().fromCommandToDomain();
		BonusEmployeePensionInsuranceRate bonusEmployeePension = context.getCommand().getBonusEmployeePensionInsuranceRate().fromCommandToDomain();
		EmployeesPensionMonthlyInsuranceFee employeePensonMonthly = context.getCommand().getEmployeesPensionMonthlyInsuranceFee().fromCommandToDomain();
		WelfarePensionInsuranceClassification welfarePensionClassification = context.getCommand().getWelfarePensionInsuranceClassification().fromCommandToDomain();
		if (employeePensonMonthly.getAutoCalculationCls() == AutoCalculationExecutionCls.NOT_AUTO) return false;
		return socialInsuranceOfficeAndHistoryService.checkWelfareInsuranceGradeFeeChange(officeCode, yearMonthItem, bonusEmployeePension, employeePensonMonthly, welfarePensionClassification);
	}

}