package nts.uk.ctx.pr.core.dom.socialinsurance.contribution.service;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.core.dom.socialinsurance.AutoCalculationExecutionCls;
import nts.uk.ctx.pr.core.dom.socialinsurance.contribution.ContributionRate;
import nts.uk.ctx.pr.core.dom.socialinsurance.contribution.ContributionRateHistory;
import nts.uk.ctx.pr.core.dom.socialinsurance.contribution.ContributionRateRepository;
import nts.uk.ctx.pr.core.dom.socialinsurance.welfarepensioninsurance.WelfarePensionStandardMonthlyFee;
import nts.uk.ctx.pr.core.dom.socialinsurance.welfarepensioninsurance.WelfarePensionStandardMonthlyFeeRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;

@Stateless
public class ContributionService {

	@Inject
	private ContributionRateRepository contributionRateRepository;

	@Inject
	private WelfarePensionStandardMonthlyFeeRepository welfarePensionStandardMonthlyFeeRepository;

	public void registerContributionRate(String officeCode, ContributionRate contributionRate,
			YearMonthHistoryItem yearMonthItem) {
		String cid = AppContexts.user().companyId();
		ContributionRateHistory contributionRateHistory = null;
		Optional<ContributionRateHistory> optContributionRateHistory = contributionRateRepository.getContributionRateHistoryByOfficeCode(officeCode);
		// アルゴリズム「月額拠出金計算処理」を実行する
		contributionRate = monthlyContributionCalProcess(contributionRate, yearMonthItem);

		if (!optContributionRateHistory.isPresent()) {
			// add history if not exist
			this.addContribution(contributionRate, officeCode, yearMonthItem);
			return;
		}
		// delete old history
		contributionRateHistory = optContributionRateHistory.get();
		if (!contributionRateHistory.getHistory().contains(yearMonthItem)) {
			// add history
			contributionRateHistory.add(yearMonthItem);
			this.addContribution(contributionRate, officeCode, yearMonthItem);
			// if have previous history
			if (contributionRateHistory.getHistory().size() > 1) {
			    this.updateHistoryItemSpan(officeCode, contributionRateHistory.getHistory().get(0));
            }
		} else {
			this.updateContribution(contributionRate, officeCode, yearMonthItem);
		}
	}

	public boolean checkContributionRate(ContributionRate contributionRate,
			YearMonthHistoryItem yearMonthItem) {
		 //String cid = AppContexts.user().companyId();

		// ドメインモデル「拠出金率」を取得する
		Optional<ContributionRate> otpContributionRate = contributionRateRepository
				.getContributionRateByHistoryId(contributionRate.getHistoryId());
		// アルゴリズム「月額拠出金計算処理」を実行する
		contributionRate = monthlyContributionCalProcess(contributionRate, yearMonthItem);
		// 取得したドメインモデル「拠出金率.等級毎拠出金」と計算した値を比較する
		boolean checker = otpContributionRate.get().getChildContributionRatio().v().equals(contributionRate.getChildContributionRatio().v());
		return checker;
	}

	public void addContribution(ContributionRate contributionRate, String officeCode, YearMonthHistoryItem yearMonth) {
		contributionRateRepository.add(contributionRate, officeCode, yearMonth);
	}

	public void updateContribution(ContributionRate contributionRate, String officeCode, YearMonthHistoryItem yearMonth) {
		contributionRateRepository.update(contributionRate, officeCode, yearMonth);
	}

	public void updateHistory(String officeCode, YearMonthHistoryItem yearMonth) {
		Optional<ContributionRateHistory> optContributionRateHis = contributionRateRepository.getContributionRateHistoryByOfficeCode(officeCode);
		if (!optContributionRateHis.isPresent()) {
			return;
		}
		// get history and change span
        this.updateHistoryItemSpan(officeCode, yearMonth);
		ContributionRateHistory contributionRateHis = optContributionRateHis.get();
		int currentIndex = contributionRateHis.getHistory().indexOf(yearMonth);
        try {
            YearMonthHistoryItem previousHistory = contributionRateHis.getHistory().get(currentIndex + 1);
            contributionRateHis.changeSpan(previousHistory, new YearMonthPeriod(previousHistory.start() , yearMonth.start().addMonths(-1)));
            this.updateHistoryItemSpan(officeCode, contributionRateHis.getHistory().get(currentIndex + 1));
        } catch (IndexOutOfBoundsException e){
            return;
        }
	}

	public void deleteHistory(String officeCode, YearMonthHistoryItem yearMonth) {
		Optional<ContributionRateHistory> optContributionRateHis = contributionRateRepository.getContributionRateHistoryByOfficeCode(officeCode);
		if (!optContributionRateHis.isPresent()) {
			return;
		}
		// remove last history and update previous history
		ContributionRateHistory contributionRateHis = optContributionRateHis.get();
		if (contributionRateHis.getHistory().size() == 0)
			return;
		YearMonthHistoryItem lastestHistory = contributionRateHis.getHistory().get(0);
		contributionRateHis.remove(lastestHistory);
		if (contributionRateHis.getHistory().size() > 0) {
			lastestHistory = contributionRateHis.getHistory().get(0);
			contributionRateHis.changeSpan(contributionRateHis.getHistory().get(0),
					new YearMonthPeriod(lastestHistory.start(), new YearMonth(new Integer(999912))));
		}
		this.updateHistoryItemSpan(officeCode, lastestHistory);
		contributionRateRepository.deleteByHistoryIds(Arrays.asList(yearMonth.identifier()),officeCode);
		contributionRateRepository.deleteContributionByGradeByHistoryId(Arrays.asList(yearMonth.identifier()));
	}

	// 月額拠出金計算処理
	public ContributionRate monthlyContributionCalProcess(ContributionRate contributionRate,
			YearMonthHistoryItem yearMonthItem) {
		if (AutoCalculationExecutionCls.AUTO.equals(contributionRate.getAutomaticCalculationCls())) {
			Optional<WelfarePensionStandardMonthlyFee> otpWelfarePensionStandardMonthlyFee = this.welfarePensionStandardMonthlyFeeRepository
					.getWelfarePensionStandardMonthlyFeeByStartYearMonth(yearMonthItem.start().v());

			contributionRate.algorithmWelfarePensionInsurancePremiumCal(otpWelfarePensionStandardMonthlyFee,
					contributionRate);
		} else {
			contributionRate.updateContributionByGrade(Collections.EMPTY_LIST);
		}
		return contributionRate;
	}

	private void updateHistoryItemSpan (String officeCode, YearMonthHistoryItem yearMonth) {
	    contributionRateRepository.updateHistoryItem(officeCode, yearMonth);
    }

}
