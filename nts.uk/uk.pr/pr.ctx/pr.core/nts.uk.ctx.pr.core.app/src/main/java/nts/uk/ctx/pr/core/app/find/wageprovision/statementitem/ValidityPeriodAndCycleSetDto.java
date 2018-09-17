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
				domain.getCycleSetting().getMonthlyList().map(i -> i.getJanuary().value).orElse(null),
				domain.getCycleSetting().getMonthlyList().map(i -> i.getFebruary().value).orElse(null),
				domain.getCycleSetting().getMonthlyList().map(i -> i.getMarch().value).orElse(null),
				domain.getCycleSetting().getMonthlyList().map(i -> i.getApril().value).orElse(null),
				domain.getCycleSetting().getMonthlyList().map(i -> i.getMay().value).orElse(null),
				domain.getCycleSetting().getMonthlyList().map(i -> i.getJune().value).orElse(null),
				domain.getCycleSetting().getMonthlyList().map(i -> i.getJuly().value).orElse(null),
				domain.getCycleSetting().getMonthlyList().map(i -> i.getAugust().value).orElse(null),
				domain.getCycleSetting().getMonthlyList().map(i -> i.getSeptember().value).orElse(null),
				domain.getCycleSetting().getMonthlyList().map(i -> i.getOctober().value).orElse(null),
				domain.getCycleSetting().getMonthlyList().map(i -> i.getNovember().value).orElse(null),
				domain.getCycleSetting().getMonthlyList().map(i -> i.getDecember().value).orElse(null),
				domain.getValidityPeriodSetting().getPeriodAtr().value,
				domain.getValidityPeriodSetting().getYearPeriod().map(i -> i.start().v()).orElse(null),
				domain.getValidityPeriodSetting().getYearPeriod().map(i -> i.end().v()).orElse(null));
	}
}
