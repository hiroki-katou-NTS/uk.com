package nts.uk.ctx.exio.dom.exo.dataformat.init;

import java.math.BigDecimal;
import java.util.Optional;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.exio.dom.exo.base.ItemType;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
* 時間型データ形式設定
*/
@Getter
public class TimeDataFmSet extends DataFormatSetting
{
    
    /**
    * 会社ID
    */
    private String cid;
    
    /**
    * マイナス値を0で出力する
    */
    private NotUseAtr outputMinusAsZero;
    
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
    private FixedLengthEditingMethod fixedLengthEditingMethod;
    
    /**
    * 区切り文字設定
    */
    private DelimiterSetting delimiterSetting;
    
    /**
    * 時分/分選択
    */
    private HourMinuteClassification selectHourMinute;
    
    /**
    * 分/小数処理桁
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
    private Optional<DataFormatFixedValueOperation> fixedCalculationValue;
    
    /**
    * 分/小数処理端数区分
    */
    private Rounding minuteFractionDigitProcessCls;

	public TimeDataFmSet(int itemType, String cid, int nullValueSubs, int outputMinusAsZero, int fixedValue,
			String valueOfFixedValue, int fixedLengthOutput, Integer fixedLongIntegerDigit, int fixedLengthEditingMethod,
			int delimiterSetting, int selectHourMinute, Integer minuteFractionDigit, int decimalSelection,
			int fixedValueOperationSymbol, int fixedValueOperation, BigDecimal fixedCalculationValue,
			String valueOfNullValueSubs, int minuteFractionDigitProcessCls) {
		super(itemType, fixedValue, valueOfFixedValue, nullValueSubs, valueOfNullValueSubs);
		this.cid = cid;
		this.outputMinusAsZero = EnumAdaptor.valueOf(outputMinusAsZero, NotUseAtr.class);
		this.fixedLengthOutput = EnumAdaptor.valueOf(fixedLengthOutput, NotUseAtr.class);
		this.fixedLongIntegerDigit = Optional.ofNullable((fixedLongIntegerDigit != null) ? new DataFormatIntegerDigit(fixedLongIntegerDigit) : null);
		this.fixedLengthEditingMethod = EnumAdaptor.valueOf(fixedLengthEditingMethod, FixedLengthEditingMethod.class);
		this.delimiterSetting = EnumAdaptor.valueOf(delimiterSetting, DelimiterSetting.class);
		this.selectHourMinute = EnumAdaptor.valueOf(selectHourMinute, HourMinuteClassification.class);
		this.minuteFractionDigit = Optional.ofNullable((minuteFractionDigit != null) ? new DataFormatDecimalDigit(minuteFractionDigit) : null);
		this.decimalSelection = EnumAdaptor.valueOf(decimalSelection, DecimalSelection.class);
		this.fixedValueOperationSymbol = EnumAdaptor.valueOf(fixedValueOperationSymbol,
				FixedValueOperationSymbol.class);
		this.fixedValueOperation = EnumAdaptor.valueOf(fixedValueOperation, NotUseAtr.class);
		this.fixedCalculationValue = Optional.ofNullable((fixedCalculationValue != null) ?  new DataFormatFixedValueOperation(fixedCalculationValue) : null);
		this.minuteFractionDigitProcessCls = EnumAdaptor.valueOf(minuteFractionDigitProcessCls, Rounding.class);
	}

	public TimeDataFmSet(ItemType itemType, String cid, NotUseAtr nullValueSubs, NotUseAtr outputMinusAsZero,
			NotUseAtr fixedValue, Optional<DataTypeFixedValue> valueOfFixedValue, NotUseAtr fixedLengthOutput,
			Optional<DataFormatIntegerDigit> fixedLongIntegerDigit, FixedLengthEditingMethod fixedLengthEditingMethod,
			DelimiterSetting delimiterSetting, HourMinuteClassification selectHourMinute,
			Optional<DataFormatDecimalDigit> minuteFractionDigit, DecimalSelection decimalSelection,
			FixedValueOperationSymbol fixedValueOperationSymbol, NotUseAtr fixedValueOperation,
			Optional<DataFormatFixedValueOperation> fixedCalculationValue,
			Optional<DataFormatNullReplacement> valueOfNullValueSubs, Rounding minuteFractionDigitProcessCls) {
		super(itemType, fixedValue, valueOfFixedValue, nullValueSubs, valueOfNullValueSubs);
		this.cid = cid;
		this.outputMinusAsZero = outputMinusAsZero;
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
		this.minuteFractionDigitProcessCls = minuteFractionDigitProcessCls;
	}

}
