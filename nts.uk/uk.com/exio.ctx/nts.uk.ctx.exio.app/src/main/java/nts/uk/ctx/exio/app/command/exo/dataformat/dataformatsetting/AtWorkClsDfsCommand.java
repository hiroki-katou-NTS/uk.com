package nts.uk.ctx.exio.app.command.exo.dataformat.dataformatsetting;

import lombok.Value;

@Value
public class AtWorkClsDfsCommand {

	/**
	 * 条件設定コード
	 */
	private String condSetCd;

	/**
	 * 出力項目コード
	 */
	private String outItemCd;

	/**
	 * 会社ID
	 */
	private String cid;

	/**
	 * 固定値
	 */
	private int fixedValue;

	/**
	 * 休業時出力
	 */
	private String closedOutput;

	/**
	 * 休職時出力
	 */
	private String absenceOutput;

	/**
	 * 固定値の値
	 */
	private String valueOfFixedValue;

	/**
	 * 在職時出力
	 */
	private String atWorkOutput;

	/**
	 * 退職時出力
	 */
	private String retirementOutput;

}
