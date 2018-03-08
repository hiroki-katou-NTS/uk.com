package nts.uk.ctx.exio.app.find.exi.dataformat;

import lombok.Value;
import nts.uk.ctx.exio.dom.exi.dataformat.InsTimeDatFmSet;

/**
 * 時刻型データ形式設定
 */

@Value
public class InsTimeDatFmSetDto {

	/**
	 * 区切り文字設定
	 */
	private int delimiterSet;

	/**
	 * 固定値
	 */
	private int fixedValue;

	/**
	 * 時分/分選択
	 */
	private int hourMinSelect;

	/**
	 * 有効桁長
	 */
	private int effectiveDigitLength;

	/**
	 * 端数処理
	 */
	private int roundProc;

	/**
	 * 進数選択
	 */
	private int decimalSelect;

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

	/**
	 * 端数処理区分
	 */
	private int roundProcCls;

	public static InsTimeDatFmSetDto fromDomain(InsTimeDatFmSet domain) {
		return new InsTimeDatFmSetDto(domain.getDelimiterSet().value, domain.getFixedValue().value,
				domain.getHourMinSelect().value, domain.getEffectiveDigitLength().value, domain.getRoundProc().value,
				domain.getDecimalSelect().value, domain.getValueOfFixedValue().get().v(),
				domain.getStartDigit().get().v(), domain.getEndDigit().get().v(), domain.getRoundProcCls().get().value);
	}

}
