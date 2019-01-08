package nts.uk.ctx.exio.app.command.exo.dataformat.dataformatsetting;

import lombok.Value;

@Value
public class CharacterDfsCommand {

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
	private int nullValueReplace;

	/**
	 * コード編集
	 */
	private int cdEditting;

	/**
	 * コード編集方法
	 */
	private int cdEdittingMethod;

	/**
	 * スペース編集
	 */
	private int spaceEditting;

	/**
	 * 固定値
	 */
	private int fixedValue;

	/**
	 * 有効桁数
	 */
	private int effectDigitLength;

	/**
	 * NULL値置換の値
	 */
	private String valueOfNullValueReplace;

	/**
	 * コード変換コード
	 */
	private String cdConvertCd;

	/**
	 * コード編集桁
	 */
	private int cdEditDigit;

	/**
	 * 固定値の値
	 */
	private String valueOfFixedValue;

	/**
	 * 有効桁数開始桁
	 */
	private int startDigit;

	/**
	 * 有効桁数終了桁
	 */
	private int endDigit;

}
