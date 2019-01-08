package nts.uk.ctx.exio.app.command.exo.charegister;

import lombok.Value;


@Value
public class ChacDataFmSetCommand
{
    
    /**
    * 会社ID
    */
    private String cid;
    
    /**
    * NULL値置換
    */
    private int nullValueReplace;
    
    /**
    * NULL値置換の値
    */
    private String valueOfNullValueReplace;
    
    /**
    * コード編集
    */
    private int cdEditting;
    
    /**
    * 固定値
    */
    private int fixedValue;
    
    /**
    * コード編集方法
    */
    private int cdEdittingMethod;
    
    /**
    * コード編集桁
    */
    private Integer cdEditDigit;
    
    /**
    * コード変換コード
    */
    private String cdConvertCd;
    
    /**
    * スペース編集
    */
    private int spaceEditting;
    
    /**
    * 有効桁数
    */
    private int effectDigitLength;
    
    /**
    * 有効桁数開始桁
    */
    private Integer startDigit;
    
    /**
    * 有効桁数終了桁
    */
    private Integer endDigit;
    
    /**
    * 固定値の値
    */
    private String valueOfFixedValue;
    
    private Long version;

}
