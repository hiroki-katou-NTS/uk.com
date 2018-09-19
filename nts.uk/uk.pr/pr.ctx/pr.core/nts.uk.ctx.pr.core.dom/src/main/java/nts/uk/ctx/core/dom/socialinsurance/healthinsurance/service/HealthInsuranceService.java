package nts.uk.ctx.core.dom.socialinsurance.healthinsurance.service;

import java.util.Arrays;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.core.dom.socialinsurance.healthinsurance.BonusHealthInsuranceRate;
import nts.uk.ctx.core.dom.socialinsurance.healthinsurance.BonusHealthInsuranceRateRepository;
import nts.uk.ctx.core.dom.socialinsurance.healthinsurance.HealthInsuranceFeeRateHistory;
import nts.uk.ctx.core.dom.socialinsurance.healthinsurance.HealthInsuranceFeeRateHistoryRepository;
import nts.uk.ctx.core.dom.socialinsurance.healthinsurance.HealthInsuranceMonthlyFee;
import nts.uk.ctx.core.dom.socialinsurance.healthinsurance.HealthInsuranceMonthlyFeeRepository;
import nts.uk.ctx.core.dom.socialinsurance.socialinsuranceoffice.SocialInsuranceOfficeRepository;
import nts.uk.ctx.core.dom.socialinsurance.welfarepensioninsurance.WelfarePensionInsuranceRateHistory;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.YearMonthHistoryItem;

@Stateless
public class HealthInsuranceService {

	@Inject
	private HealthInsuranceFeeRateHistoryRepository healthInsuranceFeeRateHistoryRepository;

	@Inject
	private BonusHealthInsuranceRateRepository bonusHealthInsuranceRateRepository;

	@Inject
	private HealthInsuranceMonthlyFeeRepository healthInsuranceMonthlyFeeRepository;

	public void registerHealthInsurance(String officeCode, BonusHealthInsuranceRate bonusHealthInsuranceRate,
			HealthInsuranceMonthlyFee healthInsuranceMonthlyFee, YearMonthHistoryItem yearMonthItem) {
		HealthInsuranceFeeRateHistory welfarePensionHistory = null;
		Optional<HealthInsuranceFeeRateHistory> opt_healthInsurance = healthInsuranceFeeRateHistoryRepository
				.getHealthInsuranceFeeRateHistoryByCid(AppContexts.user().companyId(), officeCode);
		if (!opt_healthInsurance.isPresent()) {
			welfarePensionHistory = new HealthInsuranceFeeRateHistory(AppContexts.user().companyId(), officeCode,
					Arrays.asList(yearMonthItem));
			healthInsuranceFeeRateHistoryRepository.add(welfarePensionHistory);
			this.addHealthInsurance(bonusHealthInsuranceRate, healthInsuranceMonthlyFee);
			return;
		}
		healthInsuranceFeeRateHistoryRepository.deleteByCidAndCode(AppContexts.user().companyId(), officeCode);
		welfarePensionHistory = opt_healthInsurance.get();
		if (!welfarePensionHistory.getHistory().contains(yearMonthItem)) {
			welfarePensionHistory.add(yearMonthItem);
			this.addHealthInsurance(bonusHealthInsuranceRate, healthInsuranceMonthlyFee);
		} else {
			this.updateHealthInsurance(bonusHealthInsuranceRate, healthInsuranceMonthlyFee);
		}
		healthInsuranceFeeRateHistoryRepository.add(welfarePensionHistory);
	}

	public void addHealthInsurance(BonusHealthInsuranceRate bonusHealthInsuranceRate,
			HealthInsuranceMonthlyFee healthInsuranceMonthlyFee) {
		bonusHealthInsuranceRateRepository.add(bonusHealthInsuranceRate);
		healthInsuranceMonthlyFeeRepository.add(healthInsuranceMonthlyFee);
	}

	public void updateHealthInsurance(BonusHealthInsuranceRate bonusHealthInsuranceRate,
			HealthInsuranceMonthlyFee healthInsuranceMonthlyFee) {
		bonusHealthInsuranceRateRepository.update(bonusHealthInsuranceRate);
		healthInsuranceMonthlyFeeRepository.update(healthInsuranceMonthlyFee);
	}

}
