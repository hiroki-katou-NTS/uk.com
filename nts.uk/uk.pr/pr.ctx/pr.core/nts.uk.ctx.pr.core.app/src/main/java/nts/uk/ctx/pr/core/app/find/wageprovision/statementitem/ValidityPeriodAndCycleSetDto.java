package nts.uk.ctx.pr.core.app.find.wageprovision.statementitem;

import lombok.Value;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.validityperiodset.SetValidityPeriodCycle;

@Value
public class ValidityPeriodAndCycleSetDto {

	/**
	 * 給与項目ID
	 */
	private String salaryItemId;

	/**
	 * サイクル設定区分
	 */
	private int cycleSettingAtr;

	/**
	 * 対象月リスト
	 */
	private Integer january;

	/**
	 * 対象月リスト
	 */
	private Integer february;

	/**
	 * 対象月リスト
	 */
	private Integer march;

	/**
	 * 対象月リスト
	 */
	private Integer april;

	/**
	 * 対象月リスト
	 */
	private Integer may;

	/**
	 * 対象月リスト
	 */
	private Integer june;

	/**
	 * 対象月リスト
	 */
	private Integer july;

	/**
	 * 対象月リスト
	 */
	private Integer august;

	/**
	 * 対象月リスト
	 */
	private Integer september;

	/**
	 * 対象月リスト
	 */
	private Integer october;

	/**
	 * 対象月リスト
	 */
	private Integer november;

	/**
	 * 対象月リスト
	 */
	private Integer december;

	/**
	 * 有効期間設定区分
	 */
	private int periodAtr;

	/**
	 * 年期間(開始年)
	 */
	private Integer yearPeriodStart;

	/**
	 * 年期間(終了年)
	 */
	private Integer yearPeriodEnd;

	public static ValidityPeriodAndCycleSetDto fromDomain(SetValidityPeriodCycle domain) {
		return new ValidityPeriodAndCycleSetDto(domain.getSalaryItemId(),
				domain.getCycleSetting().getCycleSettingAtr().value,
				domain.getCycleSetting().getMonthlyList().getJanuary().orElse(null),
				domain.getCycleSetting().getMonthlyList().getFebruary().orElse(null),
				domain.getCycleSetting().getMonthlyList().getMarch().orElse(null),
				domain.getCycleSetting().getMonthlyList().getApril().orElse(null),
				domain.getCycleSetting().getMonthlyList().getMay().orElse(null),
				domain.getCycleSetting().getMonthlyList().getJune().orElse(null),
				domain.getCycleSetting().getMonthlyList().getJuly().orElse(null),
				domain.getCycleSetting().getMonthlyList().getAugust().orElse(null),
				domain.getCycleSetting().getMonthlyList().getSeptember().orElse(null),
				domain.getCycleSetting().getMonthlyList().getOctober().orElse(null),
				domain.getCycleSetting().getMonthlyList().getNovember().orElse(null),
				domain.getCycleSetting().getMonthlyList().getDecember().orElse(null),
				domain.getValidityPeriodSetting().getPeriodAtr().value,
				domain.getValidityPeriodSetting().getYearPeriod().start().v(),
				domain.getValidityPeriodSetting().getYearPeriod().end().v());
	}
}
