package nts.uk.ctx.core.dom.socialinsurance.healthinsurance.service;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.time.YearMonth;
import nts.uk.ctx.core.dom.socialinsurance.AutoCalculationExecutionCls;
import nts.uk.ctx.core.dom.socialinsurance.healthinsurance.BonusHealthInsuranceRate;
import nts.uk.ctx.core.dom.socialinsurance.healthinsurance.BonusHealthInsuranceRateRepository;
import nts.uk.ctx.core.dom.socialinsurance.healthinsurance.HealthInsuranceFeeRateHistory;
import nts.uk.ctx.core.dom.socialinsurance.healthinsurance.HealthInsuranceFeeRateHistoryRepository;
import nts.uk.ctx.core.dom.socialinsurance.healthinsurance.HealthInsuranceMonthlyFee;
import nts.uk.ctx.core.dom.socialinsurance.healthinsurance.HealthInsuranceMonthlyFeeRepository;
import nts.uk.ctx.core.dom.socialinsurance.healthinsurance.HealthInsuranceStandardMonthly;
import nts.uk.ctx.core.dom.socialinsurance.healthinsurance.HealthInsuranceStandardMonthlyRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;

@Stateless
public class HealthInsuranceService {

	@Inject
	private HealthInsuranceFeeRateHistoryRepository healthInsuranceFeeRateHistoryRepository;

	@Inject
	private BonusHealthInsuranceRateRepository bonusHealthInsuranceRateRepository;

	@Inject
	private HealthInsuranceMonthlyFeeRepository healthInsuranceMonthlyFeeRepository;
	
	@Inject
    private HealthInsuranceStandardMonthlyRepository healthInsuranceStandardMonthlyRepository;

	public void registerHealthInsurance(String officeCode, BonusHealthInsuranceRate bonusHealthInsuranceRate,
			HealthInsuranceMonthlyFee healthInsuranceMonthlyFee, YearMonthHistoryItem yearMonthItem) {
		HealthInsuranceFeeRateHistory welfarePensionHistory = null;
		// find history
		Optional<HealthInsuranceFeeRateHistory> opt_healthInsurance = healthInsuranceFeeRateHistoryRepository
				.getHealthInsuranceFeeRateHistoryByCid(AppContexts.user().companyId(), officeCode);
		// calculation
		// アルゴリズム「月額健康保険料計算処理」を実行する
		healthInsuranceMonthlyFee = calculationGradeFee(bonusHealthInsuranceRate, healthInsuranceMonthlyFee, yearMonthItem);
		if (!opt_healthInsurance.isPresent()) {
			// add new history if no history existed
			welfarePensionHistory = new HealthInsuranceFeeRateHistory(AppContexts.user().companyId(), officeCode,
					Arrays.asList(yearMonthItem));
			healthInsuranceFeeRateHistoryRepository.add(welfarePensionHistory);
			this.addHealthInsurance(bonusHealthInsuranceRate, healthInsuranceMonthlyFee, officeCode, yearMonthItem);
			return;
		}
		// delete old history
		healthInsuranceFeeRateHistoryRepository.deleteByCidAndCode(AppContexts.user().companyId(), officeCode);
		welfarePensionHistory = opt_healthInsurance.get();
		if (!welfarePensionHistory.getHistory().contains(yearMonthItem)) {
			// add history if not exist
			welfarePensionHistory.add(yearMonthItem);
			this.addHealthInsurance(bonusHealthInsuranceRate, healthInsuranceMonthlyFee, officeCode, yearMonthItem);
		} else {
			this.updateHealthInsurance(bonusHealthInsuranceRate, healthInsuranceMonthlyFee, officeCode, yearMonthItem);
		}
		healthInsuranceFeeRateHistoryRepository.add(welfarePensionHistory);
	}

	public void addHealthInsurance(BonusHealthInsuranceRate bonusHealthInsuranceRate,
			HealthInsuranceMonthlyFee healthInsuranceMonthlyFee, String officeCode, YearMonthHistoryItem yearMonth) {
		bonusHealthInsuranceRateRepository.add(bonusHealthInsuranceRate, officeCode, yearMonth);
		healthInsuranceMonthlyFeeRepository.add(healthInsuranceMonthlyFee, officeCode, yearMonth);
	}

	public void updateHealthInsurance(BonusHealthInsuranceRate bonusHealthInsuranceRate,
			HealthInsuranceMonthlyFee healthInsuranceMonthlyFee, String officeCode, YearMonthHistoryItem yearMonth) {
		bonusHealthInsuranceRateRepository.update(bonusHealthInsuranceRate, officeCode, yearMonth);
		healthInsuranceMonthlyFeeRepository.update(healthInsuranceMonthlyFee, officeCode, yearMonth);
	}
	
