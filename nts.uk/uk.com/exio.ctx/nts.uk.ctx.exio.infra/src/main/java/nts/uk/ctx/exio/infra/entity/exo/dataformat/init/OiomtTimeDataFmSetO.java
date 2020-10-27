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
import nts.uk.ctx.exio.dom.exo.dataformat.init.TimeDataFmSet;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
* 外部出力時間型データ形式設定（初期値）
*/
@NoArgsConstructor
@Entity
@Table(name = "OIOMT_EX_OUT_FM_TI_INIT")
public class OiomtTimeDataFmSetO extends ContractUkJpaEntity implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
    * ID
    */
    @EmbeddedId
    public OiomtTimeDataFmSetPk timeDataFmSetPk;
    
	/**
	 * 時分/分選択
	 */
	@Basic(optional = false)
	@Column(name = "HOUR_MIN_SELECT")
	public int selectHourMinute;

	/**
	 * データ形式小数桁
	 */
	@Basic(optional = true)
	@Column(name = "MINUTE_FRACTION_DIGIT")
	public Integer minuteFractionDigit;

	/**
	 * 分/小数処理端数区分
	 */
	@Basic(optional = false)
	@Column(name = "DECIMAL_FRACTION")
	public int minuteFractionDigitProcessCls;

	/**
	 * 進数選択
	 */
	@Basic(optional = false)
	@Column(name = "DECIMAL_SELECT")
	public int decimalSelection;

	/**
	 * マイナス値を0で出力
	 */
	@Basic(optional = false)
	@Column(name = "OUTPUT_MINUS_AS_ZERO")
	public int outputMinusAsZero;

	/**
	 * 区切り文字設定
	 */
	@Basic(optional = false)
	@Column(name = "DELIMITER_SET")
	public int delimiterSetting;

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
	public Integer fixedLongIntegerDigit;

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
	public int nullValueSubs;

	/**
	 * NULL値置換の値
	 */
	@Basic(optional = true)
	@Column(name = "NULL_REPLACE_VAL")
	public String valueOfNullValueSubs;
	
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
		return timeDataFmSetPk;
	}

    public TimeDataFmSet toDomain() {
        return new TimeDataFmSet(ItemType.TIME.value, this.timeDataFmSetPk.cid, this.nullValueSubs, this.outputMinusAsZero, this.fixedValue, this.valueOfFixedValue, this.fixedLengthOutput, this.fixedLongIntegerDigit, this.fixedLengthEditingMethod, this.delimiterSetting, this.selectHourMinute, this.minuteFractionDigit, this.decimalSelection, this.fixedValueOperationSymbol, this.fixedValueOperation, this.fixedCalculationValue, this.valueOfNullValueSubs, this.minuteFractionDigitProcessCls);
    }
    
    public static OiomtTimeDataFmSetO toEntity(TimeDataFmSet domain) {
        return new OiomtTimeDataFmSetO(new OiomtTimeDataFmSetPk(domain.getCid()),
        		domain.getNullValueReplace().value,
        		domain.getOutputMinusAsZero().value,
        		domain.getFixedValue().value,
        		domain.getValueOfFixedValue().isPresent() ? domain.getValueOfFixedValue().get().v() : null,
        		domain.getFixedLengthOutput().value,
        		domain.getFixedLongIntegerDigit().isPresent() ? domain.getFixedLongIntegerDigit().get().v() : null,
        		domain.getFixedLengthEditingMethod().value,
        		domain.getDelimiterSetting().value,
        		domain.getSelectHourMinute().value,
        		domain.getMinuteFractionDigit().isPresent() ? domain.getMinuteFractionDigit().get().v() : null,
        		domain.getDecimalSelection().value,
        		domain.getFixedValueOperationSymbol().value,
        		domain.getFixedValueOperation().value,
        		domain.getFixedCalculationValue().isPresent() ? domain.getFixedCalculationValue().get().v() : null,
        		domain.getValueOfNullValueReplace().isPresent() ? domain.getValueOfNullValueReplace().get().v() : null,
        		domain.getMinuteFractionDigitProcessCls().value);
    }

	public OiomtTimeDataFmSetO(OiomtTimeDataFmSetPk timeDataFmSetPk, int nullValueSubs,
			int outputMinusAsZero, int fixedValue, String valueOfFixedValue, int fixedLengthOutput,
			Integer fixedLongIntegerDigit, int fixedLengthEditingMethod, int delimiterSetting, int selectHourMinute,
			Integer minuteFractionDigit, int decimalSelection, int fixedValueOperationSymbol, int fixedValueOperation,
			BigDecimal fixedCalculationValue, String valueOfNullValueSubs, int minuteFractionDigitProcessCls) {
		super();
		this.timeDataFmSetPk = timeDataFmSetPk;
		this.nullValueSubs = nullValueSubs;
		this.outputMinusAsZero = outputMinusAsZero;
		this.fixedValue = fixedValue;
		this.valueOfFixedValue = valueOfFixedValue;
		this.fixedLengthOutput = fixedLengthOutput;
		this.fixedLongIntegerDigit = fixedLongIntegerDigit;
		this.fixedLengthEditingMethod = fixedLengthEditingMethod;
		this.delimiterSetting = delimiterSetting;
		this.selectHourMinute = selectHourMinute;
		this.minuteFractionDigit = minuteFractionDigit;
		this.decimalSelection = decimalSelection;
		this.fixedValueOperationSymbol = fixedValueOperationSymbol;
		this.fixedValueOperation = fixedValueOperation;
		this.fixedCalculationValue = fixedCalculationValue;
		this.valueOfNullValueSubs = valueOfNullValueSubs;
		this.minuteFractionDigitProcessCls = minuteFractionDigitProcessCls;
	}
    

}
