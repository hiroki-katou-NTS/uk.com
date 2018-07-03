package nts.uk.ctx.exio.app.command.exo.datafomat;

import lombok.Value;

@Value
public class DateFormatSetCommand
{
    
    /**
    * 会社ID
    */
    private String cid;
    
    /**
    * NULL値置換
    */
    private int nullValueSubstitution;
    
    /**
    * 固定値
    */
    private int fixedValue;
    
    /**
    * 固定値の値
    */
    private String valueOfFixedValue;
    
    /**
    * 小数桁
    */
    private String valueOfNullValueSubs;
    
    /**
    * 形式選択
    */
    private int formatSelection;
    
    private Long version;

}
