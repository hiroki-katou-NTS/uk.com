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
	private boolean january;

	/**
	 * 対象月リスト
	 */
	private boolean february;

	/**
	 * 対象月リスト
	 */
	private boolean march;

	/**
	 * 対象月リスト
	 */
	private boolean april;

	/**
	 * 対象月リスト
	 */
	private boolean may;

	/**
	 * 対象月リスト
	 */
	private boolean june;

	/**
	 * 対象月リスト
	 */
	private boolean july;

	/**
	 * 対象月リスト
	 */
	private boolean august;

	/**
	 * 対象月リスト
	 */
	private boolean september;

	/**
	 * 対象月リスト
	 */
	private boolean october;

	/**
	 * 対象月リスト
	 */
	private boolean november;

	/**
	 * 対象月リスト
	 */
	private boolean december;

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
				domain.getCycleSetting().getMonthlyList().map(i -> i.getJanuary().value > 0).orElse(false),
				domain.getCycleSetting().getMonthlyList().map(i -> i.getFebruary().value > 0).orElse(false),
				domain.getCycleSetting().getMonthlyList().map(i -> i.getMarch().value > 0).orElse(false),
				domain.getCycleSetting().getMonthlyList().map(i -> i.getApril().value > 0).orElse(false),
				domain.getCycleSetting().getMonthlyList().map(i -> i.getMay().value > 0).orElse(false),
				domain.getCycleSetting().getMonthlyList().map(i -> i.getJune().value > 0).orElse(false),
				domain.getCycleSetting().getMonthlyList().map(i -> i.getJuly().value > 0).orElse(false),
				domain.getCycleSetting().getMonthlyList().map(i -> i.getAugust().value > 0).orElse(false),
				domain.getCycleSetting().getMonthlyList().map(i -> i.getSeptember().value > 0).orElse(false),
				domain.getCycleSetting().getMonthlyList().map(i -> i.getOctober().value > 0).orElse(false),
				domain.getCycleSetting().getMonthlyList().map(i -> i.getNovember().value > 0).orElse(false),
				domain.getCycleSetting().getMonthlyList().map(i -> i.getDecember().value > 0).orElse(false),
				domain.getValidityPeriodSetting().getPeriodAtr().value,
				domain.getValidityPeriodSetting().getYearPeriod().map(i -> i.start().v()).orElse(null),
				domain.getValidityPeriodSetting().getYearPeriod().map(i -> i.end().v()).orElse(null));
	}
}
