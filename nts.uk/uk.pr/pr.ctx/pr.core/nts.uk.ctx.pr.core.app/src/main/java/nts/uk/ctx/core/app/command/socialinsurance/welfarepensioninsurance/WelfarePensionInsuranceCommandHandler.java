package nts.uk.ctx.core.app.command.socialinsurance.welfarepensioninsurance;

import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.core.app.command.socialinsurance.welfarepensioninsurance.command.WelfarePensionInsuraceRateCommand;
import nts.uk.ctx.core.dom.socialinsurance.welfarepensioninsurance.BonusEmployeePensionInsuranceRate;
import nts.uk.ctx.core.dom.socialinsurance.welfarepensioninsurance.BonusEmployeePensionInsuranceRateRepository;
import nts.uk.ctx.core.dom.socialinsurance.welfarepensioninsurance.EmployeesPensionMonthlyInsuranceFee;
import nts.uk.ctx.core.dom.socialinsurance.welfarepensioninsurance.EmployeesPensionMonthlyInsuranceFeeRepository;
import nts.uk.ctx.core.dom.socialinsurance.welfarepensioninsurance.WelfarePensionInsuranceClassification;
import nts.uk.ctx.core.dom.socialinsurance.welfarepensioninsurance.WelfarePensionInsuranceClassificationRepository;
import nts.uk.ctx.core.dom.socialinsurance.welfarepensioninsurance.service.FindSocialInsuranceOfficeAndHistoryService;
import nts.uk.shr.com.history.YearMonthHistoryItem;

public class WelfarePensionInsuranceCommandHandler extends CommandHandler<WelfarePensionInsuraceRateCommand> {

	@Inject
	private BonusEmployeePensionInsuranceRateRepository bonusEmployeePensionInsuranceRateRepository;
	
	@Inject
	private EmployeesPensionMonthlyInsuranceFeeRepository employeesPensionMonthlyInsuranceFeeRepository;
	
	@Inject
	private WelfarePensionInsuranceClassificationRepository welfarePensionInsuranceClassificationRepository;
	
	@Inject
	private FindSocialInsuranceOfficeAndHistoryService findSocialInsuranceOfficeAndHistoryService;
	
	@Override
	protected void handle(CommandHandlerContext<WelfarePensionInsuraceRateCommand> context) {
		String officeCode = context.getCommand().getOfficeCode();
		YearMonthHistoryItem yearMonthItem = context.getCommand().getYearMonthHistoryItem().fromCommandToDomain();
		findSocialInsuranceOfficeAndHistoryService.addNewHistory(officeCode, yearMonthItem);
		BonusEmployeePensionInsuranceRate bonusEmployeePension = context.getCommand().getBonusEmployeePensionInsuranceRate().fromCommandToDomain();
		EmployeesPensionMonthlyInsuranceFee employeePensonMonthly = context.getCommand().getEmployeesPensionMonthlyInsuranceFee().fromCommandToDomain();
		WelfarePensionInsuranceClassification welfarePensionClassification = context.getCommand().getWelfarePensionInsuranceClassification().fromCommandToDomain();
		bonusEmployeePensionInsuranceRateRepository.add(bonusEmployeePension);
		employeesPensionMonthlyInsuranceFeeRepository.add(employeePensonMonthly);
		welfarePensionInsuranceClassificationRepository.add(welfarePensionClassification);
	}

}
