package nts.uk.ctx.pr.core.app.find.socialinsurance.contributionrate.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.pr.core.dom.socialinsurance.contribution.ContributionByGrade;

@AllArgsConstructor
@Data
public class ContributionByGradeDto {
	/**
	 * 厚生年金等級
	 */
	private int welfarePensionGrade;

	/**
	 * 子ども・子育て拠出金
	 */
	private BigDecimal childCareContribution;

	public static ContributionByGradeDto fromDomainToDto(ContributionByGrade domain) {
		return new ContributionByGradeDto(domain.getWelfarePensionGrade(), domain.getChildCareContribution().v());
	}
}
