package nts.uk.ctx.pr.core.app.command.salary;

import lombok.Value;
import nts.arc.time.GeneralDate;

@Value
public class SetDaySupportCommand
{
    
    /**
    * 会社ID
    */
    private String cid;
    
    /**
    * 処理区分NO
    */
    private int processCateNo;
    
    /**
    * 勤怠締め日
    */
    private GeneralDate closeDateTime;
    
    /**
    * 雇用保険基準日
    */
    private GeneralDate empInsurdStanDate;
    
    /**
    * 経理締め年月日
    */
    private GeneralDate closureDateAccounting;
    
    /**
    * 支払年月日
    */
    private GeneralDate paymentDate;
    
    /**
    * 社員抽出基準日
    */
    private GeneralDate empExtraRefeDate;
    
    /**
    * 社会保険基準日
    */
    private GeneralDate socialInsurdStanDate;
    
    /**
    * 社会保険徴収年月
    */
    private int socialInsurdCollecMonth;
    
    /**
    * 処理年月
    */
    private int processDate;
    
    /**
    * 所得税基準日
    */
    private GeneralDate incomeTaxDate;
    
    /**
    * 要勤務日数
    */
    private String numberWorkDay;
    
    private Long version;

}
