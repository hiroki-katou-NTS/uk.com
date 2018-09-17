package nts.uk.ctx.pr.core.app.command.wageprovision.statementitem;

import lombok.Value;

@Value
public class ValidityPeriodAndCycleSetCommand {

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

}
