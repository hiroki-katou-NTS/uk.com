package nts.uk.ctx.pr.shared.app.command.payrollgeneralpurposeparameters;

import lombok.Value;
import nts.arc.time.GeneralDate;

@Value
public class SalGenYearMonthHistoryCommand {

    private String paraNo;

    private String cId;

    private String hisId;

    private Integer start;

    private Integer end;
    
    private int mode;
    

}
