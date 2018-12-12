package nts.uk.ctx.pr.shared.app.command.payrollgeneralpurposeparameters;

import lombok.Value;
import nts.arc.time.GeneralDate;

@Value
public class SalGenDateHistoryCommand {

    private String paraNo;

    private String cId;

    private GeneralDate start;

    private GeneralDate end;
    
    private int mode;

    private String hisId;
    

}
