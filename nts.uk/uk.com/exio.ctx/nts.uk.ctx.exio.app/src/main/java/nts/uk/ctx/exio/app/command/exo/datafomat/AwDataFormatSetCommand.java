package nts.uk.ctx.exio.app.command.exo.datafomat;

import lombok.Value;


@Value
public class AwDataFormatSetCommand
{
    
    /**
    * 会社ID
    */
    private String cid;
    
    /**
    * 休業時出力
    */
    private String closedOutput;
    
    /**
    * 休職時出力
    */
    private String absenceOutput;
    
    /**
    * 固定値
    */
    private int fixedValue;
    
    /**
    * 固定値の値
    */
    private String valueOfFixedValue;
    
    /**
    * 在職時出力
    */
    private String atWorkOutput;
    
    /**
    * 退職時出力
    */
    private String retirementOutput;
    
    private Long version;

}
