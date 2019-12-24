package nts.uk.ctx.pr.core.dom.socialinsurance.welfarepensioninsurance.service;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.core.dom.socialinsurance.AutoCalculationExecutionCls;
import nts.uk.ctx.pr.core.dom.socialinsurance.welfarepensioninsurance.BonusEmployeePensionInsuranceRate;
import nts.uk.ctx.pr.core.dom.socialinsurance.welfarepensioninsurance.BonusEmployeePensionInsuranceRateRepository;
import nts.uk.ctx.pr.core.dom.socialinsurance.welfarepensioninsurance.EmployeesPensionMonthlyInsuranceFee;
import nts.uk.ctx.pr.core.dom.socialinsurance.welfarepensioninsurance.EmployeesPensionMonthlyInsuranceFeeRepository;
import nts.uk.ctx.pr.core.dom.socialinsurance.welfarepensioninsurance.FundClassification;
import nts.uk.ctx.pr.core.dom.socialinsurance.welfarepensioninsurance.WelfarePensionInsuranceClassification;
import nts.uk.ctx.pr.core.dom.socialinsurance.welfarepensioninsurance.WelfarePensionInsuranceClassificationRepository;
import nts.uk.ctx.pr.core.dom.socialinsurance.welfarepensioninsurance.WelfarePensionInsuranceRateHistory;
import nts.uk.ctx.pr.core.dom.socialinsurance.welfarepensioninsurance.WelfarePensionStandardMonthlyFee;
import nts.uk.ctx.pr.core.dom.socialinsurance.welfarepensioninsurance.WelfarePensionStandardMonthlyFeeRepository;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;

@Stateless
public class WelfareInsuranceService {

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
		Optional<WelfarePensionInsuranceRateHistory> opt_welfarePensionHistory = welfarePensionInsuranceClassificationRepository.getWelfarePensionHistoryByOfficeCode(officeCode);
		// Update exemption rate to null when not join fund
		if (welfarePensionClassification.getFundClassification() == FundClassification.NOT_JOIN){
			bonusEmployeePension.changeDataWhenNotJoinFund();
			employeePensonMonthly.changeDataWhenNotJoinFund();
		}
		// アルゴリズム「月額厚生年金保険料計算処理」を実行する
		employeePensonMonthly = calculationWelfarePensionInsurance(employeePensonMonthly, welfarePensionClassification,
				yearMonthItem);
		
