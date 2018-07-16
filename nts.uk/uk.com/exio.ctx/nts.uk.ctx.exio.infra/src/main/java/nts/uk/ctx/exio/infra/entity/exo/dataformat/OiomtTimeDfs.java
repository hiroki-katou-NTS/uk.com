package nts.uk.ctx.exio.infra.entity.exo.dataformat;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 時間型データ形式設定
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "OIOMT_TIME_DFS")
public class OiomtTimeDfs extends UkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@EmbeddedId
	public OiomtTimeDfsPk timeDfsPk;

	/**
	 * NULL値置換
	 */
	@Basic(optional = false)
	@Column(name = "NULL_VALUE_SUBS")
	public int nullValueSubs;

	/**
	 * マイナス値を0で出力
	 */
	@Basic(optional = false)
	@Column(name = "OUTPUT_MINUS_AS_ZERO")
	public int outputMinusAsZero;

	/**
	 * 分/小数処理端数区分
	 */
	@Basic(optional = false)
	@Column(name = "MINUTE_FRACTION_DIGIT_PROCESS_CLA")
	public int minuteFractionDigitProcessCla;

	/**
	 * 区切り文字設定
	 */
	@Basic(optional = false)
	@Column(name = "DELIMITER_SETTING")
	public int delimiterSetting;

	/**
	 * 固定値
	 */
	@Basic(optional = false)
	@Column(name = "FIXED_VALUE")
	public int fixedValue;

	/**
	 * 固定値演算
	 */
	@Basic(optional = false)
	@Column(name = "FIXED_VALUE_OPERATION")
	public int fixedValueOperation;

	/**
	 * 固定値演算符号
	 */
	@Basic(optional = false)
	@Column(name = "FIXED_VALUE_OPERATION_SYMBOL")
	public int fixedValueOperationSymbol;

	/**
	 * 固定長出力
	 */
	@Basic(optional = false)
	@Column(name = "FIXED_LENGTH_OUTPUT")
	public int fixedLengthOutput;

	/**
	 * 固定長編集方法
	 */
	@Basic(optional = false)
	@Column(name = "FIXED_LENGTH_EDITING_METHOD")
	public int fixedLengthEditingMethod;

	/**
	 * 時分/分選択
	 */
	@Basic(optional = false)
	@Column(name = "SELECT_HOUR_MINUTE")
	public int selectHourMinute;

	/**
	 * 進数選択
	 */
	@Basic(optional = false)
	@Column(name = "DECIMAL_SELECTION")
	public int decimalSelection;

	/**
	 * NULL値置換の値
	 */
	@Basic(optional = true)
	@Column(name = "VALUE_OF_NULL_VALUE_SUBS")
	public String valueOfNullValueSubs;

	/**
	 * 分/小数処理桁
	 */
	@Basic(optional = true)
	@Column(name = "MINUTE_FRACTION_DIGIT")
	public int minuteFractionDigit;

	/**
	 * 固定値の値
	 */
	@Basic(optional = true)
	@Column(name = "VALUE_OF_FIXED_VALUE")
	public String valueOfFixedValue;

	/**
	 * 固定値演算値
	 */
	@Basic(optional = true)
	@Column(name = "FIXED_CALCULATION_VALUE")
	public String fixedCalculationValue;

	/**
	 * 固定長整数桁
	 */
	@Basic(optional = true)
	@Column(name = "FIXED_LONG_INTEGER_DIGIT")
	public int fixedLongIntegerDigit;

	@Override
	protected Object getKey() {
		return timeDfsPk;
	}
}
