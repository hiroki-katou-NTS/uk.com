package nts.uk.ctx.exio.app.command.exo.dataformat;

import lombok.Value;

@Value
public class DateFormatSettingCommand {
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
	 * 固定値の値
	 */
	private String valueOfFixedValue;

	/**
	 * NULL値置換の値
	 */
	private String valueOfNullValueSubs;

	/**
	 * 形式選択
	 */
	private int formatSelection;
}