		if (!opt_welfarePensionHistory.isPresent()) {
			// add new history if there are no history
			addWelfarePensionInsurance(bonusEmployeePension, employeePensonMonthly, welfarePensionClassification, officeCode, yearMonthItem);
			return;
		}
		// delete old history 
		welfarePensionHistory = opt_welfarePensionHistory.get();
		if (!welfarePensionHistory.getHistory().contains(yearMonthItem)) {

            // add new item to history
            welfarePensionHistory.add(yearMonthItem);
            this.addWelfarePensionInsurance(bonusEmployeePension, employeePensonMonthly, welfarePensionClassification, officeCode, yearMonthItem);
            // if have previous history
            if (welfarePensionHistory.getHistory().size() > 1) {
                this.updateHistorySpan(officeCode, welfarePensionHistory.getHistory().get(0));
            }

		} else {
			// update if existed
			updateWelfarePensionInsurance(bonusEmployeePension, employeePensonMonthly, welfarePensionClassification, officeCode, yearMonthItem);
		}
	}

	private EmployeesPensionMonthlyInsuranceFee calculationWelfarePensionInsurance(
			EmployeesPensionMonthlyInsuranceFee employeePensonMonthly,
			WelfarePensionInsuranceClassification welfarePension, YearMonthHistoryItem yearMonthItem) {
		// calculate if yes, empty list if other
		if (employeePensonMonthly.getAutoCalculationCls() == AutoCalculationExecutionCls.AUTO) {
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
											WelfarePensionInsuranceClassification welfarePensionClassification, String officeCode, YearMonthHistoryItem yearMonth) {
		bonusEmployeePensionInsuranceRateRepository.add(bonusEmployeePension, officeCode, yearMonth);
		employeesPensionMonthlyInsuranceFeeRepository.add(employeePensonMonthly, officeCode, yearMonth);
		welfarePensionInsuranceClassificationRepository.add(welfarePensionClassification, officeCode, yearMonth);
	}

	private void updateWelfarePensionInsurance(BonusEmployeePensionInsuranceRate bonusEmployeePension,
			EmployeesPensionMonthlyInsuranceFee employeePensonMonthly,
			WelfarePensionInsuranceClassification welfarePensionClassification, String officeCode, YearMonthHistoryItem yearMonth) {
		bonusEmployeePensionInsuranceRateRepository.update(bonusEmployeePension, officeCode, yearMonth);
		employeesPensionMonthlyInsuranceFeeRepository.update(employeePensonMonthly, officeCode, yearMonth);
		welfarePensionInsuranceClassificationRepository.update(welfarePensionClassification, officeCode, yearMonth);
	}
	
	public void updateHistory (String officeCode, YearMonthHistoryItem yearMonth) {
		Optional<WelfarePensionInsuranceRateHistory> opt_WelfarePensionHist = welfarePensionInsuranceClassificationRepository.getWelfarePensionHistoryByOfficeCode(officeCode);
		if (!opt_WelfarePensionHist.isPresent()) {
			return;
		}
		this.updateHistorySpan(officeCode, yearMonth);
		// get history and change span\
		WelfarePensionInsuranceRateHistory welfarePensionHist = opt_WelfarePensionHist.get();
		int currentIndex = welfarePensionHist.getHistory().indexOf(yearMonth);
		try {
            YearMonthHistoryItem previousHistory =  welfarePensionHist.getHistory().get(currentIndex + 1);
            welfarePensionHist.changeSpan(previousHistory, new YearMonthPeriod(previousHistory.start() , yearMonth.start().addMonths(-1)));
            this.updateHistorySpan(officeCode, welfarePensionHist.getHistory().get(currentIndex + 1));
        } catch (IndexOutOfBoundsException e){
            return;
        }
	}
	
	public void deleteHistory (String officeCode, YearMonthHistoryItem yearMonth) {
		Optional<WelfarePensionInsuranceRateHistory> opt_WelfarePensionHist = welfarePensionInsuranceClassificationRepository.getWelfarePensionHistoryByOfficeCode(officeCode);
		if (!opt_WelfarePensionHist.isPresent()) {
			return;
		}
		// remove last history and update previous history
		WelfarePensionInsuranceRateHistory welfarePensionHist = opt_WelfarePensionHist.get();
		if (welfarePensionHist.getHistory().size() == 0) return;
		YearMonthHistoryItem lastestHistory = welfarePensionHist.getHistory().get(0);
		welfarePensionHist.remove(lastestHistory);
		if (welfarePensionHist.getHistory().size() > 0){
			lastestHistory = welfarePensionHist.getHistory().get(0);
			welfarePensionHist.changeSpan(welfarePensionHist.getHistory().get(0),  new YearMonthPeriod(lastestHistory.start(), new YearMonth(new Integer(999912))));
		}
		this.updateHistorySpan(officeCode, lastestHistory);
		bonusEmployeePensionInsuranceRateRepository.deleteByHistoryIds(Arrays.asList(yearMonth.identifier()));
		employeesPensionMonthlyInsuranceFeeRepository.deleteByHistoryIds(Arrays.asList(yearMonth.identifier()));
		welfarePensionInsuranceClassificationRepository.deleteByHistoryIds(Arrays.asList(yearMonth.identifier()));
	}
	
	public boolean checkWelfareInsuranceGradeFeeChange(String officeCode, YearMonthHistoryItem yearMonthItem, BonusEmployeePensionInsuranceRate bonusEmployeePension, EmployeesPensionMonthlyInsuranceFee employeePensonMonthly, WelfarePensionInsuranceClassification welfarePensionClassification) {
		// Update exemption rate to null when not join fund
		if (welfarePensionClassification.getFundClassification() == FundClassification.NOT_JOIN){
			bonusEmployeePension.changeDataWhenNotJoinFund();
			employeePensonMonthly.changeDataWhenNotJoinFund();
		}
		// アルゴリズム「月額厚生年金保険料計算処理」を実行する
		employeePensonMonthly = calculationWelfarePensionInsurance(employeePensonMonthly, welfarePensionClassification,
				yearMonthItem);
		Optional<EmployeesPensionMonthlyInsuranceFee> opt_oldEmployePension = employeesPensionMonthlyInsuranceFeeRepository.getEmployeesPensionMonthlyInsuranceFeeByHistoryId(yearMonthItem.identifier());
		if (!opt_oldEmployePension.isPresent()){
			return false;
		}
		return !employeePensonMonthly.getPensionInsurancePremium().containsAll(opt_oldEmployePension.get().getPensionInsurancePremium());	
	}

	private void updateHistorySpan (String officeCode, YearMonthHistoryItem yearMonth) {
		bonusEmployeePensionInsuranceRateRepository.updateHistory(officeCode, yearMonth);
		employeesPensionMonthlyInsuranceFeeRepository.updateHistory(officeCode, yearMonth);
		welfarePensionInsuranceClassificationRepository.updateHistory(officeCode, yearMonth);
	}
}
