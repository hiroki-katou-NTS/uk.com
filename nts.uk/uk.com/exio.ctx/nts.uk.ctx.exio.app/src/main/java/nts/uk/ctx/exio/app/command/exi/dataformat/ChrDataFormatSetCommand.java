package nts.uk.ctx.exio.app.command.exi.dataformat;

import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;

@Value
public class ChrDataFormatSetCommand
{
    
    /**
    * コード編集
    */
    private int cdEditing;
    
    /**
    * 固定値
    */
    private int fixedValue;
    
    /**
    * 有効桁長
    */
    private int effectiveDigitLength;
    
    /**
    * コード変換コード
    */
    private String cdConvertCd;
    
    /**
    * コード編集方法
    */
    private int cdEditMethod;
    
    /**
    * コード編集桁
    */
    private int cdEditDigit;
    
    /**
    * 固定値の値
    */
    private String fixedVal;
    
    /**
    * 有効桁数開始桁
    */
    private int startDigit;
    
    /**
    * 有効桁数終了桁
    */
    private int endDigit;

}
