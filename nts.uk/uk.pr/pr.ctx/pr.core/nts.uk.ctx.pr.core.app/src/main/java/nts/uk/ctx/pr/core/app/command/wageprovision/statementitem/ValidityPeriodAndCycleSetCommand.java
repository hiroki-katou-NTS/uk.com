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

}
