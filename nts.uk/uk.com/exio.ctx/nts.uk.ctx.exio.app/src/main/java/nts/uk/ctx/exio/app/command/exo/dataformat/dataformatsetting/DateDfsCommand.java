package nts.uk.ctx.exio.app.command.exo.dataformat.dataformatsetting;

import lombok.Value;

@Value
public class DateDfsCommand {

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
	 * NULL値置換
	 */
	private int nullValueSubstitution;

	/**
	 * 固定値
	 */
	private int fixedValue;

	/**
	 * 形式選択
	 */
	private int formatSelection;

	/**
	 * 固定値の値
	 */
	private String valueOfFixedValue;

	/**
	 * NULL値置換の値
	 */
	private String valueOfNullValueSubs;

}