	public HealthInsuranceMonthlyFee calculationGradeFee (BonusHealthInsuranceRate bonusHealthInsuranceRate, HealthInsuranceMonthlyFee healthInsuranceMonthlyFee, YearMonthHistoryItem yearMonthItem) {
		if (AutoCalculationExecutionCls.AUTO.equals(healthInsuranceMonthlyFee.getAutoCalculationCls())) {
			Optional<HealthInsuranceStandardMonthly> healthInsuranceStandardMonthlyOptional = this.healthInsuranceStandardMonthlyRepository.getHealthInsuranceStandardMonthlyByStartYearMonth(yearMonthItem.start().v());
			healthInsuranceMonthlyFee.algorithmMonthlyHealthInsurancePremiumCalculation(healthInsuranceStandardMonthlyOptional);
		} else {
			healthInsuranceMonthlyFee.updateGradeFee(Collections.EMPTY_LIST);
		}
		return healthInsuranceMonthlyFee;
	}
	
	public void updateHistory (String officeCode, YearMonthHistoryItem yearMonth){
		Optional<HealthInsuranceFeeRateHistory> opt_healthInsurance = healthInsuranceFeeRateHistoryRepository
				.getHealthInsuranceFeeRateHistoryByCid(AppContexts.user().companyId(), officeCode);
		if (!opt_healthInsurance.isPresent()) {
			return;
		}
		HealthInsuranceFeeRateHistory healthInsurance = opt_healthInsurance.get();
		Optional<YearMonthHistoryItem> currentSpan = healthInsurance.getHistory().stream().filter(item -> item.identifier().equals(yearMonth.identifier())).findFirst();
		if (!currentSpan.isPresent()) return;
		healthInsurance.changeSpan(currentSpan.get(), new YearMonthPeriod(yearMonth.start() , yearMonth.end()));
		healthInsuranceFeeRateHistoryRepository.update(healthInsurance);
	}
	
	public void deleteHistory (String officeCode, YearMonthHistoryItem yearMonth){
		Optional<HealthInsuranceFeeRateHistory> opt_healthInsurance = healthInsuranceFeeRateHistoryRepository
				.getHealthInsuranceFeeRateHistoryByCid(AppContexts.user().companyId(), officeCode);
		if (!opt_healthInsurance.isPresent()) {
			return;
		}
		HealthInsuranceFeeRateHistory healthInsurance = opt_healthInsurance.get();
		if (healthInsurance.getHistory().size() == 0) return;
		YearMonthHistoryItem lastestHistory = healthInsurance.getHistory().get(0);
		healthInsurance.remove(lastestHistory);
		if (healthInsurance.getHistory().size() > 0){
			lastestHistory = healthInsurance.getHistory().get(0);
			healthInsurance.changeSpan(healthInsurance.getHistory().get(0),  new YearMonthPeriod(lastestHistory.start(), new YearMonth(new Integer(999912))));
		}
		healthInsuranceFeeRateHistoryRepository.remove(healthInsurance);
		bonusHealthInsuranceRateRepository.deleteByHistoryIds(Arrays.asList(yearMonth.identifier()));
		healthInsuranceMonthlyFeeRepository.deleteByHistoryIds(Arrays.asList(yearMonth.identifier()));
	}
	
	public boolean checkHealthInsuranceGradeFeeChange (String officeCode, BonusHealthInsuranceRate bonusHealthInsuranceRate,
			HealthInsuranceMonthlyFee healthInsuranceMonthlyFee, YearMonthHistoryItem yearMonthItem){
		// calculation
		// アルゴリズム「月額健康保険料計算処理」を実行する
		healthInsuranceMonthlyFee = calculationGradeFee(bonusHealthInsuranceRate, healthInsuranceMonthlyFee, yearMonthItem);
		Optional<HealthInsuranceMonthlyFee> opt_oldHealthInsuranceMonthlyFee = healthInsuranceMonthlyFeeRepository.getHealthInsuranceMonthlyFeeById(bonusHealthInsuranceRate.getHistoryID());
		if (!opt_oldHealthInsuranceMonthlyFee.isPresent()) return false;
		HealthInsuranceMonthlyFee oldHealthInsuranceMonthlyFee = opt_oldHealthInsuranceMonthlyFee.get();
		return !oldHealthInsuranceMonthlyFee.getHealthInsurancePerGradeFee().containsAll(healthInsuranceMonthlyFee.getHealthInsurancePerGradeFee());
	}
}
