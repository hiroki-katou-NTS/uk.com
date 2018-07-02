package nts.uk.ctx.exio.dom.exo.datafomat;

import java.util.Optional;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.shr.com.enumcommon.NotUseAtr;


/**
* 時刻型データ形式設定
*/
@Getter
public class InTimeDataFmSet extends AggregateRoot
{
    
    /**
    * 会社ID
    */
    private String cid;
    
    /**
    * NULL値置換
    */
    private NotUseAtr nullValueSubs;
    
    /**
    * NULL値置換の値
    */
    private Optional<DataFormatNullReplacement> valueOfNullValueSubs;
    
    /**
    * マイナス値を0で出力
    */
    private NotUseAtr outputMinusAsZero;
    
    /**
    * 固定値
    */
    private NotUseAtr fixedValue;
    
    /**
    * 固定値の値
    */
    private Optional<DataTypeFixedValue> valueOfFixedValue;
    
    /**
    * 時分/分選択
    */
    private HourMinuteClassification timeSeletion;
    
    /**
    * 固定長出力
    */
    private NotUseAtr fixedLengthOutput;
    
    /**
    * 固定長整数桁
    */
    private Optional<DataFormatIntegerDigit> fixedLongIntegerDigit;
    
    /**
    * 固定長編集方法
    */
    private FixedLengthEditingMethod fixedLengthEditingMothod;
    
    /**
    * 区切り文字設定
    */
    private DelimiterSetting delimiterSetting;
    
    /**
    * 前日出力方法
    */
    private PreviousDayOutputMethod previousDayOutputMethod;
    
    /**
    * 前日出力方法
    */
    private NextDayOutputMethod nextDayOutputMethod;
    
    /**
    * データ形式小数桁
    */
    private Optional<DataFormatDecimalDigit> minuteFractionDigit;
    
    /**
    * 進数選択
    */
    private DecimalSelection decimalSelection;
    
    /**
    * 分/小数処理端数区分
    */
    private Rounding minuteFractionDigitProcessCla;

	public InTimeDataFmSet(String cid, int nullValueSubs,
			String valueOfNullValueSubs, int outputMinusAsZero, int fixedValue,
			String valueOfFixedValue, int timeSeletion,
			int fixedLengthOutput, int fixedLongIntegerDigit,
			int fixedLengthEditingMothod, int delimiterSetting,
			int previousDayOutputMethod, int nextDayOutputMethod,
			int minuteFractionDigit, int decimalSelection,
			int minuteFractionDigitProcessCla) {
		super();
		this.cid = cid;
		this.nullValueSubs = EnumAdaptor.valueOf(nullValueSubs,NotUseAtr.class);
		this.valueOfNullValueSubs = Optional.of(new DataFormatNullReplacement(valueOfNullValueSubs));
		this.outputMinusAsZero = EnumAdaptor.valueOf(outputMinusAsZero,NotUseAtr.class);
		this.fixedValue = EnumAdaptor.valueOf(fixedValue,NotUseAtr.class);
		this.valueOfFixedValue = Optional.of(new DataTypeFixedValue(valueOfFixedValue));
		this.timeSeletion = EnumAdaptor.valueOf(timeSeletion,HourMinuteClassification.class);
		this.fixedLengthOutput = EnumAdaptor.valueOf(fixedLengthOutput,NotUseAtr.class);
		this.fixedLongIntegerDigit = Optional.of(new DataFormatIntegerDigit(fixedLongIntegerDigit));
		this.fixedLengthEditingMothod = EnumAdaptor.valueOf(fixedLengthEditingMothod,FixedLengthEditingMethod.class);
		this.delimiterSetting = EnumAdaptor.valueOf(delimiterSetting,DelimiterSetting.class);
		this.previousDayOutputMethod = EnumAdaptor.valueOf(previousDayOutputMethod,PreviousDayOutputMethod.class);
		this.nextDayOutputMethod = EnumAdaptor.valueOf(nextDayOutputMethod,NextDayOutputMethod.class);
		this.minuteFractionDigit = Optional.of(new DataFormatDecimalDigit(minuteFractionDigit));
		this.decimalSelection = EnumAdaptor.valueOf(decimalSelection,DecimalSelection.class);
		this.minuteFractionDigitProcessCla = EnumAdaptor.valueOf(minuteFractionDigitProcessCla,Rounding.class);
	}
    
    
}
