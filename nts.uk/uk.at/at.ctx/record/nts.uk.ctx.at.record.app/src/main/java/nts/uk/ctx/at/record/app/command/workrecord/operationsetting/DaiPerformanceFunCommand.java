package nts.uk.ctx.at.record.app.command.workrecord.operationsetting;

import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;

@Value
public class DaiPerformanceFunCommand
{
    
    /**
    * 会社ID
    */
    private String cid;
    
    /**
    * コメント
    */
    private String comment;
    
    /**
    * 1ヵ月の確認・承認が完了した場合、メッセージを表示する
    */
    private int isCompleteConfirmOneMonth;
    
    /**
    * 36協定情報を表示する
    */
    private int isDisplayAgreementThirtySix;
    
    /**
    * クリアした内容は手修正にする
    */
    private int isFixClearedContent;
    
    /**
    * フレックス勤務者のフレックス不足情報を表示する
    */
    private int isDisplayFlexWorker;
    
    /**
    * 
    */
    private int isUpdateBreak;
    
    /**
    * 
    */
    private int isSettingTimeBreak;
    
    /**
    * 休日の場合、出勤/退勤時刻をクリアにする
    */
    private int isDayBreak;
    
    /**
    * 出勤/退勤時刻を自動で設定する
    */
    private int isSettingAutoTime;
    
    /**
    * 
    */
    private int isUpdateEarly;
    
    /**
    * 
    */
    private int isUpdateOvertime;
    
    /**
    * 
    */
    private int isUpdateOvertimeWithinLegal;
    
    /**
    * 自動で設定した内容は手修正にする
    */
    private int isFixContentAuto;
    
    private Long version;

}
