package nts.uk.ctx.pr.shared.app.command.payrollgeneralpurposeparameters;

import lombok.Value;

@Value
public class SalGenParaYeahMonthValueCommand
{
    
    /**
    * パラメータNo
    */
    private String paraNo;

    private String startTime;

    private String endTime;

    private int modeHistory;

    private SalGenParaValueCommand mSalGenParaValueCommand;



}
