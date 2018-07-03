package nts.uk.ctx.exio.app.command.exo.datafomat;

import lombok.Value;

@Value
public class InTimeDataFmSetCommand
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
    * NULL値置換の値
    */
    private String valueOfNullValueSubs;
    
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
    * 時分/分選択
    */
    private int timeSeletion;
    
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
    private int previousDayOutputMethod;
    
    /**
    * 前日出力方法
    */
    private int nextDayOutputMethod;
    
    /**
    * データ形式小数桁
    */
    private int minuteFractionDigit;
    
    /**
    * 進数選択
    */
    private int decimalSelection;
    
    /**
    * 分/小数処理端数区分
    */
    private int minuteFractionDigitProcessCla;
    
    private Long version;

}
