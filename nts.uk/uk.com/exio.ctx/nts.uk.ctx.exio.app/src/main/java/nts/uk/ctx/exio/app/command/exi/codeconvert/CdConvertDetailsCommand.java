package nts.uk.ctx.exio.app.command.exi.codeconvert;

import lombok.Value;

@Value
public class CdConvertDetailsCommand {

	/**
	 * 会社ID
	 */
	private String cid;

	/**
	 * コード変換コード
	 */
	private String convertCd;

	/**
	 * 行番号
	 */
	private int lineNumber;

	/**
	 * 出力項目
	 */
	private String outputItem;

	/**
	 * 本システムのコード
	 */
	private String systemCd;

	private Long version;

}
