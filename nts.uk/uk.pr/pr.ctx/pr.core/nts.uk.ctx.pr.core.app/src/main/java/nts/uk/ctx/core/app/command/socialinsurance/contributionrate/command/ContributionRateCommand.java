package nts.uk.ctx.core.app.command.socialinsurance.contributionrate.command;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.core.dom.socialinsurance.contribution.ContributionRate;

@Data
@AllArgsConstructor
public class ContributionRateCommand {
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

	private List<ContributionByGradeCommand> contributionByGrade;

	public ContributionRate fromCommandToDomain() {
		return new ContributionRate(this.historyId, this.childContributionRatio, this.automaticCalculationCls,
				this.contributionByGrade.stream().map(x -> x.fromCommandToDomain()).collect(Collectors.toList()));
	}
}
