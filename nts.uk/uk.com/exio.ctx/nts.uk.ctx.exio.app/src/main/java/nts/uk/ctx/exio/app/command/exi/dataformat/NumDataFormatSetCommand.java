package nts.uk.ctx.exio.app.command.exi.dataformat;

import lombok.Value;

@Value
public class NumDataFormatSetCommand
{
    
    /**
    * 固定値
    */
    private int fixedValue;
    
    /**
    * 小数区分
    */
    private int decimalDivision;
    
    /**
    * 有効桁長
    */
    private int effectiveDigitLength;
    
    /**
    * コード変換コード
    */
    private String cdConvertCd;
    
    /**
    * 固定値の値
    */
    private String valueOfFixedValue;
    
    /**
    * 少数桁数
    */
    private int decimalDigitNum;
    
    /**
    * 有効桁数開始桁
    */
    private int startDigit;
    
    /**
    * 有効桁数終了桁
    */
    private int endDigit;
    
    /**
    * 小数点区分
    */
    private int decimalPointCls;
    
    /**
    * 小数端数
    */
    private int decimalFraction;
    

}
