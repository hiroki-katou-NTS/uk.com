package nts.uk.ctx.exio.infra.entity.exo.dataformat;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.uk.ctx.exio.dom.exo.datafomat.InTimeDataFmSet;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
* 時刻型データ形式設定
*/
@NoArgsConstructor
@Entity
@Table(name = "OIOMT_IN_TIME_DATA_FM_SET")
public class OiomtInTimeDataFmSet extends UkJpaEntity implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
    * ID
    */
    @EmbeddedId
    public OiomtInTimeDataFmSetPk inTimeDataFmSetPk;
    
    
    /**
    * NULL値置換
    */
    @Basic(optional = false)
    @Column(name = "NULL_VALUE_SUBS")
    public int nullValueSubs;
    
    /**
    * NULL値置換の値
    */
    @Basic(optional = true)
    @Column(name = "VALUE_OF_NULL_VALUE_SUBS")
    public String valueOfNullValueSubs;
    
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
    * 時分/分選択
    */
    @Basic(optional = false)
    @Column(name = "TIME_SELETION")
    public int timeSeletion;
    
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
    @Column(name = "PREVIOUS_DAY_OUTPUT_METHOD")
    public int previousDayOutputMethod;
    
    /**
    * 前日出力方法
    */
    @Basic(optional = false)
    @Column(name = "NEXT_DAY_OUTPUT_METHOD")
    public int nextDayOutputMethod;
    
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
    * 分/小数処理端数区分
    */
    @Basic(optional = false)
    @Column(name = "MINUTE_FRACTION_DIGIT_PROCESS_CLA")
    public int minuteFractionDigitProcessCla;
    
    @Override
    protected Object getKey()
    {
        return inTimeDataFmSetPk;
    }

    public InTimeDataFmSet toDomain() {
        return new InTimeDataFmSet(this.inTimeDataFmSetPk.cid, this.nullValueSubs, this.valueOfNullValueSubs, this.outputMinusAsZero, this.fixedValue, this.valueOfFixedValue, this.timeSeletion, this.fixedLengthOutput, this.fixedLongIntegerDigit, this.fixedLengthEditingMothod, this.delimiterSetting, this.previousDayOutputMethod, this.nextDayOutputMethod, this.minuteFractionDigit, this.decimalSelection, this.minuteFractionDigitProcessCla);
    }
    public static OiomtInTimeDataFmSet toEntity(InTimeDataFmSet domain) {
        return new OiomtInTimeDataFmSet(
        		new OiomtInTimeDataFmSetPk(domain.getCid()),
        		domain.getNullValueSubs().value,
        		domain.getValueOfNullValueSubs().orElse(null).v(),
        		domain.getOutputMinusAsZero().value,
        		domain.getFixedValue().value,
        		domain.getValueOfFixedValue().orElse(null).v(),
        		domain.getTimeSeletion().value,
        		domain.getFixedLengthOutput().value,
        		domain.getFixedLongIntegerDigit().orElse(null).v(),
        		domain.getFixedLengthEditingMothod().value,
        		domain.getDelimiterSetting().value,
        		domain.getPreviousDayOutputMethod().value,
        		domain.getNextDayOutputMethod().value,
        		domain.getMinuteFractionDigit().orElse(null).v(),
        		domain.getDecimalSelection().value,
        		domain.getMinuteFractionDigitProcessCla().value);
    }

	public OiomtInTimeDataFmSet(OiomtInTimeDataFmSetPk inTimeDataFmSetPk, int nullValueSubs,
			String valueOfNullValueSubs, int outputMinusAsZero, int fixedValue, String valueOfFixedValue,
			int timeSeletion, int fixedLengthOutput, int fixedLongIntegerDigit, int fixedLengthEditingMothod,
			int delimiterSetting, int previousDayOutputMethod, int nextDayOutputMethod, int minuteFractionDigit,
			int decimalSelection, int minuteFractionDigitProcessCla) {
		super();
		this.inTimeDataFmSetPk = inTimeDataFmSetPk;
		this.nullValueSubs = nullValueSubs;
		this.valueOfNullValueSubs = valueOfNullValueSubs;
		this.outputMinusAsZero = outputMinusAsZero;
		this.fixedValue = fixedValue;
		this.valueOfFixedValue = valueOfFixedValue;
		this.timeSeletion = timeSeletion;
		this.fixedLengthOutput = fixedLengthOutput;
		this.fixedLongIntegerDigit = fixedLongIntegerDigit;
		this.fixedLengthEditingMothod = fixedLengthEditingMothod;
		this.delimiterSetting = delimiterSetting;
		this.previousDayOutputMethod = previousDayOutputMethod;
		this.nextDayOutputMethod = nextDayOutputMethod;
		this.minuteFractionDigit = minuteFractionDigit;
		this.decimalSelection = decimalSelection;
		this.minuteFractionDigitProcessCla = minuteFractionDigitProcessCla;
	}
    
}
