package nts.uk.ctx.pr.shared.app.command.payrollgeneralpurposeparameters;

import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;

@Value
public class SalGenParaValueCommand
{
    
    /**
    * 履歴ID
    */
    private String historyId;
    
    /**
    * 選択肢
    */
    private Integer selection;
    
    /**
    * 有効区分
    */
    private int availableAtr;
    
    /**
    * 値（数値）
    */
    private String numValue;
    
    /**
    * 値（文字）
    */
    private String charValue;
    
    /**
    * 値（時間）
    */
    private Integer timeValue;
    
    /**
    * 対象区分
    */
    private Integer targetAtr;

    private Integer modeScreen;
    

}
