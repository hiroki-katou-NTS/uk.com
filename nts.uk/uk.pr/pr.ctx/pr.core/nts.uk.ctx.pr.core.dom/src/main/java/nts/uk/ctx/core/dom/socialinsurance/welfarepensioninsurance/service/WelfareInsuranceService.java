package nts.uk.ctx.core.dom.socialinsurance.welfarepensioninsurance.service;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.core.dom.socialinsurance.welfarepensioninsurance.BonusEmployeePensionInsuranceRate;
import nts.uk.ctx.core.dom.socialinsurance.welfarepensioninsurance.BonusEmployeePensionInsuranceRateRepository;
import nts.uk.ctx.core.dom.socialinsurance.welfarepensioninsurance.EmployeesPensionMonthlyInsuranceFee;
import nts.uk.ctx.core.dom.socialinsurance.welfarepensioninsurance.EmployeesPensionMonthlyInsuranceFeeRepository;
import nts.uk.ctx.core.dom.socialinsurance.welfarepensioninsurance.FundClassification;
import nts.uk.ctx.core.dom.socialinsurance.welfarepensioninsurance.WelfarePensionInsuranceClassification;
import nts.uk.ctx.core.dom.socialinsurance.welfarepensioninsurance.WelfarePensionInsuranceClassificationRepository;
import nts.uk.ctx.core.dom.socialinsurance.welfarepensioninsurance.WelfarePensionInsuranceRateHistory;
import nts.uk.ctx.core.dom.socialinsurance.welfarepensioninsurance.WelfarePensionInsuranceRateHistoryRepository;
import nts.uk.ctx.core.dom.socialinsurance.welfarepensioninsurance.WelfarePensionStandardMonthlyFee;
import nts.uk.ctx.core.dom.socialinsurance.welfarepensioninsurance.WelfarePensionStandardMonthlyFeeRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.YearMonthHistoryItem;

@Stateless
public class WelfareInsuranceService {

	@Inject
	private WelfarePensionInsuranceRateHistoryRepository welfarePensionInsuranceRateHistoryRepository;

	@Inject
	private BonusEmployeePensionInsuranceRateRepository bonusEmployeePensionInsuranceRateRepository;

	@Inject
	private EmployeesPensionMonthlyInsuranceFeeRepository employeesPensionMonthlyInsuranceFeeRepository;

	@Inject
	private WelfarePensionInsuranceClassificationRepository welfarePensionInsuranceClassificationRepository;

	@Inject
	private WelfarePensionStandardMonthlyFeeRepository welfarePensionStandardMonthlyFeeRepository;

	public void registerWelfarePensionInsurance(String officeCode, YearMonthHistoryItem yearMonthItem, BonusEmployeePensionInsuranceRate bonusEmployeePension, EmployeesPensionMonthlyInsuranceFee employeePensonMonthly, WelfarePensionInsuranceClassification welfarePensionClassification) {
		WelfarePensionInsuranceRateHistory welfarePensionHistory = null;
		Optional<WelfarePensionInsuranceRateHistory> opt_welfarePensionHistory = welfarePensionInsuranceRateHistoryRepository
				.getWelfarePensionInsuranceRateHistoryByOfficeCode(officeCode);
		employeePensonMonthly = calculationWelfarePensionInsurance(employeePensonMonthly, welfarePensionClassification,
				yearMonthItem);
		if (!opt_welfarePensionHistory.isPresent()) {
			welfarePensionHistory = new WelfarePensionInsuranceRateHistory(AppContexts.user().companyId(), officeCode,
					Arrays.asList(yearMonthItem));
			welfarePensionInsuranceRateHistoryRepository.add(welfarePensionHistory);
			addWelfarePensionInsurance(bonusEmployeePension, employeePensonMonthly, welfarePensionClassification);
			return;
		}
		welfarePensionHistory = opt_welfarePensionHistory.get();
		welfarePensionInsuranceRateHistoryRepository.deleteByCidAndCode(AppContexts.user().companyId(), officeCode);
		if (!welfarePensionHistory.getHistory().contains(yearMonthItem)) {
			welfarePensionHistory.add(yearMonthItem);
			addWelfarePensionInsurance(bonusEmployeePension, employeePensonMonthly, welfarePensionClassification);
		} else {
			updateWelfarePensionInsurance(bonusEmployeePension, employeePensonMonthly, welfarePensionClassification);
		}
		welfarePensionInsuranceRateHistoryRepository.add(welfarePensionHistory);
	}

	private EmployeesPensionMonthlyInsuranceFee calculationWelfarePensionInsurance(
			EmployeesPensionMonthlyInsuranceFee employeePensonMonthly,
			WelfarePensionInsuranceClassification welfarePension, YearMonthHistoryItem yearMonthItem) {
		if (welfarePension.getFundClassification() == FundClassification.JOIN) {
			Optional<WelfarePensionStandardMonthlyFee> welfarePensionStandardMonthlyFee = welfarePensionStandardMonthlyFeeRepository
					.getWelfarePensionStandardMonthlyFeeByStartYearMonth(yearMonthItem.start().v());
			employeePensonMonthly.algorithmMonthlyWelfarePensionInsuranceFeeCalculation(
					welfarePensionStandardMonthlyFee, welfarePension);
		} else {
			employeePensonMonthly.updateGradeList(Collections.EMPTY_LIST);
		}
		return employeePensonMonthly;
	}

	private void addWelfarePensionInsurance(BonusEmployeePensionInsuranceRate bonusEmployeePension,
			EmployeesPensionMonthlyInsuranceFee employeePensonMonthly,
			WelfarePensionInsuranceClassification welfarePensionClassification) {
		bonusEmployeePensionInsuranceRateRepository.add(bonusEmployeePension);
		employeesPensionMonthlyInsuranceFeeRepository.add(employeePensonMonthly);
		welfarePensionInsuranceClassificationRepository.add(welfarePensionClassification);
	}

	private void updateWelfarePensionInsurance(BonusEmployeePensionInsuranceRate bonusEmployeePension,
			EmployeesPensionMonthlyInsuranceFee employeePensonMonthly,
			WelfarePensionInsuranceClassification welfarePensionClassification) {
		bonusEmployeePensionInsuranceRateRepository.update(bonusEmployeePension);
		employeesPensionMonthlyInsuranceFeeRepository.update(employeePensonMonthly);
		welfarePensionInsuranceClassificationRepository.update(welfarePensionClassification);
	}
}
