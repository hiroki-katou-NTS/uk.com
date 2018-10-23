package nts.uk.ctx.pr.shared.app.command.payrollgeneralpurposeparameters;

import lombok.Value;

@Value
public class SalGenDateHistoryCommand {

    /**
     * パラメータNo
     */
    private String paraNo;

    /**
     * 会社ID
     */
    private String cId;

    private int start;

    private int end;
    
    private int mode;
    

}
