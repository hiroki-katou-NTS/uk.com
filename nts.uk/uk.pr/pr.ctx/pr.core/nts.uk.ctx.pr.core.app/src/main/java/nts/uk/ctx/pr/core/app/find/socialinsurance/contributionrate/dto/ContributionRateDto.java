package nts.uk.ctx.pr.core.app.find.socialinsurance.contributionrate.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

@Data

public class ContributionRateDto {
	/**
	 * 履歴ID
	 */
	private String historyId;

	/**
	 * 子ども・子育て拠出金事業主負担率
	 */
	private BigDecimal childContributionRatio;

	/**
	 * 自動計算区分
	 */
	private int automaticCalculationCls;

	/**
	 * 等級毎拠出金
	 */

	private List<ContributionByGradeDto> contributionByGrade;

	public ContributionRateDto(String historyId, BigDecimal childContributionRatio, int automaticCalculationCls,
			List<ContributionByGradeDto> contributionByGrade) {
		this.historyId = historyId;
		this.childContributionRatio = childContributionRatio;
		this.automaticCalculationCls = automaticCalculationCls;
		this.contributionByGrade = contributionByGrade;
	}

}
