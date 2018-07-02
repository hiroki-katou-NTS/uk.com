package nts.uk.ctx.exio.dom.exo.datafomat;

import java.util.Optional;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
* 時間型データ形式設定
*/
@Getter
public class TimeDataFmSet extends AggregateRoot
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
    private HourMinuteClassification selectHourMinute;
    
    /**
    * データ形式小数桁
    */
    private Optional<DataFormatDecimalDigit> minuteFractionDigit;
    
    /**
    * 進数選択
    */
    private DecimalSelection decimalSelection;
    
    /**
    * 固定値演算符号
    */
    private FixedValueOperationSymbol fixedValueOperationSymbol;
    
    /**
    * 固定値演算
    */
    private NotUseAtr fixedValueOperation;
    
    /**
    * 固定値演算値
    */
    private String fixedCalculationValue;
    
    /**
    * NULL値置換の値
    */
    private Optional<DataFormatNullReplacement> valueOfNullValueSubs;
    
    /**
    * 分/小数処理端数区分
    */
    private Rounding minuteFractionDigitProcessCla;

	public TimeDataFmSet(String cid, int nullValueSubs, int outputMinusAsZero, int fixedValue,
			String valueOfFixedValue, int fixedLengthOutput,
			int fixedLongIntegerDigit, int fixedLengthEditingMothod,int delimiterSetting, 
			int selectHourMinute, int minuteFractionDigit, int decimalSelection, int fixedValueOperationSymbol, 
			int fixedValueOperation, String fixedCalculationValue, String valueOfNullValueSubs,int minuteFractionDigitProcessCla) {
		super();
		this.cid = cid;
		this.nullValueSubs = EnumAdaptor.valueOf(nullValueSubs, NotUseAtr.class);
		this.outputMinusAsZero = EnumAdaptor.valueOf(outputMinusAsZero, NotUseAtr.class);
		this.fixedValue = EnumAdaptor.valueOf(fixedValue, NotUseAtr.class);
		this.valueOfFixedValue = Optional.of(new DataTypeFixedValue (valueOfFixedValue));
		this.fixedLengthOutput = EnumAdaptor.valueOf(fixedLengthOutput, NotUseAtr.class);
		this.fixedLongIntegerDigit = Optional.of(new DataFormatIntegerDigit(fixedLongIntegerDigit));
		this.fixedLengthEditingMothod = EnumAdaptor.valueOf(fixedLengthEditingMothod, FixedLengthEditingMethod.class);
		this.delimiterSetting = EnumAdaptor.valueOf(delimiterSetting,DelimiterSetting.class);
		this.selectHourMinute = EnumAdaptor.valueOf(selectHourMinute, HourMinuteClassification.class);
		this.minuteFractionDigit = Optional.of(new DataFormatDecimalDigit (minuteFractionDigit));
		this.decimalSelection = EnumAdaptor.valueOf(decimalSelection,DecimalSelection.class);
		this.fixedValueOperationSymbol = EnumAdaptor.valueOf(fixedValueOperationSymbol,FixedValueOperationSymbol.class);
		this.fixedValueOperation = EnumAdaptor.valueOf(fixedValueOperation,NotUseAtr.class);
		this.fixedCalculationValue = fixedCalculationValue;
		this.valueOfNullValueSubs = Optional.of(new DataFormatNullReplacement(valueOfNullValueSubs));
		this.minuteFractionDigitProcessCla = EnumAdaptor.valueOf(minuteFractionDigitProcessCla,Rounding.class);
	}
    
    
}
