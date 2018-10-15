package nts.uk.ctx.core.app.command.socialinsurance.contributionrate.command;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.core.dom.socialinsurance.contribution.ContributionRate;
import nts.uk.shr.com.context.AppContexts;

@Data
@AllArgsConstructor
public class ContributionRateCommand {


	/**
	 * 会社ID
	 */
	public String cid;

	/**
	 * 社会保険事業所コード
	 */
	public String socialInsuranceOfficeCd;

	/**
	 * 履歴ID
	 */
	private String historyId;

	/**
	 * 年月開始
	 */
	public int startYearMonth;

	/**
	 * 年月終了
	 */
	public int endYearMonth;

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

	public ContributionRate fromCommandToDomain(String officeCode) {
		return new ContributionRate(AppContexts.user().companyId(),officeCode,this.historyId, this.startYearMonth,this.endYearMonth,this.childContributionRatio, this.automaticCalculationCls,
				this.contributionByGrade.stream().map(x -> x.fromCommandToDomain()).collect(Collectors.toList()));
	}
}
