package nts.uk.ctx.core.dom.socialinsurance.contribution;

import lombok.Getter;
import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.core.dom.socialinsurance.AutoCalculationExecutionCls;
import nts.uk.ctx.core.dom.socialinsurance.InsuranceRate;
import nts.uk.ctx.core.dom.socialinsurance.RoundCalculatedValue;
import nts.uk.ctx.core.dom.socialinsurance.healthinsurance.HealthInsurancePerGradeFee;
import nts.uk.ctx.core.dom.socialinsurance.welfarepensioninsurance.InsurancePremiumFractionClassification;
import nts.uk.ctx.core.dom.socialinsurance.welfarepensioninsurance.WelfarePensionStandardMonthlyFee;

import javax.persistence.Basic;
import javax.persistence.Column;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * 拠出金率
 */
@Getter
public class ContributionRate extends AggregateRoot {

	/**
	 * 履歴ID
	 */
	private String historyId;

	/**
	 * 子ども・子育て拠出金事業主負担率
	 */
	private InsuranceRate childContributionRatio;

	/**
	 * 自動計算区分
	 */
	private AutoCalculationExecutionCls automaticCalculationCls;

	/**
	 * 等級毎拠出金
	 */
	private List<ContributionByGrade> contributionByGrade;

	/**
	 * 拠出金率
	 *
	 * @param historyId
	 *            履歴ID
	 * @param childContributionRatio
	 *            子ども・子育て拠出金事業主負担率
	 * @param automaticCalculationCls
	 *            自動計算区分
	 * @param contributionByGrade
	 *            等級毎拠出金
	 */
	public ContributionRate(String historyId, BigDecimal childContributionRatio, int automaticCalculationCls,
			List<ContributionByGrade> contributionByGrade) {
		super();
		this.historyId = historyId;
		this.childContributionRatio = new InsuranceRate(childContributionRatio);
		this.automaticCalculationCls = EnumAdaptor.valueOf(automaticCalculationCls, AutoCalculationExecutionCls.class);
		this.contributionByGrade = contributionByGrade;
	}

	public void algorithmWelfarePensionInsurancePremiumCal(
			Optional<WelfarePensionStandardMonthlyFee> otpWelfarePensionStandardMonthlyFee,
			ContributionRate contributionRate) {

		this.contributionByGrade.clear();
		if (otpWelfarePensionStandardMonthlyFee.isPresent()) {
			val welfarePensionStandardMonthlyFee = otpWelfarePensionStandardMonthlyFee.get().getStandardMonthlyPrice();

			welfarePensionStandardMonthlyFee.forEach(x -> {
				// 子ども・子育て拠出金 = 取得した「厚生年金標準報酬月額表.等級毎標準報酬月額.標準月額」

				val welfarePensionGrade = x.getWelfarePensionGrade();
				val childCareContribution = x.getStandardMonthlyFee()
						* contributionRate.getChildContributionRatio().v().doubleValue() / 1000;
				BigDecimal childContribution = new BigDecimal(childCareContribution);
				childContribution = RoundCalculatedValue.calculation(x.getStandardMonthlyFee(),
						contributionRate.getChildContributionRatio().v(),
						InsurancePremiumFractionClassification.TRUNCATION, RoundCalculatedValue.ROUND_1_AFTER_DOT);
				this.contributionByGrade.add(new ContributionByGrade(welfarePensionGrade, childContribution));
			});
		}

	}

	public void updateContributionByGrade(List<ContributionByGrade> contributionByGrade) {
		this.contributionByGrade = contributionByGrade;
	}

}
