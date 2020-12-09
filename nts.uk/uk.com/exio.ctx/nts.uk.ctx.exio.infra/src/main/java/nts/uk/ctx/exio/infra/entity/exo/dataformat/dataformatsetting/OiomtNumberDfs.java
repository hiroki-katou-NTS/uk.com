package nts.uk.ctx.exio.infra.entity.exo.dataformat.dataformatsetting;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.exio.dom.exo.dataformat.dataformatsetting.NumberDataFmSetting;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 外部出力時刻型データ形式設定（項目単位）
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "OIOMT_EX_OUT_FM_NUM")
public class OiomtNumberDfs extends ContractUkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@EmbeddedId
	public OiomtNumberDfsPk numberDfsPk;

	/**
	 * 形式選択
	 */
	@Basic(optional = false)
	@Column(name = "FORMAT_SELECTION")
	public int formatSelection;

	/**
	 * 小数桁
	 */
	@Basic(optional = true)
	@Column(name = "DECIMAL_DIGIT")
	public Integer decimalDigit;

	/**
	 * 小数点区分
	 */
	@Basic(optional = false)
	@Column(name = "DECIMAL_POINT_ATR")
	public int decimalPointCls;

	/**
	 * 小数端数
	 */
	@Basic(optional = false)
	@Column(name = "DECIMAL_FRACTION")
	public int decimalFraction;

	/**
	 * マイナス値を0で出力
	 */
	@Basic(optional = false)
	@Column(name = "OUTPUT_MINUS_AS_ZERO")
	public int outputMinusAsZero;

	/**
	 * 固定値演算
	 */
	@Basic(optional = false)
	@Column(name = "CALC_ATR")
	public int fixedValueOperation;

	/**
	 * 固定値演算符号
	 */
	@Basic(optional = false)
	@Column(name = "FIXED_VALUE_CALC_SYMBOL")
	public int fixedValueOperationSymbol;

	/**
	 * 固定値演算値
	 */
	@Basic(optional = true)
	@Column(name = "FIXED_CALC_VAL")
	public BigDecimal fixedCalculationValue;

	/**
	 * 固定長出力
	 */
	@Basic(optional = false)
	@Column(name = "FIXED_LENGTH_OUTPUT")
	public int fixedLengthOutput;

	/**
	 * 固定長整数桁
	 */
	@Basic(optional = true)
	@Column(name = "FIXED_LENGTH_INTEGER_DIGIT")
	public Integer fixedLengthIntegerDigit;

	/**
	 * 固定長編集方法
	 */
	@Basic(optional = false)
	@Column(name = "FIXED_LENGTH_EDIT_METHOD")
	public int fixedLengthEditingMethod;

	/**
	 * NULL値置換
	 */
	@Basic(optional = false)
	@Column(name = "NULL_REPLACE_VAL_ATR")
	public int nullValueReplace;

	/**
	 * NULL値置換の値
	 */
	@Basic(optional = true)
	@Column(name = "NULL_REPLACE_VAL")
	public String valueOfNullValueReplace;

	/**
	 * 固定値
	 */
	@Basic(optional = false)
	@Column(name = "FIXED_VAL_ATR")
	public int fixedValue;

	/**
	 * 固定値の値
	 */
	@Basic(optional = true)
	@Column(name = "FIXED_VAL")
	public String valueOfFixedValue;

	@Override
	protected Object getKey() {
		return numberDfsPk;
	}

	public NumberDataFmSetting toDomain() {
		return new NumberDataFmSetting(this.numberDfsPk.cid, this.nullValueReplace, this.valueOfNullValueReplace,
				this.outputMinusAsZero, this.fixedValue, this.valueOfFixedValue, this.fixedValueOperation,
				this.fixedCalculationValue, this.fixedValueOperationSymbol,
				new Integer(this.fixedLengthOutput), this.fixedLengthIntegerDigit, this.fixedLengthEditingMethod,
				new Integer(this.decimalDigit), this.decimalPointCls, this.decimalFraction, this.formatSelection,
				this.numberDfsPk.condSetCd, this.numberDfsPk.outItemCd);
	}

	public static OiomtNumberDfs toEntity(NumberDataFmSetting domain) {
		return new OiomtNumberDfs(
				new OiomtNumberDfsPk(
						domain.getCid(), 
						domain.getConditionSettingCode().v(),
						domain.getOutputItemCode().v()),
				domain.getFormatSelection().value,
				domain.getDecimalDigit().isPresent() ? domain.getDecimalDigit().get().v() : null,
				domain.getDecimalPointClassification().value, 
				domain.getDecimalFraction().value,
				domain.getOutputMinusAsZero().value, 
				domain.getFixedValueOperation().value, 
				domain.getFixedValueOperationSymbol().value,
				domain.getFixedCalculationValue().isPresent() ? domain.getFixedCalculationValue().get().v() : null,
				domain.getFixedLengthOutput().value, 
				domain.getFixedLengthIntegerDigit().isPresent() ? domain.getFixedLengthIntegerDigit().get().v() : null,
				domain.getFixedLengthEditingMethod().value,
				domain.getNullValueReplace().value, 
				domain.getValueOfNullValueReplace().isPresent() ? domain.getValueOfNullValueReplace().get().v() : null,
				domain.getFixedValue().value,
				domain.getValueOfFixedValue().isPresent() ? domain.getValueOfFixedValue().get().v() : null);
	}
}
