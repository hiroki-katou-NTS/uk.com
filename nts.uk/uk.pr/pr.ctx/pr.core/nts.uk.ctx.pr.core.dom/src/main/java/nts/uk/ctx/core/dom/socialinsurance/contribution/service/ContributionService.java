package nts.uk.ctx.core.dom.socialinsurance.contribution.service;

import java.util.Arrays;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.core.dom.socialinsurance.AutoCalculationExecutionCls;
import nts.uk.ctx.core.dom.socialinsurance.contribution.ContributionRate;
import nts.uk.ctx.core.dom.socialinsurance.contribution.ContributionRateHistory;
import nts.uk.ctx.core.dom.socialinsurance.contribution.ContributionRateHistoryRepository;
import nts.uk.ctx.core.dom.socialinsurance.contribution.ContributionRateRepository;
import nts.uk.ctx.core.dom.socialinsurance.welfarepensioninsurance.WelfarePensionStandardMonthlyFee;
import nts.uk.ctx.core.dom.socialinsurance.welfarepensioninsurance.WelfarePensionStandardMonthlyFeeRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.YearMonthHistoryItem;

@Stateless
public class ContributionService {

	@Inject
	private ContributionRateRepository contributionRateRepository;
	@Inject
	private ContributionRateHistoryRepository contributionRateHistoryRepository;
	@Inject
	WelfarePensionStandardMonthlyFeeRepository welfarePensionStandardMonthlyFeeRepository;

	public void registerContributionRate(String officeCode, ContributionRate contributionRate,
			YearMonthHistoryItem yearMonthItem) {
		String cid = AppContexts.user().companyId();
		ContributionRateHistory contributionRateHistory = null;
		Optional<ContributionRateHistory> optContributionRateHistory = contributionRateHistoryRepository
				.findByCodeAndCid(cid, officeCode);
		// アルゴリズム「月額拠出金計算処理」を実行する
		monthlyContributionCalProcess(contributionRate, yearMonthItem);
		/*if (!optContributionRateHistory.isPresent()) {
			contributionRateHistory = new ContributionRateHistory(cid, officeCode, Arrays.asList(yearMonthItem));
			contributionRateHistoryRepository.add(contributionRateHistory);
		}
		contributionRateHistoryRepository.deleteByCidAndCode(cid, officeCode);*/

		//contributionRateHistory = optContributionRateHistory.get();
		if (!optContributionRateHistory.isPresent()) {
			// add history if not exist
			contributionRateHistory = new ContributionRateHistory(cid, officeCode, Arrays.asList(yearMonthItem));
			this.addHistoryContribution(contributionRateHistory);
		} else {
			this.updateHistoryContribution(contributionRateHistory);
		}

		/*if (!contributionRateHistory.getHistory().contains(yearMonthItem)) {
			contributionRateHistory.add(yearMonthItem);
		}
		contributionRateHistoryRepository.add(contributionRateHistory);*/
	}

	public void addHistoryContribution(ContributionRateHistory contributionRateHistory) {
		contributionRateHistoryRepository.add(contributionRateHistory);		
	}
	
	public void updateHistoryContribution(ContributionRateHistory contributionRateHistory) {
		contributionRateHistoryRepository.update(contributionRateHistory);		
	}

	// 月額拠出金計算処理
	public void monthlyContributionCalProcess(ContributionRate contributionRate, YearMonthHistoryItem yearMonthItem) {
		if (AutoCalculationExecutionCls.AUTO.equals(contributionRate.getAutomaticCalculationCls())) {
			Optional<WelfarePensionStandardMonthlyFee> otpWelfarePensionStandardMonthlyFee = this.welfarePensionStandardMonthlyFeeRepository
					.getWelfarePensionStandardMonthlyFeeByStartYearMonth(yearMonthItem.start().v());

			contributionRate.algorithmWelfarePensionInsurancePremiumCal(otpWelfarePensionStandardMonthlyFee,
					contributionRate);
		}
	}

}
