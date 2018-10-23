package nts.uk.ctx.pr.shared.app.command.payrollgeneralpurposeparameters;

import lombok.Value;
import nts.arc.time.GeneralDate;

@Value
public class SalGenYearMonthHistoryCommand {

    /**
     * パラメータNo
     */
    private String paraNo;

    /**
     * 会社ID
     */
    private String cId;

    private String hisId;

    private Integer start;

    private Integer end;
    
    private int mode;
    

}
