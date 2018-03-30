package nts.uk.ctx.exio.app.command.exi.codeconvert;

import java.util.List;

import lombok.Value;

@Value
public class AcceptCdConvertCommand {

	/**
	 * 会社ID
	 */
	private String cid;

	/**
	 * コード変換コード
	 */
	private String convertCd;

	/**
	 * コード変換名称
	 */
	private String convertName;

	/**
	 * 設定のないコードの受入
	 */
	private int acceptWithoutSetting;

	private Long version;

	private List<CdConvertDetailsCommand> cdConvertDetails;
}
