package nts.uk.ctx.core.dom.socialinsurance.contribution.service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.YearMonth;
import nts.uk.ctx.core.dom.socialinsurance.AutoCalculationExecutionCls;
import nts.uk.ctx.core.dom.socialinsurance.contribution.ContributionByGrade;
import nts.uk.ctx.core.dom.socialinsurance.contribution.ContributionRate;
import nts.uk.ctx.core.dom.socialinsurance.contribution.ContributionRateHistory;
import nts.uk.ctx.core.dom.socialinsurance.contribution.ContributionRateHistoryRepository;
import nts.uk.ctx.core.dom.socialinsurance.contribution.ContributionRateRepository;
import nts.uk.ctx.core.dom.socialinsurance.welfarepensioninsurance.WelfarePensionStandardMonthlyFee;
import nts.uk.ctx.core.dom.socialinsurance.welfarepensioninsurance.WelfarePensionStandardMonthlyFeeRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;

@Stateless
public class ContributionService {

	@Inject
	private ContributionRateRepository contributionRateRepository;

	@Inject
	private ContributionRateHistoryRepository contributionRateHistoryRepository;
	@Inject
	private WelfarePensionStandardMonthlyFeeRepository welfarePensionStandardMonthlyFeeRepository;

	public void registerContributionRate(String officeCode, ContributionRate contributionRate,
			YearMonthHistoryItem yearMonthItem) {
		String cid = AppContexts.user().companyId();
		ContributionRateHistory contributionRateHistory = null;
		Optional<ContributionRateHistory> optContributionRateHistory = contributionRateHistoryRepository
				.findByCodeAndCid(cid, officeCode);
		// アルゴリズム「月額拠出金計算処理」を実行する
		contributionRate = monthlyContributionCalProcess(contributionRate, yearMonthItem);

		if (!optContributionRateHistory.isPresent()) {
			// add history if not exist
			contributionRateHistory = new ContributionRateHistory(cid, officeCode, Arrays.asList(yearMonthItem));
			this.addContribution(contributionRate);
			contributionRateHistoryRepository.add(contributionRateHistory);
			return;
		}
		// delete old history
		contributionRateHistoryRepository.deleteByCidAndCode(AppContexts.user().companyId(), officeCode);
		contributionRateHistory = optContributionRateHistory.get();
		if (!contributionRateHistory.getHistory().contains(yearMonthItem)) {
			// add history
			contributionRateHistory.add(yearMonthItem);
			this.addContribution(contributionRate);
		} else {
			this.updateContribution(contributionRate);
		}
		contributionRateHistoryRepository.add(contributionRateHistory);
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

	public void addContribution(ContributionRate contributionRate) {
		contributionRateRepository.add(contributionRate);
	}

	public void updateContribution(ContributionRate contributionRate) {
		contributionRateRepository.update(contributionRate);
	}

	public void updateHistory(String officeCode, YearMonthHistoryItem yearMonth) {
		Optional<ContributionRateHistory> optContributionRateHis = contributionRateHistoryRepository
				.getContributionRateHistoryByOfficeCode(officeCode);
		if (!optContributionRateHis.isPresent()) {
			return;
		}
		// get history and change span
		ContributionRateHistory contributionRateHis = optContributionRateHis.get();
		Optional<YearMonthHistoryItem> currentSpan = contributionRateHis.getHistory().stream()
				.filter(item -> item.identifier().equals(yearMonth.identifier())).findFirst();
		if (!currentSpan.isPresent())
			return;
		contributionRateHis.changeSpan(currentSpan.get(), new YearMonthPeriod(yearMonth.start(), yearMonth.end()));
		contributionRateHistoryRepository.update(contributionRateHis);
	}

	public void deleteHistory(String officeCode, YearMonthHistoryItem yearMonth) {
		Optional<ContributionRateHistory> optContributionRateHis = contributionRateHistoryRepository
				.getContributionRateHistoryByOfficeCode(officeCode);
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
		contributionRateHistoryRepository.remove(contributionRateHis);
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

}
