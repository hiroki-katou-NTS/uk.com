package nts.uk.ctx.pr.shared.app.command.payrollgeneralpurposeparameters;

import lombok.Value;

@Value
public class SalGenParaDateValueCommand {
    /**
     * パラメータNo
     */
    private String paraNo;

    /**
     * 開始日
     */
    private int startDate;

    /**
     * 終了日
     */
    private int endDate;

    private  SalGenParaValueCommand mSalGenParaValueCommand;
}
