package nts.uk.ctx.exio.dom.exo.datafomat;

import java.math.BigDecimal;
import java.util.Optional;


import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
* 数値型データ形式設定
*/
@Getter
public class NumberDataFmSet extends AggregateRoot
{
    
    /**
    * 会社ID
    */
    private String cid;
    
    /**
    * NULL値置換
    */
    private NotUseAtr nullValueReplace;
    
    /**
    * NULL値置換の値
    */
    private Optional<DataFormatNullReplacement> valueOfNullValueReplace;
    
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
    * 固定値演算
    */
    private NotUseAtr fixedValueOperation;
    
    /**
    * 固定値演算値
    */
    private Optional<DataFormatFixedValue> fixedCalculationValue;
    
    /**
    * 固定値演算符号
    */
    private FixedValueOperationSymbol fixedValueOperationSymbol;
    
    /**
    * 固定長出力
    */
    private NotUseAtr fixedLengthOutput;
    
    /**
    * 固定長整数桁
    */
    private Optional<DataFormatIntegerDigit> fixedLengthIntegerDigit;
    
    /**
    * 固定長編集方法
    */
    private FixedLengthEditingMethod fixedLengthEditingMethod;
    
    /**
    * 小数桁
    */
    private Optional<DataFormatDecimalDigit> decimalDigit;
    
    /**
    * 小数点区分
    */
    private DecimalPointClassification decimalPointClassification;
    
    /**
    * 小数端数
    */
    private Rounding decimalFraction;
    
    /**
    * 形式選択
    */
    private DateOutputFormat formatSelection;

	public NumberDataFmSet(String cid, int nullValueReplace,
			String valueOfNullValueReplace, int outputMinusAsZero, int fixedValue,
			String valueOfFixedValue, int fixedValueOperation, BigDecimal fixedCalculationValue,
			int fixedValueOperationSymbol, int fixedLengthOutput,
			int fixedLengthIntegerDigit, int fixedLengthEditingMethod, int decimalDigit,
			int decimalPointClassification, int decimalFraction,
			int formatSelection) {
		this.cid = cid;
		this.nullValueReplace = EnumAdaptor.valueOf(nullValueReplace, NotUseAtr.class);
		this.valueOfNullValueReplace = Optional.of(new DataFormatNullReplacement(valueOfNullValueReplace));
		this.outputMinusAsZero = EnumAdaptor.valueOf(outputMinusAsZero, NotUseAtr.class);
		this.fixedValue = EnumAdaptor.valueOf(fixedValue, NotUseAtr.class);
		this.valueOfFixedValue = Optional.of(new DataTypeFixedValue(valueOfFixedValue));
		this.fixedValueOperation = EnumAdaptor.valueOf(fixedValueOperation, NotUseAtr.class);
		this.fixedCalculationValue = Optional.of(new DataFormatFixedValue(fixedCalculationValue));
		this.fixedValueOperationSymbol = EnumAdaptor.valueOf(fixedValueOperationSymbol, FixedValueOperationSymbol.class);
		this.fixedLengthOutput = EnumAdaptor.valueOf(fixedLengthOutput, NotUseAtr.class);
		this.fixedLengthIntegerDigit = Optional.of(new DataFormatIntegerDigit (fixedLengthIntegerDigit));
		this.fixedLengthEditingMethod = EnumAdaptor.valueOf(fixedLengthEditingMethod,FixedLengthEditingMethod.class);
		this.decimalDigit = Optional.of(new DataFormatDecimalDigit(decimalDigit));
		this.decimalPointClassification = EnumAdaptor.valueOf(decimalPointClassification,DecimalPointClassification.class) ;
		this.decimalFraction = EnumAdaptor.valueOf(decimalFraction,Rounding.class);
		this.formatSelection = EnumAdaptor.valueOf(formatSelection,DateOutputFormat.class);
	}
    
    
}
