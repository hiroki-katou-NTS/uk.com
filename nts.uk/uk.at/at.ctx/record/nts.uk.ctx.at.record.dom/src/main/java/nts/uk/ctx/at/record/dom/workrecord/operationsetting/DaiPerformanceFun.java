package nts.uk.ctx.at.record.dom.workrecord.operationsetting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;

/**
* 日別実績の修正の機能
*/
@AllArgsConstructor
@Getter
public class DaiPerformanceFun extends AggregateRoot
{
    
    /**
    * 会社ID
    */
    private String cid;
    
    /**
    * コメント
    */
    private Comment comment;
    
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
    
    public static DaiPerformanceFun createFromJavaType(String cid, String comment, int isCompleteConfirmOneMonth, int isDisplayAgreementThirtySix, int isFixClearedContent, int isDisplayFlexWorker, int isUpdateBreak, int isSettingTimeBreak, int isDayBreak, int isSettingAutoTime, int isUpdateEarly, int isUpdateOvertime, int isUpdateOvertimeWithinLegal, int isFixContentAuto)
    {
        DaiPerformanceFun  daiPerformanceFun =  new DaiPerformanceFun(cid, new Comment(comment) , isCompleteConfirmOneMonth, isDisplayAgreementThirtySix, isFixClearedContent, isDisplayFlexWorker, isUpdateBreak, isSettingTimeBreak, isDayBreak, isSettingAutoTime, isUpdateEarly, isUpdateOvertime, isUpdateOvertimeWithinLegal,  isFixContentAuto);
        return daiPerformanceFun;
    }
    
}
