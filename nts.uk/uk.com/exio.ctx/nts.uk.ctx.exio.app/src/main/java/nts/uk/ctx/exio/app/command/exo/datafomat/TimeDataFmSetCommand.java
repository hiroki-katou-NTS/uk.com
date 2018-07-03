package nts.uk.ctx.exio.app.command.exo.datafomat;

import lombok.Value;

@Value
public class TimeDataFmSetCommand
{
    
    /**
    * 会社ID
    */
    private String cid;
    
    /**
    * NULL値置換
    */
    private int nullValueSubs;
    
    /**
    * マイナス値を0で出力
    */
    private int outputMinusAsZero;
    
    /**
    * 固定値
    */
    private int fixedValue;
    
    /**
    * 固定値の値
    */
    private String valueOfFixedValue;
    
    /**
    * 固定長出力
    */
    private int fixedLengthOutput;
    
    /**
    * 固定長整数桁
    */
    private int fixedLongIntegerDigit;
    
    /**
    * 固定長編集方法
    */
    private int fixedLengthEditingMothod;
    
    /**
    * 区切り文字設定
    */
    private int delimiterSetting;
    
    /**
    * 前日出力方法
    */
    private int selectHourMinute;
    
    /**
    * データ形式小数桁
    */
    private int minuteFractionDigit;
    
    /**
    * 進数選択
    */
    private int decimalSelection;
    
    /**
    * 固定値演算符号
    */
    private int fixedValueOperationSymbol;
    
    /**
    * 固定値演算
    */
    private int fixedValueOperation;
    
    /**
    * 固定値演算値
    */
    private String fixedCalculationValue;
    
    /**
    * NULL値置換の値
    */
    private String valueOfNullValueSubs;
    
    /**
    * 分/小数処理端数区分
    */
    private int minuteFractionDigitProcessCla;
    
    private Long version;

}
