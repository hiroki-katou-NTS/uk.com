package nts.uk.ctx.at.request.app.command.application.workchange;

import lombok.Value;

@Value
public class AppWorkChangeCommand
{
    
    /**
    * 会社ID
    */
    private String cid;
    
    /**
    * 申請ID
    */
    private String appId;
    
    /**
    * 勤務種類コード
    */
    private String workTypeCd;
    
    /**
    * 就業時間帯コード
    */
    private String workTimeCd;
    
    /**
    * 休日を除外する
    */
    private Integer excludeHolidayAtr;
    
    /**
    * 勤務を変更する
    */
    private Integer workChangeAtr;
    
    /**
    * 勤務直行1
    */
    private Integer goWorkAtr1;
    
    /**
    * 勤務直帰1
    */
    private Integer backHomeAtr1;
    
    /**
    * 休憩時間開始1
    */
    private Integer breakTimeStart1;
    
    /**
    * 休憩時間終了1
    */
    private Integer breakTimeEnd1;
    
    /**
    * 勤務時間開始1
    */
    private Integer workTimeStart1;
    
    /**
    * 勤務時間終了1
    */
    private Integer workTimeEnd1;
    
    /**
    * 勤務時間開始2
    */
    private Integer workTimeStart2;
    
    /**
    * 勤務時間終了2
    */
    private Integer workTimeEnd2;
    
    /**
    * 勤務直行2
    */
    private Integer goWorkAtr2;
    
    /**
    * 勤務直帰2
    */
    private Integer backHomeAtr2;
    
    /**
     * 勤務種類名
     */
    private String workTypeName;
    
    /**
     * 就業時間帯名
     */
    private String workTimeName;
    
    private Long version;
}
