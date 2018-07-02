package nts.uk.ctx.exio.infra.entity.exo.dataformat;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.uk.ctx.exio.dom.exo.datafomat.TimeDataFmSet;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
* 時間型データ形式設定
*/

@NoArgsConstructor
@Entity
@Table(name = "OIOMT_TIME_DATA_FM_SET_O")
public class OiomtTimeDataFmSetO extends UkJpaEntity implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
    * ID
    */
    @EmbeddedId
    public OiomtTimeDataFmSetPk timeDataFmSetPk;
    
    
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
    * 固定長出力
    */
    @Basic(optional = false)
    @Column(name = "FIXED_LENGTH_OUTPUT")
    public int fixedLengthOutput;
    
    /**
    * 固定長整数桁
    */
    @Basic(optional = true)
    @Column(name = "FIXED_LONG_INTEGER_DIGIT")
    public int fixedLongIntegerDigit;
    
    /**
    * 固定長編集方法
    */
    @Basic(optional = false)
    @Column(name = "FIXED_LENGTH_EDITING_MOTHOD")
    public int fixedLengthEditingMothod;
    
    /**
    * 区切り文字設定
    */
    @Basic(optional = false)
    @Column(name = "DELIMITER_SETTING")
    public int delimiterSetting;
    
    /**
    * 前日出力方法
    */
    @Basic(optional = false)
    @Column(name = "SELECT_HOUR_MINUTE")
    public int selectHourMinute;
    
    /**
    * データ形式小数桁
    */
    @Basic(optional = true)
    @Column(name = "MINUTE_FRACTION_DIGIT")
    public int minuteFractionDigit;
    
    /**
    * 進数選択
    */
    @Basic(optional = false)
    @Column(name = "DECIMAL_SELECTION")
    public int decimalSelection;
    
    /**
    * 固定値演算符号
    */
    @Basic(optional = false)
    @Column(name = "FIXED_VALUE_OPERATION_SYMBOL")
    public int fixedValueOperationSymbol;
    
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
    public String fixedCalculationValue;
    
    /**
    * NULL値置換の値
    */
    @Basic(optional = true)
    @Column(name = "VALUE_OF_NULL_VALUE_SUBS")
    public String valueOfNullValueSubs;
    
    /**
    * 分/小数処理端数区分
    */
    @Basic(optional = false)
    @Column(name = "MINUTE_FRACTION_DIGIT_PROCESS_CLA")
    public int minuteFractionDigitProcessCla;
    
    @Override
    protected Object getKey()
    {
        return timeDataFmSetPk;
    }

    public TimeDataFmSet toDomain() {
        return new TimeDataFmSet(this.timeDataFmSetPk.cid, this.nullValueSubs, this.outputMinusAsZero, this.fixedValue, this.valueOfFixedValue, this.fixedLengthOutput, this.fixedLongIntegerDigit, this.fixedLengthEditingMothod, this.delimiterSetting, this.selectHourMinute, this.minuteFractionDigit, this.decimalSelection, this.fixedValueOperationSymbol, this.fixedValueOperation, this.fixedCalculationValue, this.valueOfNullValueSubs, this.minuteFractionDigitProcessCla);
    }
    public static OiomtTimeDataFmSetO toEntity(TimeDataFmSet domain) {
        return new OiomtTimeDataFmSetO(new OiomtTimeDataFmSetPk(domain.getCid()),
        		domain.getNullValueSubs().value,
        		domain.getOutputMinusAsZero().value,
        		domain.getFixedValue().value,
        		domain.getValueOfFixedValue().orElse(null).v(),
        		domain.getFixedLengthOutput().value,
        		domain.getFixedLongIntegerDigit().orElse(null).v(),
        		domain.getFixedLengthEditingMothod().value,
        		domain.getDelimiterSetting().value,
        		domain.getSelectHourMinute().value,
        		domain.getMinuteFractionDigit().orElse(null).v(),
        		domain.getDecimalSelection().value,
        		domain.getFixedValueOperationSymbol().value,
        		domain.getFixedValueOperation().value,
        		domain.getFixedCalculationValue(),
        		domain.getValueOfNullValueSubs().orElse(null).v(),
        		domain.getMinuteFractionDigitProcessCla().value);
    }

	public OiomtTimeDataFmSetO(OiomtTimeDataFmSetPk timeDataFmSetPk, int nullValueSubs,
			int outputMinusAsZero, int fixedValue, String valueOfFixedValue, int fixedLengthOutput,
			int fixedLongIntegerDigit, int fixedLengthEditingMothod, int delimiterSetting, int selectHourMinute,
			int minuteFractionDigit, int decimalSelection, int fixedValueOperationSymbol, int fixedValueOperation,
			String fixedCalculationValue, String valueOfNullValueSubs, int minuteFractionDigitProcessCla) {
		super();
		this.timeDataFmSetPk = timeDataFmSetPk;
		this.nullValueSubs = nullValueSubs;
		this.outputMinusAsZero = outputMinusAsZero;
		this.fixedValue = fixedValue;
		this.valueOfFixedValue = valueOfFixedValue;
		this.fixedLengthOutput = fixedLengthOutput;
		this.fixedLongIntegerDigit = fixedLongIntegerDigit;
		this.fixedLengthEditingMothod = fixedLengthEditingMothod;
		this.delimiterSetting = delimiterSetting;
		this.selectHourMinute = selectHourMinute;
		this.minuteFractionDigit = minuteFractionDigit;
		this.decimalSelection = decimalSelection;
		this.fixedValueOperationSymbol = fixedValueOperationSymbol;
		this.fixedValueOperation = fixedValueOperation;
		this.fixedCalculationValue = fixedCalculationValue;
		this.valueOfNullValueSubs = valueOfNullValueSubs;
		this.minuteFractionDigitProcessCla = minuteFractionDigitProcessCla;
	}
    

}
