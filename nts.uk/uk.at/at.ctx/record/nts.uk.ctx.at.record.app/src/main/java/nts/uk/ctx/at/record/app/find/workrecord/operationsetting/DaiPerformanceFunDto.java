package nts.uk.ctx.at.record.app.find.workrecord.operationsetting;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.DaiPerformanceFun;

/**
* 日別実績の修正の機能
*/
@AllArgsConstructor
@Value
public class DaiPerformanceFunDto
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
    
    
    public static DaiPerformanceFunDto fromDomain(DaiPerformanceFun domain)
    {
        return new DaiPerformanceFunDto(domain.getCid(), domain.getComment().toString() ,domain.getIsCompleteConfirmOneMonth(), domain.getIsDisplayAgreementThirtySix(), domain.getIsFixClearedContent(), domain.getIsDisplayFlexWorker(), domain.getIsUpdateBreak(), domain.getIsSettingTimeBreak(), domain.getIsDayBreak(), domain.getIsSettingAutoTime(), domain.getIsUpdateEarly(), domain.getIsUpdateOvertime(), domain.getIsUpdateOvertimeWithinLegal(), domain.getIsFixContentAuto());
    }
    
}
