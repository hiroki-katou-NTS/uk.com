package nts.uk.ctx.core.app.command.socialinsurance.contributionrate.command;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.core.dom.socialinsurance.contribution.ContributionByGrade;

@Data
@AllArgsConstructor
public class ContributionByGradeCommand {
	/**
	 * 厚生年金等級
	 */
	private int welfarePensionGrade;

	/**
	 * 子ども・子育て拠出金
	 */
	private BigDecimal childCareContribution;

	public ContributionByGrade fromCommandToDomain() {
		return new ContributionByGrade(this.welfarePensionGrade, this.childCareContribution);
	}
}
