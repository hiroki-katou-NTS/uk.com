package nts.uk.ctx.at.function.app.command.annualworkschedule;

import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;

@Value
public class CalcFormulaItemCommand
{
    
    /**
    * 会社ID
    */
    private String cid;
    
    /**
    * コード
    */
    private String setOutCd;
    
    /**
    * コード
    */
    private String itemOutCd;
    
    /**
    * 勤怠項目ID
    */
    private int attendanceItemId;
    
    /**
    * オペレーション
    */
    private int operation;
    
    private Long version;

}
