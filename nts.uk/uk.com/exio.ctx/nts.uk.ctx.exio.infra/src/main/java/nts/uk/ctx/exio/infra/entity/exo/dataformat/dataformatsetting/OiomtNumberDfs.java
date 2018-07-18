package nts.uk.ctx.exio.infra.entity.exo.dataformat.dataformatsetting;

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
 * 数値型データ形式設定
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "OIOMT_NUMBER_DFS")
public class OiomtNumberDfs extends UkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@EmbeddedId
	public OiomtNumberDfsPk numberDfsPk;

	/**
	 * NULL値置換
	 */
	@Basic(optional = false)
	@Column(name = "NULL_VALUE_REPLACE")
	public int nullValueReplace;

	/**
	 * マイナス値を0で出力
	 */
	@Basic(optional = false)
	@Column(name = "OUTPUT_MINUS_AS_ZERO")
	public int outputMinusAsZero;

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
	 * 小数点区分
	 */
	@Basic(optional = false)
	@Column(name = "DECIMAL_POINT_CLS")
	public int decimalPointCls;

	/**
	 * 小数端数
	 */
	@Basic(optional = false)
	@Column(name = "DECIMAL_FRACTION")
	public int decimalFraction;

	/**
	 * 形式選択
	 */
	@Basic(optional = false)
	@Column(name = "FORMAT_SELECTION")
	public int formatSelection;

	/**
	 * NULL値置換の値
	 */
	@Basic(optional = true)
	@Column(name = "VALUE_OF_NULL_VALUE_REPLACE")
	public String valueOfNullValueReplace;

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
	@Column(name = "FIXED_LENGTH_INTEGER_DIGIT")
	public int fixedLengthIntegerDigit;

	/**
	 * 小数桁
	 */
	@Basic(optional = true)
	@Column(name = "DECIMAL_DIGIT")
	public int decimalDigit;

	@Override
	protected Object getKey() {
		return numberDfsPk;
	}
}
