package nts.uk.ctx.exio.infra.entity.exo.dataformat.init;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.uk.ctx.exio.dom.exo.base.ItemType;
import nts.uk.ctx.exio.dom.exo.dataformat.init.NumberDataFmSet;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 数値型データ形式設定
 */
@NoArgsConstructor
@Entity
@Table(name = "OIOMT_NUMBER_DATA_FM_SET")
public class OiomtNumberDataFmSet extends UkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@EmbeddedId
	public OiomtNumberDataFmSetPk numberDataFmSetPk;

	/**
	 * NULL値置換
	 */
	@Basic(optional = false)
	@Column(name = "NULL_VALUE_REPLACE")
	public int nullValueReplace;

	/**
	 * NULL値置換の値
	 */
	@Basic(optional = true)
	@Column(name = "VALUE_OF_NULL_VALUE_REPLACE")
	public String valueOfNullValueReplace;

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
	 * 固定値の値
	 */
	@Basic(optional = true)
	@Column(name = "VALUE_OF_FIXED_VALUE")
	public String valueOfFixedValue;

	/**
	 * 固定値演算
	 */
	@Basic(optional = false)
	@Column(name = "FIXED_VALUE_OPERATION")
	public int fixedValueOperation;

	/**
	 * 固定値演算値
	 */
	@Basic(optional = true)
	@Column(name = "FIXED_CALCULATION_VALUE")
	public BigDecimal fixedCalculationValue;

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
	 * 固定長整数桁
	 */
	@Basic(optional = true)
	@Column(name = "FIXED_LENGTH_INTEGER_DIGIT")
	public Integer fixedLengthIntegerDigit;

	/**
	 * 固定長編集方法
	 */
	@Basic(optional = false)
	@Column(name = "FIXED_LENGTH_EDITING_METHOD")
	public int fixedLengthEditingMethod;

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

	@Override
	protected Object getKey() {
		return numberDataFmSetPk;
	}

	public NumberDataFmSet toDomain() {
		return new NumberDataFmSet(ItemType.NUMERIC.value, this.numberDataFmSetPk.cid, this.nullValueReplace,
				this.valueOfNullValueReplace, this.outputMinusAsZero, this.fixedValue, this.valueOfFixedValue,
				this.fixedValueOperation, this.fixedCalculationValue, this.fixedValueOperationSymbol,
				this.fixedLengthOutput, this.fixedLengthIntegerDigit,
				this.fixedLengthEditingMethod, this.decimalDigit, this.decimalPointCls,
				this.decimalFraction, this.formatSelection);
	}

	public static OiomtNumberDataFmSet toEntity(NumberDataFmSet domain) {
		return new OiomtNumberDataFmSet(new OiomtNumberDataFmSetPk(domain.getCid()), domain.getNullValueReplace().value,
				domain.getValueOfNullValueReplace().isPresent() ? domain.getValueOfNullValueReplace().get().v() : null,
				domain.getOutputMinusAsZero().value, domain.getFixedValue().value,
				domain.getValueOfFixedValue().isPresent() ? domain.getValueOfFixedValue().get().v() : null,
				domain.getFixedValueOperation().value,
				domain.getFixedCalculationValue().isPresent() ? domain.getFixedCalculationValue().get().v() : null,
				domain.getFixedValueOperationSymbol().value, domain.getFixedLengthOutput().value,
				domain.getFixedLengthIntegerDigit().isPresent() ? domain.getFixedLengthIntegerDigit().get().v() : null,
				domain.getFixedLengthEditingMethod().value,
				domain.getDecimalDigit().isPresent() ? domain.getDecimalDigit().get().v() : null,
				domain.getDecimalPointClassification().value, domain.getDecimalFraction().value,
				domain.getFormatSelection().value);
	}

	public OiomtNumberDataFmSet(OiomtNumberDataFmSetPk numberDataFmSetPk, int nullValueReplace,
			String valueOfNullValueReplace, int outputMinusAsZero, int fixedValue, String valueOfFixedValue,
			int fixedValueOperation, BigDecimal fixedCalculationValue, int fixedValueOperationSymbol,
			int fixedLengthOutput, Integer fixedLengthIntegerDigit, int fixedLengthEditingMethod, Integer decimalDigit,
			int decimalPointClassification, int decimalFraction, int formatSelection) {
		super();
		this.numberDataFmSetPk = numberDataFmSetPk;
		this.nullValueReplace = nullValueReplace;
		this.valueOfNullValueReplace = valueOfNullValueReplace;
		this.outputMinusAsZero = outputMinusAsZero;
		this.fixedValue = fixedValue;
		this.valueOfFixedValue = valueOfFixedValue;
		this.fixedValueOperation = fixedValueOperation;
		this.fixedCalculationValue = fixedCalculationValue;
		this.fixedValueOperationSymbol = fixedValueOperationSymbol;
		this.fixedLengthOutput = fixedLengthOutput;
		this.fixedLengthIntegerDigit = fixedLengthIntegerDigit;
		this.fixedLengthEditingMethod = fixedLengthEditingMethod;
		this.decimalDigit = decimalDigit;
		this.decimalPointCls = decimalPointClassification;
		this.decimalFraction = decimalFraction;
		this.formatSelection = formatSelection;
	}

}
