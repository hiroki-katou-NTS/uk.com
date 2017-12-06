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
    private int excludeHolidayAtr;
    
    /**
    * 勤務を変更する
    */
    private int workChangeAtr;
    
    /**
    * 勤務直行1
    */
    private int goWorkAtr1;
    
    /**
    * 勤務直帰1
    */
    private int backHomeAtr1;
    
    /**
    * 休憩時間開始1
    */
    private int breakTimeStart1;
    
    /**
    * 休憩時間終了1
    */
    private int breakTimeEnd1;
    
    /**
    * 勤務時間開始1
    */
    private int workTimeStart1;
    
    /**
    * 勤務時間終了1
    */
    private int workTimeEnd1;
    
    /**
    * 勤務時間開始2
    */
    private int workTimeStart2;
    
    /**
    * 勤務時間終了2
    */
    private int workTimeEnd2;
    
    /**
    * 勤務直行2
    */
    private int goWorkAtr2;
    
    /**
    * 勤務直帰2
    */
    private int backHomeAtr2;
    
    private Long version;
}
