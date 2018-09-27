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
    private int monthChkMsgAtr;
    
    /**
    * 36協定情報を表示する
    */
    private int disp36Atr;
    
    /**
    * クリアした内容は手修正にする
    */
    private int clearManuAtr;
    
    /**
    * フレックス勤務者のフレックス不足情報を表示する
    */
    private int flexDispAtr;
    
    /**
    * 休出計算区分を変更する場合、休出深夜計算区分を変更する
    */
    private int breakCalcUpdAtr;
    
    /**
    * 休憩時刻を自動で設定する
    */
    private int breakTimeAutoAtr;
    
    /**
    * 早出計算区分を変更する場合、早出残業深夜計算区分を変更する
    */
    private int ealyCalcUpdAtr;
    
    /**
    * 残業計算区分を変更する場合、残業深夜区分を変更する
    */
    private int overtimeCalcUpdAtr;
    
    /**
    * 法定内残業計算区分を変更する場合、法定内深夜残業計算区分を変更する
    */
    private int lawOverCalcUpdAtr;
    
    /**
    * 自動で設定した内容は手修正にする
    */
    private int manualFixAutoSetAtr;
    
    /**
     * 抽出時にエラーがある場合はエラー参照ダイアログを表示する
     * 
     */
    private int checkErrRefDisp;
    
    
    public static DaiPerformanceFunDto fromDomain(DaiPerformanceFun domain)
    {
        return new DaiPerformanceFunDto(domain.getCid(), domain.getComment().toString(), 
						        		domain.getMonthChkMsgAtr(), 
						        		domain.getDisp36Atr(), 
						        		domain.getClearManuAtr(), 
						        		domain.getFlexDispAtr(), 
						        		domain.getBreakCalcUpdAtr(), 
						        		domain.getBreakTimeAutoAtr(), 
						        		domain.getEalyCalcUpdAtr(), 
						        		domain.getOvertimeCalcUpdAtr(), 
						        		domain.getLawOverCalcUpdAtr(), 
						        		domain.getManualFixAutoSetAtr(),
						        		domain.getCheckErrRefDisp());
    }
    
}
